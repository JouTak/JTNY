package com.joutak.jtny

import com.joutak.jtny.commands.IceSkatesCommand
import com.joutak.jtny.listeners.IceSkates
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File


class JouTakNewYear : JavaPlugin() {
    companion object {
        @JvmStatic
        lateinit var instance: JouTakNewYear
    }

    override fun onEnable() {
        // Plugin startup logic
        instance = this
        val fx = File(dataFolder, "config.yml")
        if (!fx.exists()) {
            saveResource("config.yml", true)
        }

        // Register commands and events
        Bukkit.getPluginManager().registerEvents(IceSkates, this)
        getCommand("skates")!!.setExecutor(IceSkatesCommand)

        logger.info("JTNY version ${pluginMeta.version} enabled!")
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
