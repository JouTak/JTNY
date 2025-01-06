package com.joutak.jtny.listeners

import com.joutak.jtny.Config
import com.joutak.jtny.JouTakNewYear
import net.kyori.adventure.bossbar.BossBar.Color
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.metadata.FixedMetadataValue
import java.util.*
import kotlin.concurrent.schedule
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.sign


object IceSkates : Listener {
    private fun isIce(block: Block): Boolean {
        return when (block.type) {
            Material.ICE, Material.BLUE_ICE, Material.PACKED_ICE, Material.FROSTED_ICE -> true
            else -> false
        }
    }

    @Suppress("DEPRECATION")
    @EventHandler
    fun onPlayerMove(e: PlayerMoveEvent) {
        val to = e.to.clone()
        to.y = e.from.y
        if (to.subtract(e.from).length() < 0.1) {
            return
        }
        val player = e.player
        if (player.isFlying) {
            return
        }
        if (player.hasMetadata("fell")) {
            val ceilLocation = e.to.clone().add(0.0, 1.0, 0.0)
            if (!ceilLocation.block.isSolid) {
                player.sendBlockChange(ceilLocation, Material.BARRIER.createBlockData())
            }
            Timer().schedule(1000) {
                player.sendBlockChange(ceilLocation, ceilLocation.block.blockData)
            }
            return
        }
        val blockBelow = player.location.subtract(0.0, 0.5, 0.0).block
        val velocity = player.velocity
        if ((!isIce(blockBelow) && blockBelow.type != Material.AIR) || !player.isSneaking) {
            if (player.hasMetadata("skating") && velocity.length() > 0.8) {
                player.removeMetadata("skating", JouTakNewYear.instance)
                player.setMetadata("fell", FixedMetadataValue(JouTakNewYear.instance, true))
                player.damage(velocity.length() * 5)
                player.isSwimming = true
                val walkSpeed = player.walkSpeed
                player.walkSpeed = 0.0f
                Timer().schedule(1000) {
                    player.removeMetadata("fell", JouTakNewYear.instance)
                    player.walkSpeed = walkSpeed
                    player.isSwimming = false
                }
            }
            return
        }
        val boots = player.inventory.boots ?: return
        val nbtKey = NamespacedKey(JouTakNewYear.instance, "isSkates")
        if (boots.itemMeta.hasDisplayName() && boots.itemMeta.displayName() == Component.text("Коньки", Style.style(
                TextColor.color(0x55ffff)))) {
            if (!player.hasMetadata("skating"))
                player.setMetadata("skating", FixedMetadataValue(JouTakNewYear.instance, true))
            val direction = player.location.direction
            // set direction independent of pitch
            direction.y = 0.0
            direction.normalize().multiply(Config.acceleration)
            // add speed
            direction.x = velocity.x + direction.x.sign * min(Config.maxSpeed, abs(direction.x))
            direction.y = player.velocity.y
            direction.z = velocity.z +direction.z.sign * min(Config.maxSpeed, abs(direction.z))
            // set player velocity
            player.velocity = direction
        }
    }
}
