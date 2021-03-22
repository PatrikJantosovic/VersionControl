package org.jantosovic.versioncontrol.api;

public interface IAppFactory {

  IFileAccessor GetFileAccessor();

  IRepoAccessor GetRepoAccessor();

  IMessageBuilder GetMessageBuilder();

}
