package fr.ubordeaux.ddd.tests.utils;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import fr.ubordeaux.ddd.rules.utils.PackageUtils;
import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.reference.CtPackageReference;
import spoon.reflect.reference.CtTypeReference;

public class LayeredArchitectureAnnotationProcessor extends AbstractProcessor<CtPackage> {
	private Set<String> getAnnotations(CtPackage element) {
		Set<String> annotations = new HashSet<>();
		for (String annotation : PackageUtils.annotatedPackages.keySet()) {
			if (PackageUtils.annotatedPackages.get(annotation).contains(element.getQualifiedName())) {
				annotations.add(annotation);
			}
		}
		return annotations;
	}

	@Override
	public void process(CtPackage element) {
		Set<String> annotations = getAnnotations(element);
		if (!annotations.isEmpty()) {
			CtPackageReference ctPackageReference = getFactory().Core().createPackageReference();
			ctPackageReference.setSimpleName("fr.ubordeaux.ddd.annotations.packages");
			for (String annotation : annotations) {
				CtTypeReference<? extends Annotation> ctTypeReference = getFactory().Core().createTypeReference();
				ctTypeReference.setSimpleName(annotation);
				ctTypeReference.setPackage(ctPackageReference);
				CtAnnotation<Annotation> ctAnnotation = getFactory().Core().createAnnotation();
				ctAnnotation.setAnnotationType(ctTypeReference);
				element.addAnnotation(ctAnnotation);
			}
		}
	}
}