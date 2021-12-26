package tokeee.rubixstudio.customenchants.builder;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import tokeee.rubixstudio.customenchants.enchantments.CustomEnchantment;
import tokeee.rubixstudio.customenchants.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoreBuilder implements Builder {
    private List<String> lore = new ArrayList<>();
    private ItemStack item;


    public LoreBuilder(ItemStack item) {
        this.item = item;
    }

    @Override
    public List<String> buildLore() {
        return this.lore;
    }

    @Override
    public List<String> getLore() {
        return lore;
    }

    @Override
    public LoreBuilder withEnchantments() {
        // TODO loop through enchantments and add them to lore list
        final Map<Enchantment, Integer> enchantments = this.item.getEnchantments();

        if (enchantments.size() == 0) {
            return this;
        }

        for (Enchantment ench : enchantments.keySet()) {
            final boolean isCustomEnchantment = ench instanceof CustomEnchantment;

            if (!isCustomEnchantment) continue;
            if (!((CustomEnchantment) ench).shouldBeDisplayedOnItemLore()) continue;

            this.lore.add(ChatColor.GRAY + ench.getName() + " " + StringUtils.getEnchantmentLevelName(ench.getMaxLevel()));
        }
        return this;
    }
}
