package aethereal.core;

import aethereal.api.ApiAnnotation002;
import java.util.function.Supplier;

@FunctionalInterface
@ApiAnnotation002
public interface CoreContract038<T> extends Supplier<T> {
   @Override
   T get();
}
