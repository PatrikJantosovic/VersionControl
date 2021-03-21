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

  public VersionLine(String name, String id, VersionNumber versionNumber, int versionPadding, int namePadding) {
    var formatter = new SimpleDateFormat("dd.MM.yyyy");
    var today = Calendar.getInstance().getTime();
    this.Name = name;
    this.VersionDate = formatter.format(today) ;
    this.Message = id;
    this.Version = versionNumber;
    this.VersionPadding = versionPadding;
    this.NamePadding = namePadding;
  }

  public VersionLine(String versionLine) {
    this.VersionDate = versionLine.substring(0, 10); // DD.MM.YYYY
    var parseToNumber = versionLine.replaceFirst(this.VersionDate, "").strip(); // now we start with verison number
    this.Version = VersionNumber.ReadVersionNumber(parseToNumber);
    var parseToName = parseToNumber.replaceFirst(this.Version.GetVersionNumber(), "").strip(); // now we start with name
    this.Name = parseToName.substring(0, parseToName.indexOf(' '));
    var parseToMessage = parseToName.replaceFirst(this.Name, "").strip(); // now we start with message
    this.Message = parseToMessage;
    this.VersionPadding = versionLine.length() - this.VersionDate.length() - parseToName.length();
    this.NamePadding = versionLine.length() - this.VersionDate.length() - this.VersionPadding - parseToMessage.length();
  }

  public String ConstructVersionLine(FileType fileType) {
    var versionNumber = this.getVersion().GetVersionNumber();
    return this.VersionDate
        + StringUtils.center(versionNumber, this.VersionPadding)
        + String.format("%1$-" + this.NamePadding + 's', this.Name)
        + this.Message
        ;
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

  @Override
  public String toString() {
    return "VersionLine{"
        + "Name='" + Name + '\''
        + ", VersionDate=" + VersionDate
        + ", Message='" + Message + '\''
        + '}';
  }
}
