package webserver;

import fsc.entity.*;
import fsc.entity.query.Query;
import fsc.gateway.Gateway;
import fsc.service.Authorizer;
import fsc.service.query.*;

import java.util.List;

public class DataFixture {
  private Gateway gateway;
  private QueryStringConverter converter;

  public DataFixture(Gateway gateway) {
    this.gateway = gateway;
    this.converter = new QueryStringConverter(
          new ValidatingQueryStringParserFactory(
                new GatewayBackedQueryValidator(gateway)
          ));
  }

  public void populateDatabase() {
    addPasswordRecord("skiadas", "apassword", Authorizer.Role.ROLE_USER);
    addPasswordRecord("wilsont", "apassword", Authorizer.Role.ROLE_USER);
    addPasswordRecord("johnsonk", "apassword", Authorizer.Role.ROLE_USER);
    addPasswordRecord("stokes", "apassword", Authorizer.Role.ROLE_USER);
    addPasswordRecord("admin", "adminpass", Authorizer.Role.ROLE_ADMIN);
    addDivision("Natural Sciences");
    addDivision("Humanities");
    addDivision("Arts and Letters");
    addDivision("Social Sciences");
    addDivision("Other");
    addContract("tenured");
    addContract("untenured");
    addContract("librarian");
    addContract("chaplain");
    addNamedQuery("isActive", Query.isActive());
    addNamedQuery("notChaplain", Query.not(Query.has("contract", "chaplain")));
    addNamedQuery("isTenured", Query.has("contract", "tenured"));
    addNamedQuery("isUntenured", Query.has("contract", "untenured"));
    addNamedQuery("isUntenuredOrLibrarian",
                  Query.any(Query.has("contract", "untenured"),
                            Query.has("contract", "librarian")));
    addNamedQuery("isNaturalSciences", Query.has("division", "Natural Sciences"));
    addNamedQuery("isHumanities", Query.has("division", "Humanities"));
    addNamedQuery("isSocialSciences", Query.has("division", "Social Sciences"));
    addNamedQuery("isArtsAndLetters", Query.has("division", "Arts and Letters"));
    Query isActive = queryFromString("isActive");
    Query isTenuredOrUntenured = queryFromString("isTenured or isUntenured");
    Query isActiveAndNotChaplain = queryFromString("isActive and notChaplain");
    Profile skiadas = addProfile("Charilaos Skiadas", "skiadas", "Natural Sciences", "tenured");
    Profile wilson = addProfile("Theresa Wilson", "wilsont", "Natural Sciences", "untenured");
    Profile johnson = addProfile("Kate Johnson", "johnsonk", "Humanities", "tenured");
    Profile stokes = addProfile("Kay Stokes", "stokes", "Arts and Letters", "administrator");
    Committee cof = addCommittee("CoF", "Committee of the Faculty", isTenuredOrUntenured);
    Committee fec = addCommittee("FEC", "Faculty Evaluation Committee", isTenuredOrUntenured);
    Committee fsc = addCommittee("FSC", "Faculty Steering Committee", isActiveAndNotChaplain);
    Committee pprc = addCommittee("PPRC", "Program and Position Review Committee",
                                  isActiveAndNotChaplain);
    Committee rac = addCommittee("RAC", "Rules Application Committee", isActiveAndNotChaplain);
    Committee botRep = addCommittee("BoT-Rep", "Faculty Representatives to the Board of Trustees",
                                    isActive);
    Committee budgFin = addCommittee("Budget-Finance", "Advisory Committee on Budget and Finance",
                                     isActiveAndNotChaplain);
    Committee enrollMarket = addCommittee("Enrollment-Marketing",
                                          "Enrollment and Marketing Advisory Committee",
                                          isActiveAndNotChaplain);
    Seat cofSeat1 = addSeat("CoF-1", isTenuredOrUntenured, cof);
    Seat cofSeat2 = addSeat("CoF-2", isTenuredOrUntenured, cof);
    Seat cofSeat3 = addSeat("CoF-3", isTenuredOrUntenured, cof);
    Seat cofSeat4 = addSeat("CoF-4", isTenuredOrUntenured, cof);
    Seat cofSeat5 = addSeat("CoF-5", isTenuredOrUntenured, cof);
    Seat fecTenured1 = addSeat("FEC-Tenured-1", isTenuredOrUntenured, fec);
    Seat fecTenured2 = addSeat("FEC-Tenured-2", isTenuredOrUntenured, fec);
    Seat fecTenured3 = addSeat("FEC-Tenured-3", isTenuredOrUntenured, fec);
    Seat fecTenured4 = addSeat("FEC-Tenured-4", isTenuredOrUntenured, fec);
    Seat fecTenured5 = addSeat("FEC-Tenured-5", isTenuredOrUntenured, fec);
    Seat fecTenured6 = addSeat("FEC-Tenured-6", isTenuredOrUntenured, fec);
    Seat fecUntenured1 = addSeat("FEC-Untenured-1", isTenuredOrUntenured, fec);
    Seat fecUntenured2 = addSeat("FEC-Untenured-2", isTenuredOrUntenured, fec);
    Seat fscAtLarge1 = addSeat("FSC-At-Large-1", isActiveAndNotChaplain, fsc);
    Seat fscAtLarge2 = addSeat("FSC-At-Large-2", isActiveAndNotChaplain, fsc);
    Seat fscNS = addSeat("FSC-Natural-Sciences",
                         queryFromString("isActive and notChaplain and isNaturalSciences"),
                         fsc);
    Seat fscHum = addSeat("FSC-Humanities",
                          queryFromString("isActive and notChaplain and isHumanities"),
                          fsc);
    Seat fscSoc = addSeat("FSC-Social-Sciences",
                          queryFromString("isActive and notChaplain and isSocialSciences"),
                          fsc);
    Seat fscArtsLetters = addSeat("FSC-Arts-and-Letters",
                                  queryFromString("isActive and notChaplain and isArtsAndLetters"),
                                  fsc);
    Seat pprcAtLarge = addSeat("PPRC-At-Large", isActiveAndNotChaplain, pprc);
    Seat pprcNS = addSeat("PPRC-Natural-Sciences",
                          queryFromString("isActive and notChaplain and isNaturalSciences"),
                          pprc);
    Seat pprcHum = addSeat("PPRC-Humanities",
                           queryFromString("isActive and notChaplain and isHumanities"),
                           pprc);
    Seat pprcSoc = addSeat("PPRC-Social-Sciences",
                           queryFromString("isActive and notChaplain and isSocialSciences"),
                           pprc);
    Seat pprcArtsLetters = addSeat("PPRC-Arts-and-Letters",
                                   queryFromString("isActive and notChaplain and isArtsAndLetters"),
                                   pprc);
    Seat rac1 = addSeat("RAC-At-Large", isActiveAndNotChaplain, rac);
    Seat botRepTenured = addSeat("BoT-Rep-Tenured", queryFromString("isTenured"), botRep);
    Seat botRepAtLarge = addSeat("BoT-Rep-At-Large", isActive, botRep);
    Seat budgFin1 = addSeat("Budget-Finance-1", isActiveAndNotChaplain, budgFin);
    Seat budgFin2 = addSeat("Budget-Finance-2", isActiveAndNotChaplain, budgFin);
    Seat enrollMarket1 = addSeat("Enrollment-Marketing-1", isActiveAndNotChaplain, enrollMarket);
    Seat enrollMarket2 = addSeat("Enrollment-Marketing-2", isActiveAndNotChaplain, enrollMarket);
    Election election1 = addElection(fecTenured1, Election.State.DecideToStand,
                                     List.of(skiadas, wilson, johnson, stokes),
                                     List.of(skiadas, wilson, johnson, stokes));
    Election election2 = addElection(fecUntenured1, Election.State.Vote,
                                     List.of(skiadas, wilson, johnson, stokes),
                                     List.of(skiadas, wilson, johnson, stokes));
    Election election3 = addElection(fecTenured2, Election.State.Closed,
                                     List.of(skiadas),
                                     List.of(skiadas, wilson, johnson, stokes));
    Election election4 = addElection(fecTenured3, Election.State.DecideToStand,
                                     List.of(johnson),
                                     List.of(wilson, johnson, stokes));
  }

