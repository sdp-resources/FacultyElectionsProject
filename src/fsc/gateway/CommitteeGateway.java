package fsc.gateway;

import fsc.entity.Committee;

public interface CommitteeGateway {
  Committee getCommitteeFromCommitteeName(String name) throws NoCommitteeWithThatNameException;
  void addCommittee(Committee committee);
  void save();
  class NoCommitteeWithThatNameException extends Exception {}
}
