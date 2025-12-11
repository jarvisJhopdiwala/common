# Project Documentation: Auto-Binding Guice Library

## 1\. What is this project about?

This project is a lightweight extension for **Google Guice** that automates the dependency injection binding process. Instead of manually writing boilerplate binding code (e.g., `bind(Interface.class).to(Implementation.class)`) in your Guice Modules, this library allows you to declare bindings directly on your implementation classes using the custom `@BindImplementation` annotation.

**Key Features:**

  * **Automatic Discovery:** Scans specified packages for annotated classes at runtime.
  * **Declarative Bindings:** Define scopes (e.g., Singleton) and Names directly on the class.
  * **Smart Defaults:** Automatically binds an implementation to its interfaces if not explicitly specified.
  * **Environment Configuration:** Controls which packages to scan via environment variables.

## 2\. How to use this project in your existing code

### Step 1: Add Dependencies

Ensure your project has the `common` library, **Google Guice**, and **Reflections** available.

### Step 2: Annotate Your Implementations

Mark your implementation classes with `@BindImplementation`.

**Example 1: Simple Binding**

```java
// Automatically binds MyService (interface) to MyServiceImpl
@BindImplementation
public class MyServiceImpl implements MyService { ... }
```

**Example 2: Singleton with Named Binding**

```java
// Binds named "FastProcessor" with Singleton scope
@BindImplementation(name = "FastProcessor", scope = Singleton.class)
public class FastProcessorImpl implements Processor { ... }
```

### Step 3: Register the Module

Install the `BindModule` when creating your Guice injector.

```java
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.syphenlm12.module.BindModule;

public class Main {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new BindModule());
        // ... use injector
    }
}
```

### Step 4: Configure Environment Variables

You must tell the application which packages to scan for the annotations. Set the following environment variable before running the application:

  * **Key:** `PACKAGES_TO_SCAN`
  * **Value:** A comma-separated list of package names (e.g., `com.myapp.services,com.myapp.repositories`).

## 3\. Supported Frameworks

  * **Google Guice (Version 7.0.0):** The core dependency injection framework this project extends.
  * **Reflections (Version 0.10.2):** Used for classpath scanning to discover annotated classes at runtime.
  * **Java (JDK 8+):** The project uses Java Streams and functional interfaces.
  * **Maven:** The project is built and managed using Apache Maven.

## 4\. Code Structure

The project is organized into the following packages:

  * **`io.syphenlm12.annotations`**: Contains the core API annotations.
      * `BindImplementation`: The main annotation for marking classes.
      * `NoScope`: A marker class used as a default value for scopes.
  * **`io.syphenlm12.module`**: Contains the Guice configuration logic.
      * `BindModule`: The Guice `AbstractModule` that performs scanning and binding.
  * **`io.syphenlm12.utils`**: Utility helper classes.
      * `CommonUtils`: Handles reflection logic and package scanning.
  * **`io.syphenlm12.usecase`**: Contains example code demonstrating how to use the library (not part of the core logic).

