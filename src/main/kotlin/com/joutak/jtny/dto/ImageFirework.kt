package com.joutak.jtny.dto

import com.joutak.jtny.JouTakNewYear
import org.bukkit.configuration.serialization.ConfigurationSerializable


class ImageFirework(val name: String, val imageName: String, val modelData: Int, val displayTime: Int,
                    val flightDuration: Int
) : ConfigurationSerializable {

    override fun serialize(): Map<String, Any> {
        val data: MutableMap<String, Any> = HashMap()

        data["name"] = name
        data["imageName"] = imageName
        data["customModelData"] = modelData
        data["displayTime"] = displayTime
        data["flightDuration"] = flightDuration

        return data
    }

    companion object {
        fun deserialize(args: Map<String, Any>): ImageFirework {
            return ImageFirework(
                args["name"] as String,
                args["imageName"] as String,
                args["modelData"] as Int,
                args["displayTime"] as Int,
                args["flightDuration"] as Int,
            )
        }
    }

    override fun toString(): String {
        return "ImageFirework(name=$name, imageName=$imageName, modelData=$modelData, displayTime=$displayTime, flightDuration=$flightDuration)"
    }
}
