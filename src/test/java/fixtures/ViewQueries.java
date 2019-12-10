package fixtures;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ViewQueries {
  public List<List<List<String>>> query() {
    return TestContext.app.getAllQueries().entrySet()
                          .stream()
                          .map(this::flatten)
                          .collect(Collectors.toList());
  }

  private List<List<String>> flatten(Map.Entry<String, String> entry) {
    return List.of(List.of("name", entry.getKey()),
                   List.of("string", entry.getValue()));
  }
}
