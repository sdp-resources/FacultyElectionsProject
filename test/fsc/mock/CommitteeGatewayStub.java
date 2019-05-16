package fsc.mock;

import fsc.entity.Committee;
import fsc.gateway.CommitteeGateway;

import java.util.ArrayList;

public class CommitteeGatewayStub implements CommitteeGateway {

  ArrayList<Committee> committees;

  public CommitteeGatewayStub(){
    committees.add(new Committee("aaaa","aaaa"));
    committees.add(new Committee("bbbb","bbbb"));
    committees.add(new Committee("cccc","cccc"));
    committees.add(new Committee("dddd","dddd"));
  }

  public void getCommitteeFromCommitteeName(String name) {

  }

  public void addCommittee(Committee makeCommitteeFromRequest) {

  }
}
