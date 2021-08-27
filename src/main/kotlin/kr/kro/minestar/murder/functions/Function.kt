package kr.kro.minestar.murder.functions

import kr.kro.minestar.murder.creature.interfaces.creature.Creature
import kr.kro.minestar.murder.creature.interfaces.creature.Sacrificer
import kr.kro.minestar.murder.creature.interfaces.creature.Slayer
import kr.kro.minestar.murder.creature.slayer.job.Slaughter
import kr.kro.minestar.murder.creature.slayer.job.SlayerList
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.HashMap

class Function {
    companion object {
        val playerCreature: MutableMap<UUID, Creature> = HashMap()
    }

    fun randomSlayer(): Class<Slayer> {
        val list = SlayerList.values()
        val r = Random().nextInt(list.size)
//        Bukkit.broadcastMessage(list[r].toString())
        return Class.forName("kr.kro.minestar.murder.creature.slayer.job.Slaughter") as Class<Slayer>
    }

    fun setSlayer(p: Player) {
        val slayerClass: Slayer = randomSlayer().cast(Slaughter())
        slayerClass.addPlayer(p)
        playerCreature[p.uniqueId] = slayerClass
        val job = playerCreature[p.uniqueId] as Slayer
        val inv = p.inventory
        inv.heldItemSlot = 4
        inv.clear()
        inv.setItem(0, job.weapon.getWeapon())
        inv.setItem(1, job.activeSkill.getIcon())
        inv.setItem(2, job.passiveSkill.getIcon())
        p.sendTitle("§c${job.name}", "§8${job.codeName}", 10, 30, 10)
    }

    fun setSacrificer(p: Player, sacrificer: Sacrificer) {
        playerCreature[p.uniqueId] = sacrificer
        val job = playerCreature[p.uniqueId] as Sacrificer
        val inv = p.inventory
        inv.heldItemSlot = 4
        inv.clear()
        if (job.tool != null) inv.setItem(0, job.tool!!.getTool())
        if (job.activeSkill != null) inv.setItem(1, job.activeSkill!!.getIcon())
        if (job.passiveSkill != null) inv.setItem(2, job.passiveSkill!!.getIcon())
    }
}