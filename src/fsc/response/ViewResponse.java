package fsc.response;

import fsc.entity.Candidate;
import fsc.entity.Profile;
import fsc.service.Context;
import fsc.service.ViewableEntityConverter;
import fsc.viewable.ViewableCandidate;
import fsc.viewable.ViewableProfile;

import java.util.List;
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

  public static ViewResponse<ViewableCandidate> ofCandidate(Candidate candidate) {
    return new ViewResponse<>(entityConverter.convert(candidate));
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
