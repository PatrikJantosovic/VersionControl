package org.jantosovic.versioncontrol.api;

import java.nio.file.Path;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 */
public final class FileAccessor implements IFileAccessor {

  private final Path PathToRepo;

  private static final Logger LOG = Logger.getLogger(FileAccessor.class);

  public FileAccessor(Path pathToRepo) {
    PathToRepo = pathToRepo;
  }

  @Override
  public void AddVersionLines(String name, String taskMessage, List<SourceFile> files) {
    ;
  }

  @Override
  public List<VersionedSourceFile> GetFilesById(String id, boolean onlyLastCommit) {
    return null;
  }

  @Override
  public String toString() {
    return "FileAccessor{"
        + "PathToRepo=" + PathToRepo
        + '}';
  }
}
