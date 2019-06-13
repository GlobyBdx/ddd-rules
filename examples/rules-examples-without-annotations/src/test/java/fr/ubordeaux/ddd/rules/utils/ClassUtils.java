package fr.ubordeaux.ddd.rules.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaAccess;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

import fr.ubordeaux.ddd.tests.utils.ArchitectureImportUtils;

public class ClassUtils {
	final public static String[] ANNOTATIONS = {"ValueObject", "Entity", "Aggregate", "Factory", "Repository", "Service"};

	final public static Map<String, Set<String>> annotatedClasses = new HashMap<>();

	private static String formatMessage(JavaClass item, String message) {
		return String.format("%s %s", item.getDescription(), message);
	}

	protected static boolean isClassAnnotatedWith(JavaClass owner, String annotation) {
		if (annotatedClasses.isEmpty()) {
			ArchitectureImportUtils.importArchitecture();
		}
		return annotatedClasses.get(annotation).contains(owner.getFullName());
	}

	public static DescribedPredicate<JavaClass> areAnnotatedWith(String annotation) {
		return new DescribedPredicate<JavaClass>("are annotated with @" + annotation) {
			@Override
			public boolean apply(JavaClass item) {
				return isClassAnnotatedWith(item, annotation);
			}
		};
	}

	public static DescribedPredicate<JavaClass> areNotAnnotatedWith(String annotation) {
		return new DescribedPredicate<JavaClass>("are not annotated with @" + annotation) {
			@Override
			public boolean apply(JavaClass item) {
				return !isClassAnnotatedWith(item, annotation);
			}
		};
	}

	public static ArchRule shouldResideInAPackage(String annotation, String name) {
		return classes()
				.that(areAnnotatedWith(annotation))
				.should().resideInAPackage(name)
				.as("Classes annotated with @" + annotation + " should reside in a package named '" + name + "'");
	}

	private static boolean isInAPackageAnnotatedWith(JavaClass item, String annotation) {
		for (String annotatedPackage : PackageUtils.getAllPackagesAnnotatedWith(annotation)) {
			if (item.getPackageName().startsWith(annotatedPackage)) {
				return true;
			}
		}
		return false;
	}

	public static ArchCondition<JavaClass> resideInAPackageAnnotatedWith(String annotation) {
		return new ArchCondition<JavaClass>("reside in a package annotated with @" + annotation) {
			@Override
			public void check(JavaClass item, ConditionEvents events) {
				if (isInAPackageAnnotatedWith(item, annotation)) {
					String message = formatMessage(item, "does reside in a package annotated with @" + annotation);
					events.add(SimpleConditionEvent.satisfied(item, message));
					return;
				}
				String message = formatMessage(item, "does not reside in a package annotated with @" + annotation);
				events.add(SimpleConditionEvent.violated(item, message));
			}
		};
	}

	public static ArchRule shouldResideInAPackageAnnotatedWith(String annotation, String packageAnnotation) {
		return classes()
				.that(areAnnotatedWith(annotation))
				.should(resideInAPackageAnnotatedWith(packageAnnotation))
				.as("Classes annotated with @" + annotation + " should reside in a package annotated with @" + packageAnnotation);
	}

	public static ArchCondition<JavaClass> beAnnotatedWith(String annotation) {
		return new ArchCondition<JavaClass>("be annotated with @" + annotation) {
			@Override
			public void check(JavaClass item, ConditionEvents events) {
				if (isClassAnnotatedWith(item, annotation)) {
					String message = formatMessage(item, "is annotated with @" + annotation);
					events.add(SimpleConditionEvent.satisfied(item, message));
					return;
				}
				String message = formatMessage(item, "is not annotated with @" + annotation);
				events.add(SimpleConditionEvent.violated(item, message));
			}
		};
	}

	public static ArchCondition<JavaClass> notBeAnnotatedWith(String annotation) {
		return new ArchCondition<JavaClass>("not be annotated with @" + annotation) {
			@Override
			public void check(JavaClass item, ConditionEvents events) {
				if (!isClassAnnotatedWith(item, annotation)) {
					String message = formatMessage(item, "is not annotated with @" + annotation);
					events.add(SimpleConditionEvent.satisfied(item, message));
					return;
				}
				String message = formatMessage(item, "is annotated with @" + annotation);
				events.add(SimpleConditionEvent.violated(item, message));
			}
		};
	}

	public static ArchRule shouldNotBeAnnotatedWith(String annotation, String anotherAnnotation) {
		return classes()
				.that(areAnnotatedWith(annotation))
				.should(notBeAnnotatedWith(anotherAnnotation))
				.as("Classes annotated with @" + annotation + " should not also be annotated with @" + anotherAnnotation);
	}

