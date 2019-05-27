package fsc.interactor;

import fsc.entity.Ballot;
import fsc.entity.Profile;
import fsc.mock.gateway.ballot.ExistingBallotGatewaySpy;
import fsc.mock.gateway.ballot.EmptyBallotGatewaySpy;
import fsc.request.ViewCandidatesRequest;
import fsc.response.ErrorResponse;
import fsc.response.Response;
import fsc.response.ViewResponse;
import fsc.service.ViewableEntityConverter;
import fsc.viewable.ViewableProfile;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ViewCandidatesInteractorTest {
  public static final String MOCK_ID = "mockID";
  private Ballot ballot;
  private List<ViewableProfile> viewableProfiles;

  private static Ballot sampleBallot() {
    Ballot aBallot = new Ballot();
    aBallot.add(new Profile("Haris Skiadas", "skiadas", "Natural Science", "Tenured"));
    aBallot.add(new Profile("Theresa Wilson", "wilson", "Natural Science", "tenure-track"));
    aBallot.add(new Profile("Barb Wahl", "wahl", "Natural Science", "Tenured"));
    aBallot.add(new Profile("John Collins", "collins", "Natural Science", "Tenured"));
    return aBallot;
  }

  @Before
  public void setup() {
    ballot = sampleBallot();
    ballot.setBallotID(MOCK_ID);
    ViewableEntityConverter converter = new ViewableEntityConverter();
    viewableProfiles = ballot.stream().map(converter::convert).collect(Collectors.toList());
  }

  @Test
  public void canRecognizeEmptyBallot() throws Exception {
    ViewCandidatesRequest request = new ViewCandidatesRequest(MOCK_ID);
    EmptyBallotGatewaySpy gateway = new EmptyBallotGatewaySpy();
    ViewCandidatesInteractor interactor = new ViewCandidatesInteractor(gateway);
    Response response = interactor.execute(request);

    assertEquals(ErrorResponse.unknownElectionID(), response);
  }

  @Test
  public void canViewABallot() throws Exception {
    ViewCandidatesRequest request = new ViewCandidatesRequest(MOCK_ID);
    ExistingBallotGatewaySpy gateway = new ExistingBallotGatewaySpy(ballot);
    ViewCandidatesInteractor interactor = new ViewCandidatesInteractor(gateway);
    Response initialResponse = interactor.execute(request);

    assertEquals(MOCK_ID, gateway.requestedBallotId);
    assertEquals(viewableProfiles, ((ViewResponse<List<ViewableProfile>>) initialResponse).values);
  }

}
