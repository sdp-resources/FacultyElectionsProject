package fsc.interactor;

import fsc.mock.SimpleInteractorSpy;
import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

public class InteractorChainingTest {
  @Test
  public void interactorsAreAddedInOrder() {
    Interactor interactor1 = new SimpleInteractorSpy("1");
    Interactor interactor2 = new SimpleInteractorSpy("2");
    Interactor interactor3 = new SimpleInteractorSpy("3");
    Interactor interactor = interactor1.append(interactor2).append(interactor3);
    assertSame(interactor, interactor1);
    assertSame(interactor.next, interactor2);
    assertSame(interactor.next.next, interactor3);
    assertNull(interactor.next.next.next);
  }

}