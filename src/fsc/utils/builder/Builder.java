package fsc.utils.builder;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Builder<S, E> {
  static <S, E> Builder<S, E> ofValue(S value) {
    return new PendingBuilder<>(value);
  }
  static <S, E> Builder<S, E> ofResponse(E response) {
    return new ResolvedBuilder<>(response);
  }

  static <S, T, E> Function<S, Builder<T, E>> lift(Function<S, T> func) {
    return func.andThen(Builder::ofValue);
  }

  static <S, T, R, E> BiFunction<S, T, Builder<R, E>> lift(BiFunction<S, T, R> func) {
    return func.andThen(Builder::ofValue);
  }

  Builder<S, E> escapeIf(Function<S, Boolean> shouldEscape, E escape);
  Builder<S, E> escapeUnless(Function<S, Boolean> shouldEscape, E escape);
  Builder<S, E> perform(Consumer<S> consumer);
  <T> Builder<T, E> mapThrough(Function<S, Builder<T, E>> mapper);
  <T, R> Builder<R, E> bindWith(Builder<T, E> other, BiFunction<S, T, Builder<R, E>> combiner);
  E resolveWith(Function<S, E> process);
}
