package fsc.gateway;

import fsc.entity.Committee;

public interface CommitteeGateway {
  Committee getCommitteeFromCommitteeName(String name) throws NoCommitteeWithThatNameException;
  void addCommittee(Committee makeCommitteeFromRequest);
  class NoCommitteeWithThatNameException extends Exception {}
}
