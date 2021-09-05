package kr.kro.minestar.murder.`object`.creature.slayer

import kr.kro.minestar.murder.interfaces.creature.Slayer
import kr.kro.minestar.murder.interfaces.item.Weapon
import kr.kro.minestar.murder.interfaces.skill.ActiveSkill
import kr.kro.minestar.murder.interfaces.skill.PassiveSkill
import kr.kro.minestar.murder.`object`.item.weapon.MurderAxe
import kr.kro.minestar.murder.`object`.skill.slayer.active.Dash
import kr.kro.minestar.murder.`object`.skill.slayer.passive.Chase
import kr.kro.minestar.murder.interfaces.item.Tool
import org.bukkit.entity.Player

class Slaughter(override var player: Player) : Slayer {
    override var name: String = "도살자"
    override var codeName: String = this.javaClass.simpleName
    override var number: Int = 1
    override var weapon: Weapon = MurderAxe(player)
    override var activeSkill: ActiveSkill = Dash(player)
    override var passiveSkill: PassiveSkill = Chase(player)
    override var tool: Tool? = null
}