	public static ArchCondition<JavaClass> haveAtLeastOneFieldAnnotatedWith(String annotation) {
		return new ArchCondition<JavaClass>("have at least one field" + ((annotation != null && annotation.compareTo("") != 0) ? " annotated with @" + annotation : "")) {
			@Override
			public void check(JavaClass item, ConditionEvents events) {
				Set<JavaField> fields = item.getFields();
				if (fields.isEmpty()) {
					String message = formatMessage(item, "does not have at least one field" + ((annotation != null && annotation.compareTo("") != 0) ? " annotated with @" + annotation : ""));
					events.add(SimpleConditionEvent.violated(item, message));
					return;
				}
				if (annotation == null) {
					String message = formatMessage(item, "does have at least one field");
					events.add(SimpleConditionEvent.satisfied(item, message));
					return;
				}
				for (JavaField field : fields) {
					if (FieldUtils.isFieldAnnotatedWith(field, annotation)) {
						String message = formatMessage(item, "does have at least one field annotated with @" + annotation);
						events.add(SimpleConditionEvent.satisfied(item, message));
						return;
					}
				}
				String message = formatMessage(item, "does not have at least one field annotated with @" + annotation);
				events.add(SimpleConditionEvent.violated(item, message));
			}
		};
	}

	final public static ArchCondition<JavaClass> haveAtLeastOneField = haveAtLeastOneFieldAnnotatedWith(null);

	private static void overrideObjectClassEqualsMethodAccessingFields(JavaClass item, ConditionEvents events, Set<JavaField> fields) {
		for (JavaMethod method : item.getMethods()) {
			if (MethodUtils.isObjectClassEqualsMethod(method)) {
				if (fields.isEmpty() || FieldUtils.areFieldsAccessedByMethod(fields, method)) {
					String message = formatMessage(item, "does override correctly Object.class equals method");
					events.add(SimpleConditionEvent.satisfied(item, message));
					return;
				}
				break;
			}
		}
		String message = formatMessage(item, "does not override correctly Object.class equals method");
		events.add(SimpleConditionEvent.violated(item, message));
	}

	final public static ArchCondition<JavaClass> overrideObjectClassEqualsMethodAccessingAllFields =
			new ArchCondition<JavaClass>("override Object.class equals method accessing all fields") {
		@Override
		public void check(JavaClass item, ConditionEvents events) {
			overrideObjectClassEqualsMethodAccessingFields(item, events, item.getFields());
		}
	};

	final public static ArchCondition<JavaClass> overrideObjectClassEqualsMethodAccessingAllEntityIDs =
			new ArchCondition<JavaClass>("override Object.class equals method accessing all entity IDs") {
		@Override
		public void check(JavaClass item, ConditionEvents events) {
			overrideObjectClassEqualsMethodAccessingFields(item, events, FieldUtils.getFieldsAnnotatedWith(item.getFields(), "EntityID"));
		}
	};

	private static void overrideObjectClassHashCodeMethodAccessingFields(JavaClass item, ConditionEvents events, Set<JavaField> fields) {
		for (JavaMethod method : item.getMethods()) {
			if (MethodUtils.isObjectClassHashCodeMethod(method)) {
				if (fields.isEmpty() || FieldUtils.areFieldsAccessedByMethod(fields, method)) {
					String message = formatMessage(item, "does override correctly Object.class hashCode method");
					events.add(SimpleConditionEvent.satisfied(item, message));
					return;
				}
				break;
			}
		}
		String message = formatMessage(item, "does not override correctly Object.class hashCode method");
		events.add(SimpleConditionEvent.violated(item, message));
	}

	final public static ArchCondition<JavaClass> overrideObjectClassHashCodeMethodAccessingAllFields =
			new ArchCondition<JavaClass>("override Object.class hashCode method accessing all fields") {
		@Override
		public void check(JavaClass item, ConditionEvents events) {
			overrideObjectClassHashCodeMethodAccessingFields(item, events, item.getFields());
		}
	};

	final public static ArchCondition<JavaClass> overrideObjectClassHashCodeMethodAccessingAllEntityIDs =
			new ArchCondition<JavaClass>("override Object.class hashCode method accessing all entity IDs") {
		@Override
		public void check(JavaClass item, ConditionEvents events) {
			overrideObjectClassHashCodeMethodAccessingFields(item, events, FieldUtils.getFieldsAnnotatedWith(item.getFields(), "EntityID"));
		}
	};

	private static void overrideObjectClassToStringMethodAccessingFields(JavaClass item, ConditionEvents events, Set<JavaField> fields) {
		for (JavaMethod method : item.getMethods()) {
			if (MethodUtils.isObjectClassToStringMethod(method)) {
				if (fields.isEmpty() || FieldUtils.areFieldsAccessedByMethod(fields, method)) {
					String message = formatMessage(item, "does override correctly Object.class toString method");
					events.add(SimpleConditionEvent.satisfied(item, message));
					return;
				}
				break;
			}
		}
		String message = formatMessage(item, "does not override correctly Object.class toString method");
		events.add(SimpleConditionEvent.violated(item, message));
	}

	final public static ArchCondition<JavaClass> overrideObjectClassToStringMethodAccessingAllFields =
			new ArchCondition<JavaClass>("override Object.class toString method accessing all fields") {
		@Override
		public void check(JavaClass item, ConditionEvents events) {
			overrideObjectClassToStringMethodAccessingFields(item, events, item.getFields());
		}
	};

