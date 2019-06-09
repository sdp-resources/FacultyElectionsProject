package fsc.service.query;

import fsc.entity.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

class QueryStringParser {
  private Scanner scanner;

  public QueryStringParser(String s) {
    scanner = new Scanner(s);
  }

  public Query parse() {
    Query query = processNext();
    if (scanner.hasNext()) {
      throw new InvalidStringException();
    }
    return query;
  }

  private Query processNext() {
    if (tryReadWord("all")) return Query.always();
    if (tryReadWord("none")) return Query.never();
    if (tryReadOpenParens()) return processParentheses();
    return processSimpleCase();
  }

  private Query processSimpleCase() {
    String key = readNext();
    scanner.next("equals");
    String value = readPhrase();
    return Query.has(key, value);
  }

  private Query processParentheses() {
    List<Query> queries = new ArrayList<>();
    return processAndPopulateList(queries);
  }

  private Query processAndPopulateList(List<Query> queries) {
    String connective = null;
    addNextQuery(queries);
    while (!tryReadCloseParens()) {
      connective = scanPastConnective(connective);
      addNextQuery(queries);
    }
    return queries.size() == 0 ? queries.get(0)
                               : connective.equals("AND")
                                 ? Query.all(queries)
                                 : Query.any(queries);
  }

  private String scanPastConnective(String connective) {
    return connective == null ? scanner.next("AND|OR").trim()
                              : scanner.next(connective);
  }

  private boolean addNextQuery(List<Query> queries) {
    return queries.add(processNext());
  }

  private boolean tryReadWord(String word) {
    return tryReadWithDelimiter(word, "AND|OR|\\)|\\s+");
  }

  private boolean tryReadOpenParens() {
    return tryReadWithDelimiter("\\(", "");
  }

  private boolean tryReadCloseParens() {
    return tryReadWithDelimiter("\\)", "");
  }

  private boolean tryReadWithDelimiter(String pattern, String delimiter) {
    return doWithDelimiter(delimiter, () -> {
      skipWhitespace();
      return checkAndSkip(pattern);
    });
  }

  private void skipWhitespace() {
    while (scanner.hasNext("\\s")) { scanner.next("\\s"); }
  }

  private Boolean checkAndSkip(String pattern) {
    boolean check = scanner.hasNext(pattern);
    if (check) scanner.next(pattern);
    return check;
  }

  private String readPhrase() {
    return doWithDelimiter("AND|OR|\\)", () -> readNext().trim());
  }

  private String readNext() {
    return scanner.next();
  }

  private <T> T doWithDelimiter(String delimiter, Supplier<T> supplier) {
    scanner.useDelimiter(delimiter);
    T value = supplier.get();
    scanner.reset();
    return value;
  }

  public static class InvalidStringException extends RuntimeException {
    public InvalidStringException() {}
  }
}
