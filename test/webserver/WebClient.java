package webserver;

import junit.framework.TestCase;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class WebClient {
  public static String base = "http://localhost:4568";
  protected Connection.Response response;
  private String url;
  protected Connection connection;
  protected Document document;
  private String method;
  private String sessionID;

  static WebClient getWithoutRedirect(String path) {
    return new WebClient(path, "GET", false);
  }

  public static WebClient get(String url) {
    return new WebClient(url, "GET", true);
  }

  public static WebClient post(
        String url, Map<String, String> parameters, boolean followRedirects
  ) {
    return new WebClient(url, "POST", parameters, followRedirects);
  }

  public WebClient(String url, String method, boolean followRedirects) {
    this(url, method, new HashMap<>(), followRedirects);
  }

  public WebClient(
        String url, String method, Map<String, String> parameters, boolean followRedirects
  ) {
    setup(url, method, parameters, followRedirects);
  }

  protected void setup(
        String url, String method, Map<String, String> parameters,
        boolean followRedirects
  ) {
    prepareConnection(url, method, parameters, followRedirects);
    execute();
  }

  protected void prepareConnection(
        String url, String method, Map<String, String> parameters, boolean followRedirects
  ) {
    this.url = normalize(url);
    this.method = method;
    connection = Jsoup.connect(this.url);
    connection.method(Connection.Method.valueOf(this.method));
    connection.followRedirects(followRedirects);
    if (sessionID != null) {
      connection.cookie("JSESSIONID", sessionID);
    }
    for (String key : parameters.keySet()) {
      addParameter(key, parameters.get(key));
    }
  }

  private String normalize(String url) {
    return url.matches("^http") ? url : base + url;
  }

  public void execute() {
    try {
      response = connection.execute();
      document = response.parse();
      if (connection.response().hasCookie("JSESSIONID")) {
        sessionID = connection.response().cookie("JSESSIONID");
      }
    } catch (IOException e) {
      e.printStackTrace();
      response = null;
      document = null;
    }
  }

  public int getResponseCode() {
    return response.statusCode();
  }

  public Element getElement(String selector) {
    return document.selectFirst(selector);
  }

  public void addParameter(String parameter, String value) {
    connection.data(parameter, value);
  }

  public String getResponseHeader(String header) {
    return connection.response().header(header);
  }

  String fullPathFor(String partialUrl) {
    return base + partialUrl;
  }

  WebClient followGet(String path) {
    return follow(path, "GET", false);
  }

  WebClient followPost(String path, HashMap<String, String> parameters) {
    return follow(path, "POST", parameters, false);
  }

  public WebClient follow(String path, String method, boolean followRedirects) {
    return follow(path, method, new HashMap<>(), followRedirects);
  }

  public WebClient follow(
        String path, String method, HashMap<String, String> parameters, boolean followRedirects
  ) {
    setup(path, method, parameters, followRedirects);
    return this;
  }

  WebClient assertRedirectsTo(String path) {
    assertResponseCodeIs(302);
    TestCase.assertEquals(fullPathFor(path),
                          getResponseHeader("Location"));
    return this;
  }

  WebClient assertResponseCodeIs(int responseCode) {
    TestCase.assertEquals(responseCode, getResponseCode());
    return this;
  }

  WebClient assertMatch(String selector) {
    assertNotNull(getElement(selector));
    return this;
  }

  WebClient assertNoMatch(String selector) {
    TestCase.assertNull(getElement(selector));
    return this;
  }
}
