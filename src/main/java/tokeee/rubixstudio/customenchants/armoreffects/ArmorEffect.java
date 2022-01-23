package tokeee.rubixstudio.customenchants.armoreffects;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

public interface ArmorEffect {
    boolean condition(Player player);
    boolean addEffect(Player player);
    boolean removeEffect(Player player);
    void run(Player player);
    Enchantment enchantment();
}
