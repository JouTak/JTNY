package com.joutak.jtny.commands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object IceSkatesCommand: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) {
            sender.sendMessage("команда только для игроков!")
            return false
        }
        if (!sender.hasPermission("joutak.newyear.admin") && !sender.isOp()) {
            sender.sendMessage("команда только для операторов!")
            return false
        }
        val boots = ItemStack(Material.IRON_BOOTS)
        val itemMeta = boots.itemMeta
        itemMeta.displayName(Component.text("Коньки", Style.style(TextColor.color(0x55ffff))))
        boots.itemMeta = itemMeta
        sender.inventory.addItem(boots)
        sender.sendMessage("держи коньки")
        return true
    }
}