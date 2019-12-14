package webserver;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import fsc.entity.Election;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MyHandlebarsTemplateEngineTest {

  private MyHandlebarsTemplateEngine engine;
  private Handlebars handlebars;

  @Before
  public void setUp() {
    engine = new MyHandlebarsTemplateEngine();
    handlebars = engine.getHandlebars();
  }

  @Test
  public void datetimeHelperWorks() throws IOException {
    assertTemplateInContextProduces(
          "{{datetime this 'dd-MM-yyyy hh:mm'}}",
          LocalDateTime.of(2019, 2, 3, 4, 22),
          "03-02-2019 04:22");
  }

  @Test
  public void remainderHelperWorks() throws IOException {
    assertTemplateInContextProduces(
          "{{remainder this 3}}", 5, "2");
    assertTemplateInContextProduces(
          "{{remainder this 3}}", 7, "1");
  }

  @Test
  public void remainderHelperWithinEq() throws IOException {
    assertTemplateInContextProduces(
          "{{#each this}}{{#eq 0 (remainder this 3)}}{{this}}{{/eq}}{{/each}}",
          List.of(0, 1, 2, 3, 4, 5, 6, 7),
          "036");

  }

  @Test
  public void electionStatesAreRead() throws IOException {
    assertTemplateInContextProduces(
          "{{#each this}}{{this}} {{/each}}",
          List.of(Election.State.values()),
          "Setup DecideToStand Vote Closed ");
  }

  @Test
  public void electionStatesCanCompareToString() throws IOException {
    assertTemplateInContextProduces(
          "{{#eq this.string 'Vote'}}yes{{/eq}}",
          Election.State.Vote,
          "yes");
  }

  private void assertTemplateInContextProduces(String template, Object context, String expected)
        throws IOException {
    Template compiled = handlebars.compileInline(template);
    String result = compiled.apply(context);
    assertEquals(expected, result);
  }
}