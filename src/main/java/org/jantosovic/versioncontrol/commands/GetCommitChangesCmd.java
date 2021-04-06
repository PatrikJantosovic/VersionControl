package org.jantosovic.versioncontrol.commands;

import java.util.concurrent.Callable;
import org.apache.log4j.Logger;
import org.jantosovic.versioncontrol.api.AppFactory;
import org.jantosovic.versioncontrol.api.IAppFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 *
 */
@Command(name = "get",
    description = "Get list of files and their version for given commit.",
    mixinStandardHelpOptions = true)
public final class GetCommitChangesCmd implements Callable<Integer> {

  @Option(names = {"-c", "--commit"}, required = true,
      description = "Commit ID - hash ")
  private String CommitID;

  private final IAppFactory appFactory;

  private static final Logger LOG = Logger.getLogger(GetCommitChangesCmd.class);

  public GetCommitChangesCmd(){
    this.appFactory = new AppFactory();
  }

  @Override
  public Integer call() {
    var fileAccessor = appFactory.GetFileAccessor();
    var messageBuilder = appFactory.GetMessageBuilder();
    var result = messageBuilder.GetMessage(fileAccessor.GetFilesByCommit(CommitID));
    LOG.info("Message created:");
    LOG.info(result);
    return 0;
  }

  @Override
  public String toString() {
    return "GetCommitChangesCmd{"
        + "Commit='" + CommitID + '\''
        + '}';
  }
}
