package kr.kro.minestar.murder.`object`.creature.slayer

import kr.kro.minestar.murder.`object`.item.tool.BlastBooster
import kr.kro.minestar.murder.`object`.item.weapon.BlastHammer
import kr.kro.minestar.murder.`object`.skill.slayer.active.DaggerSpray
import kr.kro.minestar.murder.`object`.skill.slayer.active.ImpactGrenade
import kr.kro.minestar.murder.`object`.skill.slayer.passive.DoorBlast
import kr.kro.minestar.murder.`object`.skill.slayer.passive.Hide
import kr.kro.minestar.murder.interfaces.creature.Slayer
import kr.kro.minestar.murder.interfaces.item.Tool
import kr.kro.minestar.murder.interfaces.item.Weapon
import kr.kro.minestar.murder.interfaces.skill.ActiveSkill
import kr.kro.minestar.murder.interfaces.skill.PassiveSkill
import org.bukkit.entity.Player

class BlastManiac(override var player: Player) : Slayer {
    override var name: String = "폭파광"
    override var codeName: String = this.javaClass.simpleName
    override var number: Int = 3
    override var weapon: Weapon = BlastHammer(player)
    override var activeSkill: ActiveSkill = ImpactGrenade(player)
    override var passiveSkill: PassiveSkill = DoorBlast(player)
    override var tool: Tool? = BlastBooster(player)
}