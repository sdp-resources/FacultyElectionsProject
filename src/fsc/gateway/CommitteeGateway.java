package fsc.gateway;

import fsc.entity.Committee;

public interface CommitteeGateway {
  Committee getCommittee(String name) throws UnknownCommitteeException;
  void addCommittee(Committee committee);
  void save();
  boolean hasCommittee(String name);
  class UnknownCommitteeException extends Exception {}
}
