package me.coding2gether.chestlocker.listener;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import me.coding2gether.chestlocker.main.API;
import me.coding2gether.chetslocker.utils.FW;

public class onBlockPlace implements Listener {
	public static Inventory inv = Bukkit.createInventory(null, 9 * 3, "§aPasswort setzen");
	public static HashMap<Block, String> password = new HashMap<>();
	public static HashMap<Player, Block> passwords = new HashMap<>();

	@EventHandler
	public void on(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if (e.getBlock().getType() == Material.CHEST) {
			FW f = new FW("plugins//chestlocker",
					 e.getBlock().getX() + "." + e.getBlock().getY() + "." + e.getBlock().getZ()+".yml");
			if(!f.exist()) {
			f.createFile();

			password.put(e.getBlock(), "");
			passwords.put(p, e.getBlock());

			loadInv();
			p.openInventory(inv);
			}else {
				f.delete();
				f.createFile();
	
				password.put(e.getBlock(), "");
				passwords.put(p, e.getBlock());

				loadInv();
				p.openInventory(inv);
			}
		}

	}
	public static HashMap<Player, Inventory>chestinv = new HashMap<>();


	@EventHandler
	public void on(PlayerInteractEvent e) {
		Player p =e.getPlayer();
	Inventory invs = Bukkit.createInventory(null, 9 * 3, "§aPasswort eingeben");

		for (int x = 0; x < 27; x++) {
			invs.setItem(x, API.createItem(Material.STAINED_GLASS_PANE, "§6", "", 1, 15));

		}
		invs.setItem(3, API.createItem(Material.STAINED_CLAY, "§61", "", 1, 5));
		invs.setItem(4, API.createItem(Material.STAINED_CLAY, "§62", "", 2, 5));
		invs.setItem(5, API.createItem(Material.STAINED_CLAY, "§63", "", 3, 5));

		invs.setItem(12, API.createItem(Material.STAINED_CLAY, "§64", "", 4, 5));
		invs.setItem(13, API.createItem(Material.STAINED_CLAY, "§65", "", 5, 5));
		invs.setItem(14, API.createItem(Material.STAINED_CLAY, "§66", "", 6, 5));

		invs.setItem(21, API.createItem(Material.STAINED_CLAY, "§67", "", 7, 5));
		invs.setItem(22, API.createItem(Material.STAINED_CLAY, "§68", "", 8, 5));
		invs.setItem(23, API.createItem(Material.STAINED_CLAY, "§69", "", 9, 5));
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getType() == Material.CHEST) {
				FW f = new FW("plugins//chestlocker",e.getClickedBlock().getX() + "." + e.getClickedBlock().getY() + "." + e.getClickedBlock().getZ()+".yml");
				if(f.exist()) {
					e.setCancelled(true);
					Chest c = (Chest) e.getClickedBlock().getState();
					chestinv.put(p, c.getBlockInventory());
					passwords.put(p, e.getClickedBlock());

					p.openInventory(invs);
					
					
					
				}
				
				
			}
			
		}
		
		
	}
	@EventHandler
	public void on(BlockBreakEvent e) {
		
		if(e.getBlock().getType() == Material.CHEST) {
			FW f = new FW("plugins//chestlocker", e.getBlock().getX() + "." + e.getBlock().getY() + "." + e.getBlock().getZ()+".yml");
			if(f.exist()) {
				f.delete();
			}

			
		}
	}
	HashMap<Player, String> pw = new HashMap<>();

	@EventHandler
	public void on(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		try {
			if(e.getSlotType() == SlotType.OUTSIDE) {
				e.setCancelled(true);
			}
			if (e.getInventory().getTitle().equalsIgnoreCase("§aPasswort setzen")) {

				e.setCancelled(true);
				if(!pw.containsKey(p)) {
				pw.put(p, "");
				}
				if(pw.get(p).length()== 4) {
					p.closeInventory();
					p.sendMessage("§aDu hast das Chest Passwort gesetzt!");
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 2f, 3f);
					password.put(passwords.get(p), pw.get(p));
					pw.remove(p);
					
					FW f = new FW("plugins//chestlocker",passwords.get(p).getX()+"."+passwords.get(p).getY()+"."+passwords.get(p).getZ()+".yml");
					f.setValue("Password", password.get(passwords.get(p)));
					f.saveFile();
					password.remove(passwords.get(p));
					passwords.remove(p);
				}else {
					p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1f, 1f);
					String pass = pw.get(p);
				if (e.getSlot() == 3) {
					pw.put(p, pass+"1");
					if(pw.get(p).length()== 4) {
						p.closeInventory();
						p.sendMessage("§aDu hast das Chest Passwort gesetzt!");
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 2f, 3f);
						password.put(passwords.get(p), pw.get(p));
						pw.remove(p);
						
						FW f = new FW("plugins//chestlocker",passwords.get(p).getX()+"."+passwords.get(p).getY()+"."+passwords.get(p).getZ()+".yml");
						f.setValue("Password", password.get(passwords.get(p)));
						f.saveFile();
						password.remove(passwords.get(p));
						passwords.remove(p);
					}

				}
				if (e.getSlot() == 4) {
					pw.put(p, pass+"2");
					if(pw.get(p).length()== 4) {
						p.closeInventory();
						p.sendMessage("§aDu hast das Chest Passwort gesetzt!");
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 2f, 3f);
						password.put(passwords.get(p), pw.get(p));
						pw.remove(p);
						
						FW f = new FW("plugins//chestlocker",passwords.get(p).getX()+"."+passwords.get(p).getY()+"."+passwords.get(p).getZ()+".yml");
						f.setValue("Password", password.get(passwords.get(p)));
						f.saveFile();
						password.remove(passwords.get(p));
						passwords.remove(p);
					}

				}
				if (e.getSlot() == 5) {
					pw.put(p, pass+"3");
					if(pw.get(p).length()== 4) {
						p.closeInventory();
						p.sendMessage("§aDu hast das Chest Passwort gesetzt!");
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 2f, 3f);
						password.put(passwords.get(p), pw.get(p));
						pw.remove(p);
						
						FW f = new FW("plugins//chestlocker",passwords.get(p).getX()+"."+passwords.get(p).getY()+"."+passwords.get(p).getZ()+".yml");
						f.setValue("Password", password.get(passwords.get(p)));
						f.saveFile();
						password.remove(passwords.get(p));
						passwords.remove(p);
					}

				}	
				if (e.getSlot() == 12) {
					pw.put(p, pass+"4");
				
					if(pw.get(p).length()== 4) {
						p.closeInventory();
						p.sendMessage("§aDu hast das Chest Passwort gesetzt!");
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 2f, 3f);
						password.put(passwords.get(p), pw.get(p));
						pw.remove(p);
						
						FW f = new FW("plugins//chestlocker",passwords.get(p).getX()+"."+passwords.get(p).getY()+"."+passwords.get(p).getZ()+".yml");
						f.setValue("Password", password.get(passwords.get(p)));
						f.saveFile();
						password.remove(passwords.get(p));
						passwords.remove(p);
					}
				}
				if (e.getSlot() == 13) {
					pw.put(p, pass+"5");
					if(pw.get(p).length()== 4) {
						p.closeInventory();
						p.sendMessage("§aDu hast das Chest Passwort gesetzt!");
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 2f, 3f);
						password.put(passwords.get(p), pw.get(p));
						pw.remove(p);
						
						FW f = new FW("plugins//chestlocker",passwords.get(p).getX()+"."+passwords.get(p).getY()+"."+passwords.get(p).getZ()+".yml");
						f.setValue("Password", password.get(passwords.get(p)));
						f.saveFile();
						password.remove(passwords.get(p));
						passwords.remove(p);
					}

				}
				if (e.getSlot() == 14) {
					pw.put(p, pass+"6");
					if(pw.get(p).length()== 4) {
						p.closeInventory();
						p.sendMessage("§aDu hast das Chest Passwort gesetzt!");
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 2f, 3f);
						password.put(passwords.get(p), pw.get(p));
						pw.remove(p);
						
						FW f = new FW("plugins//chestlocker",passwords.get(p).getX()+"."+passwords.get(p).getY()+"."+passwords.get(p).getZ()+".yml");
						f.setValue("Password", password.get(passwords.get(p)));
						f.saveFile();
						password.remove(passwords.get(p));
						passwords.remove(p);
					}
				}
				if (e.getSlot() == 21) {
					pw.put(p, pass+"7");
				
					if(pw.get(p).length()== 4) {
						p.closeInventory();
						p.sendMessage("§aDu hast das Chest Passwort gesetzt!");
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 2f, 3f);
						password.put(passwords.get(p), pw.get(p));
						pw.remove(p);
						
						FW f = new FW("plugins//chestlocker",passwords.get(p).getX()+"."+passwords.get(p).getY()+"."+passwords.get(p).getZ()+".yml");
						f.setValue("Password", password.get(passwords.get(p)));
						f.saveFile();
						password.remove(passwords.get(p));
						passwords.remove(p);
					}
				}
				if (e.getSlot() == 22) {
					pw.put(p, pass+"8");
					if(pw.get(p).length()== 4) {
						p.closeInventory();
						p.sendMessage("§aDu hast das Chest Passwort gesetzt!");
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 2f, 3f);
						password.put(passwords.get(p), pw.get(p));
						pw.remove(p);
						
						FW f = new FW("plugins//chestlocker",passwords.get(p).getX()+"."+passwords.get(p).getY()+"."+passwords.get(p).getZ()+".yml");
						f.setValue("Password", password.get(passwords.get(p)));
						f.saveFile();
						password.remove(passwords.get(p));
						passwords.remove(p);
					}

				}
				if (e.getSlot() == 23) {
					pw.put(p, pass+"9");
					if(pw.get(p).length()== 4) {
						p.closeInventory();
						p.sendMessage("§aDu hast das Chest Passwort gesetzt!");
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 2f, 3f);
						password.put(passwords.get(p), pw.get(p));
						pw.remove(p);
						
						FW f = new FW("plugins//chestlocker",passwords.get(p).getX()+"."+passwords.get(p).getY()+"."+passwords.get(p).getZ()+".yml");
						f.setValue("Password", password.get(passwords.get(p)));
						f.saveFile();
						password.remove(passwords.get(p));
						passwords.remove(p);
					}

				}
				}

			}else 	if(e.getClickedInventory().getTitle().equalsIgnoreCase("§aPasswort eingeben")) {
				
				e.setCancelled(true);
				FW f = new FW("plugins//chestlocker",passwords.get(p).getX()+"."+passwords.get(p).getY()+"."+passwords.get(p).getZ()+".yml");

				if(!pw.containsKey(p)) {
					pw.put(p, "");
					}
				if(pw.get(p).length()== 4) {
					p.closeInventory();
					if(f.getString("Password").equalsIgnoreCase(pw.get(p))) {
						p.openInventory(chestinv.get(p));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 2f, 3f);
						pw.remove(p);
						passwords.remove(p);
						chestinv.remove(p);
					}else {
						pw.remove(p);
						passwords.remove(p);
						chestinv.remove(p);
						p.sendMessage("§cPasswort falsch!");
						p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 1f);
					}
					
				}else {
					p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1f, 1f);

				if (e.getSlot() == 3) {
					pw.put(p, pw.get(p)+"1");
					if(pw.get(p).length()== 4) {
						p.closeInventory();
						if(f.getString("Password").equalsIgnoreCase(pw.get(p))) {
							p.openInventory(chestinv.get(p));
							p.playSound(p.getLocation(), Sound.LEVEL_UP, 2f, 3f);
							pw.remove(p);
							passwords.remove(p);
							chestinv.remove(p);
						}else {
							pw.remove(p);
							passwords.remove(p);
							chestinv.remove(p);
							p.sendMessage("§cPasswort falsch!");
							p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 1f);
						}
						
					}
				}
				if (e.getSlot() == 4) {
					pw.put(p, pw.get(p)+"2");
					if(pw.get(p).length()== 4) {
						p.closeInventory();
						if(f.getString("Password").equalsIgnoreCase(pw.get(p))) {
							p.openInventory(chestinv.get(p));
							p.playSound(p.getLocation(), Sound.LEVEL_UP, 2f, 3f);
							pw.remove(p);
							passwords.remove(p);
							chestinv.remove(p);
						}else {
							pw.remove(p);
							passwords.remove(p);
							chestinv.remove(p);
							p.sendMessage("§cPasswort falsch!");
							p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 1f);
						}
						
					}
				}
				if (e.getSlot() == 5) {
					pw.put(p, pw.get(p)+"3");
					if(pw.get(p).length()== 4) {
						p.closeInventory();
						if(f.getString("Password").equalsIgnoreCase(pw.get(p))) {
							p.openInventory(chestinv.get(p));
							p.playSound(p.getLocation(), Sound.LEVEL_UP, 2f, 3f);
							pw.remove(p);
							passwords.remove(p);
							chestinv.remove(p);
						}else {
							pw.remove(p);
							passwords.remove(p);
							chestinv.remove(p);
							p.sendMessage("§cPasswort falsch!");
							p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 1f);
						}
						
					}
				}	
				if (e.getSlot() == 12) {
					pw.put(p, pw.get(p)+"4");
					if(pw.get(p).length()== 4) {
						p.closeInventory();
						if(f.getString("Password").equalsIgnoreCase(pw.get(p))) {
							p.openInventory(chestinv.get(p));
							p.playSound(p.getLocation(), Sound.LEVEL_UP, 2f, 3f);
							pw.remove(p);
							passwords.remove(p);
							chestinv.remove(p);
						}else {
							pw.remove(p);
							passwords.remove(p);
							chestinv.remove(p);
							p.sendMessage("§cPasswort falsch!");
							p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 1f);
						}
						
					}
				}
				if (e.getSlot() == 13) {
					pw.put(p, pw.get(p)+"5");
					if(pw.get(p).length()== 4) {
						p.closeInventory();
						if(f.getString("Password").equalsIgnoreCase(pw.get(p))) {
							p.openInventory(chestinv.get(p));
							p.playSound(p.getLocation(), Sound.LEVEL_UP, 2f, 3f);
							pw.remove(p);
							passwords.remove(p);
							chestinv.remove(p);
						}else {
							pw.remove(p);
							passwords.remove(p);
							chestinv.remove(p);
							p.sendMessage("§cPasswort falsch!");
							p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 1f);
						}
						
					}
				}
				if (e.getSlot() == 14) {
					pw.put(p, pw.get(p)+"6");
					if(pw.get(p).length()== 4) {
						p.closeInventory();
						if(f.getString("Password").equalsIgnoreCase(pw.get(p))) {
							p.openInventory(chestinv.get(p));
							p.playSound(p.getLocation(), Sound.LEVEL_UP, 2f, 3f);
							pw.remove(p);
							passwords.remove(p);
							chestinv.remove(p);
						}else {
							pw.remove(p);
							passwords.remove(p);
							chestinv.remove(p);
							p.sendMessage("§cPasswort falsch!");
							p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 1f);
						}
						
					}
				}
				if (e.getSlot() == 21) {
					pw.put(p, pw.get(p)+"7");
					if(pw.get(p).length()== 4) {
						p.closeInventory();
						if(f.getString("Password").equalsIgnoreCase(pw.get(p))) {
							p.openInventory(chestinv.get(p));
							p.playSound(p.getLocation(), Sound.LEVEL_UP, 2f, 3f);
							pw.remove(p);
							passwords.remove(p);
							chestinv.remove(p);
						}else {
							pw.remove(p);
							passwords.remove(p);
							chestinv.remove(p);
							p.sendMessage("§cPasswort falsch!");
							p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 1f);
						}
						
					}
				}
				if (e.getSlot() == 22) {
					pw.put(p, pw.get(p)+"8");
					if(pw.get(p).length()== 4) {
						p.closeInventory();
						if(f.getString("Password").equalsIgnoreCase(pw.get(p))) {
							p.openInventory(chestinv.get(p));
							p.playSound(p.getLocation(), Sound.LEVEL_UP, 2f, 3f);
							pw.remove(p);
							passwords.remove(p);
							chestinv.remove(p);
						}else {
							pw.remove(p);
							passwords.remove(p);
							chestinv.remove(p);
							p.sendMessage("§cPasswort falsch!");
							p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 1f);
						}
						
					}
				}
				if (e.getSlot() == 23) {
					pw.put(p, pw.get(p)+"9");
					if(pw.get(p).length()== 4) {
						p.closeInventory();
						if(f.getString("Password").equalsIgnoreCase(pw.get(p))) {
							p.openInventory(chestinv.get(p));
							p.playSound(p.getLocation(), Sound.LEVEL_UP, 2f, 3f);
							pw.remove(p);
							passwords.remove(p);
							chestinv.remove(p);
						}else {
							pw.remove(p);
							passwords.remove(p);
							chestinv.remove(p);
							p.sendMessage("§cPasswort falsch!");
							p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1f, 1f);
						}
						
					}
				}
				}
				
			
		}else {
			
		}
		} catch (Exception e2) {
			// TODO: handle exception
		}
	
		
	}

	public static void loadInv() {
		for (int x = 0; x < 27; x++) {
			inv.setItem(x, API.createItem(Material.STAINED_GLASS_PANE, "§6", "", 1, 15));

		}
		inv.setItem(3, API.createItem(Material.STAINED_CLAY, "§61", "", 1, 5));
		inv.setItem(4, API.createItem(Material.STAINED_CLAY, "§62", "", 2, 5));
		inv.setItem(5, API.createItem(Material.STAINED_CLAY, "§63", "", 3, 5));

		inv.setItem(12, API.createItem(Material.STAINED_CLAY, "§64", "", 4, 5));
		inv.setItem(13, API.createItem(Material.STAINED_CLAY, "§65", "", 5, 5));
		inv.setItem(14, API.createItem(Material.STAINED_CLAY, "§66", "", 6, 5));

		inv.setItem(21, API.createItem(Material.STAINED_CLAY, "§67", "", 7, 5));
		inv.setItem(22, API.createItem(Material.STAINED_CLAY, "§68", "", 8, 5));
		inv.setItem(23, API.createItem(Material.STAINED_CLAY, "§69", "", 9, 5));

	}

}
