package fr.ubordeaux.ddd.annotations.packages;

import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(PACKAGE)
@Retention(RUNTIME)
public @interface Infrastructure {}