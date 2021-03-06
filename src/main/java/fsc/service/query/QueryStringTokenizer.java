package fsc.service.query;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.io.StreamTokenizer.TT_EOF;
import static java.io.StreamTokenizer.TT_WORD;

class QueryStringTokenizer {
  private static final String ALL = "all";
  private static final String NONE = "none";
  private static final String EQUALS = "equals";
  private static final String IS = "is";
  private static final String AND = "and";
  private static final String OR = "or";
  private static final String NOT = "not";

  private StreamTokenizer tokenizer;
  ParseToken nextToken = null;
  private int location = 0;

  public QueryStringTokenizer(String s) {
    Reader reader = new BufferedReader(new StringReader(s));
    tokenizer = new StreamTokenizer(reader);
  }

  ParseToken lookahead() {
    ParseToken lookaheadToken = produceNextToken();
    tokenizer.pushBack();
    return lookaheadToken;
  }

  boolean advance() {
    nextToken = produceNextToken();
    return reachedEndToken();
  }

  private boolean reachedEndToken() {
    return nextToken != ParseToken.end;
  }

  private ParseToken produceNextToken() {
    switch (readNextToken()) {
      case TT_EOF: return ParseToken.end;
      case TT_WORD: return readWord(tokenizer.sval);
      case '"': return string(tokenizer.sval);
      case '(': return leftParen();
      case ')': return rightParen();
    }
    throw new RuntimeException("Should not get here");
  }

  private ParseToken leftParen() {
    return ParseToken.leftParen;
  }

  private ParseToken rightParen() {
    return ParseToken.rightParen;
  }

  private ParseToken string(String string) {
    return ParseToken.string(string);
  }

  private ParseToken readWord(String word) {
    String lword = word.toLowerCase();
    if (lword.equals(ALL)) { return ParseToken.all; }
    if (lword.equals(NONE)) { return ParseToken.none; }
    if (lword.equals(EQUALS) || lword.equals(IS)) { return ParseToken.equals; }
    if (lword.equals(AND)) { return ParseToken.and; }
    if (lword.equals(OR)) { return ParseToken.or; }
    if (lword.equals(NOT)) { return ParseToken.not; }
    return ParseToken.name(word);
  }

  private int readNextToken() {
    try {
      int token = tokenizer.nextToken();
      advanceLocation(token);
      return token;
    } catch (IOException e) {
      throw new RuntimeException("This should not be happening");
    }
  }

  private void advanceLocation(int token) {
    switch (token) {
      case TT_WORD: location += tokenizer.sval.length();
        return;
      case '"': location += tokenizer.sval.length() + 2;
        return;
      case '(':
      case ')': location += 1;
        return;
      case TT_EOF: return;
    }
  }

  public List<ParseToken> readAll() {
    List<ParseToken> tokens = new ArrayList<>();
    boolean thereIsMore;
    do {
      thereIsMore = advance();
      tokens.add(nextToken);
    } while (thereIsMore);

    return tokens;
  }

  public int getLocation() {
    return location;
  }

  public static class ParseToken {
    public static ParseToken not = new ParseToken(TokenType.Not);
    public static ParseToken end = new ParseToken(TokenType.End);
    public static ParseToken all = new ParseToken(TokenType.All);
    public static ParseToken none = new ParseToken(TokenType.None);
    public static ParseToken equals = new ParseToken(TokenType.Equals);
    public static ParseToken leftParen = new ParseToken(TokenType.LeftParen);
    public static ParseToken rightParen = new ParseToken(TokenType.RightParen);
    public static ParseToken and = new ParseToken(TokenType.And);
    public static ParseToken or = new ParseToken(TokenType.Or);

    public final TokenType type;
    public final String value;

    boolean isNameToken() {
      return type.equals(TokenType.Name);
    }

    boolean isStringToken() {
      return type.equals(TokenType.String);
    }

    boolean isEqualsToken() {
      return equals(equals);
    }

    boolean isAllToken() {
      return equals(all);
    }

    boolean isNoneToken() {
      return equals(none);
    }

    boolean isLeftParenToken() {
      return equals(leftParen);
    }

    boolean isRightParensToken() {
      return equals(rightParen);
    }

    boolean isAndToken() {
      return equals(and);
    }

    boolean isOrToken() {
      return equals(or);
    }

    boolean isEndToken() {
      return equals(end);
    }

    boolean isNotToken() {
      return equals(not);
    }

    public enum TokenType {
      All, None, And, Or, Not, Equals, String, Name, LeftParen, RightParen, End
    }

    public ParseToken(TokenType type, String value) {
      this.type = type;
      this.value = value;
    }

    public ParseToken(TokenType type) {
      this(type, "");
    }

    public static ParseToken string(String string) {
      return new ParseToken(TokenType.String, string);
    }

    public static ParseToken name(String name) {
      return new ParseToken(TokenType.Name, name);
    }

    public String toString() {
      if (value.equals("")) { return "Token{" + type + ")"; }
      return "Token{" + type + ", '" + value + "'}";
    }

    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      ParseToken that = (ParseToken) o;
      return type == that.type && value.equals(that.value);
    }

    public int hashCode() {
      return Objects.hash(type, value);
    }
  }
}
