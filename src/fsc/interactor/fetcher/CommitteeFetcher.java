package fsc.interactor.fetcher;

import fsc.entity.Committee;
import fsc.gateway.CommitteeGateway;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.utils.builder.Builder;

import java.util.List;

public class CommitteeFetcher {
  CommitteeGateway gateway;

  public CommitteeFetcher(CommitteeGateway gateway) {
    this.gateway = gateway;
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
    return Builder.ofValue(new Committee(name, description));
  }

  public Boolean hasCommittee(Committee committee) {
    return hasCommitteeNamed(committee.getName());
  }
}