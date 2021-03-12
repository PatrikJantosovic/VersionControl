package org.jantosovic.versioncontrol.api;

import java.nio.file.Path;

public class SourceFile {

  private final Path FilePath;

  public SourceFile(String filePathWithFileName){
    this.FilePath = Path.of(filePathWithFileName);
  }

  @Override
  public String toString() {
    return "SourceFile{"
        + ", FilePath=" + getFilePath()
        + '}';
  }

  /**
   * Value of field FilePath.
   *
   * @return value of field FilePath
   */
  public Path getFilePath() {
    return FilePath;
  }

}
