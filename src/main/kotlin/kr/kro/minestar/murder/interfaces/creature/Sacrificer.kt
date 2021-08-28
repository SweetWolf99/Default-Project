package kr.kro.minestar.murder.interfaces.creature

import kr.kro.minestar.murder.interfaces.item.Tool
import kr.kro.minestar.murder.interfaces.skill.ActiveSkill
import kr.kro.minestar.murder.interfaces.skill.PassiveSkill

interface Sacrificer : Creature {
    var tool: Tool?
    var passiveSkill: PassiveSkill?
    var activeSkill: ActiveSkill?
}