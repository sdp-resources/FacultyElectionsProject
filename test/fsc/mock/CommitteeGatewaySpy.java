package fsc.mock;

import fsc.entity.Committee;
import fsc.gateway.CommitteeGateway;

import java.util.ArrayList;
import java.util.List;

public class CommitteeGatewaySpy implements CommitteeGateway {

  private List<Committee> committees = new ArrayList<>();
  public static String submittedCommitteeName;

  public CommitteeGatewaySpy(){
    committees.add(new Committee("aaaa","aaaa"));
    committees.add(new Committee("bbbb","bbbb"));
    committees.add(new Committee("cccc","cccc"));
    committees.add(new Committee("dddd","dddd"));
  }

  public Committee getCommitteeFromCommitteeName(String name) throws NoCommitteeWithThatNameException {
    submittedCommitteeName = name;
    throw new NoCommitteeWithThatNameException();
  }

  public void addCommittee(Committee makeCommitteeFromRequest) {
    committees.add(makeCommitteeFromRequest);
  }
}
