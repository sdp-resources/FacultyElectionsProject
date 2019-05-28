package fsc.request;

import fsc.entity.Profile;

import java.util.ArrayList;
import java.util.List;

public class EditProfileRequest extends Request {
  public final String username;
  private List<ProfileChange> changes;

  public EditProfileRequest(String username) {
    this.username = username;
    this.changes = new ArrayList<>();
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

      ProfileActiveStatusChange(Object active) { this.active = Boolean.valueOf(active.toString()); }

      public void updateProfile(Profile profile) {
        if (active) { profile.setActive(); } else { profile.setInactive(); }
      }
    }
  }
}
