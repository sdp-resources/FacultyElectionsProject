package fsc.interactor.fetcher;

import fsc.entity.EntityFactory;
import fsc.entity.Profile;
import fsc.entity.query.Query;
import fsc.gateway.ProfileGateway;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.utils.builder.Builder;

import java.util.HashSet;
import java.util.List;

public class ProfileFetcher {
  public ProfileGateway profileGateway;
  private EntityFactory entityFactory;

  public ProfileFetcher(ProfileGateway profileGateway, EntityFactory entityFactory) {
    this.profileGateway = profileGateway;
    this.entityFactory = entityFactory;
  }

  public Builder<Profile, Response> fetchProfile(String username) {
    try {
      Profile voterProfile = profileGateway.getProfile(username);
      return Builder.ofValue(voterProfile);
    } catch (ProfileGateway.InvalidProfileUsernameException e) {
      return Builder.ofResponse(ResponseFactory.unknownProfileName());
    }
  }

  public Builder<List<Profile>, Response> fetchProfiles(List<String> usernames) {
    try {
      return Builder.ofValue(profileGateway.getProfiles(usernames));
    } catch (ProfileGateway.InvalidProfileUsernameException e) {
      return Builder.ofResponse(ResponseFactory.unknownProfileName());
    }
  }

  public Builder<List<Profile>, Response> fetchProfilesIfNoDuplicates(List<String> vote) {
    return fetchProfiles(vote)
                 .escapeIf(this::thereAreMultipleProfileOccurences,
                           ResponseFactory.multipleRanksForCandidate());
  }

  public boolean thereAreMultipleProfileOccurences(List<Profile> profiles) {
    HashSet profilesSet = new HashSet<>(profiles);
    return profilesSet.size() != profiles.size();
  }

  public Builder<Profile, Response> makeProfile(String name, String username, String division,
                                       String contract) {
    return Builder.ofValue(
          entityFactory.createProfile(name, username, division, contract));
  }

  public Boolean profileExists(Profile profile) {
    return profileGateway.hasProfile(profile.getUsername());
  }

  public void addProfile(Profile profile) {
    profileGateway.addProfile(profile);
  }

  public <T> void save(T entity) {
    profileGateway.save();
  }

  public List<Profile> getProfilesMatchingQuery(Query query) {
    return profileGateway.getProfilesMatching(query);
  }
}