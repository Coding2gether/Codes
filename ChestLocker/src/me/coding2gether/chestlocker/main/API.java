package me.coding2gether.chestlocker.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class API extends JavaPlugin{
	
	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("Die API startet.....");
		//SO Jetzt aber fertig viel spaﬂ mit der API :D

		Bukkit.getConsoleSender().sendMessage("Die API ist gestartet!");

	}
	public static ItemStack createItem(Material m, String name, String lore, int anzahl, int nebenID) {
		ItemStack is = new ItemStack(m, anzahl, (short) nebenID);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(name);
		im.setLore(Arrays.asList(lore));
		is.setAmount(anzahl);
		is.setItemMeta(im);
		return is;
	}
	public static ItemStack createItem(Material m, String name, ArrayList<String> lore, int anzahl, int nebenID) {
		ItemStack is = new ItemStack(m, anzahl, (short) nebenID);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(name);
		im.setLore(lore);
		is.setAmount(anzahl);
		is.setItemMeta(im);
		return is;
	}
	public static ItemStack createItemWithHiddenEnchant(Material m, String name, String lore, int anzahl, int nebenID) {
		ItemStack is = new ItemStack(m, anzahl, (short) nebenID);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(name);
		im.setLore(Arrays.asList(lore));
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		is.setAmount(anzahl);
		is.setItemMeta(im);
		unSafeEnchantment(org.bukkit.enchantments.Enchantment.OXYGEN, 1, is);
		return is;
		
	}
	public static ItemStack unSafeEnchantment(org.bukkit.enchantments.Enchantment ench, int Level, ItemStack is) {
		is.addUnsafeEnchantment(ench, Level);
		return is;
	}
	public static void playEffect(Location loc ,EnumParticle ep , float f, int anzahl,int schnelle) {
		PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(ep, true,(float)loc.getX(), (float)loc.getY(), (float)loc.getZ(), f,f,f,schnelle,anzahl,0,0);
		for(Player p : Bukkit.getOnlinePlayers()) {
			((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
		}
	}
	public static ItemStack createSkull(String HeadOwner, String ItemName, String lore) {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM,1,(byte)SkullType.PLAYER.ordinal());
		SkullMeta skullmeta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
		skullmeta.setOwner(HeadOwner);
		skullmeta.setDisplayName(ItemName);
		skullmeta.setLore(Arrays.asList(lore));
		skull.setItemMeta(skullmeta);
		return skull;
	}
	public static ItemStack setColor(ItemStack itemstack, int r, int g, int b) {
		LeatherArmorMeta itemmeta = (LeatherArmorMeta)itemstack.getItemMeta();
		itemmeta.setColor(Color.fromBGR(b,g, r));
		itemstack.setItemMeta(itemmeta);
		return itemstack;
	}
	public static void sendActionbar(Player p , String msg) {
		String NewMSg = msg.replace("_", " ");
		String s = ChatColor.translateAlternateColorCodes('&', NewMSg);
		IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \""+s+"\"}");
		PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte)2);
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(bar);
	}
	public static void setArmor(Player p, Color c, Collection<? extends Player>recievers) {
		int id = p.getEntityId();
		ItemStack armor = new ItemStack(Material.LEATHER_CHESTPLATE);
		LeatherArmorMeta meta = (LeatherArmorMeta)armor.getItemMeta();
		meta.setColor(c);
		net.minecraft.server.v1_8_R3.ItemStack istack = CraftItemStack.asNMSCopy(armor);
		PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(id, 2, istack);
		for(Player on: recievers) {
			((CraftPlayer)on).getHandle().playerConnection.sendPacket(packet);
		}
		
	}
}
