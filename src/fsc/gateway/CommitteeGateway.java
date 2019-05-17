package fsc.gateway;

import fsc.entity.Committee;

public interface CommitteeGateway {
  Committee getCommitteeFromCommitteeName(String name) throws Exception;
  void addCommittee(Committee makeCommitteeFromRequest);
}
