package kr.kro.minestar.murder.creature.item.tool

import kr.kro.minestar.murder.Main
import kr.kro.minestar.murder.interfaces.Type
import kr.kro.minestar.murder.interfaces.item.Tool
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.Listener

class MasterKey(override var player: Player) : Tool, Listener {
    init {
        Bukkit.getPluginManager().registerEvents(this, Main.pl!!)
    }

    override var material: Material = Material.IRON_SHOVEL
    override var number: Int = 1
    override var display: String = "마스터 키"
    override var description: List<String> = listOf("모든 철문을 열고 닫을 수 있습니다")
    override var type: Type = Type.DISTURBING


    override fun active() {
    }
}