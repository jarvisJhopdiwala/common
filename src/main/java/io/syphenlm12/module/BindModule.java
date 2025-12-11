package io.syphenlm12.module;

import com.google.inject.AbstractModule;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.binder.ScopedBindingBuilder;
import com.google.inject.name.Names;
import io.syphenlm12.annotations.BindImplementation;
import io.syphenlm12.annotations.NoScope;
import io.syphenlm12.utils.CommonUtils;
import java.lang.annotation.Annotation;
import java.util.Set;

public class BindModule extends AbstractModule {

  /**
   * The environment variable key used to define packages to scan.
   */
  private static final String ENV_KEY_PACKAGES_TO_SCAN = "PACKAGES_TO_SCAN";

  /**
   * Configures the module.
   * 1. Reads the 'PACKAGES_TO_SCAN' environment variable.
   * 2. Scans those packages for classes annotated with @BindImplementation.
   * 3. Iterates through discovered classes and creates dynamic Guice bindings.
   */
  @Override
  protected void configure() {
    String commaSeperatedPackages = System.getenv(ENV_KEY_PACKAGES_TO_SCAN);
    String[] extractPackagesToScan = CommonUtils.extractPackagesToScan(commaSeperatedPackages);
    Set<Class<?>> annotatedClasses = CommonUtils.discoverClasses(BindImplementation.class, extractPackagesToScan);
    if (annotatedClasses == null || annotatedClasses.isEmpty()) {
      return;
    }
    annotatedClasses.forEach(this::createBinding);
  }

  @SuppressWarnings("unchecked")
  private <T> void createBinding(Class<?> implClass) {
    BindImplementation annotation = implClass.getAnnotation(BindImplementation.class);
    if (annotation == null) {
      return;
    }
    Class<?>[] bindWith =
        annotation.bindWith().length == 0 ? implClass.getInterfaces() : annotation.bindWith();
    for (Class<?> interfaceClass : bindWith) {
      createAndConfigureBinding((Class<T>) interfaceClass, (Class<? extends T>) implClass, annotation.name(), annotation.scope());
    }
  }

  private <T> void createAndConfigureBinding(Class<T> interfaceClass,
      Class<? extends T> implementationClass, String name, Class<? extends Annotation> scope) {
    try {
      LinkedBindingBuilder<T> builder = CommonUtils.isNullOrEmpty(name)
          ? bind(interfaceClass)
          : bind(interfaceClass).annotatedWith(Names.named(name));
      ScopedBindingBuilder scopedBindingBuilder = builder.to(implementationClass);
      if (scope != null && !scope.equals(NoScope.class)) {
        scopedBindingBuilder.in(scope);
      }
    } catch (Exception e) {
      String errorMsg = String.format(
          "Failed to create binding for interface: %s with implementation: %s",
          interfaceClass.getName(), implementationClass.getName());
      throw new RuntimeException(errorMsg, e);
    }
  }
}
