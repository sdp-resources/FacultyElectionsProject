package fsc.service.query;

import fsc.service.query.QueryStringTokenizer.ParseToken;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class QueryStringTokenizerTest {

  @Test
  public void stringsCorrespondToQueries() {
    assertStringProducesTokens("    ",
                               List.of(ParseToken.end));
    assertStringProducesTokens(" all",
                               List.of(ParseToken.all, ParseToken.end));
    assertStringProducesTokens("none",
                               List.of(ParseToken.none, ParseToken.end));
    assertStringProducesTokens("contract equals tenured",
                               List.of(ParseToken.name("contract"),
                                       ParseToken.equals,
                                       ParseToken.name("tenured"),
                                       ParseToken.end));
    assertStringProducesTokens("contract equals \"tenured\"",
                               List.of(ParseToken.name("contract"),
                                       ParseToken.equals,
                                       ParseToken.string("tenured"),
                                       ParseToken.end));
    assertStringProducesTokens("division is \"Arts and Letters\"",
                               List.of(ParseToken.name("division"),
                                       ParseToken.equals,
                                       ParseToken.string("Arts and Letters"),
                                       ParseToken.end));
    assertStringProducesTokens("(contract equals tenured AND division equals \"Arts and Letters\")",
                               List.of(ParseToken.leftParen,
                                       ParseToken.name("contract"), ParseToken.equals,
                                       ParseToken.name("tenured"),
                                       ParseToken.and,
                                       ParseToken.name("division"), ParseToken.equals,
                                       ParseToken.string("Arts and Letters"),
                                       ParseToken.rightParen, ParseToken.end));
    assertStringProducesTokens("(all OR none OR contract equals tenured)",
                               List.of(ParseToken.leftParen,
                                       ParseToken.all,
                                       ParseToken.or, ParseToken.none,
                                       ParseToken.or, ParseToken.name("contract"),
                                       ParseToken.equals,
                                       ParseToken.name("tenured"),
                                       ParseToken.rightParen, ParseToken.end));
  }

  private void assertStringProducesTokens(String string, List<ParseToken> tokens) {
    assertEquals(tokens, new QueryStringTokenizer(string).readAll());
  }
}