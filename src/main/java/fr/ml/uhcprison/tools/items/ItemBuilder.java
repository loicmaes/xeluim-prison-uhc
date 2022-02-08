package fr.ml.uhcprison.tools.items;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.*;

public class ItemBuilder implements Cloneable {
    // Item result.
    private final ItemStack item;

    /**
     * Clone an existing item to modify some properties.
     * @param item = Item to clone.
     */
    public ItemBuilder(ItemStack item) {
        this.item = item;
    }

    /**
     * Create a custom item based on a default minecraft type.
     * @param material = Default minecraft type.
     */
    public ItemBuilder(@Nonnull Material material) {
        this.item = new ItemStack(material, 1, (short) 0);
    }

    /**
     * Create a custom item based on a default minecraft type and with a certain amount.
     * @param material = Default minecraft type.
     * @param amount = Items amount.
     */
    public ItemBuilder(@Nonnull Material material, int amount) {
        this.item = new ItemStack(material, amount, (short) 0);
    }

    /**
     * Create a custom item based on a default minecraft type, a certain variant and a certain amount.
     * @param material = Default minecraft type.
     * @param amount = Items amount.
     * @param data = Variant id.
     */
    public ItemBuilder(@Nonnull Material material, int amount, int data) {
        this.item = new ItemStack(material, amount, (short) data);
    }

    /**
     * Set a custom name to the item.
     * Colors are supported!
     * @param name = Name to set
     * @return the current instance of the ItemBuilder object.
     */
    public ItemBuilder setName(@Nullable String name) {
        ItemMeta meta = this.meta();
        meta.setDisplayName(Objects.isNull(name) ? null : ChatColor.translateAlternateColorCodes('&', name));
        return this.apply(meta);
    }

    /**
     * Set a custom description to the item.
     * Colors are supported!
     * @param lore = Lines list to set
     * @return the current instance of the ItemBuilder object.
     */
    public ItemBuilder setLore(@Nullable List<String> lore) {
        ItemMeta meta = this.meta();
        meta.setLore(lore);
        return this.apply(meta);
    }

    /**
     * Set a custom description to the item.
     * Colors are supported!
     * @param lore = Lines to set
     * @return the current instance of the ItemBuilder object.
     */
    public ItemBuilder setLore(String... lore) {
        List<String> ls = new ArrayList<>();
        Arrays.asList(lore).forEach(l -> ls.add(ChatColor.translateAlternateColorCodes('&', Objects.isNull(l) ? "&8" : l)));
        return this.setLore(ls);
    }

    /**
     * Add some flags to the item.
     * @param flags = Flags to add
     * @return the current instance of the ItemBuilder object.
     */
    public ItemBuilder addItemFlags(ItemFlag... flags) {
        ItemMeta meta = this.meta();
        meta.addItemFlags(flags);
        return this.apply(meta);
    }

    /**
     * Remove some flags from the item.
     * @param flags = Flags to remove
     * @return the current instance of the ItemBuilder object.
     */
    public ItemBuilder removeItemFlags(ItemFlag... flags) {
        ItemMeta meta = this.meta();
        meta.removeItemFlags(flags);
        return this.apply(meta);
    }

    /**
     * Set the unbreakable state of the item.
     * @param state = State (true / false)
     * @return the current instance of the ItemBuilder object.
     */
    public ItemBuilder setUnbreakable(boolean state) {
        ItemMeta meta = this.meta();
        meta.spigot().setUnbreakable(state);
        return this.apply(meta);
    }

    /**
     * Add an enchantment to the item.
     * @param enchantment = Enchantment to add
     * @param level = Level of the enchantment
     * @param safe = Is the enchantment safe?
     * @return the current instance of the ItemBuilder object.
     */
    public ItemBuilder addEnchantment(@Nonnull Enchantment enchantment, int level, boolean safe) {
        if (safe) {
            ItemMeta meta = this.meta();
            meta.addEnchant(enchantment, level, true);
            return this.apply(meta);
        }

        item.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    /**
     * Remove an enchantment from the item.
     * @param enchantment = Enchantment to remove (if present)
     * @return the current instance of the ItemBuilder object.
     */
    public ItemBuilder removeEnchantment(@Nonnull Enchantment enchantment) {
        ItemMeta meta = this.meta();
        meta.removeEnchant(enchantment);
        return this.apply(meta);
    }

    /**
     * Remove some enchantments from the item.
     * @param enchantments = Enchantments to remove
     * @return the current instance of the ItemBuilder object.
     */
    public ItemBuilder removeEnchantments(Enchantment... enchantments) {
        Arrays.asList(enchantments).forEach(this::removeEnchantment);
        return this;
    }

    /**
     * Clear all item's enchantments.
     * @return the current instance of the ItemBuilder object.
     */
    public ItemBuilder unEnchant() {
        this.meta().getEnchants().keySet().forEach(this::removeEnchantment);
        return this;
    }

    /**
     * Set the owner of a custom skull.
     * @param name = Owner's name to set
     * @return the current instance of the ItemBuilder object.
     */
    @SuppressWarnings("deprecation")
    public ItemBuilder setSkullOwner(@Nullable String name) {
        if (!this.item.getType().equals(Material.SKULL_ITEM) && this.item.getData().getData() != 3) return this;
        SkullMeta meta = this.skullMeta();
        meta.setOwner(name);
        return this.apply(meta);
    }

    /**
     * Set a custom texture to a custom skull.
     * @param texture = Texture value to set
     * @return the current instance of the ItemBuilder object.
     */
    @SuppressWarnings("deprecation")
    public ItemBuilder setSkullTexture(@Nonnull String texture) {
        if (!this.item.getType().equals(Material.SKULL_ITEM) && this.item.getData().getData() != 3) return this;
        SkullMeta meta = this.skullMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        try {
            profile.getProperties().put("textures", new Property("textures", texture));
            Field f = meta.getClass().getDeclaredField("profile");
            f.setAccessible(true);
            f.set(meta, profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return this.apply(meta);
    }

    /**
     * Set a color to a leather armor part.
     * @param color = Color to set
     * @return the current instance of the ItemBuilder object.
     */
    public ItemBuilder setArmorColor(@Nonnull Color color) {
        if (!this.item.getType().name().contains("LEATHER_")) return this;
        LeatherArmorMeta meta = this.leatherArmorMeta();
        meta.setColor(color);
        return this.apply(meta);
    }

    /**
     * Get item's meta data.
     * @return item's meta data.
     */
    private ItemMeta meta() {
        return this.item.getItemMeta();
    }

    /**
     * Get item's skull meta data.
     * @return item's skull meta data.
     */
    private SkullMeta skullMeta() {
        return (SkullMeta) this.meta();
    }

    /**
     * Get item's armor meta data.
     * @return item's armor meta data.
     */
    private LeatherArmorMeta leatherArmorMeta() {
        return (LeatherArmorMeta) this.meta();
    }

    /**
     * Apply modifications.
     * @param meta = New item's meta data to apply.
     * @return the current instance of the ItemBuilder object.
     */
    private ItemBuilder apply(@Nonnull ItemMeta meta) {
        this.item.setItemMeta(meta);
        return this;
    }

    /**
     * Clone the object.
     * @return a clone of the current instance of the ItemBuilder object.
     */
    public ItemBuilder clone() {
        try {
            return (ItemBuilder) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Create the final render.
     * @return the final item.
     */
    public ItemStack create() {
        return this.item;
    }
}
