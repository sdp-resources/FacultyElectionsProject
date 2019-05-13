public class Rule {
  private boolean requiresActive;
  private boolean requiresOneYearCooldown;
  private int yearsPerTerm;

  public void setRequiresActive(boolean b) {
    requiresActive = b;
  }

  public boolean getRequiresActive() {
    return requiresActive;
  }

  public void setRequiresOneYearCooldown(boolean requiresOneYearCooldown) {
    this.requiresOneYearCooldown = requiresOneYearCooldown;
  }

  public boolean getRequiresOneYearCooldown() {
    return requiresOneYearCooldown;
  }

  public void setYearsPerTerm(int yearsPerTerm) {
    this.yearsPerTerm = yearsPerTerm;
  }

  public int getYearsPerTerm() {
    return yearsPerTerm;
  }
}
