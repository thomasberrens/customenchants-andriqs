package tokeee.rubixstudio.customenchants.manager;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.scheduler.BukkitRunnable;
import tokeee.rubixstudio.customenchants.CustomEnchants;
import tokeee.rubixstudio.customenchants.builder.ParticleTrailObject;
import tokeee.rubixstudio.customenchants.utils.ParticleUtil;

import java.util.ArrayList;
import java.util.List;

public class ParticleTrailManager {
    private @Getter
    List<ParticleTrailObject> arrows;
    private @Getter BukkitRunnable setupTask;
    private @Getter List<ParticleTrailObject> removalCache;

    public ParticleTrailManager(){
        this.arrows = new ArrayList<>();
        this.removalCache = new ArrayList<>();
        this.setupTask = setupTasks();
        this.setupTask.runTaskTimer(CustomEnchants.getInstance(), 0L, 0L);
    }

    private BukkitRunnable setupTasks() {
        final int timeAfterRemoval = 7; // 7 sec

        return new BukkitRunnable() {
            @Override
            public void run() {

                if (!removalCache.isEmpty()) {
                    removalCache.forEach(arrows::remove);
                }

                if (arrows.isEmpty()) return;
                arrows.forEach((p) -> {

                    final Arrow arrow = p.getArrow();
                    if (arrow == null){
                        System.out.println("!!! ARROW IS NULL !!! ParticleTrailManager.java line 41 CustomEnchants --Tokeee");
                        removeParticleTrail(p);
                        return;
                    }

                    final Location loc = arrow.getLocation();
                    ParticleUtil.runColoredParticles(loc, 1, p.getRed(), p.getGreen(), p.getBlue(), p.getEffect());

                    boolean afterTime = p.getTimeThrown() + 1000 * timeAfterRemoval < System.currentTimeMillis();

                    if (arrow.isDead() || !arrow.isValid() || arrow.isOnGround()){ afterTime = true; }
                    if (afterTime) {
                        removeParticleTrail(p);
                    }
                });
            }
        };
    }

    private void removeParticleTrail(final ParticleTrailObject particleTrailObject) {
        removalCache.add(particleTrailObject);
    }
}
