package kr.kro.minestar.murder.`object`.item.tool

import kr.kro.minestar.murder.Main
import kr.kro.minestar.murder.interfaces.Type
import kr.kro.minestar.murder.interfaces.item.Tool
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.block.data.BlockData
import org.bukkit.block.data.type.Door
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

class MasterKey(override var player: Player) : Tool, Listener {
    init {
        Bukkit.getPluginManager().registerEvents(this, Main.pl!!)
    }

    override var material: Material = Material.NETHERITE_SCRAP
    override var number: Int = 1
    override var display: String = "마스터 키"
    override var description: List<String> = listOf(
        "철문을 좌클릭으로",
        "열고 닫을 수 있습니다")
    override var amount: Int = 5
    override var type: Type = Type.DISTURBING


    override fun active(e: PlayerInteractEvent): Boolean {
        if (e.action != Action.LEFT_CLICK_BLOCK) return false
        val door: BlockData = e.clickedBlock!!.blockData
        if (door !is Door) return false
        if (door.material != Material.IRON_DOOR) return false
        if (!door.isOpen) {
            door.isOpen = true
            e.clickedBlock!!.location.world.playSound(e.clickedBlock!!.location, Sound.BLOCK_IRON_DOOR_OPEN, SoundCategory.BLOCKS, 1F, 1F)
        } else if (door.isOpen) {
            door.isOpen = false
            e.clickedBlock!!.location.world.playSound(e.clickedBlock!!.location, Sound.BLOCK_IRON_DOOR_CLOSE, SoundCategory.BLOCKS, 1F, 1F)
        }
        e.clickedBlock!!.blockData = door
        return true
    }
}