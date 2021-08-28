package kr.kro.minestar.murder.creature.sacrificer

import kr.kro.minestar.murder.interfaces.creature.Sacrificer
import kr.kro.minestar.murder.interfaces.item.Tool
import kr.kro.minestar.murder.interfaces.skill.ActiveSkill
import kr.kro.minestar.murder.interfaces.skill.PassiveSkill
import kr.kro.minestar.murder.creature.item.tool.MasterKey
import kr.kro.minestar.murder.creature.skill.active.*
import kr.kro.minestar.murder.creature.skill.passive.*
import org.bukkit.entity.Player

class KeyMaster(override var player: Player) : Sacrificer {
    override var name: String = "열쇠공"
    override var codeName: String = this.javaClass.simpleName
    override var number: Int = 1
    override var tool: Tool? = MasterKey(player)
    override var activeSkill: ActiveSkill? = null
    override var passiveSkill: PassiveSkill? = null
}