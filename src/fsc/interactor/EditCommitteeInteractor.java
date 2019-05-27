package fsc.interactor;

import fsc.entity.Committee;
import fsc.gateway.CommitteeGateway;
import fsc.request.EditCommitteeRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.SuccessResponse;

import java.util.Map;

public class EditCommitteeInteractor {
  private CommitteeGateway committeeGateway;

  public EditCommitteeInteractor(CommitteeGateway committeeGateway) {
    this.committeeGateway = committeeGateway;
  }

  public Response execute(EditCommitteeRequest request) {
    try {
      Committee committee = committeeGateway.getCommittee(request.name);
      performUpdates(committee, request.changes);
      committeeGateway.save();
      return new SuccessResponse();
    } catch (CommitteeGateway.UnknownCommitteeException e) {
      return ErrorResponse.unknownCommitteeName();
    }

  }

  private void performUpdates(Committee committee, Map<String, Object> changes) {
    for (String field : changes.keySet()) {
      updateProfile(committee, field, changes.get(field));
    }
  }

  public void updateProfile(Committee committee, String field, Object value) {
    switch (field) {
      case "name":
        committee.setName((String) value);
        break;
      case "description":
        committee.setDescription((String) value);
        break;
    }
  }
}
