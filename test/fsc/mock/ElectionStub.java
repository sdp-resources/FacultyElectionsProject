package fsc.mock;

import fsc.entity.Ballot;
import fsc.entity.Committee;
import fsc.entity.Election;

public class ElectionStub extends Election {
  private final Ballot ballot;
  private final Committee committee;

  public ElectionStub(Ballot ballot, Committee committee) {
    this.ballot = ballot;
    this.committee = committee;
  }
}
