package kr.kro.minestar.murder.`object`.item.tool

import kr.kro.minestar.murder.Main
import kr.kro.minestar.murder.interfaces.Type
import kr.kro.minestar.murder.interfaces.item.Tool
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.util.Vector

class BlastBooster(override var player: Player) : Tool, Listener {
    init {
        Bukkit.getPluginManager().registerEvents(this, Main.pl!!)
    }

    override var material: Material = Material.NETHERITE_SCRAP
    override var number: Int = 3
    override var display: String = "폭발 부스터"
    override var description: List<String> = listOf(
        "폭발을 이르켜 앞으로 점프 합니다"
    )
    override var amount: Int = 1
    override var type: Type = Type.MOVEMENT


    override fun active(e: PlayerInteractEvent): Boolean {
        if (!e.action.toString().contains("RIGHT")) return false
        if (e.action == Action.RIGHT_CLICK_BLOCK && e.hand == EquipmentSlot.HAND) return false
        val x = player.location.direction.x
        val z = player.location.direction.z
        val v = Vector(x, 0.0, z).normalize()
        player.velocity = v.add(Vector(0, 0, 0)).multiply(1).setY(1.5)
        player.world.playSound(player.location, Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER, 1.0F, 1.0F)
        player.world.spawnParticle(Particle.EXPLOSION_LARGE, player.location, 5, 0.4, 0.0, 0.4, 0.0)
        return true
    }
}