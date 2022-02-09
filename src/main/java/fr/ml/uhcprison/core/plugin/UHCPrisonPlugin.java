package fr.ml.uhcprison.core.plugin;

import fr.ml.uhcprison.UHCPrison;
import fr.ml.uhcprison.core.commands.UHCPrisonCommand;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;

public class UHCPrisonPlugin {
    private final UHCPrison main;
    private final Settings settings;

    private UHCPrisonPlugin(UHCPrison main) {
        this.main = main;
        this.settings = new Settings(main);
    }
    public static UHCPrisonPlugin create(UHCPrison main) {
        return new UHCPrisonPlugin(main);
    }

    public void onLoad() {
        this.main.saveResource("messages.yml", false);
        this.settings.load();
    }

    public void onEnable() {
        PluginManager pm = this.main.getServer().getPluginManager();

        this.main.getCommand("uhcprison").setExecutor(new UHCPrisonCommand());
    }

    public void onDisable() {
        this.settings.save();
    }

    public void reset() {
        this.main.saveResource("messages.yml", true);
        this.settings.reset();
    }

    public Settings getSettings() {
        return settings;
    }
}
