package io.syphenlm12.usecase.impl;

import com.google.inject.Singleton;
import io.syphenlm12.annotations.BindImplementation;
import io.syphenlm12.usecase.OneToManyBindingWithSingletonScope;

@BindImplementation(scope = Singleton.class, name = "Kind1")
public class OneToManyKind1Impl implements OneToManyBindingWithSingletonScope {

  @Override
  public void run() {
    System.out.println("OneToManyKind1Impl running");
  }
}
