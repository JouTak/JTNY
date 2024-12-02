package com.joutak.jtny

import com.joutak.jtny.commands.IceSkatesCommand
import com.joutak.jtny.listeners.IceSkates
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class JouTakNewYear : JavaPlugin() {
    companion object {
        @JvmStatic
        lateinit var instance: JouTakNewYear
    }

    override fun onEnable() {
        // Plugin startup logic
        instance = this
        Bukkit.getPluginManager().registerEvents(IceSkates, this)
        getCommand("skates")!!.setExecutor(IceSkatesCommand)
        logger.info("JTNY enabled 1")
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
