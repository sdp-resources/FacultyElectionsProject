package fsc.interactor;

import fsc.request.Request;
import fsc.response.Response;
import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

public class InteractorChainingTest {
  @Test
  public void interactorsAreAddedInOrder() {
    Interactor interactor1 = new InteractorStub("1");
    Interactor interactor2 = new InteractorStub("2");
    Interactor interactor3 = new InteractorStub("3");
    Interactor interactor = interactor1.append(interactor2).append(interactor3);
    assertSame(interactor, interactor1);
    assertSame(interactor.next, interactor2);
    assertSame(interactor.next.next, interactor3);
    assertNull(interactor.next.next.next);
  }

  private class InteractorStub extends Interactor {
    private String name;

    public InteractorStub(String name) {
      this.name = name;
    }

    public String toString() {
      return "Interactor " + name;
    }

    public boolean canHandle(Request request) {
      return false;
    }

    public Response execute(Request request) {
      return null;
    }
  }
}