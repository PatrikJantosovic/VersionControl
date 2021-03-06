package org.jantosovic.versioncontrol.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum FileType {
  PACKAGE("pkb", 3,false, "\\d+\\.\\d+\\.\\d+\\s+\\d+\\.\\d+\\s+\\w+\\s+\\[.+\\-.+\\]\\s+.*(?=(\\r)?\\n\\*\\/(\\r)?\\n(\\s+)?BEGIN(\\s+)?(\\r)?\\n(\\s+)?RETURN)"),
  VIEW("vw", 2,true, "\\d+\\.\\d+\\.\\d+\\s+\\d+\\s+\\w+\\s+\\[.+\\-.+\\]\\s+.*(?=(\\r)?\\n\\*\\/(\\r)?\\n(\\s+)?.*(\\s+)?(?i)(version))"),
  OTHER("", 0, false, "");

  private final String Extension;
  private final int VersionInfoPosition;
  private final boolean MajorChangeOnly;
  private final String RegexPattern;

  FileType(String extension, int versionInfoPosition, boolean majorChangeOnly, String RegexPattern) {
    this.Extension = extension;
    this.VersionInfoPosition = versionInfoPosition;
    this.MajorChangeOnly = majorChangeOnly;
    this.RegexPattern = RegexPattern;
  }

  /**
   * Value of field extension.
   *
   * @return value of field extension
   */
  public String getExtension() {
    return Extension;
  }

  /**
   * Value of field regexPattern.
   *
   * @return value of field regexPattern
   */
  public String getRegexPattern() {
    return RegexPattern;
  }

  /**
   * Value of field MajorChangeOnly.
   *
   * @return value of field MajorChangeOnly
   */
  public boolean isMajorChangeOnly() {
    return MajorChangeOnly;
  }

  public static FileType GetByExtension(String extension) {
    return Arrays.stream(values())
        .filter(fileType -> fileType.Extension.equalsIgnoreCase(extension))
        .findFirst().orElse(OTHER)
        ;
  }

  public static FileType GetByFileName(String fileName) {
    return Arrays.stream(values())
        .filter(fileType -> fileName.endsWith(fileType.Extension))
        .findFirst().orElse(OTHER)
        ;
  }

  public static List<String> GetExtensions() {
    return Arrays.stream(values())
        .filter(fileType -> fileType!=OTHER)
        .map(FileType::getExtension)
        .collect(Collectors.toList());
  }

  public int getVersionInfoPosition() {
    return this.VersionInfoPosition;
  }
}

