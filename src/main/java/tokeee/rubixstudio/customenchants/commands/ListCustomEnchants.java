package tokeee.rubixstudio.customenchants.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import tokeee.rubixstudio.customenchants.CustomEnchants;

public class ListCustomEnchants implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender.hasPermission("customenchants.admin") || commandSender.isOp()) {
            for (final Enchantment enchant : CustomEnchants.getInstance().getAllCustomEnchantments()) {
                final char ch = '_';
                final String enchantmentName = enchant.getName().replace(' ', ch);
                commandSender.sendMessage(ChatColor.GREEN + "Enchantment name: " + enchantmentName);
            }
        } else {
            commandSender.sendMessage(ChatColor.RED + "You do not have permission for this command.");
        }
        return true;
    }
}
