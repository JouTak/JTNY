package com.joutak.jtny.listeners

import com.joutak.jtny.Config
import com.joutak.jtny.JouTakNewYear
import com.joutak.jtny.dto.ImageFirework
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Particle.DustOptions
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.FireworkExplodeEvent
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
import kotlin.math.cos
import kotlin.math.sin


object FireforkExplode {
    @EventHandler
    @Throws(IOException::class)
    fun onFireworkExplode(event: FireworkExplodeEvent) {
        val firework = event.entity
        val fireworkMeta = firework.fireworkMeta
        if (!fireworkMeta.hasCustomModelData()) return
        val modelData = fireworkMeta.customModelData

        val imageFirework: ImageFirework = Config.fireworks.find { firework: ImageFirework -> firework.modelData == modelData } ?: return
        val explodeLocation: Location = event.entity.location
        val yawRotation = explodeLocation.yaw.toDouble()

        displayImage(imageFirework, explodeLocation, yawRotation)
    }

    @Throws(IOException::class)
    private fun displayImage(firework: ImageFirework, explodeLocation: Location, yawRotation: Double) {
        val plugin = JouTakNewYear.instance

        val imageFile: File = File(plugin.dataFolder, "images/${firework.imageName}")
        if (!imageFile.exists()) {
            plugin.logger.warning("Image file not found: ${firework.imageName}")
            return
        }


        val image = ImageIO.read(imageFile)
        val width = image.width
        val height = image.height

        val yawRadians = Math.toRadians(yawRotation)

        val taskId = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, Runnable {
            for (x in 0..<width) {
                for (y in 0..<height) {
                    val color = image.getRGB(x, y)
                    val alpha = (color shr 24) and 0xff
                    if (alpha != 0) {
                        val red = (color shr 16) and 0xff
                        val green = (color shr 8) and 0xff
                        val blue = color and 0xff

                        val offsetX = x / 10.0 - width / 20.0
                        val offsetY = -y / 10.0 + height / 10.0 / 4.0
                        val offsetZ = 0.0

                        val rotatedX = offsetX * cos(yawRadians) - offsetZ * sin(yawRadians)
                        val rotatedZ = offsetX * sin(yawRadians) + offsetZ * cos(yawRadians)

                        val location: Location = explodeLocation.clone().add(rotatedX, offsetY, rotatedZ)
                        val dust = DustOptions(Color.fromRGB(red, green, blue), 1f)
                        explodeLocation.world!!.spawnParticle(Particle.REDSTONE, location, 0, 0.0, 0.0, 0.0, dust)
                    }
                }
            }
        }, 0L, 3L)

        Bukkit.getScheduler().runTaskLater(plugin, Runnable { taskId.cancel() }, firework.displayTime * 20L)
    }
}