package com.joutak.jtny

import com.joutak.jtny.commands.IceSkatesCommand
import com.joutak.jtny.dto.ImageFirework
import com.joutak.jtny.listeners.FireworkExplode
import com.joutak.jtny.commands.ShardsCommand
import com.joutak.jtny.listeners.IceSkates
import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File


class JouTakNewYear : JavaPlugin() {
    companion object {
        @JvmStatic
        lateinit var instance: JouTakNewYear
    }

    private var customConfig = YamlConfiguration()

    fun loadConfig() {
        val fx = File(dataFolder, "config.yml")
        if (!fx.exists()) {
            saveResource("config.yml", true)
        }
        customConfig.load(fx)
        Config.acceleration = customConfig.getDouble("skates.acceleration")
        Config.maxSpeed = customConfig.getDouble("skates.max-speed")
        Config.particleLimit = customConfig.getInt("image-fireworks.particle-limit")
        val fireworkMaps = customConfig.getList("image-fireworks.list") as? List<Map<String, Any>> ?: return
        logger.info("Fireworks: $fireworkMaps")
        Config.fireworks = fireworkMaps.map { ImageFirework.deserialize(it) }
        logger.info("Loaded ${Config.fireworks.size} fireworks: ${Config.fireworks}")
    }

    override fun onEnable() {
        // Plugin startup logic
        instance = this

        loadConfig()

        // Register commands and events
        Bukkit.getPluginManager().registerEvents(IceSkates, this)
        Bukkit.getPluginManager().registerEvents(FireworkExplode, this)
        getCommand("skates")!!.setExecutor(IceSkatesCommand)
        getCommand("shards")!!.setExecutor(ShardsCommand)

        logger.info("JTNY version ${pluginMeta.version} enabled!")
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
