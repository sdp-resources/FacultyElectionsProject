package fsc.mock;

import fsc.interactor.Interactor;
import fsc.request.Request;
import fsc.response.Response;

public class SimpleInteractorSpy extends Interactor {
  private String name;
  public boolean handleCalled = false;

  public SimpleInteractorSpy(String name) {
    this.name = name;
  }

  public Response handle(Request request) {
    handleCalled = true;
    return null;
  }

  public String toString() {
    return "Interactor " + name;
  }

}
