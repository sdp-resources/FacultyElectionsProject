package fsc.service.query;

import fsc.entity.query.Query;
import fsc.entity.query.UnknownNamedQuery;

public class SimpleQueryStringParser implements QueryStringParser {
  private final QueryStringTokenizer tokenizer;

  public SimpleQueryStringParser(String s) {tokenizer = new QueryStringTokenizer(s);}

  public Query parse() throws QueryParseException {
    return advanceAndReadQuery();
  }

  private Query advanceAndReadQuery() throws QueryParseException {
    return advanceAndGetToken().isNotToken() ? Query.not(advanceAndReadQuery())
                                             : continueAfter(readSimpleQuery());
  }

  private Query continueAfter(Query simpleQuery) throws QueryParseException {
    if (checkAndSkip(lookahead().isAndToken())) {
      return Query.all(simpleQuery, advanceAndReadQuery());
    }
    if (checkAndSkip(lookahead().isOrToken())) {
      return Query.any(simpleQuery, advanceAndReadQuery());
    }
    if (checkAndSkip(lookahead().isEndToken())) { return simpleQuery; }
    if (lookahead().isRightParensToken()) { return simpleQuery; }
    throw generateException("Expected AND, OR, or the end of the query.");
  }

  private Query readSimpleQuery() throws QueryParseException {
    if (nextToken().isAllToken()) { return Query.always(); }
    if (nextToken().isNoneToken()) { return Query.never(); }
    if (nextToken().isLeftParenToken()) { return readQueryAndCloseParentheses(); }
    if (nextToken().isNameToken()) { return readNamedQueryOrAttributeQuery(nextToken().value); }
    throw generateException("Expected a name here");
  }

  private Query readNamedQueryOrAttributeQuery(String name) throws QueryParseException {
    if (checkAndSkip(lookahead().isEqualsToken())) {
      return processKeyValueCase(name, advanceAndGetToken());
    } else {
      throwUnlessNamedQueryExists(name);
      return Query.named(name, getQueryNamed(name));
    }
  }

  private Query processKeyValueCase(String name, QueryStringTokenizer.ParseToken valueToken)
        throws QueryParseException {
    throwUnlessValueIsStringOrName(valueToken);
    throwUnlessIsAllowedValueForKey(name, valueToken.value);
    return Query.has(name, valueToken.value);
  }

  private void throwUnlessNamedQueryExists(String key) throws QueryParseException {
    if (!hasQueryNamed(key)) {
      throw generateException("No query named: " + key);
    }
  }

  private void throwUnlessValueIsStringOrName(QueryStringTokenizer.ParseToken token)
        throws QueryParseException {
    if (!token.isNameToken() && !token.isStringToken()) {
      throw generateException("Expected string or name");
    }
  }

  private void throwUnlessIsAllowedValueForKey(String key, String value)
        throws QueryParseException {
    if (!isValidValueForKey(key, value)) {
      throw generateException("Invalid value \"" + value + "\" for key \"" + key + "\"");
    }
  }

  private QueryParseException generateException(String message) {
    return new QueryParseException(message, tokenizer.getLocation());
  }

  private Query readQueryAndCloseParentheses() throws QueryParseException {
    Query query = advanceAndReadQuery();
    if (advanceAndGetToken().isRightParensToken()) {
      return query;
    }
    throw generateException("Expected parentheses");
  }

  private boolean checkAndSkip(boolean b) {
    if (b) { skip(); }
    return b;
  }

  private void skip() {
    advanceAndGetToken();
  }

  private QueryStringTokenizer.ParseToken advanceAndGetToken() {
    tokenizer.advance();
    return nextToken();
  }

  private QueryStringTokenizer.ParseToken nextToken() {
    return tokenizer.nextToken;
  }

  private QueryStringTokenizer.ParseToken lookahead() {
    return tokenizer.lookahead();
  }

  protected Query getQueryNamed(String name) {
    return new UnknownNamedQuery();
  }

  protected boolean hasQueryNamed(String key) {
    return true;
  }

  protected boolean isValidValueForKey(String key, String value) {
    return true;
  }

}
