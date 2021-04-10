package org.jantosovic.versioncontrol;

import java.util.concurrent.Callable;
import org.apache.log4j.Logger;
import org.jantosovic.versioncontrol.commands.AddVersionCmd;
import org.jantosovic.versioncontrol.commands.GetCommitChangesCmd;
import org.jantosovic.versioncontrol.commands.GetTaskChangesCmd;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(
    name = "version-control", version = "0.1",
    mixinStandardHelpOptions = true,
    subcommands = {AddVersionCmd.class, GetCommitChangesCmd.class, GetTaskChangesCmd.class}
    )
public final class App implements Callable<Integer>
{

    private static final Logger LOG = Logger.getLogger(App.class);

    /**
     * Entry point of application
     * @param args - application arguments
     */
    public static void main( String[] args )
    {
        var exitCode = new CommandLine(new App())
            .setCaseInsensitiveEnumValuesAllowed(true)
            .execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        LOG.error("No command supplied!");
        return -1;
    }
}
