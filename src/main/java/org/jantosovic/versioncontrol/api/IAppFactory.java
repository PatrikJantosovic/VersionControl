package org.jantosovic.versioncontrol.api;

public interface IAppFactory {

  IFileAccessor GetFileAccessor();

  IRepoAccessor GetRepoAccessor(String pathToRepo);

  IMessageBuilder GetMessageBuilder();

}
