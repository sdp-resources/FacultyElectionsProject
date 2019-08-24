package webserver;

import org.json.JSONObject;

import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

class JSONClient extends WebClient {
  private JSONObject jsonBody;

  public JSONClient(
        String url, String method, Map<String, String> parameters
  ) {
    super(url, method, parameters, false);
  }

  protected void prepareConnection(
        String url, String method, Map<String, String> parameters, boolean followRedirects
  ) {
    super.prepareConnection(url, method, parameters, followRedirects);
    connection.header("Accept", "application/json");
    connection.ignoreContentType(true);
    connection.ignoreHttpErrors(true);
  }

  public void execute() {
    super.execute();
    jsonBody = new JSONObject(document.text());
  }

  JSONClient assertResponseCodeIs(int responseCode) {
    super.assertResponseCodeIs(responseCode);
    return this;
  }

    JSONClient assertServedTypeIsJSON() {
    assertThat(response.header("content-type"), containsString("application/json"));
    return this;
  }

  public JSONClient assertContainsKey(String key, String value) {
    assertEquals(value, jsonBody.getString(key));
    return this;
  }

  public JSONClient assertContainsKeyMatching(String key, String value) {
    assertThat(jsonBody.getString(key), containsString(value));
    return this;
  }
}
