package kr.kro.minestar.murder.creature.slayer.weapon

import kr.kro.minestar.murder.creature.interfaces.item.Weapon
import org.bukkit.Material

class MurderAxe : Weapon {
    override var material: Material = Material.IRON_AXE
    override var number: Int = 1
    override var display: String = "살인 도끼"
    override var lore: List<String> = listOf("살인자의 도끼이다")
    override var damage: Double = 8.0


}