package org.jantosovic.versioncontrol.api;

import java.util.List;

public interface IFileAccessor {

  void AddVersionLines(String name, String taskMessage, List<SourceFile> files);

  VersionLine GetLatestVersion(SourceFile file);

  String ParseLatestVersion(String fileContent, String fileName);

  List<VersionedSourceFile> GetFilesById(String id, boolean onlyLastCommit, List<SourceFile> files);

  List<VersionedSourceFile> GetFilesByCommit(String commit, List<SourceFile> files);

}
