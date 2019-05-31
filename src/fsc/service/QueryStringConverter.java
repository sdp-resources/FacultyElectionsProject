package fsc.service;

import fsc.entity.query.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QueryStringConverter implements Query.QueryVisitor {

  public String visit(AndQuery query) {
    if (query.queries.length == 0) { return "all"; }
    String output = "(";
    for (int i = 0; i < query.queries.length; i++) {
      output += visit(query.queries[i]);
      if (i < query.queries.length - 1) output += " AND ";
    }
    output += ")";
    return output;
  }

  public String visit(OrQuery query) {
    if (query.queries.length == 0) { return "none"; }
    String output = "(";
    for (int i = 0; i < query.queries.length; i++) {
      output += visit(query.queries[i]);
      if (i < query.queries.length - 1) output += " OR ";
    }
    output += ")";
    return output;
  }

  public String visit(AttributeQuery query) {
    return query.key + " equals " + query.value;
  }

  public Query fromString(String s) {
    return new StringParser(s).parse();
  }

  public String toString(Query query) {
    return (String) visit(query);
  }

  private class StringParser {
    private Scanner scanner;

    public StringParser(String s) {
      scanner = new Scanner(s);
    }

    public Query parse() {
      System.out.println();
      Query query = processNext();
      if (scanner.hasNext()) {
        throw new InvalidStringException();
      }
      return query;
    }

    private Query processNext() {
      if (tryReadWord("all")) {
        return Query.always();
      } else if (tryReadWord("none")) {
        return Query.never();
      } else if (tryReadParens("\\(")) {
        return processParentheses();
      } else {
        return processSimpleCase();
      }
    }

    private boolean tryReadWord(String word) {
      scanner.useDelimiter("AND|OR|\\)|\\s+");
      boolean check = scanner.hasNext(word);
      if (check) scanner.next(word);
      return check;
    }

    private boolean tryReadParens(String pattern) {
      scanner.useDelimiter("");
      while (scanner.hasNext("\\s")) { scanner.next("\\s"); }
      boolean check = scanner.hasNext(pattern);
      if (check) scanner.next(pattern);
      scanner.reset();
      return check;
    }

    private Query processSimpleCase() {
      String key =  scanner.next();
      scanner.next("equals");
      scanner.useDelimiter("AND|OR|\\)");
      String value = scanner.next().trim();
      scanner.reset();
      return Query.has(key, value);
    }

    private Query processParentheses() {
      List<Query> queries = new ArrayList<>();
      queries.add(processNext());
      if (tryReadParens("\\)")) {
        return queries.get(0);  // case of single
      }
      String connective = scanner.next("AND|OR").trim();
      queries.add(processNext());
      while(!tryReadParens("\\)")) {
        scanner.next(connective);
        queries.add(processNext());
      }
      return connective.equals("AND") ? Query.all(queries) : Query.any(queries);
    }
  }

  private class InvalidStringException extends RuntimeException {}
}
