package tokeee.rubixstudio.customenchants.armoreffects;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import tokeee.rubixstudio.customenchants.CustomEnchants;
import tokeee.rubixstudio.customenchants.utils.EnchantUtils;

public class NoFoodEffect implements ArmorEffect {

    @Override
    public boolean condition(final Player player) {
        return player.getInventory().getHelmet() != null &&
                EnchantUtils.hasItemStackEnchantment(player.getInventory().getHelmet(),
                        CustomEnchants.getInstance().getNoFoodEnchantment());
    }

    @Override
    public boolean addEffect(Player player) {
        player.setFoodLevel(20);
        return true;
    }

    @Override
    public boolean removeEffect(Player player) {
        return true;
    }

    @Override
    public void run(Player player) {

    }

    @Override
    public Enchantment enchantment() {
        return CustomEnchants.getInstance().getNoFoodEnchantment();
    }
}
