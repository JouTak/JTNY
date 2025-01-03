package com.joutak.jtny.dto

import org.bukkit.configuration.serialization.ConfigurationSerializable


class ImageFirework(name: String, imageName: String, modelData: Int, displayTime: Int, flightDuration: Int) : ConfigurationSerializable {
    val name = ""
    val imageName = ""
    val modelData = 0
    val displayTime = 1
    val flightDuration = 3

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
        fun deserialize(args: Map<String?, Any?>): ImageFirework {
            return ImageFirework(
                args["name"] as String,
                args["imageName"] as String,
                args["customModelData"] as Int,
                args["displayTime"] as Int,
                args["flightDuration"] as Int,
            )
        }
    }
}
