package gateway;

import fsc.entity.*;
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
  private EntityFactory entityFactory;

  public JSONElectionDataReader(File file, EntityFactory entityFactory) throws FileNotFoundException {
    jsonObject = readJson(file);
    this.entityFactory = entityFactory;
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
    return entityFactory.createProfile(name, username, division, contractType);
  }

  private Committee makeCommittee(Object o) {
    JSONObject json = (JSONObject) o;
    String name = json.getString("name");
    String description = json.getString("description");
    Committee committee = entityFactory.createCommittee(name, description, null);
    for (Object s : json.getJSONArray("seats")) {
      makeSeat(s, committee);
    }
    return committee;
  }

  private Seat makeSeat(Object o, Committee committee) {
    JSONObject json = (JSONObject) o;
    String name = json.getString("name");
    Query query = makeQuery(json.getJSONObject("query"));
    return entityFactory.createSeat(name, query, committee);
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
