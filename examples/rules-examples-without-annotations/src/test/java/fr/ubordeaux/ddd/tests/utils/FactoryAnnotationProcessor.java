package fr.ubordeaux.ddd.tests.utils;

import java.lang.annotation.Annotation;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.reference.CtPackageReference;
import spoon.reflect.reference.CtTypeReference;

public class FactoryAnnotationProcessor extends AbstractProcessor<CtClass<?>> {
	private boolean mayBeFactory(CtClass<?> element) {
		return element.getSimpleName().contains("Factory");
	}

	@Override
	public void process(CtClass<?> element) {
		if (mayBeFactory(element)) {
			CtPackageReference ctPackageReference = getFactory().Core().createPackageReference();
			ctPackageReference.setSimpleName("fr.ubordeaux.ddd.annotations.types");
			CtTypeReference<? extends Annotation> ctTypeReference = getFactory().Core().createTypeReference();
			ctTypeReference.setSimpleName("Factory");
			ctTypeReference.setPackage(ctPackageReference);
			CtAnnotation<Annotation> ctAnnotation = getFactory().Core().createAnnotation();
			ctAnnotation.setAnnotationType(ctTypeReference);
			element.addAnnotation(ctAnnotation);
		}
	}
}