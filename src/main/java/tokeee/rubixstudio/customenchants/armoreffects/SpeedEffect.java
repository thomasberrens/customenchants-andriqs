package tokeee.rubixstudio.customenchants.armoreffects;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import tokeee.rubixstudio.customenchants.CustomEnchants;
import tokeee.rubixstudio.customenchants.utils.EnchantUtils;

import java.util.ArrayList;
import java.util.List;

public class SpeedEffect implements ArmorEffect{

    final List<Player> playerList = new ArrayList<>();

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

       player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
        return true;
    }

    @Override
    public boolean removeEffect(Player player) {

        player.removePotionEffect(PotionEffectType.SPEED);
        playerList.remove(player);
        return true;
    }

    @Override
    public void run(Player player) {

        boolean foundSpeed = false;

        for (final PotionEffect potionEffect: player.getActivePotionEffects()) {

            if (potionEffect.getDuration() < 43200 && potionEffect.getType().equals(PotionEffectType.SPEED) && !playerList.contains(player)) {
                playerList.add(player);
                player.removePotionEffect(PotionEffectType.SPEED);
                player.addPotionEffect(potionEffect);
                return;
            }

            if (playerList.contains(player) && potionEffect.getType().equals(PotionEffectType.SPEED)){
                foundSpeed = true;
            }
        }

        if (!foundSpeed && playerList.contains(player)){
            addEffect(player);
            playerList.remove(player);
        }
    }

    @Override
    public Enchantment enchantment() {
        return CustomEnchants.getInstance().getSpeedEnchantment();
    }
}
