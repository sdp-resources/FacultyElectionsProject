package fsc.gateway;

import fsc.entity.Committee;

public interface CommitteeGateway {
  void getCommitteeFromCommitteeName(String name);
  void addCommittee(Committee makeCommitteeFromRequest);
}
