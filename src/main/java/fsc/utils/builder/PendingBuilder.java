package fsc.utils.builder;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

class PendingBuilder<S, E> implements Builder<S, E> {
  private S value;

  public PendingBuilder(S value) { this.value = value; }

  public Builder<S, E> escapeIf(Function<S, Boolean> shouldEscape, E escape) {
    return shouldEscape.apply(value) ? new ResolvedBuilder<>(escape) : this;
  }

  public Builder<S, E> escapeUnless(Function<S, Boolean> shouldEscape, E escape) {
    return shouldEscape.apply(value) ? this : new ResolvedBuilder<>(escape);
  }

  public Builder<S, E> perform(Consumer<S> consumer) {
    consumer.accept(value);
    return this;
  }

  public <T> Builder<T, E> mapThrough(Function<S, Builder<T, E>> mapper) {
    return mapper.apply(value);
  }

  public <T, R> Builder<R, E> bindWith(
        Builder<T, E> other, BiFunction<S, T, Builder<R, E>> combiner
  ) {
    return other.mapThrough(value2 -> combiner.apply(value, value2));
  }

  public E resolveWith(Function<S, E> process) {
    return process.apply(value);
  }

}
