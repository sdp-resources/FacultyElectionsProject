package fsc.service;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MyUtils {
  public static <T, S> List<T> convertList(Collection<S> items, Function<S, T> f) {
    return items.stream().map(f).collect(Collectors.toList());
  }
}
