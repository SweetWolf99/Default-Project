package kr.kro.minestar.murder.`object`.creature.sacrificer

import kr.kro.minestar.murder.Main
import kr.kro.minestar.murder.`object`.item.tool.SwitchPosition
import kr.kro.minestar.murder.interfaces.creature.Sacrificer
import kr.kro.minestar.murder.interfaces.item.Tool
import kr.kro.minestar.murder.interfaces.skill.ActiveSkill
import kr.kro.minestar.murder.interfaces.skill.PassiveSkill
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask

class Teleporter(override var player: Player) : Sacrificer {
    init {
        Bukkit.getPluginManager().registerEvents(this, Main.pl!!)
        init()
    }

    override var name: String = "텔레포터"
    override var codeName: String = this.javaClass.simpleName
    override var number: Int = 4
    override var tool: Tool? = SwitchPosition(player)
    override var activeSkill: ActiveSkill? = null
    override var passiveSkill: PassiveSkill? = null

    override var coolTime: Long = 0
    override var coolDown: Long = 0
    override var coolDownTimer: BukkitTask? = null
    override var spawnParts: BukkitTask? = null
}