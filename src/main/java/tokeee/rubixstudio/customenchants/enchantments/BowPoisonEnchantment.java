package tokeee.rubixstudio.customenchants.enchantments;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import tokeee.rubixstudio.customenchants.CustomEnchants;
import tokeee.rubixstudio.customenchants.PotionBowValue;
import tokeee.rubixstudio.customenchants.utils.EnchantUtils;

public class BowPoisonEnchantment extends PotionBowEnchantment{
    public BowPoisonEnchantment(int id) {
        super(id);

        this.setRed(50);
        this.setGreen(205);
        this.setBlue(50);

        this.setMin(CustomEnchants.getInstance().getConfig().getInt("poisonbow-effect-minimal.chance"));
        this.setMax(CustomEnchants.getInstance().getConfig().getInt("poisonbow-effect-maximal.chance"));

        this.setMetadata(PotionBowValue.POISON);

        final int duration = CustomEnchants.getInstance().getConfig().getInt("poisonbow-effect.seconds");
        final int amplifier = CustomEnchants.getInstance().getConfig().getInt("poisonbow-effect.amplifier");

        this.setPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * duration, amplifier));

        this.initialize();
    }

    @Override
    public boolean shouldBeDisplayedOnItemLore() {
        return true;
    }

    @Override
    public String getName() {
        return "Poison";
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
        return EnchantmentTarget.BOW;
    }

    @Override
    public boolean conflictsWith(Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack itemStack) {
        return itemStack.getType().equals(Material.BOW) && !EnchantUtils.hasItemStackEnchantment(itemStack, this);
    }
}
