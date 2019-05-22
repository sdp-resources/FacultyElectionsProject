package fsc.request;

import java.util.Map;

public class EditCommitteeRequest extends Request {
  public String name;
  public Map<String, Object> changes;

  public EditCommitteeRequest(String name, Map<String, Object> changes) {
    this.name = name;
    this.changes = changes;
  }
}
