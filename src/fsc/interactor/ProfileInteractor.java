package fsc.interactor;

import fsc.entity.EntityFactory;
import fsc.gateway.ProfileGateway;
import fsc.interactor.fetcher.ProfileFetcher;
import fsc.interactor.fetcher.QueryFetcher;
import fsc.request.*;
import fsc.response.Response;
import fsc.response.ResponseFactory;
import fsc.utils.builder.Builder;

public class ProfileInteractor extends Interactor {

  private ProfileFetcher profileFetcher;
  private QueryFetcher queryFetcher;

  public ProfileInteractor(ProfileGateway profileGateway, EntityFactory entityFactory) {
    profileFetcher = new ProfileFetcher(profileGateway, entityFactory);
    queryFetcher = new QueryFetcher();
  }

  public Response execute(CreateProfileRequest request) {
    return profileFetcher
                 .makeProfile(request.name, request.username,
                              request.division, request.contract)
                 .escapeIf(profileFetcher::profileExists,
                           ResponseFactory.resourceExists())
                 .perform(profileFetcher::addProfile)
                 .perform(profileFetcher::save)
                 .resolveWith(s -> ResponseFactory.success());
  }

  public Response execute(EditProfileRequest request) {
    return profileFetcher
                 .fetchProfile(request.username)
                 .perform(request::applyChangesTo)
                 .perform(profileFetcher::save)
                 .resolveWith((s -> ResponseFactory.success()));
  }

  public Response execute(ViewProfileRequest request) {
    return profileFetcher
          .fetchProfile(request.username)
          .resolveWith(ResponseFactory::ofProfile);
  }

  public Response execute(ViewProfilesListRequest request) {
    return queryFetcher.createFromString(request.query)
          .mapThrough(Builder.lift(profileFetcher::getProfilesMatchingQuery))
          .resolveWith(ResponseFactory::ofProfileList);
  }
}
