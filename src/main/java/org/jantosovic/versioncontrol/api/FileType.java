package org.jantosovic.versioncontrol.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum FileType {
  PACKAGE("pkb", 16, false, "\\d+\\.\\d+\\.\\d+\\s+\\d+\\.\\d+\\s+\\w+\\s+\\[.+\\-.+\\]\\s+.*(?=\\n\\*\\/\\n(\\s+)?BEGIN(\\s+)?\\n(\\s+)?RETURN)"),
  VIEW("vw", 18, true, "\\d+\\.\\d+\\.\\d+\\s+\\d+\\s+\\w+\\s+\\[.+\\-.+\\]\\s+.*(?=\\n\\*\\/\\n(\\s+)?.*(\\s+)?version)");

  private final String Extension;
  private final int NamePadding;
  private final boolean MajorChangeOnly;
  private final String RegexPattern;

  FileType(String extension, int namePadding, boolean majorChangeOnly, String RegexPattern) {
    this.Extension = extension;
    this.NamePadding = namePadding;
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

  /**
   * Value of field NamePadding.
   *
   * @return value of field NamePadding
   */
  public int getNamePadding() {
    return NamePadding;
  }

  public static FileType GetByExtension(String extension) {
    return Arrays.stream(values())
        .filter(fileType -> fileType.Extension.equalsIgnoreCase(extension))
        .findFirst()
        .get()
        ;
  }

  public static FileType GetByFileName(String fileName) {
    return Arrays.stream(values())
        .filter(fileType -> fileName.endsWith(fileType.Extension))
        .findFirst()
        .get()
        ;
  }

  public static List<String> GetExtensions() {
    return Arrays.stream(values()).map(FileType::getExtension).collect(Collectors.toList());
  }

}

