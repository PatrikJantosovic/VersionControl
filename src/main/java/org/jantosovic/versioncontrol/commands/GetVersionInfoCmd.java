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
    description = "Get commit(s), list of files and their version for given BR.",
    mixinStandardHelpOptions = true)
public final class GetVersionInfoCmd implements Callable<Integer> {

  @Option(names = {"-t", "--task"}, required = true,
      description = "Task ID - [XX-XXXXXXXX] ")
  private String TaskID;

  @Option(names = {"-l", "--latest"}, defaultValue = "true",
      description = "Get information only for latest commit with this ID. Otherwise returns all commits.")
  private boolean Latest;

  private final IAppFactory appFactory;

  private static final Logger LOG = Logger.getLogger(GetVersionInfoCmd.class);

  public GetVersionInfoCmd(){
    this.appFactory = new AppFactory();
  }

  @Override
  public Integer call() {
    var repoAccessor = appFactory.GetRepoAccessor();
    var fileAccessor = appFactory.GetFileAccessor(repoAccessor.GetPathToRepo());
    var messageBuilder = appFactory.GetMessageBuilder();
    var result = messageBuilder.GetMessage(fileAccessor.GetFilesById(TaskID, Latest));
    LOG.info("Message created:");
    LOG.info(result);
    return 0;
  }

  @Override
  public String toString() {
    return "GetVersionInfoCmd{"
        + "BRMessage='" + TaskID + '\''
        + ", latest=" + Latest
        + '}';
  }
}
