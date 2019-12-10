package fsc.service.query;

public class SimpleQueryStringParserFactory implements QueryStringParserFactory {
  public QueryStringParser createQueryStringParser(String string) {
    return new SimpleQueryStringParser(string);
  }
}
