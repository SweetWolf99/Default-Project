package kr.kro.minestar.murder.functions

import kr.kro.minestar.murder.Main
import org.bukkit.Location
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File
import kotlin.random.Random

private fun Location.setRotate(rotate: Rotate): Location {
    this.pitch = 0F
    when (rotate) {
        Rotate.SOUTH -> this.yaw = 0F
        Rotate.NORTH -> this.yaw = 180F
        Rotate.WEST -> this.yaw = 90F
        Rotate.EAST -> this.yaw = -90F
    }
    return this
}

enum class Rotate {
    NORTH, SOUTH, EAST, WEST
}

class MapClass() {


    fun createMap(p: Player, mapName: String, rotate: Rotate) {
        val loc = p.location.block.location.setRotate(rotate)
        val file = File(Main.pl!!.dataFolder.toString() + "/map", "$mapName.yml")
        val yaml = YamlConfiguration.loadConfiguration(file)
        yaml["SPAWN"] = loc
        if (file.exists()) p.sendMessage("${Main.prefix} §e$mapName §f이/가 덮어쓰기 되었습니다.")
        else p.sendMessage("${Main.prefix} §e$mapName §f이/가 추가 되었습니다.")
        yaml.save(file)
    }

    fun deleteMap(p: Player, file: File) {
        if (!file.exists()) p.sendMessage("${Main.prefix} §e${file.name.replace(".yml", "")} §c이/가 존재하지 않습니다.")
        else {
            file.delete()
            p.sendMessage("${Main.prefix} §e${file.name.replace(".yml", "")} §f이/가 §c제거 §f되었습니다.")
        }
    }

    fun teleportMap(p: Player, file: File) {
        val yaml = YamlConfiguration.loadConfiguration(file)
        val loc: Location? = yaml.getLocation("SPAWN")
        p.teleport(loc!!)
    }

    fun teleportMap(players: Collection<Player>, file: File) {
        val yaml = YamlConfiguration.loadConfiguration(file)
        val loc: Location? = yaml.getLocation("SPAWN")
        for (p in players) {
            var rX = Random.nextInt(6)
            var rZ = Random.nextInt(6)
            if (Random.nextBoolean()) rX * -1
            if (Random.nextBoolean()) rZ * -1
            val randomLoc = loc!!.clone().add(rX.toDouble(), 0.0, rZ.toDouble())
            p.teleport(randomLoc)
        }
    }

    fun randomMap(): File {
        val folder = File(Main.pl!!.dataFolder.toString()).listFiles()
        return folder[Random.nextInt(folder.size)]
    }

    fun getMap(mapName: String): File {
        return File(Main.pl!!.dataFolder.toString() + "/map", "$mapName.yml")
    }
}