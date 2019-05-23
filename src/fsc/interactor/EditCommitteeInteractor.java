package fsc.interactor;

import fsc.entity.Committee;
import fsc.gateway.CommitteeGateway;
import fsc.request.EditCommitteeRequest;
import fsc.response.SuccessResponse;
import fsc.response.ErrorResponse;
import fsc.response.Response;

public class EditCommitteeInteractor {
  private CommitteeGateway committeeGateway;

  public EditCommitteeInteractor(CommitteeGateway committeeGateway) {
    this.committeeGateway = committeeGateway;
  }

  public Response execute(EditCommitteeRequest request) {
    Committee committee;

    try
    {
      committee = committeeGateway.getCommitteeFromCommitteeName(request.name);
    }
    catch (CommitteeGateway.UnknownCommitteeException e)
    {
      return new ErrorResponse("No committee with that name");
    }

    for(String field : request.changes.keySet())
    {
      updateProfile(committee, field, request.changes.get(field));
    }

    committeeGateway.save();
    return new SuccessResponse();
  }

  public void updateProfile(Committee committee, String field, Object value)
  {
    switch (field)
    {
      case "name":
        committee.setName((String) value);
        break;
      case "description":
        committee.setDescription((String) value);
        break;
    }
  }
}
