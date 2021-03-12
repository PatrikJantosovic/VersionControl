package org.jantosovic.versioncontrol.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

public final class RepoAccessor implements IRepoAccessor {

  private Path PathToRepo;
  private List<String> FileTypes;

  private static final Logger LOG = Logger.getLogger(RepoAccessor.class);

  public RepoAccessor(){
    getConfigurationFromFile();
  }

  private Git getGit() throws IOException {
    return Git.open(PathToRepo.toFile());
  }

  private void getConfigurationFromFile() {
    var classLoader = getClass().getClassLoader();
    try (var inputStream = classLoader.getResourceAsStream("config.properties")) {
      if (inputStream == null) {
        throw new IllegalArgumentException("config.properties file not found!");
      }
      try (var reader = new BufferedReader(
          new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
        var line = reader.readLine();
        while (line != null) {
          if (line.startsWith("repo-path:")) {
            this.PathToRepo = Path.of(line.substring(line.indexOf(':') + 1));
          }
          if (line.startsWith("file-type:")) {
            var types = line.substring(line.indexOf(':') + 1);
            this.FileTypes = Arrays.asList(types.split(","));
          }
          line = reader.readLine();
        }
      }
    } catch (IOException e) {
      LOG.error("Failed to read config.properties", e);
    }
  }

  private boolean isCorrectFileType(String fileName) {
    return this.FileTypes.stream().anyMatch(fileName::endsWith);
  }

  @Override
  public List<SourceFile> GetModifiedFiles(boolean onlyStaged) {
    var files = new ArrayList<SourceFile>(10);
    try (var git = getGit()) {
      var status = git.status().call();
      if (onlyStaged) {
        files.addAll(
            status.getAdded()
                .stream()
                .filter(this::isCorrectFileType)
                .map(SourceFile::new)
                .collect(Collectors.toList())
        );
        files.addAll(
            status.getChanged()
                .stream()
                .filter(this::isCorrectFileType)
                .map(SourceFile::new)
                .collect(Collectors.toList())
        );
      } else {
        files.addAll(
            status.getModified()
                .stream()
                .filter(this::isCorrectFileType)
                .map(SourceFile::new)
                .collect(Collectors.toList())
        );
        files.addAll(
            status.getUntracked()
                .stream()
                .filter(this::isCorrectFileType)
                .map(SourceFile::new)
                .collect(Collectors.toList())
        );
      }
    } catch (IOException e) {
      LOG.error("Failed to open repository " + PathToRepo);
    } catch (GitAPIException e) {
      LOG.error("Git API exception " + e);
    }
    return files;
  }

  @Override
  public List<SourceFile> GetFilesById(String id, boolean onlyLastCommit) {
    return null;
  }

  /**
   * Value of field PathToRepo.
   *
   * @return value of field PathToRepo
   */
  @Override
  public Path GetPathToRepo() {
    return this.PathToRepo;
  }

  @Override
  public String toString() {
    return "RepoAccessor{"
        + "PathToRepo=" + PathToRepo
        + ", FileTypes=" + FileTypes
        + '}';
  }
}
