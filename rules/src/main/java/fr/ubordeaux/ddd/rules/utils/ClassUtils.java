package fr.ubordeaux.ddd.rules.utils;

import java.lang.annotation.Annotation;
import java.util.Set;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.base.Optional;
import com.tngtech.archunit.core.domain.JavaAccess;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.domain.JavaPackage;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

import fr.ubordeaux.ddd.annotations.fields.EntityID;
import fr.ubordeaux.ddd.annotations.types.Aggregate;
import fr.ubordeaux.ddd.annotations.types.Entity;
import fr.ubordeaux.ddd.annotations.types.Factory;
import fr.ubordeaux.ddd.annotations.types.Repository;
import fr.ubordeaux.ddd.annotations.types.Service;
import fr.ubordeaux.ddd.annotations.types.ValueObject;

public class ClassUtils {
	final public static Class<?>[] ANNOTATIONS = {ValueObject.class, Entity.class, Aggregate.class, Factory.class, Repository.class, Service.class};

	private static String formatMessage(JavaClass item, String message) {
		return String.format("%s %s", item.getDescription(), message);
	}

	public static ArchRule shouldResideInAPackage(Class<? extends Annotation> annotation, String name) {
		return classes()
				.that().areAnnotatedWith(annotation)
				.should().resideInAPackage(name)
				.as("Classes annotated with @" + annotation.getSimpleName() + " should reside in a package named '" + name + "'");
	}

	private static boolean isInAPackageAnnotatedWith(JavaClass item, Class<? extends Annotation> annotation) {
		if (!PackageUtils.packageInfoClasses.isEmpty()) {
			for (JavaClass packageInfo : PackageUtils.packageInfoClasses) {
				if (packageInfo.isAnnotatedWith(annotation)
						&& item.getPackageName().startsWith(packageInfo.getPackageName())) {
					return true;
				}
			}
			return false;
		}
		Package annotatedPackage = PackageUtils.loadPackage(item.getPackageName(), item.getSimpleName());
		if (annotatedPackage != null && annotatedPackage.isAnnotationPresent(annotation)) {
			return true;
		}
		Optional<JavaPackage> parentPackage = item.getPackage().getParent();
		while (parentPackage.isPresent() && parentPackage.get().getName().compareTo("") != 0) {
			annotatedPackage = PackageUtils.loadPackage(parentPackage.get().getName(), PackageUtils.PACKAGE_INFO);
			if (annotatedPackage != null && annotatedPackage.isAnnotationPresent(annotation)) {
				return true;
			}
			parentPackage = parentPackage.get().getParent();
		}
		return false;
	}

	public static ArchCondition<JavaClass> resideInAPackageAnnotatedWith(Class<? extends Annotation> annotation) {
		return new ArchCondition<JavaClass>("reside in a package annotated with @" + annotation.getSimpleName()) {
			@Override
			public void check(JavaClass item, ConditionEvents events) {
				if (isInAPackageAnnotatedWith(item, annotation)) {
					String message = formatMessage(item, "does reside in a package annotated with @" + annotation.getSimpleName());
					events.add(SimpleConditionEvent.satisfied(item, message));
					return;
				}
				String message = formatMessage(item, "does not reside in a package annotated with @" + annotation.getSimpleName());
				events.add(SimpleConditionEvent.violated(item, message));
			}
		};
	}

	public static ArchRule shouldResideInAPackageAnnotatedWith(Class<? extends Annotation> annotation, Class<? extends Annotation> packageAnnotation) {
		return classes()
				.that().areAnnotatedWith(annotation)
				.should(resideInAPackageAnnotatedWith(packageAnnotation))
				.as("Classes annotated with @" + annotation.getSimpleName() + " should reside in a package annotated with @" + packageAnnotation.getSimpleName());
	}

	public static ArchRule shouldNotBeAnnotatedWith(Class<? extends Annotation> annotation, Class<? extends Annotation> anotherAnnotation) {
		return classes()
				.that().areAnnotatedWith(annotation)
				.should().notBeAnnotatedWith(anotherAnnotation)
				.as("Classes annotated with @" + annotation.getSimpleName() + " should not also be annotated with @" + anotherAnnotation.getSimpleName());
	}

