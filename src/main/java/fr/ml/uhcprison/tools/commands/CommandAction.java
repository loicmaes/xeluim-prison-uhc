package fr.ml.uhcprison.tools.commands;

import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class CommandAction {
    /**
     * The name is used to call the action.
     * @return action's name.
     */
    public abstract String name();

    /**
     * Aliases are used too to call the action.
     * @return action's aliases.
     */
    public abstract String[] aliases();

    /**
     * Description will be displayed in the help command.
     * @return action's description.
     */
    public abstract String description();

    /**
     * Usage will be displayed in the help command to help a player to use it.
     * @return action's syntax model.
     */
    public abstract String usage();

    /**
     * Tab completer method will be used to upgrade the tab usage in game.
     * @param commandSender = Command's sender
     * @param s = Command's label.
     * @param strings = Current command arguments.
     * @return sub actions list.
     */
    public abstract List<String> tabCompleter(@Nonnull CommandSender commandSender, @Nonnull String s, @Nonnull String[] strings);

    /**
     * This method will be used to perform some actions while running the action.
     * @param commandSender = Command's sender.
     * @param s = Command's label.
     * @param strings = Current command arguments.
     * @return usage display?
     */
    public abstract boolean command(@Nonnull CommandSender commandSender, @Nonnull String s, @Nonnull String[] strings);
}
