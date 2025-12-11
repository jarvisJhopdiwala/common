package io.syphenlm12.annotations;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BindImplementation {

  /**
   * The interfaces to bind this implementation to.
   * If empty, the module will bind to all interfaces implemented by the class.
   * @return Array of interface classes.
   */
  Class<?>[] bindWith() default {};

  /**
   * The Guice scope to apply to this binding (e.g., Singleton.class).
   * Defaults to NoScope.class (effectively no specific scope/prototype).
   * @return The annotation class representing the scope.
   */
  Class<? extends Annotation> scope() default NoScope.class;

  /**
   * An optional name for the binding (equivalent to @Named("name")).
   * @return The name string.
   */
  String name() default "";
}
