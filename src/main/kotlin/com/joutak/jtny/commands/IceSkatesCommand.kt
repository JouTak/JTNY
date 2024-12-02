package com.joutak.jtny.commands

import com.joutak.jtny.JouTakNewYear
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

object IceSkatesCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        val nbtKey = NamespacedKey(JouTakNewYear.instance, "isSkates")
        val boots = ItemStack(Material.IRON_BOOTS)
        val itemMeta = boots.itemMeta
        itemMeta.persistentDataContainer.set(nbtKey, PersistentDataType.BOOLEAN, true)
        boots.itemMeta = itemMeta
        if (sender is Player) {
            sender.inventory.addItem(boots)
        }
        sender.sendMessage("держи коньки")
        return true
    }
}