package kr.kro.minestar.murder.creature.slayer

import kr.kro.minestar.murder.interfaces.creature.Slayer
import kr.kro.minestar.murder.interfaces.item.Weapon
import kr.kro.minestar.murder.interfaces.skill.ActiveSkill
import kr.kro.minestar.murder.interfaces.skill.PassiveSkill
import kr.kro.minestar.murder.creature.skill.active.*
import kr.kro.minestar.murder.creature.skill.passive.*
import kr.kro.minestar.murder.creature.item.weapon.MurderAxe
import org.bukkit.entity.Player

class Tester(override var player: Player) : Slayer {
    override var name: String = "테스터"
    override var codeName: String = this.javaClass.simpleName
    override var number: Int = 1
    override var weapon: Weapon = MurderAxe(player)
    override var activeSkill: ActiveSkill = Dash(player)
    override var passiveSkill: PassiveSkill = Rush(player)
}