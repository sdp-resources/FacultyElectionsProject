package fsc.utils.builder;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class DelegatingBuilder<S, E> implements Builder<S, E> {
  protected Builder<S, E> builder;

  public DelegatingBuilder(Builder<S, E> builder) {
    this.builder = builder;
  }

  public DelegatingBuilder<S, E> escapeIf(
        Function<S, Boolean> shouldEscape, E escape
  ) {
    return newWithBuilder(builder.escapeIf(shouldEscape, escape));
  }

  public DelegatingBuilder<S, E> escapeUnless(Function<S, Boolean> shouldEscape, E escape) {
    return newWithBuilder(builder.escapeUnless(shouldEscape, escape));
  }

  public DelegatingBuilder<S, E> perform(Consumer<S> consumer) {
    builder.perform(consumer);
    return this;
  }

  public <T> Builder<T, E> mapThrough(Function<S, Builder<T, E>> mapper) {
    return builder.mapThrough(mapper);
  }

  public <T, R> Builder<R, E> bindWith(
        Builder<T, E> other, BiFunction<S, T, Builder<R, E>> combiner
  ) {
    return builder.bindWith(other, combiner);
  }

  public E resolveWith(Function<S, E> process) {
    return builder.resolveWith(process);
  }

  protected DelegatingBuilder<S, E> newWithBuilder(Builder<S, E> builder) {
    return new DelegatingBuilder<>(builder);
  }
}
