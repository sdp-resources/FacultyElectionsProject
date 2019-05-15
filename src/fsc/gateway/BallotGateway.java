package fsc.gateway;

import fsc.entity.Ballot;

public interface BallotGateway {

  Ballot getBallot(String id);
}
