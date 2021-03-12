package org.jantosovic.versioncontrol.api;

import java.nio.file.Path;

public interface IAppFactory {

  IFileAccessor GetFileAccessor(Path pathToRepo);

  IRepoAccessor GetRepoAccessor();

  IMessageBuilder GetMessageBuilder();

}
