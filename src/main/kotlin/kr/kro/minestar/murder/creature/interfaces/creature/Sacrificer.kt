package kr.kro.minestar.murder.creature.interfaces.creature

import kr.kro.minestar.murder.creature.interfaces.item.Tool
import kr.kro.minestar.murder.creature.interfaces.skill.ActiveSkill
import kr.kro.minestar.murder.creature.interfaces.skill.PassiveSkill

interface Sacrificer : Creature {
    var tool: Tool?
    var passiveSkill: PassiveSkill?
    var activeSkill: ActiveSkill?
}