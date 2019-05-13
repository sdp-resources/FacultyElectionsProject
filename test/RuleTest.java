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
  public void CanSetRuleRequiresActiveMembers()
  {

    rule.setRequiresActive(true);

    assertEquals(true, rule.getRequiresActive());
  }

  @Test
  public void CanSetRuleDoesNotRequireActiveMembers()
  {
    rule.setRequiresActive(false);

    assertEquals(false, rule.getRequiresActive());
  }

  @Test
  public void CanSetRequiresOneYearCooldown(){
    rule.setRequiresOneYearCooldown(true);

    assertEquals(true, rule.getRequiresOneYearCooldown());
  }

  @Test
  public void CanSetDoesNotRequireOneYearCooldown(){
    rule.setRequiresOneYearCooldown(false);

    assertEquals(false, rule.getRequiresOneYearCooldown());
  }
}