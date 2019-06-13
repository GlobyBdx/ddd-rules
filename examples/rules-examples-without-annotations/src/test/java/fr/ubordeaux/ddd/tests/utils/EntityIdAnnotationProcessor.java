package fr.ubordeaux.ddd.tests.utils;

import java.lang.annotation.Annotation;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtField;
import spoon.reflect.reference.CtPackageReference;
import spoon.reflect.reference.CtTypeReference;

public class EntityIdAnnotationProcessor extends AbstractProcessor<CtField<?>> {
	private boolean mayBeEntityId(CtField<?> element) {
		return element.getSimpleName().endsWith("Id");
	}

	@Override
	public void process(CtField<?> element) {
		if (mayBeEntityId(element)) {
			CtPackageReference ctPackageReference = getFactory().Core().createPackageReference();
			ctPackageReference.setSimpleName("fr.ubordeaux.ddd.annotations.fields");
			CtTypeReference<? extends Annotation> ctTypeReference = getFactory().Core().createTypeReference();
			ctTypeReference.setSimpleName("EntityID");
			ctTypeReference.setPackage(ctPackageReference);
			CtAnnotation<Annotation> ctAnnotation = getFactory().Core().createAnnotation();
			ctAnnotation.setAnnotationType(ctTypeReference);
			element.addAnnotation(ctAnnotation);
		}
	}
}