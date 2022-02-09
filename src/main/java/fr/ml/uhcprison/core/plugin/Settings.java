package fr.ml.uhcprison.core.plugin;

import fr.ml.uhcprison.UHCPrison;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class Settings {
    private final UHCPrison main;

    public String PREFIX, WORLD_PREFIX;
    public File SCHEMATICS_FOLDER;
    public List<String> DISABLED_SCHEMATICS;

    public Settings(UHCPrison main) {
        this.main = main;
    }

    public void load() {
        FileConfiguration config = this.main.getConfig();

        this.PREFIX = config.getString("Settings.Core.Prefix");
        this.WORLD_PREFIX = config.getString("Settings.Game.World_Prefix");
        this.SCHEMATICS_FOLDER = new File(this.main.getDataFolder(), config.getString("Settings.Schematics.Folder"));
        if (!this.SCHEMATICS_FOLDER.exists()) if (!this.SCHEMATICS_FOLDER.mkdir()) return;
        this.DISABLED_SCHEMATICS = config.getStringList("Settings.Schematics.Disabled_Schematics");
    }

    public void reset() {
        File file = new File(this.main.getDataFolder(), "config.yml");
        if (!file.delete()) return;
        this.main.saveDefaultConfig();
    }

    public void reload() {
        this.save();
        this.load();
    }

    public void save() {
        this.main.getConfig().set("", "");
        this.main.saveConfig();
    }
}
