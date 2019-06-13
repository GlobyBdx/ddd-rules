package fr.ubordeaux.ddd.rules.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaAccess;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaConstructor;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.core.domain.JavaFieldAccess;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

import fr.ubordeaux.ddd.tests.utils.ArchitectureImportUtils;

public class FieldUtils {
	final public static String[] ANNOTATIONS = {"EntityID", "Immutable"};

	final public static Map<String, Set<String>> annotatedFields = new HashMap<>();

	private static String formatMessage(JavaField field, String message) {
		return String.format("%s %s", field.getDescription(), message);
	}

	protected static boolean isFieldAnnotatedWith(JavaField field, String annotation) {
		if (annotatedFields.isEmpty()) {
			ArchitectureImportUtils.importArchitecture();
		}
		return annotatedFields.get(annotation).contains(field.getFullName());
	}

	public static DescribedPredicate<JavaField> areAnnotatedWith(String annotation) {
		return new DescribedPredicate<JavaField>("are annotated with @" + annotation) {
			@Override
			public boolean apply(JavaField item) {
				return isFieldAnnotatedWith(item, annotation);
			}
		};
	}

	public static DescribedPredicate<JavaField> areNotAnnotatedWith(String annotation) {
		return new DescribedPredicate<JavaField>("are not annotated with @" + annotation) {
			@Override
			public boolean apply(JavaField item) {
				return !isFieldAnnotatedWith(item, annotation);
			}
		};
	}

	protected static boolean isFieldAccessedByMethod(JavaField field, JavaMethod method) {
		for (JavaFieldAccess access : field.getAccessesToSelf()) {
			for (JavaFieldAccess methodAccess : method.getFieldAccesses()) {
				if (access.equals(methodAccess)) {
					return true;
				}
			}
		}
		return false;
	}

	protected static boolean areFieldsAccessedByMethod(Set<JavaField> fields, JavaMethod method) {
		for (JavaField field : fields) {
			if (!isFieldAccessedByMethod(field, method)) {
				return false;
			}
		}
		return true;
	}

	protected static Set<JavaField> getFieldsAnnotatedWith(Set<JavaField> fields, String annotation) {
		Set<JavaField> annotatedFields = new HashSet<>();
		for (JavaField field : fields) {
			if (isFieldAnnotatedWith(field, annotation)) {
				annotatedFields.add(field);
			}
		}
		return annotatedFields;
	}

	protected static boolean isFieldImmutable(JavaField field) {
		return (ClassUtils.isClassAnnotatedWith(field.getRawType(), "Entity")
				|| ClassUtils.isClassAnnotatedWith(field.getRawType(), "ValueObject")
				|| isFieldAnnotatedWith(field, "Immutable")
				|| (field.getRawType().isPrimitive() && field.getModifiers().contains(JavaModifier.FINAL)));
	}

	final public static DescribedPredicate<JavaField> possessImmutability =
			new DescribedPredicate<JavaField>("possess immutability") {
		@Override
		public boolean apply(JavaField item) {
			return isFieldImmutable(item);
		}
	};

	private static Set<JavaClass> getInternalClasses(JavaField field) {
		Set<JavaClass> internalClasses = new HashSet<>();
		Set<JavaClass> temporaryClasses = new HashSet<>();
		Set<JavaClass> inspectedClasses = new HashSet<>();
		internalClasses.add(field.getRawType());
		boolean check;
		do {
			check = false;
			temporaryClasses.clear();
			for (JavaClass internalClass : internalClasses) {
				if (inspectedClasses.add(internalClass) && !ClassUtils.isClassAnnotatedWith(internalClass, "Aggregate")) {
					for (JavaField classField : internalClass.getFields()) {
						if (isFieldImmutable(classField) && !classField.getRawType().isPrimitive()) {
							check |= temporaryClasses.add(classField.getRawType());
						}
					}
				}
			}
			internalClasses.clear();
			internalClasses.addAll(temporaryClasses);
		} while (check);
		return inspectedClasses;
	}

	final public static ArchCondition<JavaField> notBeExternallyAccessed =
			new ArchCondition<JavaField>("not be externally accessed") {
		/**
		 * Check of internal classes may be too restrictive
		 * 
		 */
		@Override
		public void check(JavaField item, ConditionEvents events) {
			boolean check = true;
			Set<JavaClass> classes = getInternalClasses(item);
			for (JavaClass internalClass : classes) {
				for (JavaAccess<?> access : internalClass.getAccessesToSelf()) {
					if (!classes.contains(access.getOriginOwner())) {
						String message = formatMessage(item, "internal class <" + internalClass.getFullName() + "> is externally accessed by class <" + access.getOriginOwner().getFullName() + ">");
						events.add(SimpleConditionEvent.violated(item, message));
						check = false;
					}
				}
			}
			if (check) {
				String message = formatMessage(item, "is not externally accessed");
				events.add(SimpleConditionEvent.satisfied(item, message));
			}
		}
	};

	final public static ArchCondition<JavaField> notBeExternallyInstantiated =
			new ArchCondition<JavaField>("not be externally instantiated") {
		/**
		 * Check of internal classes constructors may not be sufficient
		 * 
		 */
		@Override
		public void check(JavaField item, ConditionEvents events) {
			boolean check = true;
			Set<JavaClass> classes = getInternalClasses(item);
			for (JavaClass internalClass : classes) {
				for (JavaConstructor constructor : internalClass.getConstructors()) {
					for (JavaAccess<?> access : constructor.getAccessesToSelf()) {
						if (!classes.contains(access.getOriginOwner())
								&& !access.getOriginOwner().equals(item.getOwner())
								&& (!ClassUtils.isClassAnnotatedWith(access.getOriginOwner(), "Factory") || !ClassUtils.isFirstPublicProtectedMethodReturnType(access.getOriginOwner(), item.getOwner()))) {
							String message = formatMessage(item, "internal class <" + internalClass.getFullName() + "> is externally instantiated by class <" + access.getOriginOwner().getFullName() + ">");
							events.add(SimpleConditionEvent.violated(item, message));
							check = false;
						}
					}
				}
			}
			if (check) {
				String message = formatMessage(item, "is not externally instantiated");
				events.add(SimpleConditionEvent.satisfied(item, message));
			}
		}
	};
}