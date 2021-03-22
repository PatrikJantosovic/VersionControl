package org.jantosovic.versioncontrol.api;

import java.nio.file.Path;

public final class VersionedSourceFile extends SourceFile {

  private final String Version;

  private final String Commit;

  public VersionedSourceFile(Path filePathWithFileName, String version, String commit) {
    super(filePathWithFileName);
    this.Commit = commit;
    this.Version = version;
  }

  /**
   * Value of field Version.
   *
   * @return value of field Version
   */
  public String getVersion() {
    return Version;
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
        + "Version='" + Version + '\''
        + ", Commit='" + Commit + '\''
        + ", " + super.toString() + '}';
  }
}
