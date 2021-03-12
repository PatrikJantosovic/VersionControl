package org.jantosovic.versioncontrol.api;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 */
public final class VersionLine {

  private final String Name;

  private final String VersionDate;

  private final String Message;

  private final VersionNumber Version;

  public VersionLine(String name, String id, VersionNumber versionNumber){
    var formatter = new SimpleDateFormat("dd.MM.yyyy");
    var today = Calendar.getInstance().getTime();
    this.Name = name;
    this.VersionDate = formatter.format(today) ;
    this.Message = id;
    this.Version = versionNumber;
  }

  public VersionLine(String versionLine){
    this.VersionDate = versionLine.substring(0, 10); // DD.MM.YYYY
    var parse = versionLine.replaceFirst(this.VersionDate, "").strip(); // now we start with verison number
    this.Version = new VersionNumber(Integer.parseInt(parse.substring(0, parse.indexOf('.'))),
        Integer.parseInt(parse.substring(parse.indexOf('.')+1, parse.indexOf(' '))));
    parse = parse.replaceFirst(this.Version.GetVersionNumber(), "").strip(); // now we start with name
    this.Name = parse.substring(0, parse.indexOf(' '));
    parse = parse.replaceFirst(this.Name, "").strip(); // now we start with message
    this.Message = parse;
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
