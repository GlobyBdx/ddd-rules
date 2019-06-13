package fr.ubordeaux.ddd.tests.utils;

import java.lang.annotation.Annotation;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.reference.CtPackageReference;
import spoon.reflect.reference.CtTypeReference;

public class RepositoryAnnotationProcessor extends AbstractProcessor<CtClass<?>> {
	private boolean mayBeRepository(CtClass<?> element) {
		return element.getSimpleName().contains("Repository");
	}

	@Override
	public void process(CtClass<?> element) {
		if (mayBeRepository(element)) {
			CtPackageReference ctPackageReference = getFactory().Core().createPackageReference();
			ctPackageReference.setSimpleName("fr.ubordeaux.ddd.annotations.types");
			CtTypeReference<? extends Annotation> ctTypeReference = getFactory().Core().createTypeReference();
			ctTypeReference.setSimpleName("Repository");
			ctTypeReference.setPackage(ctPackageReference);
			CtAnnotation<Annotation> ctAnnotation = getFactory().Core().createAnnotation();
			ctAnnotation.setAnnotationType(ctTypeReference);
			element.addAnnotation(ctAnnotation);
		}
	}
}