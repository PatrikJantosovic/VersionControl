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
@Command(name = "add",
    description = "Add version line to all modified files.",
    mixinStandardHelpOptions = true)
public final class AddVersionCmd implements Callable<Integer> {

  @Option(names = {"-t", "--task"}, required = true,
      description = "Task ID ( [XXXXXXXX-XXXXXXX] ) and description.")
  private String TaskMessage;

  @Option(names = {"-n", "--name"},
      description = "Your name/nickname in version line. Defaults to OS user.")
  private String Name;

  @Option(names = {"-s", "--staged"}, defaultValue = "false",
      description = "Add version only to staged files.")
  private boolean Staged;

  private final IAppFactory appFactory;

  private static final Logger LOG = Logger.getLogger(AddVersionCmd.class);

  public AddVersionCmd(){
    this.appFactory = new AppFactory();
  }

  @Override
  public Integer call() {
    var repoAccessor = appFactory.GetRepoAccessor();
    var fileAccessor = appFactory.GetFileAccessor();
    if (Name == null || Name.isEmpty()) {
      Name = System.getProperty("user.name");
    }
    fileAccessor.AddVersionLines(Name, TaskMessage, repoAccessor.GetModifiedFiles(Staged));
    LOG.info("Files have been successfully modified");
    return 0;
  }

  @Override
  public String toString() {
    return "AddVersionCmd{"
        + "taskMessage='" + TaskMessage + '\''
        + ", name='" + Name + '\''
        + ", staged=" + Staged
        + '}';
  }
}
