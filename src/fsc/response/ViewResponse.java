package fsc.response;

import fsc.entity.Candidate;
import fsc.entity.Committee;
import fsc.entity.Profile;
import fsc.entity.query.Query;
import fsc.entity.query.QueryValidationResult;
import fsc.service.Context;
import fsc.service.ViewableEntityConverter;
import fsc.viewable.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ViewResponse<T> implements Response {
  private static ViewableEntityConverter entityConverter = Context.instance.viewableEntityConverter;

  public final T values;

  private ViewResponse(T values) {
    this.values = values;
  }

  public static ViewResponse<List<ViewableProfile>> ofProfileList(List<Profile> profiles) {
    return new ViewResponse<>(entityConverter.convert(profiles));
  }

  public static ViewResponse<ViewableProfile> ofProfile(Profile profile) {
    return new ViewResponse<>(entityConverter.convert(profile));
  }

  public static ViewResponse<List<String>> ofStrings(List<String> strings) {
    return new ViewResponse<>(strings);
  }

  public static ViewResponse<Map<String, String>> ofNamedQueries(Map<String, Query> queries) {
    return new ViewResponse<>(entityConverter.convertQueries(queries));
  }

  public static ViewResponse<ViewableCandidate> ofCandidate(Candidate candidate) {
    return new ViewResponse<>(entityConverter.convert(candidate));
  }

  public static ViewResponse<List<ViewableCommittee>> ofCommitteeList(List<Committee> committees) {
    return new ViewResponse<>(entityConverter.convertCommittees(committees));
  }

  public static Response ofQueryResult(QueryValidationResult result) {
    return new ViewResponse<>(new ViewableValidationResult(result));
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ViewResponse<?> that = (ViewResponse<?>) o;
    return Objects.equals(values, that.values);
  }

  public int hashCode() {
    return Objects.hash(values);
  }

  public String toString() {
    return "ViewResponse{" + "values=" + values + '}';
  }

  public static ViewableEntityConverter getEntityConverter() {
    return entityConverter;
  }

  public static void setEntityConverter(ViewableEntityConverter entityConverter) {
    ViewResponse.entityConverter = entityConverter;
  }
}
