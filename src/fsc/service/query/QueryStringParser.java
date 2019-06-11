package fsc.service.query;

import fsc.entity.query.Query;
import fsc.service.query.QueryStringTokenizer.ParseToken;

public class QueryStringParser {

  private final QueryStringTokenizer tokenizer;

  public QueryStringParser(String s) {
    tokenizer = new QueryStringTokenizer(s);
  }

  public Query parse() {
    return advanceAndReadQuery();
  }

  private Query advanceAndReadQuery() {
    return advanceAndGetToken().isNotToken() ? Query.not(advanceAndReadQuery())
                                             : continueAfter(readSimpleQuery());
  }

  private Query continueAfter(Query simpleQuery) {
    if (checkAndSkip(lookahead().isAndToken())) {
      return Query.all(simpleQuery, advanceAndReadQuery());
    }
    if (checkAndSkip(lookahead().isOrToken())) {
      return Query.any(simpleQuery, advanceAndReadQuery());
    }
    if (checkAndSkip(lookahead().isEndToken())) { return simpleQuery; }
    if (lookahead().isRightParensToken()) { return simpleQuery; }
    throw new RuntimeException("Expected AND, OR, or the end of the query.");
  }

  private Query readSimpleQuery() {
    if (nextToken().isAllToken()) { return Query.always(); }
    if (nextToken().isNoneToken()) { return Query.never(); }
    if (nextToken().isLeftParenToken()) { return readQueryAndCloseParentheses(); }
    if (nextToken().isNameToken()) { return readNamedQueryOrAttributeQuery(nextToken()); }
    throw new RuntimeException("Expected a name here");
  }

  private Query readNamedQueryOrAttributeQuery(ParseToken nextToken) {
    if (checkAndSkip(lookahead().isEqualsToken())) {
      ParseToken valueToken = advanceAndGetToken();
      if (valueToken.isNameToken() || valueToken.isStringToken()) {
        return Query.has(nextToken.value, valueToken.value);
      } else {
        throw new RuntimeException("Expected string or name");
      }
    } else {
      // TODO: Need to introduce named queries
      return null;
    }
  }

  private Query readQueryAndCloseParentheses() {
    Query query = advanceAndReadQuery();
    if (advanceAndGetToken().isRightParensToken()) {
      return query;
    }
    throw new RuntimeException("Expected parentheses");
  }

  private boolean checkAndSkip(boolean b) {
    if (b) { skip(); }
    return b;
  }

  private ParseToken skip() {
    return advanceAndGetToken();
  }

  private ParseToken advanceAndGetToken() {
    tokenizer.advance();
    return nextToken();
  }

  private ParseToken nextToken() {
    return tokenizer.nextToken;
  }

  private ParseToken lookahead() {
    return tokenizer.lookahead();
  }

}
