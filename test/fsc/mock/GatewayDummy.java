package fsc.mock;

import fsc.entity.Profile;
import fsc.gateway.Gateway;

import java.util.List;

public class GatewayDummy implements Gateway {
  public Profile getProfileFromUsername(String username) {
    return null;
  }

  public List<Profile> getAllProfiles() {
    return null;
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
