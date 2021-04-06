package org.jantosovic.versioncontrol.api;

import java.nio.file.Path;
import java.util.List;

public interface IRepoAccessor {

  List<SourceFile> GetModifiedFiles(boolean onlyStaged);

  List<SourceFile> GetFilesById(String id, boolean onlyLastCommit);

  List<SourceFile> GetFilesByCommit(String commitId);

  Path GetPathToRepo();

}
