package dev.esophose.rosestacker.listener;

import dev.esophose.rosestacker.RoseStacker;
import dev.esophose.rosestacker.manager.StackManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;

public class WorldListener implements Listener {

    private RoseStacker roseStacker;
    private StackManager stackManager;

    public WorldListener(RoseStacker roseStacker) {
        this.roseStacker = roseStacker;
        this.stackManager = this.roseStacker.getManager(StackManager.class);;
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        if (event.isNewChunk()) {
            for (Entity entity : event.getChunk().getEntities())
                if (entity instanceof LivingEntity)
                    this.stackManager.createEntityStack((LivingEntity) entity, true);
        } else {
            this.stackManager.loadChunk(event.getChunk());
        }
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        this.stackManager.unloadChunk(event.getChunk());
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        this.stackManager.loadWorld(event.getWorld());
    }

    @EventHandler
    public void onWorldUnload(WorldUnloadEvent event) {
        this.stackManager.unloadWorld(event.getWorld());
    }

}