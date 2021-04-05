package org.jantosovic.versioncontrol.api;

import com.sun.jdi.InternalException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

/**
 *
 */
public class FileAccessor implements IFileAccessor {

  private static final Logger LOG = Logger.getLogger(FileAccessor.class);

  @Override
  public void AddVersionLines(String name, String taskMessage, List<SourceFile> files) {
    files.forEach(file -> AddVersionLine(name, taskMessage, file));
  }

  @Override
  public VersionLine GetLatestVersion(SourceFile file) {
    try {
      var content = Files.readString(file.getFilePath());
      var latestVersionLine = ParseLatestVersion(content, file.getFileName());
      var lineNumber = GetLineNumber(file, latestVersionLine);
      return new VersionLine(latestVersionLine, lineNumber);
    } catch (IOException e) {
      LOG.error("Failed to get latest version of file.", e);
    }
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
    throw new InternalException();
  }

  @Override
  public List<VersionedSourceFile> GetFilesById(String id, boolean onlyLastCommit) {
    return null;
  }

  private void AddVersionLine(String name, String taskMessage, SourceFile file) {
    var latest = GetLatestVersion(file);
    if (latest == null) {
      LOG.error("Add version line failed for file: " + file.getFileName());
      return;
    }
    var current = new VersionLine(name,
        taskMessage,
        latest.getVersion().Increment(file.isMajorChange()),
        latest.getVersionPadding(),
        latest.getNamePadding(),
        latest.getLineNumber() + 1
    );
    AppendLineToFile(file, latest, current);
  }

  private static void AppendLineToFile(SourceFile file, VersionLine latest, VersionLine current) {
    var latestLine = latest.ConstructVersionLine();
    var currentLine = current.ConstructVersionLine();
    try {
      List<String> lines = Files.readAllLines(file.getFilePath(), StandardCharsets.UTF_8);
      lines.add(current.getLineNumber(), currentLine);
      var versionInfoPosition = current.getLineNumber() + file.getSourceFileType().getVersionInfoPosition();
      var latestVersionInfo = lines.get(versionInfoPosition);
      var currentVersionInfo = current.EditVersionInfo(latestVersionInfo);
      lines.set(versionInfoPosition, currentVersionInfo);
      Files.write(file.getFilePath(), lines, StandardCharsets.UTF_8);
    } catch (IOException ioe) {
      LOG.error("IOException: %s%n", ioe);
    }
  }

  private static int GetLineNumber(SourceFile file, String line) throws IOException {
    List<String> lines = Files.readAllLines(file.getFilePath(), StandardCharsets.UTF_8);
    return lines.indexOf(line);
  }

  @Override
  public String toString() {
    return "FileAccessor{"
        + '}';
  }
}
