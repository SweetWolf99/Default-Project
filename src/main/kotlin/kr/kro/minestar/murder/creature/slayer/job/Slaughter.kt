package kr.kro.minestar.murder.creature.slayer.job

import kr.kro.minestar.murder.creature.interfaces.creature.Slayer
import kr.kro.minestar.murder.creature.interfaces.item.Weapon
import kr.kro.minestar.murder.creature.interfaces.skill.ActiveSkill
import kr.kro.minestar.murder.creature.interfaces.skill.PassiveSkill
import kr.kro.minestar.murder.creature.slayer.skill.active.*
import kr.kro.minestar.murder.creature.slayer.skill.passive.*
import kr.kro.minestar.murder.creature.slayer.weapon.*
import org.bukkit.entity.Player

class Slaughter : Slayer {
    override var player: Player? = TODO()
    override var name: String = "도살자"
    override var codeName: String = this.javaClass.name
    override var number: Int = 1
    override var weapon: Weapon = MurderAxe()
    override var activeSkill: ActiveSkill = Dash(player!!)
    override var passiveSkill: PassiveSkill = Rush(player!!)
}