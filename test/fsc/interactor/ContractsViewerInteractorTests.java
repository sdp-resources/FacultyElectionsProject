package fsc.interactor;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ContractsViewerInteractorTests {
  @Test
  public void CanCreateContractsViewerInteractor(){
    ContractsViewerInteractor viewer = new ContractsViewerInteractor();
    assertTrue(viewer instanceof ContractsViewerInteractor);
  }
}
