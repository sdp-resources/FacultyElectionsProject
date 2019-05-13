import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RuleTest {
  private Rule rule;

  @Before
  public void setup()
  {
    rule = new Rule();
  }


  @Test
  public void CanSetRuleToRequireActiveMembers()
  {

    rule.setRequiresActive(true);

    assertEquals(true, rule.getRequiresActive());
  }

  @Test
  public void CanSetRuleToNotRequireActiveMembers()
  {
    rule.setRequiresActive(false);

    assertEquals(false, rule.getRequiresActive());
  }
}