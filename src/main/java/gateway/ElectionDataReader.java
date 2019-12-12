package gateway;

import fsc.entity.Committee;
import fsc.entity.Profile;

import java.util.stream.Stream;

interface ElectionDataReader {
  Stream<Profile> getProfiles();
  Stream<String> getDivisions();
  Stream<String> getContractTypes();
  Stream<Committee> getCommittees();
}