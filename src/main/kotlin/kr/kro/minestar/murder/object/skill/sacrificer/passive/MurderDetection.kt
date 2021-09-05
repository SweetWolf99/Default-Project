package kr.kro.minestar.murder.`object`.skill.sacrificer.passive

import kr.kro.minestar.murder.Main
import kr.kro.minestar.murder.functions.CreatureClass
import kr.kro.minestar.murder.interfaces.Type
import kr.kro.minestar.murder.interfaces.creature.Slayer
import kr.kro.minestar.murder.interfaces.skill.PassiveSkill
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask

class MurderDetection(override var player: Player) : PassiveSkill {
    override var number: Int = 2
    override var name: String = "살기 감지"
    override var codeName: String = this.javaClass.name
    override var description = mutableListOf(
        "슬레이어으로 부터 특별한 소리가 들립니다"
    )
    override var tick: BukkitTask? = null
    override var type: Type = Type.SEARCH

    init {
        tick = Bukkit.getScheduler().runTaskTimer(Main.pl!!, Runnable {
            val players = Bukkit.getOnlinePlayers()
            for (p in players) if (CreatureClass.playerCreature[p.uniqueId] is Slayer) player.playSound(p.location, Sound.ENTITY_PIGLIN_BRUTE_STEP, SoundCategory.MASTER, 1.0F, 0.5F)
        }, 1, (20 * 1.5).toLong())
    }
}