package fsc.interactor;

import fsc.entity.Committee;
import fsc.entity.EntityFactory;
import fsc.gateway.CommitteeGateway;
import fsc.interactor.fetcher.CommitteeFetcher;
import fsc.interactor.fetcher.QueryFetcher;
import fsc.request.*;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.utils.builder.Builder;

import java.util.Map;
import java.util.function.Consumer;

public class CommitteeInteractor extends Interactor {
  private CommitteeFetcher committeeFetcher;
  private QueryFetcher queryFetcher;

  public CommitteeInteractor(CommitteeGateway gateway, EntityFactory entityFactory) {
    committeeFetcher = new CommitteeFetcher(gateway, entityFactory);
    queryFetcher = new QueryFetcher();
  }

  public Response execute(CreateCommitteeRequest request) {
    return committeeFetcher.makeCommittee(request.name, request.description)
                           .escapeIf(committeeFetcher::hasCommittee,
                                     ResponseFactory.resourceExists())
                           .perform(committeeFetcher::addCommittee)
                           .perform(committeeFetcher::save)
                           .resolveWith(s -> ResponseFactory.success());
  }

  public Response execute(ViewCommitteeListRequest request) {
    return ResponseFactory.ofCommitteeList(committeeFetcher.getCommittees());
  }

  public Response execute(EditCommitteeRequest request) {
    return committeeFetcher.fetchCommittee(request.name)
                           .perform(performUpdates(request.changes))
                           .perform(committeeFetcher::save)
                           .resolveWith(s -> ResponseFactory.success());
  }

  public Response execute(CreateSeatRequest request) {
    return committeeFetcher.fetchCommittee(request.committeeName)
                           .escapeIf(c -> c.hasSeat(request.seatName),
                                     ResponseFactory.resourceExists())
                           // TODO: Don't like how this is done
                           // should build seat in fetcher from query+seat step
                           .bindWith(queryFetcher.createFromString(request.queryString),
                                     Builder.lift(committeeFetcher.createSeat(request.seatName)))
                           .perform(committeeFetcher::save)
                           .resolveWith(s -> ResponseFactory.success());

  }

  private Consumer<Committee> performUpdates(Map<String, Object> changes) {
    return committee -> {
      for (String field : changes.keySet()) {
        committee.update(field, changes.get(field));
      }
    };
  }

}
