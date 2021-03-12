package org.jantosovic.versioncontrol.api;

import java.nio.file.Path;

public final class AppFactory implements IAppFactory {

  private static final AppFactory INSTANCE = new AppFactory();

  @Override
  public IFileAccessor GetFileAccessor(Path pathToRepo) {
    return new FileAccessor(pathToRepo);
  }

  @Override
  public IRepoAccessor GetRepoAccessor() {
    return new RepoAccessor();
  }

  @Override
  public IMessageBuilder GetMessageBuilder() {
    return null;
  }

}
