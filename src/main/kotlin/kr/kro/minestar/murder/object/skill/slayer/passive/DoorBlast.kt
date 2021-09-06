package kr.kro.minestar.murder.`object`.skill.slayer.passive

import kr.kro.minestar.murder.Main
import kr.kro.minestar.murder.interfaces.Type
import kr.kro.minestar.murder.interfaces.skill.PassiveSkill
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.block.data.BlockData
import org.bukkit.block.data.type.Door
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.scheduler.BukkitTask

class DoorBlast(override var player: Player) : PassiveSkill {
    init {
        Bukkit.getPluginManager().registerEvents(this, Main.pl!!)
    }

    override var number: Int = 3
    override var name: String = "문 폭파"
    override var codeName: String = this.javaClass.name
    override var description = mutableListOf(
        "철문을 폭파시켜 열고 닫습니다"
    )
    override var tick: BukkitTask? = null
    override var type: Type = Type.MOVEMENT

    @EventHandler
    fun active(e: PlayerInteractEvent) {
        if (e.action != Action.LEFT_CLICK_BLOCK) return
        val door: BlockData = e.clickedBlock!!.blockData
        if (door !is Door) return
        if (door.material != Material.IRON_DOOR) return
        if (!door.isOpen) {
            door.isOpen = true
            e.clickedBlock!!.location.world.playSound(e.clickedBlock!!.location, Sound.BLOCK_IRON_DOOR_OPEN, SoundCategory.BLOCKS, 1F, 1F)
        } else if (door.isOpen) {
            door.isOpen = false
            e.clickedBlock!!.location.world.playSound(e.clickedBlock!!.location, Sound.BLOCK_IRON_DOOR_CLOSE, SoundCategory.BLOCKS, 1F, 1F)
        }
        e.clickedBlock!!.blockData = door
        player.world.playSound(player.location, Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER, 0.5F, 1.5F)
        return
    }
}