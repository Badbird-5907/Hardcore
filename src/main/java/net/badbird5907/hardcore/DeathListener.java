package net.badbird5907.hardcore;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class DeathListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        PlayerDataManager.loadData(event.getEntity().getUniqueId()).death();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        PlayerDataManager.join(event.getPlayer());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        PlayerDataManager.leave(event.getPlayer());
    }
}
