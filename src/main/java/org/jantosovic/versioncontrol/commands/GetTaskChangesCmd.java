package org.jantosovic.versioncontrol.commands;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
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
public final class GetTaskChangesCmd implements Callable<Integer> {

  @Option(names = {"-t", "--task"}, required = true,
      description = "Task ID - [XXXXXXXX-XXXXXXX] ")
  private String TaskID;

  @Option(names = {"-l", "--latest"}, defaultValue = "false",
      description = "Get information only for latest commit with this ID. Otherwise returns all commits.")
  private boolean Latest;

  @Option(names = {"-r", "--repository"},
      description = "Path to repository.")
  private String Repo;

  private final IAppFactory appFactory;

  private static final Logger LOG = Logger.getLogger(GetTaskChangesCmd.class);

  public GetTaskChangesCmd(){
    this.appFactory = new AppFactory();
  }

  @Override
  public Integer call() {
    var repoAccessor = appFactory.GetRepoAccessor(Repo);
    var messageBuilder = appFactory.GetMessageBuilder();
    var files = repoAccessor.GetFilesById(TaskID, Latest);
    var result = messageBuilder.GetMessage(files);
    LOG.info("Message created:");
    LOG.info('\n' + result);
    var selection = new StringSelection(result);
    var clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(selection, selection);
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
