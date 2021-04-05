package org.jantosovic.versioncontrol.api;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.commons.lang3.StringUtils;

/**
 *
 */
public final class VersionLine {

  private final String Name;

  private final String VersionDate;

  private final String Message;

  private final VersionNumber Version;

  private final int VersionPadding;

  private final int NamePadding;

  private final int LineNumber;

  public VersionLine(String name, String id, VersionNumber versionNumber, int versionPadding, int namePadding, int lineNumber) {
    var formatter = new SimpleDateFormat("dd.MM.yyyy");
    var today = Calendar.getInstance().getTime();
    this.Name = name;
    this.VersionDate = formatter.format(today) ;
    this.Message = id;
    this.Version = versionNumber;
    this.VersionPadding = versionPadding;
    this.NamePadding = namePadding;
    this.LineNumber = lineNumber;
  }

  public VersionLine(String versionLine, int lineNumber) {
    this.VersionDate = versionLine.substring(0, 10); // DD.MM.YYYY
    var parseToNumber = versionLine.replaceFirst(this.VersionDate, "").strip(); // now we start with verison number
    this.Version = VersionNumber.ReadVersionNumber(parseToNumber);
    var parseToName = parseToNumber.replaceFirst(this.Version.GetVersionNumber(), "").strip(); // now we start with name
    this.Name = parseToName.substring(0, parseToName.indexOf(' '));
    var parseToMessage = parseToName.replaceFirst(this.Name, "").strip(); // now we start with message
    this.Message = parseToMessage;
    this.VersionPadding = versionLine.length() - this.VersionDate.length() - parseToName.length();
    this.NamePadding = versionLine.length() - this.VersionDate.length() - this.VersionPadding - parseToMessage.length();
    this.LineNumber = lineNumber;
  }

  public String ConstructVersionLine() {
    var versionNumber = this.getVersion().GetVersionNumber();
    return this.VersionDate
        + StringUtils.center(versionNumber, this.VersionPadding)
        + String.format("%1$-" + this.NamePadding + 's', this.Name)
        + this.Message
        ;
  }

  public String EditVersionInfo(String latestVersionInfo) {
    var versionStart = StringUtils.ordinalIndexOf(latestVersionInfo, "'", 1) + 1;
    var versionEnd = StringUtils.ordinalIndexOf(latestVersionInfo, "'", 2);
    var version = latestVersionInfo.substring(versionStart, versionEnd);
    var versionParts = version.split("\\.");
    versionParts[0] = Integer.toString(this.Version.getMajor());
    if (latestVersionInfo.contains("RETURN")) {
      versionParts[1] = Integer.toString(this.Version.getMinor());
      versionParts[4] = this.VersionDate.replace('.', '-');
    } else {
      versionParts[2] = this.VersionDate.replace('.', '-');
    }
    var currentVersion = StringUtils.join(versionParts, '.');
    return latestVersionInfo.replace(version, currentVersion);
  }

  /**
   * Value of field Version.
   *
   * @return value of field Version
   */
  public VersionNumber getVersion() {
    return Version;
  }

  /**
   * Value of field NamePadding.
   *
   * @return value of field NamePadding
   */
  public int getNamePadding() {
    return NamePadding;
  }

  /**
   * Value of field VersionPadding.
   *
   * @return value of field VersionPadding
   */
  public int getVersionPadding() {
    return VersionPadding;
  }

  /**
   * Value of field LineNumber.
   *
   * @return value of field LineNumber
   */
  public int getLineNumber() {
    return LineNumber;
  }

  @Override
  public String toString() {
    return "VersionLine{"
        + "Name='" + Name + '\''
        + ", VersionDate=" + VersionDate
        + ", Message='" + Message + '\''
        + '}';
  }

}
