package fr.ubordeaux.ddd.tests.utils;

import java.lang.annotation.Annotation;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.reference.CtPackageReference;
import spoon.reflect.reference.CtTypeReference;

public class GetterAnnotationProcessor extends AbstractProcessor<CtMethod<?>> {
	private boolean mayBeGetter(CtMethod<?> element) {
		return element.getSimpleName().startsWith("get");
	}

	@Override
	public void process(CtMethod<?> element) {
		if (mayBeGetter(element)) {
			CtPackageReference ctPackageReference = getFactory().Core().createPackageReference();
			ctPackageReference.setSimpleName("fr.ubordeaux.ddd.annotations.methods");
			CtTypeReference<? extends Annotation> ctTypeReference = getFactory().Core().createTypeReference();
			ctTypeReference.setSimpleName("Getter");
			ctTypeReference.setPackage(ctPackageReference);
			CtAnnotation<Annotation> ctAnnotation = getFactory().Core().createAnnotation();
			ctAnnotation.setAnnotationType(ctTypeReference);
			element.addAnnotation(ctAnnotation);
		}
	}
}