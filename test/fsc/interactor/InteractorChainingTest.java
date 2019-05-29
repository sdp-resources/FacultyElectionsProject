package fsc.interactor;

import fsc.request.Request;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import org.junit.Test;

import static org.junit.Assert.*;

public class InteractorChainingTest {
  @Test
  public void interactorsAreAddedInOrder() {
    Interactor interactor1 = new RejectingInteractorSpy("1");
    Interactor interactor2 = new RejectingInteractorSpy("2");
    Interactor interactor3 = new RejectingInteractorSpy("3");
    Interactor interactor = interactor1.append(interactor2).append(interactor3);
    assertSame(interactor, interactor1);
    assertSame(interactor.next, interactor2);
    assertSame(interactor.next.next, interactor3);
    assertNull(interactor.next.next.next);
  }

  @Test
  public void requestMovesToNextInteractorUntilAHandlerIsFound() {
    RejectingInteractorSpy interactor1 = new RejectingInteractorSpy("1");
    AcceptingInteractorSpy interactor2 = new AcceptingInteractorSpy("2");
    RejectingInteractorSpy interactor3 = new RejectingInteractorSpy("2");
    interactor1.append(interactor2).append(interactor3);
    interactor1.handle(new RequestStub());
    assertTrue(interactor1.canHandleCalled);
    assertFalse(interactor1.executeCalled);
    assertTrue(interactor2.canHandleCalled);
    assertTrue(interactor2.executeCalled);
    assertFalse(interactor3.canHandleCalled);
    assertFalse(interactor3.executeCalled);
  }

  @Test
  public void whenLastInteractorCannotHandleRequestErrorResponseIsReturned() {
    Interactor interactor1 = new RejectingInteractorSpy("1");
    Interactor interactor2 = new RejectingInteractorSpy("2");
    interactor1.append(interactor2);
    Response response = interactor1.handle(new RequestStub());
    assertEquals(ErrorResponse.cannotHandle(), response);
  }

  private class RejectingInteractorSpy extends Interactor {
    private String name;
    public boolean executeCalled = false;
    public boolean canHandleCalled = false;

    public RejectingInteractorSpy(String name) {
      this.name = name;
    }

    public String toString() {
      return "Interactor " + name;
    }

    public boolean canHandle(Request request) {
      canHandleCalled = true;
      return false;
    }

    public Response execute(Request request) {
      executeCalled = true;
      return null;
    }
  }

  public class AcceptingInteractorSpy extends RejectingInteractorSpy {
    public AcceptingInteractorSpy(String name) {
      super(name);
    }

    public boolean canHandle(Request request) {
      return !super.canHandle(request);
    }

  }

  private class RequestStub extends Request {}
}