	public static ArchCondition<JavaClass> haveAtLeastOneFieldAnnotatedWith(Class<? extends Annotation> annotation) {
		return new ArchCondition<JavaClass>("have at least one field" + ((annotation != null) ? " annotated with @" + annotation.getSimpleName() : "")) {
			@Override
			public void check(JavaClass item, ConditionEvents events) {
				Set<JavaField> fields = item.getFields();
				if (fields.isEmpty()) {
					String message = formatMessage(item, "does not have at least one field" + ((annotation != null) ? " annotated with @" + annotation.getSimpleName() : ""));
					events.add(SimpleConditionEvent.violated(item, message));
					return;
				}
				if (annotation == null) {
					String message = formatMessage(item, "does have at least one field");
					events.add(SimpleConditionEvent.satisfied(item, message));
					return;
				}
				for (JavaField field : fields) {
					if (field.isAnnotatedWith(annotation)) {
						String message = formatMessage(item, "does have at least one field annotated with @" + annotation.getSimpleName());
						events.add(SimpleConditionEvent.satisfied(item, message));
						return;
					}
				}
				String message = formatMessage(item, "does not have at least one field annotated with @" + annotation.getSimpleName());
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
			overrideObjectClassEqualsMethodAccessingFields(item, events, FieldUtils.getFieldsAnnotatedWith(item.getFields(), EntityID.class));
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
			overrideObjectClassHashCodeMethodAccessingFields(item, events, FieldUtils.getFieldsAnnotatedWith(item.getFields(), EntityID.class));
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
			overrideObjectClassToStringMethodAccessingFields(item, events, FieldUtils.getFieldsAnnotatedWith(item.getFields(), EntityID.class));
		}
	};

	public static ArchCondition<JavaClass> accessAtLeastOneMethodFromAClassAnnotatedWith(Class<? extends Annotation> annotation, String name) {
		return new ArchCondition<JavaClass>("access at least one class annotated with @" + annotation.getSimpleName() + ((name != null && name.compareTo("") != 0) ? " and its method " + name : "")) {
			@Override
			public void check(JavaClass item, ConditionEvents events) {
				for (JavaAccess<?> access : item.getAccessesFromSelf()) {
					if (!access.getTargetOwner().equals(item) && access.getTargetOwner().isAnnotatedWith(annotation)
							&& (name == null || name.compareTo("") == 0 || access.getName().compareTo(name) == 0)) {
						String message = formatMessage(item, "does access at least one class annotated with @" + annotation.getSimpleName() + ((name != null && name.compareTo("") != 0) ? " and its method " + name : ""));
						events.add(SimpleConditionEvent.satisfied(item, message));
						return;
					}
				}
				String message = formatMessage(item, "does not access at least one class annotated with @" + annotation.getSimpleName() + ((name != null && name.compareTo("") != 0) ? " and its method " + name : ""));
				events.add(SimpleConditionEvent.violated(item, message));
			}
		};
	}

	public static ArchCondition<JavaClass> accessAtLeastOneConstructorFromAClassAnnotatedWith(Class<? extends Annotation> annotation) {
		return accessAtLeastOneMethodFromAClassAnnotatedWith(annotation, "<init>");
	}

	public static ArchCondition<JavaClass> accessAtLeastOneClassAnnotatedWith(Class<? extends Annotation> annotation) {
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

	private static void implementAnInterfaceInALayerAnnotatedWith(JavaClass item, ConditionEvents events, Class<? extends Annotation> annotation) {
		for (JavaClass temporaryInterface : item.getInterfaces()) {
			if (isInAPackageAnnotatedWith(temporaryInterface, annotation)) {
				String message = formatMessage(item, "does implement an interface in a layer annotated with @" + annotation.getSimpleName());
				events.add(SimpleConditionEvent.satisfied(item, message));
				return;
			}
		}
		String message = formatMessage(item, "does not implement an interface in a layer annotated with @" + annotation.getSimpleName());
		events.add(SimpleConditionEvent.violated(item, message));
	}

	public static ArchCondition<JavaClass> implementAnInterfaceInALayerAnnotatedWith(Class<? extends Annotation> annotation) {
		return new ArchCondition<JavaClass>("implement an interface in a layer annotated with @" + annotation.getSimpleName()) {
			@Override
			public void check(JavaClass item, ConditionEvents events) {
				implementAnInterfaceInALayerAnnotatedWith(item, events, annotation);
			}
		};
	}

	final public static ArchCondition<JavaClass> implementAnInterfaceInTheSameLayer =
			new ArchCondition<JavaClass>("implement an interface in the same layer") {
		/**
		 * Classes in PackageUtils.annotations correspond to layers annotations
		 * 
		 */
		@SuppressWarnings("unchecked")
		@Override
		public void check(JavaClass item, ConditionEvents events) {
			Class<? extends Annotation> annotation = null;
			for (Class<?> packageAnnotation : PackageUtils.ANNOTATIONS) {
				if (Annotation.class.isAssignableFrom(packageAnnotation)
						&& isInAPackageAnnotatedWith(item, (Class<? extends Annotation>)packageAnnotation)) {
					annotation = (Class<? extends Annotation>)packageAnnotation;
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