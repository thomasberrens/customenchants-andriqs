package tokeee.rubixstudio.customenchants.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tokeee.rubixstudio.customenchants.CustomEnchants;

import java.util.ArrayList;

public class GiveCustomItemCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (strings.length == 0) return false;
        if (commandSender.hasPermission("customenchants.admin") && commandSender instanceof Player) {
            if (strings[0].equalsIgnoreCase("give")) {
                final String targetName = strings[1];
                final Player player = Bukkit.getPlayer(targetName);

                final String enchantName = strings[2].toLowerCase();
                final int amount = Integer.parseInt(strings[3]);

                final char ch = ' ';
                final String enchantmentName = enchantName.replace('_', ch);

                for (final Enchantment enchant : CustomEnchants.getInstance().getAllCustomEnchantments()) {
                    if (enchantmentName.equalsIgnoreCase(enchant.getName())) {
                        final ItemStack itemStack = new ItemStack(specialBook(enchant, amount));
                        player.getInventory().addItem(itemStack);
                    }
                }
                return true;
            }


            return true;
        }
        return true;
    }

    private ItemStack specialBook(final Enchantment ench, int amount){
        final ItemStack item;
        item = new ItemStack(Material.ENCHANTED_BOOK);
        item.setAmount(amount);
        final ItemMeta meta = item.getItemMeta();

        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GRAY + ench.getName() + " I");
        lore.add(ChatColor.GRAY + "Applicable to: " + ChatColor.GOLD + ench.getItemTarget().toString());
        lore.add(ChatColor.DARK_PURPLE + "Use this book in a anvil!");
        meta.setDisplayName(ChatColor.GOLD + ench.getName() + " book");

        meta.setLore(lore);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(ench, ench.getMaxLevel());

        return item;
    }
}
