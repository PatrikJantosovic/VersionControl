package org.jantosovic.versioncontrol.api;

import java.nio.file.Path;
import java.util.Collection;

public class SourceFile {

  private final Path FilePath;
  private final String FileName;
  private final FileType SourceFileType;

  private boolean MajorChange;

  public SourceFile(Path filePathWithFileName){
    this.FilePath = filePathWithFileName;
    this.FileName = filePathWithFileName.getFileName().toString();
    this.SourceFileType = FileType.GetByFileName(this.FileName);
    this.MajorChange = this.SourceFileType.isMajorChangeOnly();
  }

  public boolean CalculateMajorChange(Collection<String> allFiles) {
    if (this.SourceFileType.isMajorChangeOnly()) {
      return true;
    }
    var fileNameWithOutExtension = this.FileName.substring(0, this.FileName.indexOf('.'));
    return allFiles.stream()
        .map(file -> Path.of(file).getFileName().toString())
        .filter(file -> !file.equalsIgnoreCase(this.FileName))
        .anyMatch(file -> file.startsWith(fileNameWithOutExtension));
  }

  @Override
  public String toString() {
    return "SourceFile{"
        + ", FilePath=" + getFilePath()
        + ", FileName=" + getFileName()
        + ", SourceFileType=" + getSourceFileType()
        + ", MajorChange=" + isMajorChange()
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

  /**
   * Value of field FileName.
   *
   * @return value of field FileName
   */
  public String getFileName() {
    return FileName;
  }

  /**
   * Value of field MajorChange.
   *
   * @return value of field MajorChange
   */
  public boolean isMajorChange() {
    return MajorChange;
  }

  /**
   * Set value of field MajorChange
   *
   * @param majorChange is new value to be set
   * @return self to enable chaining
   */
  public SourceFile setMajorChange(boolean majorChange) {
    MajorChange = majorChange;
    return this;
  }

  /**
   * Value of field SourceFileType.
   *
   * @return value of field SourceFileType
   */
  public FileType getSourceFileType() {
    return SourceFileType;
  }
}
