package fsc.entity;

import fsc.entity.query.Query;

import java.util.List;

public interface EntityFactory {
  List<Candidate> createBallot();

  Election createElection(
        Seat seat
  );
  Seat createSeat(String name, Query defaultQuery, Committee committee);
  Vote createVote(Profile... profiles);
  Vote createVote(List<Profile> profiles);
  List<Vote> createVoteSnapshot(List<Vote> votes);
  Voter createVoter(Profile voter, Election election);
  VoteRecord createVoteRecord(Election election, List<Profile> votes);
  Division createDivision(String newDivisionName);
  ContractType createContractType(String contractType);
  Committee createCommittee(String name, String description, Query voterQuery);
  Candidate createCandidate(Profile profile, Election election);
  Profile createProfile(
        String name, String username, String division, String contract
  );
}
