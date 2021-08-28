package kr.kro.minestar.murder.creature.item.weapon

import kr.kro.minestar.murder.Main
import kr.kro.minestar.murder.interfaces.item.Weapon
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player

class MurderAxe(override var player: Player) : Weapon {
    init {
        Bukkit.getPluginManager().registerEvents(this, Main.pl!!)
    }
    override var material: Material = Material.IRON_AXE
    override var number: Int = 1
    override var display: String = "살인 도끼"
    override var description: List<String> = listOf("살인자의 도끼이다")
    override var damage: Double = 8.0


}