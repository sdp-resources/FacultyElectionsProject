package fsc.interactor.fetcher;

import fsc.entity.Committee;
import fsc.entity.EntityFactory;
import fsc.entity.query.Query;
import fsc.gateway.CommitteeGateway;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.utils.builder.Builder;

import java.util.List;
import java.util.function.BiFunction;

public class CommitteeFetcher {
  CommitteeGateway gateway;
  private EntityFactory entityFactory;

  public CommitteeFetcher(CommitteeGateway gateway, EntityFactory entityFactory) {
    this.gateway = gateway;
    this.entityFactory = entityFactory;
  }

  public void addCommittee(Committee committee) {
    gateway.addCommittee(committee);
  }

  public void save(Committee committee) { gateway.save(); }

  public void save() {
    gateway.save();
  }

  public boolean hasCommitteeNamed(String name) {
    return gateway.hasCommittee(name);
  }

  public List<Committee> getCommittees() {
    return gateway.getCommittees();
  }

  public Builder<Committee, Response> fetchCommittee(String name) {
    try {
      return Builder.ofValue(gateway.getCommittee(name));
    } catch (CommitteeGateway.UnknownCommitteeException e) {
      return Builder.ofResponse(ResponseFactory.unknownCommitteeName());
    }
  }

  public Builder<Committee, Response> makeCommittee(String name, String description) {
    return Builder.ofValue(entityFactory.createCommittee(name, description));
  }

  public Boolean hasCommittee(Committee committee) {
    return hasCommitteeNamed(committee.getName());
  }

  public BiFunction<Committee, Query, Committee> createSeat(String seatName) {
    return ((committee, query) -> {
      committee.addSeat(entityFactory.createSeat(seatName, query));
      return committee;
    });
  }
}