package gateway;

import fsc.entity.Profile;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class JSONElectionDataReader implements ElectionDataReader {
  private JSONObject jsonObject;

  public JSONElectionDataReader(File file) throws FileNotFoundException {
    jsonObject = readJson(file);
  }

  private JSONObject readJson(File file) throws FileNotFoundException {
    FileReader reader = new FileReader(file);
    JSONTokener tokener = new JSONTokener(reader);
    return new JSONObject(tokener);
  }

  private Profile makeProfile(Object o) {
    JSONObject json = (JSONObject) o;
    String name = json.getString("name");
    String username = json.getString("username");
    String division = json.getString("division");
    String contractType = json.getString("contract");
    return new Profile(name, username, division, contractType);
  }

  public Stream<Profile> getProfiles() {
    return StreamSupport.stream(getSpliteratorForKey("profiles"), false).map(this::makeProfile);
  }

  public Stream<String> getDivisions() {
    return StreamSupport.stream(getSpliteratorForKey("divisions"), false).map(o -> (String) o);
  }

  public Stream<String> getContractTypes() {
    return StreamSupport.stream(getSpliteratorForKey("contractTypes"), false).map(o -> (String) o);
  }

  private Spliterator<Object> getSpliteratorForKey(String key) {
    return jsonObject.getJSONArray(key).spliterator();
  }

}
