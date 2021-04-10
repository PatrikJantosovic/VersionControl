package org.jantosovic.versioncontrol.api;

import java.nio.file.Path;
import java.util.List;

public interface IRepoAccessor {

  List<SourceFile> GetModifiedFiles(boolean onlyStaged);

  List<VersionedSourceFile> GetFilesById(String id, boolean onlyLastCommit);

  List<VersionedSourceFile> GetFilesByCommit(String commitId);

  Path GetPathToRepo();

}
