import org.junit.Test;

public class UpdateRuleInteractorTest {
  @Test
  public void canExecuteInteractor(){
    UpdateRuleInteractor interactor = new UpdateRuleInteractor();
    interactor.execute(new UpdateRuleRequest());
  }
}
