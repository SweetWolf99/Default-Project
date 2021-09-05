package kr.kro.minestar.murder.`object`.item.tool

import kr.kro.minestar.murder.Main
import kr.kro.minestar.murder.interfaces.Type
import kr.kro.minestar.murder.interfaces.item.Tool
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class SwitchPosition(override var player: Player) : Tool, Listener {
    init {
        Bukkit.getPluginManager().registerEvents(this, Main.pl!!)
    }

    override var material: Material = Material.NETHERITE_SCRAP
    override var number: Int = 2
    override var display: String = "위치 교환 장치"
    override var description: List<String> = listOf(
        "30블럭 이내에 있는 플레이어를 바라보고",
        "사용하면 서로의 위치로 이동합니다")
    override var amount: Int = 2
    override var type: Type = Type.MOVEMENT


    override fun active(e: PlayerInteractEvent): Boolean {
        if(e.action != Action.RIGHT_CLICK_AIR) return false
        var d = 0.0
        val loc: Location = player.eyeLocation
        var locD = loc.toVector().add(loc.direction.multiply(d)).toLocation(player.world)
        while (true) {
            if(d > 30) break
            d += 0.1
            if (locD.block.type != Material.AIR) break
            val players = locD.getNearbyPlayers(0.0)
            if (players.toTypedArray().isNotEmpty()) for (target in players) if (target != player) if (target.gameMode != GameMode.SPECTATOR) {
                val loc1 = player.location
                val loc2 = target.location
                target.teleport(loc1)
                player.teleport(loc2)
                target.playSound(target.location, Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.MASTER, 1.0F, 1.0F)
                player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.MASTER, 1.0F, 1.0F)
                return true
            }
            locD = loc.toVector().add(loc.direction.multiply(d)).toLocation(player.world)
        }
        return false
    }
}