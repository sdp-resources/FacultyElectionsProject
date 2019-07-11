package fsc.entity;

import fsc.entity.query.Query;

import java.util.List;

public interface EntityFactory {
  Election createElection(
        Seat seat, Committee committee, Query query, Ballot ballot
  );
  Seat createSeat(String name, Query defaultQuery);
  Vote createVote(Profile... profiles);
  Vote createVote(List<Profile> profiles);
  List<Vote> createVoteSnapshot(List<Vote> votes);
  Voter createVoter(Profile voter, Election election);
  VoteRecord createVoteRecord(Voter voter, List<Profile> votes);
  Division createDivision(String newDivisionName);
  Committee createCommittee(String name, String description);
  Candidate createCandidate(Profile profile);
  Profile createProfile(
        String name, String username, String division, String contract
  );
}
