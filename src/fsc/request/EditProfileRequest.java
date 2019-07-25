package fsc.request;

import fsc.entity.Profile;
import fsc.interactor.Interactor;

import java.util.*;

public class EditProfileRequest extends Request {
  public final String username;
  private List<ProfileChange> changes;

  public EditProfileRequest(String username, Map<String, String> changes) {
    this.username = username;
    this.changes = new ArrayList<>();
    processChanges(changes);
  }

  private void processChanges(Map<String, String> changes) {
    changes.forEach(this::processChange);
  }

  private void processChange(String key, String value) {
    switch (key) {
      case "name": changeFullname(value);
        break;
      case "division": changeDivision(value);
        break;
      case "contractType": changeContractType(value);
        break;
      case "status": changeActiveStatus(value);
        break;
      default: throw new RuntimeException("Unknown change key: " + key);
    }
  }

  public EditProfileRequest(String username) {
    this(username, new HashMap<>());
  }

  public void applyChangesTo(Profile profile) {
    changes.forEach(change -> change.updateProfile(profile));
  }

  public void changeFullname(Object name) {
    changes.add(ProfileChange.name(name));
  }

  public void changeDivision(Object division) {
    changes.add(ProfileChange.division(division));
  }

  public void changeContractType(Object contractType) {
    changes.add(ProfileChange.contractType(contractType));
  }

  public void changeActiveStatus(Object value) {
    changes.add(ProfileChange.activeStatus(value));
  }

  public Object accept(RequestVisitor visitor) {
    return visitor.visit(this);
  }

  public static abstract class ProfileChange {
    private static ProfileChange name(Object name) {
      return new ProfileNameChange(name);
    }

    private static ProfileChange division(Object division) {
      return new ProfileDivisionChange(division);
    }

    private static ProfileChange contractType(Object contractType) {
      return new ProfileContractTypeChange(contractType);
    }

    private static ProfileChange activeStatus(Object value) {
      return new ProfileActiveStatusChange(value);
    }

    public abstract void updateProfile(Profile profile);

    private static class ProfileNameChange extends ProfileChange {
      private final String name;

      ProfileNameChange(Object name) { this.name = (String) name; }

      public void updateProfile(Profile profile) { profile.setName(name); }
    }

    private static class ProfileDivisionChange extends ProfileChange {
      private String division;

      ProfileDivisionChange(Object division) { this.division = (String) division; }

      public void updateProfile(Profile profile) {
        profile.setDivision(division);
      }
    }

    private static class ProfileContractTypeChange extends ProfileChange {
      private String contractType;

      ProfileContractTypeChange(Object contractType) { this.contractType = (String) contractType; }

      public void updateProfile(Profile profile) { profile.setContract(contractType); }
    }

    private static class ProfileActiveStatusChange extends ProfileChange {
      private boolean active;

      ProfileActiveStatusChange(Object active) {
        this.active = active.toString().equals("active");
      }

      public void updateProfile(Profile profile) {
        if (active) { profile.setActive(); } else { profile.setInactive(); }
      }
    }
  }
}
