package fsc.service;

import fsc.entity.query.Query;
import fsc.service.query.QueryStringConverter;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QueryStringConverterTest {
  private static QueryStringConverter queryStringConverter = new QueryStringConverter();

  @Test
  public void stringsCorrespondToQueries() {
    assertStringCorrespondsToQuery("all", Query.always());
    assertStringCorrespondsToQuery("none", Query.never());
    assertStringCorrespondsToQuery("contract equals tenured",
                                   Query.has("contract", "tenured"));
    assertStringCorrespondsToQuery("division equals Arts and Letters",
                                   Query.has("division", "Arts and Letters"));
    assertStringCorrespondsToQuery("(contract equals tenured AND division equals Arts and Letters)",
                                   Query.all(Query.has("contract", "tenured"),
                                             Query.has("division", "Arts and Letters")));
    assertStringCorrespondsToQuery("(all AND none AND contract equals tenured)",
                                   Query.all(Query.always(), Query.never(),
                                             Query.has("contract", "tenured")));
    assertStringCorrespondsToQuery("(all OR none OR contract equals tenured)",
                                   Query.any(Query.always(), Query.never(),
                                             Query.has("contract", "tenured")));
    assertStringCorrespondsToQuery("(all AND (all OR none))",
                                   Query.all(Query.always(),
                                             Query.any(Query.always(), Query.never())));
    assertStringCorrespondsToQuery("((all OR none) AND (all OR none))",
                                   Query.all(Query.any(Query.always(), Query.never()),
                                             Query.any(Query.always(), Query.never())));
  }

  @Ignore
  @Test
  public void invalidStringsCannotBeParsed() {}

  private void assertStringCorrespondsToQuery(String string, Query query) {
    assertEquals(string, queryStringConverter.toString(query));
    assertEquals(queryStringConverter.fromString(string), query);
  }

  @Test
  public void noDelimiterScannerTests() {
    scannerCanReadPatternAtStartOf("\\(", "(");
    scannerCanReadPatternAtStartOf("\\s*", " ");
  }

  private void scannerCanReadPatternAtStartOf(String pattern, String string) {
    Scanner scanner = new Scanner(string);
    scanner.useDelimiter("");
    assertTrue(scanner.hasNext(pattern));
  }
}