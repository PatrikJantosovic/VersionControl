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

  public VersionNumber(int major) {
    Major = major;
    Minor = Integer.MIN_VALUE;
  }

  public static VersionNumber ReadVersionNumber(String version) {
    var dot = version.indexOf('.');
    if (dot!=-1) {
      var major = Integer.parseInt(version.substring(0, dot));
      var minor = Integer.parseInt(version.substring(dot+1, version.indexOf(' ')));
      return new VersionNumber(major, minor);
    }
    return new VersionNumber(Integer.parseInt(version));
  }

  public VersionNumber IncrementMajor(){
    return new VersionNumber(this.Major + 1, this.Minor);
  }

  public VersionNumber IncrementMinor(){
    return new VersionNumber(this.Major, this.Minor + 1);
  }

  public VersionNumber Increment(boolean major){
    if (major) {
      return IncrementMajor();
    }
    return IncrementMinor();
  }

  public String GetVersionNumber() {
    var result = String.valueOf(this.Major);
    if (this.Minor!=Integer.MIN_VALUE) {
      return result + '.' + this.Minor;
    }
    return result;
  }

  @Override
  public String toString() {
    return "VersionNumber{"
        + "Major=" + Major
        + ", Minor=" + Minor
        + '}';
  }
}
