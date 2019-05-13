import org.junit.Test;

public class UpdateRuleInteractorTest {
  @Test
  public void canExecuteInteractorWithDummyRequest(){
    UpdateRuleInteractor interactor = new UpdateRuleInteractor();

    boolean requiresActive = false;
    boolean requiresOneYearCooldown = false;
    int yearsPerTerm = 0;
    int consecutiveTermsBeforeCooldown = 0;
    Rule.TenureRequirements tenureRequirement = Rule.TenureRequirements.None;
    Rule.Divisions division = Rule.Divisions.AtLarge;

    interactor.execute(new UpdateRuleRequest(requiresActive, requiresOneYearCooldown,
                                             yearsPerTerm, consecutiveTermsBeforeCooldown,
                                             tenureRequirement, division));
  }
}