package tokeee.rubixstudio.customenchants.armoreffects;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import tokeee.rubixstudio.customenchants.CustomEnchants;
import tokeee.rubixstudio.customenchants.enchantments.CustomEnchantment;
import tokeee.rubixstudio.customenchants.utils.EnchantUtils;

public class FireResEffect implements ArmorEffect {
    @Override
    public boolean condition(Player player) {
        if(player.getInventory().getArmorContents().length == 0){
            return false;
        }

        for (final ItemStack armorItem: player.getInventory().getArmorContents()) {
            if (EnchantUtils.hasItemStackEnchantment(armorItem, enchantment())) return true;
        }

        return false;
    }

    @Override
    public boolean addEffect(Player player) {

        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0));
        return true;
    }

    @Override
    public boolean removeEffect(Player player) {
        player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
        return true;
    }

    @Override
    public void run(Player player) {

    }

    @Override
    public Enchantment enchantment() {
        return CustomEnchants.getInstance().getFireResEnchantment();
    }
}
