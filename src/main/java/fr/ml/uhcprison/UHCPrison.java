package fr.ml.uhcprison;

import fr.ml.uhcprison.core.plugin.UHCPrisonPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public class UHCPrison extends JavaPlugin {
    private final UHCPrisonPlugin plugin = UHCPrisonPlugin.create(this);

    @Override
    public void onLoad() {
        // LOADING STUFF
        this.plugin.onLoad();
    }

    @Override
    public void onEnable() {
        // ENABLING STUFF
        this.plugin.onEnable();
    }

    @Override
    public void onDisable() {
        // DISABLING STUFF
        this.plugin.onDisable();
    }

    public UHCPrisonPlugin getPlugin() {
        return this.plugin;
    }
}
