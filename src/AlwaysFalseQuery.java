public class AlwaysFalseQuery implements Query {

  public boolean isProfileValid(Profile profile){
    return false;
  }

}
