package fsc.interactor.fetcher;

import fsc.entity.Profile;
import fsc.gateway.ProfileGateway;
import fsc.response.ResponseFactory;
import fsc.response.builder.ResponseBuilder;

import java.util.HashSet;
import java.util.List;

public class ProfileFetcher {
  public ProfileGateway profileGateway;

  public ProfileFetcher(ProfileGateway profileGateway) { this.profileGateway = profileGateway;}

  public ResponseBuilder<Profile> fetchProfile(String username) {
    try {
      Profile voterProfile = profileGateway.getProfile(username);
      return ResponseBuilder.ofValue(voterProfile);
    } catch (ProfileGateway.InvalidProfileUsernameException e) {
      return ResponseBuilder.ofResponse(ResponseFactory.unknownProfileName());
    }
  }

  public ResponseBuilder<List<Profile>> fetchProfiles(List<String> usernames) {
    try {
      return ResponseBuilder.ofValue(profileGateway.getProfiles(usernames));
    } catch (ProfileGateway.InvalidProfileUsernameException e) {
      return ResponseBuilder.ofResponse(ResponseFactory.unknownProfileName());
    }
  }

  public ResponseBuilder<List<Profile>> fetchProfilesIfNoDuplicates(List<String> vote) {
    return fetchProfiles(vote)
                 .escapeIf(this::thereAreMultipleProfileOccurences,
                           ResponseFactory.multipleRanksForCandidate());
  }

  public boolean thereAreMultipleProfileOccurences(List<Profile> profiles) {
    HashSet profilesSet = new HashSet<>(profiles);
    return profilesSet.size() != profiles.size();
  }
}