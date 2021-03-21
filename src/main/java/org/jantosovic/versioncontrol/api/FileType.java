package org.jantosovic.versioncontrol.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum FileType {
  PACKAGE("pkb", "\\d+\\.\\d+\\.\\d+\\s+\\d+\\.\\d+\\s+\\w+\\s+\\[.+\\-.+\\]\\s+.*(?=\\n\\*\\/\\n(\\s+)?BEGIN(\\s+)?\\n(\\s+)?RETURN)"),
  VIEW("vw", "\\d+\\.\\d+\\.\\d+\\s+\\d+\\s+\\w+\\s+\\[.+\\-.+\\]\\s+.*(?=\\n\\*\\/\\n(\\s+)?.*(\\s+)?version)");

  private final String extension;
  private final String regexPattern;

  FileType(String extension, String regexPattern) {
    this.extension = extension;
    this.regexPattern = regexPattern;
  }

  /**
   * Value of field extension.
   *
   * @return value of field extension
   */
  public String getExtension() {
    return extension;
  }

  /**
   * Value of field regexPattern.
   *
   * @return value of field regexPattern
   */
  public String getRegexPattern() {
    return regexPattern;
  }

  public static FileType GetByExtension(String extension) {
    return Arrays.stream(values())
        .filter(fileType -> fileType.extension.equalsIgnoreCase(extension))
        .findFirst()
        .get()
        ;
  }

  public static List<String> GetExtensions() {
    return Arrays.stream(values()).map(FileType::getExtension).collect(Collectors.toList());
  }

}

