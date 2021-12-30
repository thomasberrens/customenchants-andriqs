package tokeee.rubixstudio.customenchants.enchantments;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import tokeee.rubixstudio.customenchants.CustomEnchants;
import tokeee.rubixstudio.customenchants.PotionBowValue;
import tokeee.rubixstudio.customenchants.utils.EnchantUtils;
import tokeee.rubixstudio.customenchants.utils.ParticleUtil;

import java.util.Random;


@Getter @Setter
public abstract class PotionBowEnchantment extends CustomEnchantment implements Listener {

    private PotionEffect potionEffect;

    private int min;
    private int max;

    private int red;
    private int green;
    private int blue;

    private Effect effect;

    private final Random random = new Random();

    private String metadata;

    public PotionBowEnchantment(int id) {
        super(id);
        this.effect = Effect.POTION_SWIRL;
    }


    public void initialize(){
        PotionBowValue.potionBowValues.add(this.metadata);
    }


    @EventHandler (priority = EventPriority.HIGHEST)
    public void onShoot(final EntityShootBowEvent event) {
        if (event.isCancelled()) return;
        if (!(EnchantUtils.hasItemStackEnchantment(event.getBow(), this))) return;

        final Arrow arrow = (Arrow) event.getProjectile();

        if (!hasChance()){
            arrow.setMetadata(this.metadata, new FixedMetadataValue(CustomEnchants.getInstance(), true));
            ParticleUtil.createParticleTrailForArrow(arrow, red, green, blue, effect);
            return;
        }

        int chance = random.ints(this.min, (max+1)).findFirst().getAsInt();

        if (chance != 1) return;

        arrow.setMetadata(this.metadata, new FixedMetadataValue(CustomEnchants.getInstance(), true));

        ParticleUtil.createParticleTrailForArrow(arrow, red, green, blue, effect);

    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onDamageByEntity(final EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        if (!(event.getDamager() instanceof Arrow)) return;
        final Arrow arrow = (Arrow) event.getDamager();

        if (!(event.getEntity() instanceof Player)) return;
        final Player victim = (Player) event.getEntity();

        final boolean hasMeta = arrow.hasMetadata(this.metadata) && arrow.getMetadata(this.metadata).get(0).asBoolean();
        if (!hasMeta) return;

        victim.addPotionEffect(potionEffect);
    }

    private boolean hasChance(){
        if (this.min < 0 || this.max <= 0) {
            return false;
        } else {
            return true;
        }
    }
}

