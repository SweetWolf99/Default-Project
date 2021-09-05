package kr.kro.minestar.murder.`object`.creature.slayer

import kr.kro.minestar.murder.`object`.item.weapon.HiddenDagger
import kr.kro.minestar.murder.interfaces.creature.Slayer
import kr.kro.minestar.murder.interfaces.item.Weapon
import kr.kro.minestar.murder.interfaces.skill.ActiveSkill
import kr.kro.minestar.murder.interfaces.skill.PassiveSkill
import kr.kro.minestar.murder.`object`.skill.slayer.active.DaggerSpray
import kr.kro.minestar.murder.`object`.skill.slayer.passive.Hide
import kr.kro.minestar.murder.interfaces.item.Tool
import org.bukkit.entity.Player

class Assassin(override var player: Player) : Slayer {
    override var name: String = "암살자"
    override var codeName: String = this.javaClass.simpleName
    override var number: Int = 2
    override var weapon: Weapon = HiddenDagger(player)
    override var activeSkill: ActiveSkill = DaggerSpray(player)
    override var passiveSkill: PassiveSkill = Hide(player)
    override var tool: Tool? = null
}