package fr.ml.uhcprison.core.plugin;

import fr.ml.uhcprison.UHCPrison;

public class UHCPrisonPlugin {
    private final UHCPrison main;

    private UHCPrisonPlugin(UHCPrison main) {
        this.main = main;
    }
    public static UHCPrisonPlugin create(UHCPrison main) {
        return new UHCPrisonPlugin(main);
    }
}
