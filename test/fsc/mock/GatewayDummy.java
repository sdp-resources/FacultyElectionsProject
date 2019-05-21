package fsc.mock;

import fsc.entity.Profile;
import fsc.gateway.Gateway;

import java.util.List;

public abstract class GatewayDummy implements Gateway {
  public Profile getProfile(String username) {
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

  public void addContractType(String string) {
  }

  public void getContractTypeFromProfile(String contract_type) {

  }
}
