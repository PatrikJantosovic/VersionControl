package org.jantosovic.versioncontrol.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

public final class RepoAccessor implements IRepoAccessor {

  private final Path PathToRepo;
  private final List<String> FileTypes;

  private static final Logger LOG = Logger.getLogger(RepoAccessor.class);

  public RepoAccessor(){
    this.FileTypes = FileType.GetExtensions();
    this.PathToRepo = getConfigurationFromFile();
  }

  private Git getGit() throws IOException {
    return Git.open(PathToRepo.toFile());
  }

  private Path getConfigurationFromFile() {
    var classLoader = getClass().getClassLoader();
    try (var inputStream = classLoader.getResourceAsStream("config.properties")) {
      if (inputStream == null) {
        throw new IllegalArgumentException("config.properties file not found!");
      }
      try (var reader = new BufferedReader(
          new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
        var line = reader.readLine();
        return Path.of(line.substring(line.indexOf(':') + 1));
      }
    } catch (IOException e) {
      LOG.error("Failed to read config.properties", e);
    }
    return null;
  }

  private boolean isCorrectFileType(String fileName) {
    return this.FileTypes.stream().anyMatch(fileName::endsWith);
  }

  @Override
  public List<SourceFile> GetModifiedFiles(boolean onlyStaged) {
    var files = new ArrayList<SourceFile>(10);
    try (var git = getGit()) {
      var status = git.status().call();
      var allFiles = new ArrayList<String>(10);
      if (onlyStaged) {
        files.addAll(
            status.getAdded()
                .stream()
                .filter(this::isCorrectFileType)
                .map(s -> new SourceFile(this.PathToRepo.resolve(s)))
                .collect(Collectors.toList())
        );
        files.addAll(
            status.getChanged()
                .stream()
                .filter(this::isCorrectFileType)
                .map(s -> new SourceFile(this.PathToRepo.resolve(s)))
                .collect(Collectors.toList())
        );
        allFiles.addAll(status.getChanged());
        allFiles.addAll(status.getAdded());
      } else {
        files.addAll(
            status.getModified()
                .stream()
                .filter(this::isCorrectFileType)
                .map(s -> new SourceFile(this.PathToRepo.resolve(s)))
                .collect(Collectors.toList())
        );
        files.addAll(
            status.getUntracked()
                .stream()
                .filter(this::isCorrectFileType)
                .map(s -> new SourceFile(this.PathToRepo.resolve(s)))
                .collect(Collectors.toList())
        );
        allFiles.addAll(status.getModified());
        allFiles.addAll(status.getUntracked());
      }
      files.forEach(file -> file.setMajorChange(file.CalculateMajorChange(allFiles)));
    } catch (IOException e) {
    LOG.error("Failed to open repository " + PathToRepo);
  } catch (GitAPIException e) {
    LOG.error("Git API exception " + e);
  }
    return files;
  }

  @Override
  public List<VersionedSourceFile> GetFilesByCommit(String commitId) {
    var files = new ArrayList<VersionedSourceFile>(10);
    try (var git = getGit()) {
      var repo = git.getRepository();
      var walk = new RevWalk(repo);
      var objectId = ObjectId.fromString(commitId);
      var commit = walk.parseCommit(objectId);
      var parent = walk.parseCommit(commit.getParent(0).getId());
      var reader = repo.newObjectReader();
      var oldTreeIter = new CanonicalTreeParser();
      oldTreeIter.reset(reader, parent.getTree().getId());
      var newTreeIter = new CanonicalTreeParser();
      newTreeIter.reset(reader, commit.getTree().getId());
      var diffs = git.diff()
          .setNewTree(newTreeIter)
          .setOldTree(oldTreeIter)
          .call()
          ;
      for (var entry : diffs) {
        if (entry.getChangeType() != ChangeType.DELETE) {
          files.add(new VersionedSourceFile(this.PathToRepo.resolve(entry.getNewPath()), commitId));
        }
      }
    } catch (IOException e) {
      LOG.error("Failed to open repository " + PathToRepo + '\n' + e);
    } catch (GitAPIException e) {
      LOG.error("Git API exception " + e);
    }
    return files;
  }

  private List<String> GetCommitsById(String id) {
    var result = new ArrayList<String>(10);
    try (var git = getGit()) {
      for (var commit : git.log().all().call()) {
        var commitMessage = commit.getFullMessage();
        if (commitMessage.contains(id)) {
          LOG.info(commit.getId().getName());
          result.add(commit.getId().getName());
        }
      }
    } catch (IOException e) {
      LOG.error("Failed to open repository " + PathToRepo + '\n' + e);
    } catch (GitAPIException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public List<VersionedSourceFile> GetFilesById(String id, boolean onlyLastCommit) {
    var taskId = StringUtils.substringBetween(id, "[", "]");
    var commits = GetCommitsById(id);
    var files = new ArrayList<VersionedSourceFile>(10);
    for (var commit : commits) {
      files.addAll(GetFilesByCommit(commit));
      if (onlyLastCommit) {
        return files;
      }
    }
    return files;
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
