package gateway;

import fsc.entity.Committee;
import fsc.entity.Profile;
import fsc.entity.Seat;
import fsc.entity.query.AttributeQuery;
import fsc.entity.query.Query;
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

  private Committee makeCommittee(Object o) {
    JSONObject json = (JSONObject) o;
    String name = json.getString("name");
    String description = json.getString("description");
    Committee committee = new Committee(name, description);
    for (Object s : json.getJSONArray("seats")) {
      committee.addSeat(makeSeat(s));
    }
    return committee;
  }

  private Seat makeSeat(Object o) {
    JSONObject json = (JSONObject) o;
    String name = json.getString("name");
    Query query = makeQuery(json.getJSONObject("query"));
    return new Seat(name, query);
  }

  private Query makeQuery(JSONObject o) {
    if (o.has("contractType")) {
      return new AttributeQuery("contract", o.getString("contractType"));
    } else {
      // TODO: Need more cases and compound cases
      throw new RuntimeException("Not implemented yet");
    }
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

  public Stream<Committee> getCommittees() {
    return StreamSupport.stream(getSpliteratorForKey("committees"), false).map(this::makeCommittee);
  }

  private Spliterator<Object> getSpliteratorForKey(String key) {
    return jsonObject.getJSONArray(key).spliterator();
  }

}
