package fsc.interactor;

import fsc.entity.Rule;
import fsc.request.UpdateRuleRequest;
import org.junit.Test;

public class UpdateRuleInteractorTest {
  @Test
  public void canExecuteInteractorWithDummyRequest() {
    UpdateRuleGatewaySpy spy = new UpdateRuleGatewaySpy();
    UpdateRuleInteractor interactor = new UpdateRuleInteractor(spy);

    int ruleID = 1;
    boolean requiresActive = false;
    boolean requiresOneYearCooldown = false;
    int yearsPerTerm = 0;
    int consecutiveTermsBeforeCooldown = 0;
    Rule.TenureRequirements tenureRequirement = Rule.TenureRequirements.None;
    Rule.Divisions division = Rule.Divisions.AtLarge;

    interactor.execute(
          new UpdateRuleRequest(ruleID, requiresActive, requiresOneYearCooldown, yearsPerTerm,
                                consecutiveTermsBeforeCooldown, tenureRequirement, division));
  }
}