package fsc.entity;

import fsc.gateway.ElectionGateway;
import fsc.gateway.Gateway;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ElectionCreatorTest {

  private Election election;
  private ElectionGateway mockGateway;

  @Before
  public void setUp() {
    Committee committee = new Committee("f", "g");
    Seat seat = new Seat();
    Ballot ballot = new Ballot();
    election = new Election("a", "b");
  }

  @Test
  public void validElection(){
    assertEquals("a", election.getSeat());
    assertEquals("b", election.getCommittee());
  }







    public void addProfile(Profile profile) { }

    public boolean isValidDivision(String division) {
      return false;
    }

    public void saveProfile(Profile profile) {

    }

    public String addContractType(String string) {
      return null;
    }

    public void getContractTypeFromProfile(String contract_type) {

    }
  }

}
