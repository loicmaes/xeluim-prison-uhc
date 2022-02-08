package fr.ml.uhcprison.tools.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

/**
 * This class has been developed to make the gui creation easier!
 * @author Loïc MAES
 * @version 1.0
 * @license MIT
 */
public interface GuiBuilder {
    /**
     * Set inventory's title. Colors are supported!
     * @return the inventory's title.
     */
    @Nonnull String title();

    /**
     * Set the size of the inventory with the appropriate number (slots numbers [a multiple of 9])
     * @return the inventory size.
     */
    int size();

    /**
     * Fill the inventory like you want.
     * Make sure to respect inventory's size!
     * @param player = Player who'll see the inventory.
     * @param inventory = Inventory to fill.
     */
    void fill(@Nonnull Player player, @Nonnull Inventory inventory);

    /**
     * Make some actions when the player interacts with an item.
     * @param player = Targeted player.
     * @param inventory = Interacted inventory.
     * @param item = Clicked item object.
     * @param slot = Clicked item slot.
     * @param click = Type of the click which the player performed.
     * @return if the event needs to be cancelled
     */
    boolean interact(@Nonnull Player player, @Nonnull Inventory inventory, @Nonnull ItemStack item, int slot, @Nonnull ClickType click);
}
