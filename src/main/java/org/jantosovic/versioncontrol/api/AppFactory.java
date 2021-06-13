package org.jantosovic.versioncontrol.api;

public final class AppFactory implements IAppFactory {

  private static final AppFactory INSTANCE = new AppFactory();

  @Override
  public IFileAccessor GetFileAccessor() {
    return new FileAccessor();
  }

  @Override
  public IRepoAccessor GetRepoAccessor(String pathToRepo) {
    return new RepoAccessor(pathToRepo);
  }

  @Override
  public IMessageBuilder GetMessageBuilder() {
    return new MessageBuilder();
  }

}
