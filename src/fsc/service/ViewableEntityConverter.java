package fsc.service;

import fsc.entity.*;
import fsc.service.query.QueryStringConverter;
import fsc.viewable.*;

import java.util.List;
import java.util.stream.Collectors;

public class ViewableEntityConverter {

  public ViewableProfile convert(Profile profile) {
    return new ViewableProfile(profile.getName(), profile.getUsername(), profile.getDivision(),
                               profile.getContract());
  }

  private ViewableCommittee convert(Committee committee) {
    return new ViewableCommittee(committee.getName(), committee.getDescription(),
                                 convertSeats(committee.getSeats()));
  }

  private List<ViewableSeat> convertSeats(List<Seat> seats) {
    return seats.stream().map(this::convert).collect(Collectors.toList());
  }

  private ViewableSeat convert(Seat seat) {
    return new ViewableSeat(seat.getName(),
                            new QueryStringConverter().toString(seat.getDefaultQuery()));
  }

  public List<ViewableProfile> convert(List<Profile> profiles) {
    return profiles.stream().map(this::convert).collect(Collectors.toList());
  }

  public ViewableCandidate convert(Candidate candidate) {
    return new ViewableCandidate(convert(candidate.getProfile()), candidate.getStatus());

  }

  public List<ViewableCommittee> convertCommittees(List<Committee> committees) {
    return committees.stream().map(this::convert).collect(Collectors.toList());
  }
}
