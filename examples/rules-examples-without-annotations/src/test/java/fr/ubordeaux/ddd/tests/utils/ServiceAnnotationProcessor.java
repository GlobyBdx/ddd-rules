package fr.ubordeaux.ddd.tests.utils;

import java.lang.annotation.Annotation;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.reference.CtPackageReference;
import spoon.reflect.reference.CtTypeReference;

public class ServiceAnnotationProcessor extends AbstractProcessor<CtClass<?>> {
	private boolean mayBeService(CtClass<?> element) {
		return element.getSimpleName().contains("Service");
	}

	@Override
	public void process(CtClass<?> element) {
		if (mayBeService(element)) {
			CtPackageReference ctPackageReference = getFactory().Core().createPackageReference();
			ctPackageReference.setSimpleName("fr.ubordeaux.ddd.annotations.types");
			CtTypeReference<? extends Annotation> ctTypeReference = getFactory().Core().createTypeReference();
			ctTypeReference.setSimpleName("Service");
			ctTypeReference.setPackage(ctPackageReference);
			CtAnnotation<Annotation> ctAnnotation = getFactory().Core().createAnnotation();
			ctAnnotation.setAnnotationType(ctTypeReference);
			element.addAnnotation(ctAnnotation);
		}
	}
}