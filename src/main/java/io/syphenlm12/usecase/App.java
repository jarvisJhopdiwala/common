package io.syphenlm12.usecase;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import io.syphenlm12.module.BindModule;

public class App {

  public static void main(String[] args) {
    System.out.println("Hello World!");

    Injector injector = Guice.createInjector(new BindModule());
    System.out.println("====Testing OneToOneBindingWithSingletonScope====");
    OneToOneBindingWithSingletonScope instance1 = injector.getInstance(
        OneToOneBindingWithSingletonScope.class);
    OneToOneBindingWithSingletonScope instance2 = injector.getInstance(
        OneToOneBindingWithSingletonScope.class);
    System.out.println("Are both instances same? " + (instance1 == instance2));
    instance1.run();

    System.out.println("====Testing OneToManyBindingWithSingletonScope====");

    OneToManyBindingWithSingletonScope kind1Instance1 = injector.getInstance(
        Key.get(OneToManyBindingWithSingletonScope.class, Names.named("Kind1")));
    OneToManyBindingWithSingletonScope kind1Instance2 = injector.getInstance(
        Key.get(OneToManyBindingWithSingletonScope.class, Names.named("Kind1")));
    System.out.println("Are both Kind1 instances same? " + (kind1Instance1 == kind1Instance2));
    kind1Instance1.run();

    OneToManyBindingWithSingletonScope kind2Instance1 = injector.getInstance(
        Key.get(OneToManyBindingWithSingletonScope.class, Names.named("Kind2")));
    OneToManyBindingWithSingletonScope kind2Instance2 = injector.getInstance(
        Key.get(OneToManyBindingWithSingletonScope.class, Names.named("Kind2")));
    System.out.println("Are both Kind2 instances same? " + (kind2Instance1 == kind2Instance2));
    kind2Instance1.run();
  }
}