	final public static ArchCondition<JavaClass> overrideObjectClassToStringMethodAccessingAllEntityIDs =
			new ArchCondition<JavaClass>("override Object.class toString method accessing all entity IDs") {
		@Override
		public void check(JavaClass item, ConditionEvents events) {
			overrideObjectClassToStringMethodAccessingFields(item, events, FieldUtils.getFieldsAnnotatedWith(item.getFields(), "EntityID"));
		}
	};

	public static ArchCondition<JavaClass> accessAtLeastOneMethodFromAClassAnnotatedWith(String annotation, String name) {
		return new ArchCondition<JavaClass>("access at least one class annotated with @" + annotation + ((name != null && name.compareTo("") != 0) ? " and its method " + name : "")) {
			@Override
			public void check(JavaClass item, ConditionEvents events) {
				for (JavaAccess<?> access : item.getAccessesFromSelf()) {
					if (!access.getTargetOwner().equals(item) && isClassAnnotatedWith(access.getTargetOwner(), annotation)
							&& (name == null || name.compareTo("") == 0 || access.getName().compareTo(name) == 0)) {
						String message = formatMessage(item, "does access at least one class annotated with @" + annotation + ((name != null && name.compareTo("") != 0) ? " and its method " + name : ""));
						events.add(SimpleConditionEvent.satisfied(item, message));
						return;
					}
				}
				String message = formatMessage(item, "does not access at least one class annotated with @" + annotation + ((name != null && name.compareTo("") != 0) ? " and its method " + name : ""));
				events.add(SimpleConditionEvent.violated(item, message));
			}
		};
	}

	public static ArchCondition<JavaClass> accessAtLeastOneConstructorFromAClassAnnotatedWith(String annotation) {
		return accessAtLeastOneMethodFromAClassAnnotatedWith(annotation, "<init>");
	}

	public static ArchCondition<JavaClass> accessAtLeastOneClassAnnotatedWith(String annotation) {
		return accessAtLeastOneMethodFromAClassAnnotatedWith(annotation, null);
	}

	final public static ArchCondition<JavaClass> havePublicAndProtectedMethodsWithTheSameReturnType =
			new ArchCondition<JavaClass>("have public and protected methods with the same return type") {
		@Override
		public void check(JavaClass item, ConditionEvents events) {
			JavaClass type = null;
			for (JavaMethod method : item.getMethods()) {
				if (method.getModifiers().contains(JavaModifier.PUBLIC) || method.getModifiers().contains(JavaModifier.PROTECTED)) {
					if (type == null) {
						type = method.getRawReturnType();
					}
					if (!method.getRawReturnType().equals(type)) {
						String message = formatMessage(item, "does not have public and protected methods with the same return type");
						events.add(SimpleConditionEvent.violated(item, message));
						return;
					}
				}
			}
			if (type != null) {
				String message = formatMessage(item, "does have public and protected methods with the same return type");
				events.add(SimpleConditionEvent.satisfied(item, message));
				return;
			}
			String message = formatMessage(item, "does not have any public or protected method");
			events.add(SimpleConditionEvent.violated(item, message));
		}
	};

	private static void implementAnInterfaceInALayerAnnotatedWith(JavaClass item, ConditionEvents events, String annotation) {
		for (JavaClass temporaryInterface : item.getInterfaces()) {
			if (isInAPackageAnnotatedWith(temporaryInterface, annotation)) {
				String message = formatMessage(item, "does implement an interface in a layer annotated with @" + annotation);
				events.add(SimpleConditionEvent.satisfied(item, message));
				return;
			}
		}
		String message = formatMessage(item, "does not implement an interface in a layer annotated with @" + annotation);
		events.add(SimpleConditionEvent.violated(item, message));
	}

	public static ArchCondition<JavaClass> implementAnInterfaceInALayerAnnotatedWith(String annotation) {
		return new ArchCondition<JavaClass>("implement an interface in a layer annotated with @" + annotation) {
			@Override
			public void check(JavaClass item, ConditionEvents events) {
				implementAnInterfaceInALayerAnnotatedWith(item, events, annotation);
			}
		};
	}

	final public static ArchCondition<JavaClass> implementAnInterfaceInTheSameLayer =
			new ArchCondition<JavaClass>("implement an interface in the same layer") {
		@Override
		public void check(JavaClass item, ConditionEvents events) {
			String annotation = null;
			for (String packageAnnotation : PackageUtils.ANNOTATIONS) {
				if (isInAPackageAnnotatedWith(item, packageAnnotation)) {
					annotation = packageAnnotation;
					break;
				}
			}
			if (annotation != null) {
				implementAnInterfaceInALayerAnnotatedWith(item, events, annotation);
				return;
			}
			String message = formatMessage(item, "does not reside in any layer");
			events.add(SimpleConditionEvent.violated(item, message));
		}
	};

	protected static boolean isFirstPublicProtectedMethodReturnType(JavaClass owner, JavaClass type) {
		for (JavaMethod method : owner.getMethods()) {
			if (method.getModifiers().contains(JavaModifier.PUBLIC) || method.getModifiers().contains(JavaModifier.PROTECTED)) {
				return method.getRawReturnType().equals(type);
			}
		}
		return false;
	}
}