import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RuleTest {
  @Test
  public void CanCreateRuleThatRequiresActiveMembers()
  {
    Rule rule = new Rule();
    rule.setRequiresActive(true);

    assertEquals(true, rule.getRequiresActive());
  }
}