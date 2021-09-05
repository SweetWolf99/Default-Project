package kr.kro.minestar.murder.interfaces.creature

import kr.kro.minestar.murder.interfaces.item.Tool
import kr.kro.minestar.murder.interfaces.item.Weapon
import kr.kro.minestar.murder.interfaces.skill.ActiveSkill
import kr.kro.minestar.murder.interfaces.skill.PassiveSkill

interface Slayer : Creature {
    var weapon: Weapon
    var passiveSkill: PassiveSkill
    var activeSkill: ActiveSkill
    var tool: Tool?
}