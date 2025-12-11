package io.syphenlm12.utils;


import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

public class CommonUtils {

  /**
   * Scans the classpath for classes annotated with the specific annotation.
   *
   * @param annotation The annotation to look for (e.g., BindImplementation.class).
   * @param packages   The list of package names to scan.
   * @return A Set of classes that define the given annotation.
   * @throws RuntimeException If scanning fails or annotation is null.
   */
  public static Set<Class<?>> discoverClasses(final Class<? extends Annotation> annotation,
      final String... packages) {
    if (annotation == null) {
      throw new IllegalArgumentException("annotation cannot be null");
    }
    ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
    configurationBuilder.setScanners(Scanners.TypesAnnotated);
    if (!isValidPackage(packages)) {
      configurationBuilder.addUrls(ClasspathHelper.forClassLoader());
      configurationBuilder.addUrls(ClasspathHelper.forJavaClassPath());
    } else {
      Arrays.stream(packages).map(String::trim).forEach(packageName -> {
        try {
          Collection<URL> urls = ClasspathHelper.forPackage(packageName);
          configurationBuilder.addUrls(urls);
        } catch (Exception e) {
          throw new RuntimeException("Failed to get URLs for package: " + packageName, e);
        }
      });
      configurationBuilder.forPackages(packages);
    }
    try {
      Reflections reflections = new Reflections(configurationBuilder);
      return reflections.getTypesAnnotatedWith(annotation);
    } catch (Exception e) {
      throw new RuntimeException("Failed to scan classes with annotation: " + annotation.getName(), e);
    }
  }

  private static boolean isValidPackage(final String... packages) {
    return packages != null
        && packages.length > 0
        && Arrays.stream(packages).anyMatch(pkg -> pkg != null && !pkg.trim().isEmpty());
  }

  public static boolean isNullOrEmpty(final String str) {
    return str == null || str.isEmpty();
  }

  /**
   * Parses a comma-separated string of packages into a String array.
   *
   * @param packagesToScan Comma-separated package string (e.g. "com.a, com.b").
   * @return cleaned array of package names.
   */
  public static String[] extractPackagesToScan(final String packagesToScan) {
    if (isNullOrEmpty(packagesToScan)) {
      return new String[0];
    }
    return Arrays.stream(packagesToScan.split(","))
        .map(String::trim)
        .filter(pkg -> !pkg.isEmpty())
        .toArray(String[]::new);
  }
}