  private Query queryFromString(String queryString) {
    try {
      return converter.fromString(queryString);
    } catch (QueryStringParser.QueryParseException e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }

  private void addNamedQuery(String name, Query query) {
    gateway.addQuery(name, query);
  }

  private void addContract(String contract) {
    gateway.addContractType(new ContractType(contract));
  }

  private void addDivision(String division) {
    gateway.addDivision(new Division(division));
    gateway.save();
  }

  private Profile addProfile(String fullname, String username, String division, String contract) {
    Profile profile = new Profile(fullname, username, division, contract);
    gateway.addProfile(profile);
    gateway.save();
    return profile;
  }

  private void addPasswordRecord(String username, String password, Authorizer.Role role) {
    PasswordRecord passwordRecord = PasswordRecord.create(username, password, role);
    gateway.addPasswordRecord(passwordRecord);
    gateway.save();
  }

  private Committee addCommittee(String name, String description, Query voterQuery) {
    Committee committee = new Committee(name, description, voterQuery);
    gateway.addCommittee(committee);
    gateway.save();
    return committee;
  }

  private Seat addSeat(String name, Query candidateQuery, Committee committee) {
    Seat seat = new Seat(name, candidateQuery, committee);
    gateway.addSeat(seat);
    gateway.save();
    return seat;
  }

  private Election addElection(
        Seat seat, Election.State state,
        List<Profile> candidates,
        List<Profile> voters
  ) {
    Election election = new Election(seat);
    election.setState(state);
    gateway.addElection(election);
    for (Profile profile : candidates) {
      Candidate candidate = new Candidate(profile, election);
      election.addCandidate(candidate);
      if (!election.isInDecideToStandState()) {
        candidate.setStatus(Candidate.Status.Willing);
      }
    }
    for (Profile voter : voters) {
      election.addVoter(new Voter(voter, election));
    }
    gateway.save();
    return election;
  }
}
