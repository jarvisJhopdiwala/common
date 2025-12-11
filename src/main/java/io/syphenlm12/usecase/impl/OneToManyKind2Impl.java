package io.syphenlm12.usecase.impl;

import com.google.inject.Singleton;
import io.syphenlm12.annotations.BindImplementation;
import io.syphenlm12.usecase.OneToManyBindingWithSingletonScope;

@BindImplementation(name = "Kind2", scope = Singleton.class)
public class OneToManyKind2Impl implements OneToManyBindingWithSingletonScope {

  @Override
  public void run() {
    System.out.println("OneToManyKind2Impl running");
  }
}
