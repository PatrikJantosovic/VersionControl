package org.jantosovic.versioncontrol.api;

import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

/**
 *
 */
public class FileAccessor implements IFileAccessor {

  private final Path PathToRepo;

  private static final Logger LOG = Logger.getLogger(FileAccessor.class);

  public FileAccessor(Path pathToRepo) {
    PathToRepo = pathToRepo;
  }

  @Override
  public void AddVersionLines(String name, String taskMessage, List<SourceFile> files) {
    files.forEach(file -> AddVersionLine(name, taskMessage, file));
  }

  @Override
  public VersionLine GetLatestVersion(SourceFile file) {
    // load file content
    // call ParseLatestVersion
    // parse version line to VersionLine object
    // return VersionLine
    return null;
  }

  @Override
  public String ParseLatestVersion(String fileContent, String fileName) {
    var extension = fileName.substring(fileName.indexOf('.') + 1);
    var pattern = FileType.GetByExtension(extension).getRegexPattern();
    var matcher = Pattern.compile(pattern).matcher(fileContent);
    var hasResult = matcher.find();
    if (hasResult) {
      return matcher.group();
    }
    LOG.error("Could not find version line for given file: " + fileName);
    return "01.01.2020 0.0  jantosovic       [XXXXXXXX-XXXXXXX] pattern matcher failed";
  }

  @Override
  public List<VersionedSourceFile> GetFilesById(String id, boolean onlyLastCommit) {
    return null;
  }

  private void AddVersionLine(String name, String taskMessage, SourceFile file){
    var latest = GetLatestVersion(file);
    ;
  }

  @Override
  public String toString() {
    return "FileAccessor{"
        + "PathToRepo=" + PathToRepo
        + '}';
  }
}
