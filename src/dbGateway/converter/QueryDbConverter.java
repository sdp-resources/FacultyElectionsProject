package dbGateway.converter;

import fsc.entity.query.Query;
import fsc.service.query.QueryStringConverter;
import fsc.service.query.QueryStringParser;

import javax.persistence.AttributeConverter;

public class QueryDbConverter implements AttributeConverter<Query, String> {
  private QueryStringConverter converter = new QueryStringConverter();

  public String convertToDatabaseColumn(Query query) {
    return converter.toString(query);
  }

  public Query convertToEntityAttribute(String s) {
    try {
      return converter.fromString(s);
    } catch (QueryStringParser.QueryParseException e) {
      throw new RuntimeException("Parse error on string should not occur: " + s);
    }
  }
}
