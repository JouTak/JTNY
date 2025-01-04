package com.joutak.jtny.commands

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object ShardsCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) {
            sender.sendMessage("команда только для игроков!")
            return false
        }
        if (!sender.isOp()) {
            sender.sendMessage("команда только для операторов!")
            return false
        }
        val currentItem = sender.inventory.itemInMainHand
        if (currentItem.type != Material.PRISMARINE_SHARD) {
            sender.sendMessage("возьми призмарин в руку!")
            return false
        }
        val itemMeta = currentItem.itemMeta
        itemMeta.displayName(Component.text("Осколок счастья", Style.style(TextColor.color(0x55ff55))))
        itemMeta.lore(
            listOf(
                Component.text(
                    "Получен на новогодней ярмарке 2025",
                    Style.style(TextColor.color(0xd8a234), TextDecoration.BOLD)
                )
            )
        )
        itemMeta.setCustomModelData(2025)
        currentItem.itemMeta = itemMeta
        sender.sendMessage("держи шарды")
        return true
    }
}