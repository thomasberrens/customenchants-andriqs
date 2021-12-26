package tokeee.rubixstudio.customenchants.enchantments;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.potion.PotionEffect;
import tokeee.rubixstudio.customenchants.CustomEnchants;
import tokeee.rubixstudio.customenchants.manager.EventManager;
import tokeee.rubixstudio.customenchants.utils.EnchantUtils;
import tokeee.rubixstudio.customenchants.utils.ParticleEffect;

import java.util.Random;

public abstract class MeleeEnchantment extends CustomEnchantment implements Listener {
    private final PotionEffect potionEffect;
    private final ParticleEffect particleEffect;

    private final Random random = new Random();

    //TODO make that the enchantment itself can modify those values
    private @Getter @Setter int min;
    private @Getter @Setter int max;

    private @Getter @Setter float offsetX = .3f;
    private @Getter @Setter float offsetY = 1f;
    private @Getter @Setter float offsetZ = .3f;

    private @Getter @Setter float speed = .2f;
    private @Getter @Setter int amount = 100;
    private @Getter @Setter float range = 16f;



    public MeleeEnchantment(int id, PotionEffect potionEffect, ParticleEffect particleEffect) {
        super(id);
        this.potionEffect = potionEffect;
        this.particleEffect = particleEffect;

    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onDamageByEntity(final EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;

        if (!(event.getDamager() instanceof Player)) return;
        if (!(event.getEntity() instanceof Player)) return;

        final Player victim = (Player) event.getEntity();
        final Player damager = (Player) event.getDamager();

        final ItemStack itemInHand = damager.getItemInHand();

        if (!EnchantUtils.hasItemStackEnchantment(itemInHand, this)) return;
        if (damager.getItemInHand().getType() == Material.ENCHANTED_BOOK) return;


        if (!hasChance()){
            addEffect(victim);
            return;
        }

        int chance = random.ints(this.min, (max+1)).findFirst().getAsInt();

        if (chance != 1) return;

        addEffect(victim);
    }

    private void addEffect(Player victim){
        victim.addPotionEffect(this.potionEffect);
        this.particleEffect.display(this.offsetX, this.offsetY, this.offsetZ, this.speed, this.amount, victim.getLocation(), this.range);
    }

    private boolean hasChance(){
        if (this.min < 0 || this.max <= 0) {
            return false;
        } else {
            return true;
        }
    }
}
