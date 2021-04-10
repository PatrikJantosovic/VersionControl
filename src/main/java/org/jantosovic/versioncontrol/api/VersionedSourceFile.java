package org.jantosovic.versioncontrol.api;

import java.nio.file.Path;

public final class VersionedSourceFile extends SourceFile {

  private final String Commit;

  public VersionedSourceFile(Path filePathWithFileName, String commit) {
    super(filePathWithFileName);
    this.Commit = commit;
  }

  /**
   * Value of field Commit.
   *
   * @return value of field Commit
   */
  public String getCommit() {
    return Commit;
  }

  @Override
  public String toString() {
    return "VersionedSourceFile{"
        + ", Commit='" + Commit + '\''
        + ", " + super.toString() + '}';
  }
}
