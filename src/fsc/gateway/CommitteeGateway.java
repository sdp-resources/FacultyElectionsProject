package fsc.gateway;

import fsc.entity.Committee;

import java.util.List;

public interface CommitteeGateway {
  List<Committee> getCommittees();
  Committee getCommittee(String name) throws UnknownCommitteeException;
  void addCommittee(Committee committee);
  void save();
  boolean hasCommittee(String name);
  class UnknownCommitteeException extends Exception {}
}
