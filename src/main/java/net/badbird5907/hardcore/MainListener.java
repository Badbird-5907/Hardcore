package net.badbird5907.hardcore;

import net.badbird5907.hardcore.object.CC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class MainListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeath(PlayerDeathEvent event){
        if (event.isCancelled())
            return;
        PlayerDataManager.loadData(event.getEntity().getUniqueId()).death(event.getDeathMessage());
    }
    @EventHandler
    public void onSpecTP(PlayerTeleportEvent event){
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.SPECTATE && PlayerDataManager.loadData(event.getPlayer().getUniqueId()).isDead()){
            event.setCancelled(true);
            event.getPlayer().sendMessage(CC.RED + "You can't do that!");
        }
    }
}
