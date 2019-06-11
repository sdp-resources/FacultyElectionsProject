package fsc.service.query;

import fsc.entity.query.Query;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QueryStringConverterTest {
  private static QueryStringConverter queryStringConverter = new QueryStringConverter();

  @Test
  public void stringsCorrespondToQueries() {
    assertStringCorrespondsToQuery("all", Query.always());
    assertStringCorrespondsToQuery("none", Query.never());
    assertStringCorrespondsToQuery("contract equals \"tenured\"",
                                   Query.has("contract", "tenured"));
    assertStringCorrespondsToQuery("division equals \"Arts and Letters\"",
                                   Query.has("division", "Arts and Letters"));
    assertStringCorrespondsToQuery("(contract equals \"tenured\" and division equals " +
                                         "\"Arts and Letters\")",
                                   Query.all(Query.has("contract", "tenured"),
                                             Query.has("division", "Arts and Letters")));
    assertStringCorrespondsToQuery("(all and none and contract equals \"tenured\")",
                                   Query.all(Query.always(), Query.never(),
                                             Query.has("contract", "tenured")));
    assertStringCorrespondsToQuery("(all or none or contract equals \"tenured\")",
                                   Query.any(Query.always(), Query.never(),
                                             Query.has("contract", "tenured")));
    assertStringCorrespondsToQuery("(all and (all or none))",
                                   Query.all(Query.always(),
                                             Query.any(Query.always(), Query.never())));
    assertStringCorrespondsToQuery("((all or none) and (all or none))",
                                   Query.all(Query.any(Query.always(), Query.never()),
                                             Query.any(Query.always(), Query.never())));
  }

  @Ignore
  @Test
  public void invalidStringsCannotBeParsed() {}

  private void assertStringCorrespondsToQuery(String string, Query query) {
    assertEquals(string, queryStringConverter.toString(query));
    assertEquals(query.simplify(), queryStringConverter.fromString(string).simplify());
  }

  @Test
  public void newParserStringsCorrespondToQueries() {
    assertStringParserCorrespondsToQuery("all", Query.always());
    assertStringParserCorrespondsToQuery("none", Query.never());
    assertStringParserCorrespondsToQuery("contract equals tenured",
                                         Query.has("contract", "tenured"));
    assertStringParserCorrespondsToQuery("division equals \"Arts and Letters\"",
                                         Query.has("division", "Arts and Letters"));
    assertStringParserCorrespondsToQuery("(contract equals tenured AND division equals " +
                                               "\"Arts and Letters\")",
                                         Query.all(Query.has("contract", "tenured"),
                                                   Query.has("division", "Arts and Letters")));
    assertStringParserCorrespondsToQuery("(all AND none AND contract equals tenured)",
                                         Query.all(Query.always(), Query.never(),
                                                   Query.has("contract", "tenured")));
    assertStringParserCorrespondsToQuery("(all OR none OR contract equals tenured)",
                                         Query.any(Query.always(), Query.never(),
                                                   Query.has("contract", "tenured")));
    assertStringParserCorrespondsToQuery("(all AND (all OR none))",
                                         Query.all(Query.always(),
                                                   Query.any(Query.always(), Query.never())));
    assertStringParserCorrespondsToQuery("((all OR none) AND (all OR none))",
                                         Query.all(Query.any(Query.always(), Query.never()),
                                                   Query.any(Query.always(), Query.never())));
  }

  private void assertStringParserCorrespondsToQuery(String string, Query query) {
    assertEquals(query.simplify(), new QueryStringParser(string).parse().simplify());
  }

}