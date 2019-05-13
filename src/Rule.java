public class Rule {
  private boolean requiresActive;
  private boolean requiresOneYearCooldown;
  private int yearsPerTerm;
  private int consecutiveTermsBeforeCooldown;
  private TenureRequirements tenureRequirement;
  private Divisions divisionRequirement;

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

  public void setConsecutiveTermsBeforeCooldown(int consecutiveTermsBeforeCooldown) {
    this.consecutiveTermsBeforeCooldown = consecutiveTermsBeforeCooldown;
  }

  public int getConsecutiveTermsBeforeCooldown() {
    return consecutiveTermsBeforeCooldown;
  }

  public void setTenureRequirement(TenureRequirements tenureRequirement) {
    this.tenureRequirement = tenureRequirement;
  }

  public TenureRequirements getTenureRequirement() {
    return tenureRequirement;
  }

  public void setDivisionRequirement(Divisions divisionRequirement) {
    this.divisionRequirement = divisionRequirement;
  }

  public Divisions getDivisionRequirement() {
    return divisionRequirement;
  }

  public enum TenureRequirements { Tenured, NonTenured, None }

  public enum Divisions { ArtsAndLetters, SocialSciences, Humanities, NaturalSciences, AtLarge}
}
