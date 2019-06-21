package fsc.interactor.fetcher;

import fsc.entity.Profile;
import fsc.entity.query.Query;
import fsc.gateway.ProfileGateway;
import fsc.request.ViewProfilesListRequest;
import fsc.response.ResponseFactory;
import fsc.response.builder.ResponseBuilder;
import fsc.service.query.QueryStringConverter;
import fsc.service.query.QueryStringParser;

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

  public ResponseBuilder<Profile> makeProfile(String name, String username, String division, String contract) {
    return ResponseBuilder.ofValue(new Profile(name, username, division, contract));
  }

  public Boolean profileExists(Profile profile) {
    return profileGateway.hasProfile(profile.username);
  }

  public void addProfile(Profile profile) {
    profileGateway.addProfile(profile);
  }

  public void save(Profile profile) {
    profileGateway.save();
  }

  public List<Profile> getProfilesMatchingQuery(Query query) {
    return profileGateway.getProfilesMatching(query);
  }

  public ResponseBuilder<Query> parseQueryFromString(ViewProfilesListRequest request) {
    try {
      return ResponseBuilder.ofValue(new QueryStringConverter().fromString(request.query));
    } catch (QueryStringParser.QueryParseException e) {
      // TODO Check for this
      return ResponseBuilder.ofResponse(ResponseFactory.invalidQuery(e.getMessage()));
    }
  }
}