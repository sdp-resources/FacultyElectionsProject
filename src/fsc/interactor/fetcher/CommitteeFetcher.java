package fsc.interactor.fetcher;

import fsc.entity.Committee;
import fsc.entity.EntityFactory;
import fsc.entity.Seat;
import fsc.entity.query.Query;
import fsc.gateway.CommitteeGateway;
import fsc.gateway.ProfileGateway;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.utils.builder.Builder;

import java.util.List;
import java.util.function.BiFunction;

public class CommitteeFetcher extends ProfileFetcher {
  CommitteeGateway committeeGateway;
  private EntityFactory entityFactory;

  public CommitteeFetcher(
        CommitteeGateway committeeGateway, ProfileGateway profileGateway,
        EntityFactory entityFactory
  ) {
    super(profileGateway,  entityFactory);
    this.committeeGateway = committeeGateway;
    this.entityFactory = entityFactory;
  }

  public void addCommittee(Committee committee) {
    committeeGateway.addCommittee(committee);
  }

  public <T> void save(T entity) { save(); }

  public void save() {
    committeeGateway.save();
  }

  public boolean hasCommitteeNamed(String name) {
    return committeeGateway.hasCommittee(name);
  }

  public List<Committee> getCommittees() {
    return committeeGateway.getCommittees();
  }

  public Builder<Committee, Response> fetchCommittee(String name) {
    try {
      return Builder.ofValue(committeeGateway.getCommittee(name));
    } catch (CommitteeGateway.UnknownCommitteeException e) {
      return Builder.ofResponse(ResponseFactory.unknownCommitteeName());
    }
  }

  public Builder<Seat, Response> fetchSeat(String committeeName, String seatName) {
    try {
      return Builder.ofValue(committeeGateway.getSeat(committeeName, seatName));
    } catch (CommitteeGateway.UnknownCommitteeException e) {
      return Builder.ofResponse(ResponseFactory.unknownCommitteeName());
    } catch (CommitteeGateway.UnknownSeatNameException e) {
      return Builder.ofResponse(ResponseFactory.unknownSeatName());
    }
  }

  public Builder<Committee, Response> makeCommittee(
        String name, String description, Query voterQuery
  ) {
    return Builder.ofValue(entityFactory.createCommittee(name, description, voterQuery));
  }

  public Boolean hasCommittee(Committee committee) {
    return hasCommitteeNamed(committee.getName());
  }

  public BiFunction<Committee, Query, Seat> createSeat(String seatName) {
    return (committee, query) -> entityFactory.createSeat(seatName, query, committee);
  }

  public void addSeat(Seat seat) { committeeGateway.addSeat(seat); }
}