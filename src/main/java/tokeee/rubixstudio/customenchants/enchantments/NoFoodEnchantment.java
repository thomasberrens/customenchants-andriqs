package tokeee.rubixstudio.customenchants.enchantments;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import tokeee.rubixstudio.customenchants.CustomEnchants;
import tokeee.rubixstudio.customenchants.utils.EnchantUtils;
import tokeee.rubixstudio.customenchants.utils.ItemUtils;
import tokeee.rubixstudio.customenchants.utils.ParticleUtil;

public class NoFoodEnchantment extends CustomEnchantment {

    private @Getter BukkitRunnable noFoodTask;


    public NoFoodEnchantment(int id) {
        super(id);
        this.noFoodTask = noFoodTask();
        this.noFoodTask.runTaskTimer(CustomEnchants.getInstance(), 0L, 0L);
    }

    @Override
    public boolean shouldBeDisplayedOnItemLore() {
        return true;
    }

    @EventHandler
    private void FoodChangeEvent(final FoodLevelChangeEvent event){
        if (!(event.getEntity() instanceof Player)) return;

        final Player player = (Player) event.getEntity();
        if (player.getInventory().getHelmet() == null) return;

        if(EnchantUtils.hasItemStackEnchantment(player.getInventory().getHelmet(), this)){
            event.setCancelled(true);
        }
    }

    private BukkitRunnable noFoodTask() {
        return new BukkitRunnable() {
            @Override
            public void run() {


                Bukkit.getOnlinePlayers().forEach((p) -> {
                    if (p == null || !p.isOnline() || p.getInventory().getHelmet() == null || p.getFoodLevel() == 20) return;

                    if (EnchantUtils.hasItemStackEnchantment(p.getInventory().getHelmet(), CustomEnchants.getInstance().getNoFoodEnchantment())){
                        p.setFoodLevel(20);
                    }


                });
            }
        };
    }


    @Override
    public String getName() {
        return "No Food";
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ARMOR;
    }

    @Override
    public boolean conflictsWith(Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack itemStack) {
        return ItemUtils.isArmorHelmet(itemStack.getType()) && !EnchantUtils.hasItemStackEnchantment(itemStack, this);
    }
}
