package tokeee.rubixstudio.customenchants.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import tokeee.rubixstudio.customenchants.events.*;

public class EventManager {
    public void callCustomItemUseEvent(Player player){
        final CustomItemUseEvent customItemUseEvent = new CustomItemUseEvent(player);
        Bukkit.getPluginManager().callEvent(customItemUseEvent);
    }

    public void callCantCreateCustomItem(Player player){
        final CantCreateCustomItem cantCreateCustomItem = new CantCreateCustomItem(player);
        Bukkit.getPluginManager().callEvent(cantCreateCustomItem);
    }

    public void callCreateCustomItem(Player player){
        final CreateCustomItem createCustomItem = new CreateCustomItem(player);
        Bukkit.getPluginManager().callEvent(createCustomItem);
    }

    public void callPlayerGetCustomItem(Player player){
        final PlayerGetCustomItem playerGetCustomItem = new PlayerGetCustomItem(player);
        Bukkit.getPluginManager().callEvent(playerGetCustomItem);
    }

    public void callPlayerOpensCustomAnvil(Player player){
        final PlayerOpensCustomAnvil playerOpensCustomAnvil = new PlayerOpensCustomAnvil(player);
        Bukkit.getPluginManager().callEvent(playerOpensCustomAnvil);
    }

}
