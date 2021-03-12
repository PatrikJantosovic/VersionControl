package org.jantosovic.versioncontrol.api;

/**
 *
 */
public class VersionNumber {

  private final int Major;

  private final int Minor;

  public VersionNumber(int major, int minor) {
    Major = major;
    Minor = minor;
  }

  public VersionNumber IncrementMajor(VersionNumber versionNumber){
    return new VersionNumber(this.Major + 1, this.Minor);
  }

  public VersionNumber IncrementMinor(VersionNumber versionNumber, boolean custom){
    return new VersionNumber(this.Major, this.Minor + 1);
  }

  public String GetVersionNumber() {
    return this.Major + "." + this.Minor;
  }


  @Override
  public String toString() {
    return "VersionNumber{"
        + "Major=" + Major
        + ", Minor=" + Minor
        + '}';
  }
}
