package tokeee.rubixstudio.customenchants.manager;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import tokeee.rubixstudio.customenchants.CustomEnchants;
import tokeee.rubixstudio.customenchants.armoreffects.ArmorEffect;
import tokeee.rubixstudio.customenchants.armoreffects.FireResEffect;
import tokeee.rubixstudio.customenchants.armoreffects.NoFoodEffect;
import tokeee.rubixstudio.customenchants.armoreffects.SpeedEffect;
import tokeee.rubixstudio.customenchants.utils.EnchantUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArmorEffectManager implements Listener {
    private @Getter BukkitRunnable armorEffectTask;

    private @Getter final List<ArmorEffect> armorEffectList = new ArrayList<>();
    private @Getter final HashMap<Player, List<ArmorEffect>> currentPlayersWithArmorEffects = new HashMap<>();

    public ArmorEffectManager(){
        initializeArmorEffects();
        this.armorEffectTask = armorEffectTask();
        this.armorEffectTask.runTaskTimer(CustomEnchants.getInstance(), 0L, 0L);
    }

    private void initializeArmorEffects(){
        armorEffectList.add(new NoFoodEffect());
        armorEffectList.add(new FireResEffect());
        armorEffectList.add(new SpeedEffect());
    }

    @EventHandler
    private void playerLogout(final PlayerQuitEvent playerQuitEvent){
        final Player player = playerQuitEvent.getPlayer();

        if (currentPlayersWithArmorEffects.containsKey(player)){
            final List<ArmorEffect> armorEffects = currentPlayersWithArmorEffects.get(player);
            armorEffects.forEach(armorEffect -> {
                armorEffect.removeEffect(player);
            });

            currentPlayersWithArmorEffects.remove(player);
        }
    }
    private BukkitRunnable armorEffectTask() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach((p) -> {

                    if (p == null || !p.isOnline()) return;

                    armorEffectList.forEach(armorEffect -> {
                        List<ArmorEffect> armorEffectList = currentPlayersWithArmorEffects.get(p);

                        if (armorEffectList == null && armorEffect.condition(p)){
                            armorEffectList = new ArrayList<>();
                            armorEffectList.add(armorEffect);
                            armorEffect.addEffect(p);
                            currentPlayersWithArmorEffects.put(p, armorEffectList);
                            return;
                        }

                        if (armorEffectList != null && armorEffect.condition(p) && !armorEffectList.contains(armorEffect)){
                                armorEffectList.add(armorEffect);
                                armorEffect.addEffect(p);
                                return;
                        }

                        if (!armorEffect.condition(p) && armorEffectList != null && armorEffectList.contains(armorEffect) ){
                            armorEffectList.remove(armorEffect);
                            armorEffect.removeEffect(p);
                            currentPlayersWithArmorEffects.remove(p);
                            return;
                        }

                        if (armorEffect.condition(p)) {
                            armorEffect.run(p);
                        }
                    });
                });
            }
        };
    }



}
