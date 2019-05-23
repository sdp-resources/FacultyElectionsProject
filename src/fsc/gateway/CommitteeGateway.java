package fsc.gateway;

import fsc.entity.Committee;

public interface CommitteeGateway {
  Committee getCommitteeFromCommitteeName(String name) throws UnknownCommitteeException;
  void addCommittee(Committee committee);
  void save();
  class UnknownCommitteeException extends Exception {}
}
