package fr.ubordeaux.ddd.rules.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

import fr.ubordeaux.ddd.tests.utils.ArchitectureImportUtils;

public class MethodUtils {
	final public static String[] ANNOTATIONS = {"Getter", "Setter"};

	final public static Map<String, Set<String>> annotatedMethods = new HashMap<>();

	private static String formatMessage(JavaMethod method, String message) {
		return String.format("%s %s", method.getDescription(), message);
	}

	protected static boolean isMethodAnnotatedWith(JavaMethod method, String annotation) {
		if (annotatedMethods.isEmpty()) {
			ArchitectureImportUtils.importArchitecture();
		}
		return annotatedMethods.get(annotation).contains(method.getFullName());
	}

	public static DescribedPredicate<JavaMethod> areAnnotatedWith(String annotation) {
		return new DescribedPredicate<JavaMethod>("are annotated with @" + annotation) {
			@Override
			public boolean apply(JavaMethod item) {
				return isMethodAnnotatedWith(item, annotation);
			}
		};
	}

	public static DescribedPredicate<JavaMethod> areNotAnnotatedWith(String annotation) {
		return new DescribedPredicate<JavaMethod>("are not annotated with @" + annotation) {
			@Override
			public boolean apply(JavaMethod item) {
				return !isMethodAnnotatedWith(item, annotation);
			}
		};
	}

	public static DescribedPredicate<JavaMethod> accessAtLeastOneFieldAnnotatedWith(String annotation) {
		return new DescribedPredicate<JavaMethod>("access at least one field" + ((annotation != null && annotation.compareTo("") != 0) ? " annotated with @" + annotation : "")) {
			@Override
			public boolean apply(JavaMethod item) {
				for (JavaField field : item.getOwner().getFields()) {
					if ((annotation == null || annotation.compareTo("") == 0 || FieldUtils.isFieldAnnotatedWith(field, annotation))
							&& FieldUtils.isFieldAccessedByMethod(field, item)) {
						return true;
					}
				}
				return false;
			}
		};
	}

	final public static DescribedPredicate<JavaMethod> accessAtLeastOneField = accessAtLeastOneFieldAnnotatedWith(null);

	public static ArchCondition<JavaMethod> accessOnlyImmutableFieldsAnnotatedWith(String annotation) {
		return new ArchCondition<JavaMethod>("access only immutable fields" + ((annotation != null && annotation.compareTo("") != 0) ? " annotated with @" + annotation : "")) {
			@Override
			public void check(JavaMethod item, ConditionEvents events) {
				boolean check = true;
				for (JavaField field : item.getOwner().getFields()) {
					if ((annotation == null || annotation.compareTo("") == 0 || FieldUtils.isFieldAnnotatedWith(field, annotation))
							&& FieldUtils.isFieldAccessedByMethod(field, item)
							&& !FieldUtils.isFieldImmutable(field)) {
						String message = formatMessage(item, "does access field <" + field.getFullName() + "> which may not be immutable");
						events.add(SimpleConditionEvent.violated(item, message));
						check = false;
					}
				}
				if (check) {
					String message = formatMessage(item, "certainly access only immutable fields");
					events.add(SimpleConditionEvent.satisfied(item, message));
				}
			}
		};
	}

	final public static ArchCondition<JavaMethod> accessOnlyImmutableFields = accessOnlyImmutableFieldsAnnotatedWith(null);

	protected static boolean isObjectClassEqualsMethod(JavaMethod method) {
		return (method.getName().compareTo("equals") == 0
				&& method.getRawParameterTypes().size() == 1
				&& method.getRawParameterTypes().get(0).reflect().equals(Object.class)
				&& method.getRawReturnType().reflect().equals(boolean.class));
	}

	protected static boolean isObjectClassHashCodeMethod(JavaMethod method) {
		return (method.getName().compareTo("hashCode") == 0
				&& method.getRawParameterTypes().size() == 0
				&& method.getRawReturnType().reflect().equals(int.class));
	}

	protected static boolean isObjectClassToStringMethod(JavaMethod method) {
		return (method.getName().compareTo("toString") == 0
				&& method.getRawParameterTypes().size() == 0
				&& method.getRawReturnType().reflect().equals(String.class));
	}

	final public static DescribedPredicate<JavaMethod> areNotObjectClassMethods =
			new DescribedPredicate<JavaMethod>("are not Object.class methods") {
		@Override
		public boolean apply(JavaMethod method) {
			return (!isObjectClassEqualsMethod(method)
					&& !isObjectClassHashCodeMethod(method)
					&& !isObjectClassToStringMethod(method));
		}
	};
}