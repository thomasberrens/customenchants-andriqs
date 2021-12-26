package tokeee.rubixstudio.customenchants.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemUtils {
    public static final List<int[]> BLOCK_RELATIVES;

    private static Map<String, String> ITEMS_BY_NAME;
    private static Map<String, String> ITEMS_BY_MATERIAL;

    public static Enchantment FAKE_GLOW;


    public static void removeOneItem(Player player) {
        if(player.getItemInHand().getAmount() > 1) {
            player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
            return;
        }

        player.getInventory().setItemInHand(new ItemStack(Material.AIR));
    }

    public static int getItemAmount(Player player, MaterialData data) {
        int amount = 0;

        for(ItemStack item : player.getInventory().getContents()) {
            if(item == null || item.getType() != data.getItemType()) continue;
            if(data.getItemType().getMaxDurability() == 0 && item.getData().getData() != data.getData()) continue;

            amount += item.getAmount();
        }

        return amount;
    }

    public static void removeItem(Inventory inventory, MaterialData data, int amount) {
        ItemStack[] contents = inventory.getContents();

        for(int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];

            if(item == null || data.getItemType() != item.getType()) continue;
            if(data.getItemType().getMaxDurability() == 0 && item.getData().getData() != data.getData()) continue;

            if(amount >= item.getAmount()) {
                amount -= item.getAmount();
                inventory.setItem(i, null);
            } else {
                item.setAmount(item.getAmount() - amount);
                break;
            }
        }
    }

    public static String getItemName(ItemStack item) {
        String itemName = ITEMS_BY_MATERIAL.get(item.getTypeId() + ":" + item.getDurability());
        return itemName != null ? itemName : ITEMS_BY_MATERIAL.get(item.getTypeId() + ":0");
    }

    public static String getMaterialName(Material material) {
        return ITEMS_BY_MATERIAL.get(material.getId() + ":0");
    }

    public static boolean hasEnchantment(ItemStack item, Enchantment enchantment) {
        return item != null && EnchantUtils.hasItemStackEnchantment(item, enchantment);
    }


    public static boolean isOre(Material material) {
        switch(material) {
            case DIAMOND_ORE:
            case EMERALD_ORE:
            case GOLD_ORE:
            case IRON_ORE:
            case LAPIS_ORE:
            case GLOWING_REDSTONE_ORE:
            case REDSTONE_ORE:
            case COAL_ORE:
            case QUARTZ_ORE:
                return true;
            default:
                return false;
        }
    }

    public static boolean isWeapon(Material material) {
        switch(material) {
            case DIAMOND_SWORD:
            case IRON_SWORD:
            case DIAMOND_AXE:
                return true;
        }
        return false;
    }

    public static boolean isArmor(Material material) {
        switch(material) {
            case DIAMOND_HELMET:
            case GOLD_HELMET:
            case IRON_HELMET:
            case CHAINMAIL_HELMET:
            case LEATHER_HELMET:
            case DIAMOND_CHESTPLATE:
            case GOLD_CHESTPLATE:
            case IRON_CHESTPLATE:
            case CHAINMAIL_CHESTPLATE:
            case LEATHER_CHESTPLATE:
            case DIAMOND_LEGGINGS:
            case GOLD_LEGGINGS:
            case IRON_LEGGINGS:
            case CHAINMAIL_LEGGINGS:
            case LEATHER_LEGGINGS:
            case DIAMOND_BOOTS:
            case GOLD_BOOTS:
            case IRON_BOOTS:
            case CHAINMAIL_BOOTS:
            case LEATHER_BOOTS:
                return true;
        }
        return false;
    }

    public static boolean isArmorHelmet(Material material) {
        switch(material) {
            case DIAMOND_HELMET:
            case GOLD_HELMET:
            case IRON_HELMET:
            case CHAINMAIL_HELMET:
            case LEATHER_HELMET:
                return true;
        }
        return false;
    }

    public static boolean isSalvageable(Material material) {
        switch(material) {
            case DIAMOND_HELMET:
            case GOLD_HELMET:
            case IRON_HELMET:
            case CHAINMAIL_HELMET:
            case LEATHER_HELMET:
            case DIAMOND_CHESTPLATE:
            case GOLD_CHESTPLATE:
            case IRON_CHESTPLATE:
            case CHAINMAIL_CHESTPLATE:
            case LEATHER_CHESTPLATE:
            case DIAMOND_LEGGINGS:
            case GOLD_LEGGINGS:
            case IRON_LEGGINGS:
            case CHAINMAIL_LEGGINGS:
            case LEATHER_LEGGINGS:
            case DIAMOND_BOOTS:
            case GOLD_BOOTS:
            case IRON_BOOTS:
            case CHAINMAIL_BOOTS:
            case LEATHER_BOOTS:
            case DIAMOND_SWORD:
            case GOLD_SWORD:
            case IRON_SWORD:
            case STONE_SWORD:
            case WOOD_SWORD:
            case DIAMOND_PICKAXE:
            case GOLD_PICKAXE:
            case IRON_PICKAXE:
            case STONE_PICKAXE:
            case WOOD_PICKAXE:
            case DIAMOND_AXE:
            case GOLD_AXE:
            case IRON_AXE:
            case STONE_AXE:
            case WOOD_AXE:
            case DIAMOND_SPADE:
            case GOLD_SPADE:
            case IRON_SPADE:
            case STONE_SPADE:
            case WOOD_SPADE:
            case DIAMOND_HOE:
            case GOLD_HOE:
            case IRON_HOE:
            case STONE_HOE:
            case WOOD_HOE:
            case BOW:
                return true;
            default:
                return false;
        }
    }

    public static boolean isRepairable(Material material) {
        switch(material) {
            case DIAMOND_HELMET:
            case GOLD_HELMET:
            case IRON_HELMET:
            case CHAINMAIL_HELMET:
            case LEATHER_HELMET:
            case DIAMOND_CHESTPLATE:
            case GOLD_CHESTPLATE:
            case IRON_CHESTPLATE:
            case CHAINMAIL_CHESTPLATE:
            case LEATHER_CHESTPLATE:
            case DIAMOND_LEGGINGS:
            case GOLD_LEGGINGS:
            case IRON_LEGGINGS:
            case CHAINMAIL_LEGGINGS:
            case LEATHER_LEGGINGS:
            case DIAMOND_BOOTS:
            case GOLD_BOOTS:
            case IRON_BOOTS:
            case CHAINMAIL_BOOTS:
            case LEATHER_BOOTS:
            case DIAMOND_SWORD:
            case GOLD_SWORD:
            case IRON_SWORD:
            case STONE_SWORD:
            case WOOD_SWORD:
            case DIAMOND_PICKAXE:
            case GOLD_PICKAXE:
            case IRON_PICKAXE:
            case STONE_PICKAXE:
            case WOOD_PICKAXE:
            case DIAMOND_AXE:
            case GOLD_AXE:
            case IRON_AXE:
            case STONE_AXE:
            case WOOD_AXE:
            case DIAMOND_SPADE:
            case GOLD_SPADE:
            case IRON_SPADE:
            case STONE_SPADE:
            case WOOD_SPADE:
            case DIAMOND_HOE:
            case GOLD_HOE:
            case IRON_HOE:
            case STONE_HOE:
            case WOOD_HOE:
            case FISHING_ROD:
            case BOW:
            case FLINT_AND_STEEL:
                return true;
            default:
                return false;
        }
    }

    public static EntityType getSpawnerType(short durability) {
        switch(durability) {
            case 50: return EntityType.CREEPER;
            case 51: return EntityType.SKELETON;
            case 52: return EntityType.SPIDER;
            case 53: return EntityType.GIANT;
            case 54: return EntityType.ZOMBIE;
            case 55: return EntityType.SLIME;
            case 56: return EntityType.GHAST;
            case 57: return EntityType.PIG_ZOMBIE;
            case 58: return EntityType.ENDERMAN;
            case 59: return EntityType.CAVE_SPIDER;
            case 60: return EntityType.SILVERFISH;
            case 61: return EntityType.BLAZE;
            case 62: return EntityType.MAGMA_CUBE;
            case 63: return EntityType.ENDER_DRAGON;
            case 64: return EntityType.WITHER;
            case 65: return EntityType.BAT;
            case 66: return EntityType.WITCH;
            case 91: return EntityType.SHEEP;
            case 92: return EntityType.COW;
            case 93: return EntityType.CHICKEN;
            case 94: return EntityType.SQUID;
            case 95: return EntityType.WOLF;
            case 96: return EntityType.MUSHROOM_COW;
            case 97: return EntityType.SNOWMAN;
            case 98: return EntityType.OCELOT;
            case 99: return EntityType.IRON_GOLEM;
            case 100: return EntityType.HORSE;
            default: return EntityType.PIG;
        }
    }

    public static int getSpawnerDurability(EntityType type) {
        switch(type) {
            case CREEPER: return 50;
            case SKELETON: return 51;
            case SPIDER: return 52;
            case GIANT: return 53;
            case ZOMBIE: return 54;
            case SLIME: return 55;
            case GHAST: return 56;
            case PIG_ZOMBIE: return 57;
            case ENDERMAN: return 58;
            case CAVE_SPIDER: return 59;
            case SILVERFISH: return 60;
            case BLAZE: return 61;
            case MAGMA_CUBE: return 62;
            case ENDER_DRAGON: return 63;
            case WITHER: return 64;
            case BAT: return 65;
            case WITCH: return 66;
            case SHEEP: return 91;
            case COW: return 92;
            case CHICKEN: return 93;
            case SQUID: return 94;
            case WOLF: return 95;
            case MUSHROOM_COW: return 96;
            case SNOWMAN: return 97;
            case OCELOT: return 98;
            case IRON_GOLEM: return 99;
            case HORSE: return 100;
            default: return 90;
        }
    }

    public static void registerFakeEnchantmentGlow() {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);

            Enchantment.registerEnchantment(FAKE_GLOW);
        } catch(IllegalArgumentException e1) {
            FAKE_GLOW = Enchantment.getById(70);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    static {
        BLOCK_RELATIVES = new ArrayList<>();

        for(int x = -1; x <= 1; x++) {
            for(int y = -1; y <= 1; y++) {
                for(int z = -1; z <= 1; z++) {
                    BLOCK_RELATIVES.add(new int[] { x, y, z });
                }
            }
        }


    }
}
