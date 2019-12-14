package webserver;

import com.github.jknack.handlebars.Handlebars;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class HandlebarsHelpersTest {

  Handlebars handlebars;

  @Before
  public void setUp() throws Exception {
    handlebars = new Handlebars();
  }

  @Test
  public void canLoadHelper() throws IOException {
    handlebars.registerHelper("path", HandlebarsHelpers.path());
    assertCompilationGives("Stuff here {{path 'ballot' 5}} stuff later",
                           "Stuff here /ballot/5 stuff later");
    assertCompilationGives("Stuff here {{path 'ballot'}} stuff later",
                           "Stuff here /ballot/:electionId stuff later");
  }

  @Test(expected = RuntimeException.class)
  public void throwsErrorWhenNameDoesNotExist() throws IOException {
    handlebars.registerHelper("path", HandlebarsHelpers.path());
    assertCompilationGives("Stuff here {{path 'whoBallot' 5}} stuff later",
                           "Stuff here /ballot/5 stuff later");

  }

  private void assertCompilationGives(String input, String expected) throws IOException {
    String result = handlebars.compileInline(input).apply(null);
    assertEquals(expected, result);
  }
}