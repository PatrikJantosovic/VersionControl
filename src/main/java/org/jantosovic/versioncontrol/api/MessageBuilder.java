package org.jantosovic.versioncontrol.api;

import java.util.List;
import java.util.stream.Collectors;

public class MessageBuilder implements IMessageBuilder {

  @Override
  public String GetMessage(List<VersionedSourceFile> files) {
    //group by commit
    var hashMap = files.stream().collect(Collectors.groupingBy(VersionedSourceFile::getCommit));
    var result = new StringBuilder();
    for (var commit : hashMap.entrySet()) {
      result.append("Commit: ");
      result.append(commit.getKey());
      result.append('\n');
      for (var file : commit.getValue()) {
        result.append(file.getFileName());
        result.append('\n');
      }
      result.append('\n');
    }
    return result.toString();
  }

}
