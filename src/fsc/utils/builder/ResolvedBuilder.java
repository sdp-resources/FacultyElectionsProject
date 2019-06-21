package fsc.utils.builder;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class ResolvedBuilder<S, E> implements Builder<S, E> {
  private E resolvedResponse;

  public ResolvedBuilder(E response) {this.resolvedResponse = response;}

  public Builder<S, E> perform(Consumer<S> consumer) {
    return this;
  }

  public Builder<S, E> escapeIf(Function<S, Boolean> shouldEscape, E escape) {
    return this;
  }

  public <T> Builder<T, E> mapThrough(Function<S, Builder<T, E>> mapper) {
    return (Builder<T, E>) this;
  }

  public <T, R> Builder<R, E> bindWith(
        Builder<T, E> other, BiFunction<S, T, Builder<R, E>> combiner
  ) {
    return (Builder<R, E>) this;
  }

  public E resolveWith(Function<S, E> process) {
    return resolvedResponse;
  }

  public String toString() {
    return "ResolvedBuilder{" + resolvedResponse + '}';
  }
}
