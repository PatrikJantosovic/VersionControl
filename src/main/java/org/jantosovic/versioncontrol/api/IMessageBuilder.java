package org.jantosovic.versioncontrol.api;

import java.util.List;

public interface IMessageBuilder {

  String GetMessage(List<VersionedSourceFile> files);

}
