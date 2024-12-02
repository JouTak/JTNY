package com.joutak.jtny.listeners

import com.joutak.jtny.Config
import com.joutak.jtny.JouTakNewYear
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.sign

object IceSkates : Listener {
    private fun isIce(block: Block): Boolean {
        return when (block.type) {
            Material.ICE, Material.BLUE_ICE, Material.PACKED_ICE -> true
            else -> false
        }
    }

    @EventHandler
    fun onPlayerMove(e: PlayerMoveEvent) {
        val to = e.to.clone()
        to.y = e.from.y
        if (to.subtract(e.from).length() < 0.1) {
            return
        }
        val player = e.player
        if (player.isFlying || !player.isSneaking) {
            return
        }
        if (!isIce(player.location.subtract(0.0, 0.5, 0.0).block)) {
            if (player.walkSpeed != .2f) {
                player.walkSpeed = .2f
            }
            return
        }
        val boots = player.inventory.boots
        if (boots == null) {
            if (player.walkSpeed != .2f) {
                player.walkSpeed = .2f
            }
            return
        }
        val nbtKey = NamespacedKey(JouTakNewYear.instance, "isSkates")
        if (boots.itemMeta.persistentDataContainer.has(nbtKey)) {
            val direction = player.location.direction
            val velocity = player.velocity
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