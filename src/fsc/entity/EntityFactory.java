package fsc.entity;

import fsc.entity.query.Query;

import java.util.List;

public interface EntityFactory {
  Ballot createBallot();

  Election createElection(
        Seat seat, Query query, Ballot ballot
  );
  Seat createSeat(String name, Query defaultQuery, Committee committee);
  Vote createVote(Profile... profiles);
  Vote createVote(List<Profile> profiles);
  List<Vote> createVoteSnapshot(List<Vote> votes);
  Voter createVoter(Profile voter, Election election);
  VoteRecord createVoteRecord(Voter voter, List<Profile> votes);
  Division createDivision(String newDivisionName);
  ContractType createContractType(String contractType);
  Committee createCommittee(String name, String description);
  Candidate createCandidate(Profile profile, Ballot ballot);
  Profile createProfile(
        String name, String username, String division, String contract
  );
}
