package org.jantosovic.versioncontrol.api;

import java.util.List;

public interface IFileAccessor {

  void AddVersionLines(String name, String taskMessage, List<SourceFile> files);

  List<VersionedSourceFile> GetFilesById(String id, boolean onlyLastCommit);

}
