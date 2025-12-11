package io.syphenlm12.usecase.impl;

import com.google.inject.Singleton;
import io.syphenlm12.annotations.BindImplementation;
import io.syphenlm12.usecase.OneToOneBindingWithSingletonScope;

@BindImplementation(scope = Singleton.class)
public class OneToOneBindingWithSingletonScopeImpl implements OneToOneBindingWithSingletonScope {

  @Override
  public void run() {
    System.out.println("OneToOneBindingWithSingletonScopeImpl running");
  }
}
