package org.jantosovic.versioncontrol.commands;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
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
@Command(name = "getc",
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
    var repoAccessor = appFactory.GetRepoAccessor();
    var messageBuilder = appFactory.GetMessageBuilder();
    var files = repoAccessor.GetFilesByCommit(CommitID);
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
    return "GetCommitChangesCmd{"
        + "Commit='" + CommitID + '\''
        + '}';
  }
}
