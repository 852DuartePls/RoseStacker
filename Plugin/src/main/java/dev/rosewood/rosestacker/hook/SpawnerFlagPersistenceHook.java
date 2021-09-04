package dev.rosewood.rosestacker.hook;

import com.gmail.nossr50.mcMMO;
import com.gmail.nossr50.util.compat.layers.persistentdata.MobMetaFlagType;
import dev.rosewood.roseloot.util.LootUtils;
import dev.rosewood.rosestacker.utils.PersistentDataUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

public class SpawnerFlagPersistenceHook {

    private static Boolean mcMMOEnabled;
    private static Boolean jobsEnabled;
    private static Boolean roseLootEnabled;

    /**
     * @return true if mcMMO is enabled, false otherwise
     */
    public static boolean mcMMOEnabled() {
        if (mcMMOEnabled != null)
            return mcMMOEnabled;
        return mcMMOEnabled = Bukkit.getPluginManager().getPlugin("mcMMO") != null;
    }

    /**
     * @return true if Jobs is enabled, false otherwise
     */
    public static boolean jobsEnabled() {
        if (jobsEnabled != null)
            return jobsEnabled;
        return jobsEnabled = Bukkit.getPluginManager().getPlugin("Jobs") != null;
    }

    /**
     * @return true if RoseLoot is enabled, false otherwise
     */
    public static boolean roseLootEnabled() {
        if (roseLootEnabled != null)
            return roseLootEnabled;
        return roseLootEnabled = Bukkit.getPluginManager().getPlugin("RoseLoot") != null;
    }

    /**
     * Flags a LivingEntity as having been spawned from a spawner
     *
     * @param entity The LivingEntity to flag
     */
    public static void flagSpawnerSpawned(LivingEntity entity) {
        if (mcMMOEnabled())
            mcMMO.getCompatibilityManager().getPersistentDataLayer().flagMetadata(MobMetaFlagType.MOB_SPAWNER_MOB, entity);

        if (jobsEnabled()) {
            Plugin jobsPlugin = Bukkit.getPluginManager().getPlugin("Jobs");
            if (jobsPlugin != null)
                entity.setMetadata("jobsMobSpawner", new FixedMetadataValue(jobsPlugin, true));
        }

        if (roseLootEnabled())
            LootUtils.setEntitySpawnReason(entity, SpawnReason.SPAWNER);
    }

    /**
     * Set's the LivingEntity's spawner persistence state if it was spawned from a spawner
     *
     * @param entity The entity to set the persistence state of
     */
    public static void setPersistence(LivingEntity entity) {
        if (jobsEnabled() && PersistentDataUtils.isSpawnedFromSpawner(entity)) {
            Plugin jobsPlugin = Bukkit.getPluginManager().getPlugin("Jobs");
            if (jobsPlugin != null)
                entity.setMetadata("jobsMobSpawner", new FixedMetadataValue(jobsPlugin, true));
        }
    }

}
