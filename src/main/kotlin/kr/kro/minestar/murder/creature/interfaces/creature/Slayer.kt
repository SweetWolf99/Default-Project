package kr.kro.minestar.murder.creature.interfaces.creature

import kr.kro.minestar.murder.creature.interfaces.item.Weapon
import kr.kro.minestar.murder.creature.interfaces.skill.ActiveSkill
import kr.kro.minestar.murder.creature.interfaces.skill.PassiveSkill

interface Slayer : Creature {
    var weapon: Weapon
    var passiveSkill: PassiveSkill
    var activeSkill: ActiveSkill
}