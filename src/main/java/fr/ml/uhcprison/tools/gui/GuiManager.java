package fr.ml.uhcprison.tools.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Loïc MAES
 * @version 1.0
 * @license MIT
 */
public class GuiManager {
    // All the guis off the plugin will be saved in this map.
    private final Map<Class<? extends GuiBuilder>, GuiBuilder> menus = new HashMap<>();

    /**
     * Register a GUI in the map to use it later and enable interactions.
     * @param gui = GUI Class (extends GuiBuilder) which you want to save.
     */
    public void register(@Nonnull GuiBuilder gui) {
        this.menus.put(gui.getClass(), gui);
    }

    /**
     * Open a GUI to a player.
     * @param player = Player who'll see the GUI.
     * @param gClass = The class extends of GUI which will be open to the player.
     */
    public void open(@Nonnull Player player, @Nonnull Class<? extends GuiBuilder> gClass) {
        GuiBuilder gui = this.get(gClass);
        Inventory inventory = Bukkit.createInventory(null, gui.size() > 9*6 ? 9*6 : Math.min(gui.size(), 9), ChatColor.translateAlternateColorCodes('&', gui.title()));
        gui.fill(player, inventory);
        if (Objects.nonNull(player.getOpenInventory())) player.closeInventory();
        player.openInventory(inventory);
    }

    /**
     * Get a GUI from his class.
     * @param gClass = Class of the GUI to recover.
     * @return the GUI interface.
     */
    private GuiBuilder get(@Nonnull Class<? extends GuiBuilder> gClass) {
        return this.menus.get(gClass);
    }

    /**
     * The event will be fired when the player will click into a registered GUI.
     * @param event = Event fired.
     */
    @EventHandler (priority = EventPriority.LOW)
    public void interact(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        ItemStack item = event.getCurrentItem();
        int slot = event.getSlot();
        ClickType click = event.getClick();

        if (Objects.isNull(inventory) || Objects.isNull(item) || item.getType().equals(Material.AIR)) return;

        this.menus.values().stream()
                .filter(menu -> inventory.getTitle().equals(ChatColor.translateAlternateColorCodes('&', menu.title())))
                .forEach(menu -> event.setCancelled(menu.interact(player, inventory, item, slot, click)));
    }
}
