package tokeee.rubixstudio.customenchants;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import tokeee.rubixstudio.customenchants.anvil.CustomAnvil;
import tokeee.rubixstudio.customenchants.commands.GiveCustomItemCommand;
import tokeee.rubixstudio.customenchants.commands.ListCustomEnchants;
import tokeee.rubixstudio.customenchants.enchantments.BlindnessEnchantment;
import tokeee.rubixstudio.customenchants.enchantments.BowPoisonEnchantment;
import tokeee.rubixstudio.customenchants.enchantments.NoFoodEnchantment;
import tokeee.rubixstudio.customenchants.enchantments.PotionBowEnchantment;
import tokeee.rubixstudio.customenchants.manager.EventManager;
import tokeee.rubixstudio.customenchants.manager.ParticleTrailManager;

import java.lang.reflect.Field;
import java.util.*;

public class CustomEnchants extends JavaPlugin implements Listener  {

    private static @Getter CustomEnchants instance;


    private @Getter ParticleTrailManager particleTrailManager;
    private @Getter PotionBowEnchantment potionBowEnchantment;
    private @Getter NoFoodEnchantment noFoodEnchantment;
    private @Getter BlindnessEnchantment blindnessEnchantment;
    private @Getter EventManager eventManager;
    private @Getter CustomAnvil customAnvil;

    private @Getter Enchantment[] allCustomEnchantments;

    private final FileConfiguration config = this.getConfig();


    public CustomEnchants(){
        instance = this;
    }

    @SneakyThrows
    @Override
    public void onEnable(){
        initializeConfig();
        this.registerEnchantments();
        this.particleTrailManager = new ParticleTrailManager();
        this.eventManager = new EventManager();

        this.customAnvil = new CustomAnvil();
        customAnvil.setAnvilName(config.getString("customanvil.name"));

        loadEnchantments();

        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(potionBowEnchantment, this);
        Bukkit.getPluginManager().registerEvents(customAnvil, this);
        Bukkit.getPluginManager().registerEvents(blindnessEnchantment, this);

        this.getCommand("ce").setExecutor(new GiveCustomItemCommand());
        this.getCommand("listce").setExecutor(new ListCustomEnchants());

    }

    @Override
    public void onDisable(){
        disable();
    }

    private void initializeConfig(){
        config.options().copyDefaults(true);
        config.addDefault("poisonbow-effect.seconds", 5);
        config.addDefault("poisonbow-effect.amplifier", 1);
        config.addDefault("poisonbow-effect-minimal.chance", 0);
        config.addDefault("poisonbow-effect-maximal.chance", 8);

        config.addDefault("blindness-effect.seconds", 3);
        config.addDefault("blindness-effect.amplifier", 0);
        config.addDefault("blindness-effect-minimal.chance", 0);
        config.addDefault("blindness-effect-maximal.chance", 30);

        config.addDefault("customanvil.name", "&6anvilName");

        saveConfig();
    }

    public void disable() {
        // Plugin shutdown logic
        try {
            Field byIdField = Enchantment.class.getDeclaredField("byId");
            Field byNameField = Enchantment.class.getDeclaredField("byName");

            byIdField.setAccessible(true);
            byNameField.setAccessible(true);

            HashMap<Integer, Enchantment> byId = (HashMap<Integer, Enchantment>) byIdField.get(null);
            HashMap<Integer, Enchantment> byName = (HashMap<Integer, Enchantment>) byNameField.get(null);

            for (Enchantment allCustomEnchantment : this.allCustomEnchantments) {
                if (byId.containsKey(allCustomEnchantment.getId())) {
                    byId.remove(allCustomEnchantment.getId());
                }

                if (byName.containsKey(allCustomEnchantment.getName())) {
                    byName.remove(allCustomEnchantment.getName());
                }
            }
        } catch (Exception ignored) {
        }
    }

    private void registerEnchantments() {
        potionBowEnchantment = new BowPoisonEnchantment(130);
        noFoodEnchantment = new NoFoodEnchantment(131);
        blindnessEnchantment= new BlindnessEnchantment(132);
        this.allCustomEnchantments = new Enchantment[] {
                potionBowEnchantment,
                noFoodEnchantment,
                blindnessEnchantment

        };
    }

    /**
     * Logic for loading customItems
     */
    private void loadEnchantments() {
        try {
            try {
                Field f = Enchantment.class.getDeclaredField("acceptingNew");
                f.setAccessible(true);
                f.set(null, true);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                for (Enchantment allCustomEnchantment : this.allCustomEnchantments) {
                    Enchantment.registerEnchantment(allCustomEnchantment);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
