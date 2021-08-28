package kr.kro.minestar.murder.functions

import kr.kro.minestar.murder.interfaces.creature.Creature
import kr.kro.minestar.murder.interfaces.creature.Sacrificer
import kr.kro.minestar.murder.interfaces.creature.Slayer
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.reflections.Reflections
import org.reflections.scanners.SubTypesScanner
import java.util.*
import java.util.stream.Collectors


class CreatureClass {
    companion object {
        val playerCreature: MutableMap<UUID, Creature> = HashMap()
    }

    fun setSlayer(slayer: Slayer) {
        val p = slayer.player
        removeClass(p)
        playerCreature[p.uniqueId] = slayer
        val job = playerCreature[p.uniqueId] as Slayer
        val inv = p.inventory
        inv.heldItemSlot = 4
        inv.clear()
        inv.setItem(0, job.weapon.getWeapon())
        inv.setItem(1, job.activeSkill.getIcon())
        inv.setItem(2, job.passiveSkill.getIcon())
        p.sendTitle("§c${job.name}", "§8${job.codeName}", 10, 30, 10)
    }

    fun setSacrificer(sacrificer: Sacrificer) {
        val p = sacrificer.player
        removeClass(p)
        playerCreature[p.uniqueId] = sacrificer
        val job = playerCreature[p.uniqueId] as Sacrificer
        val inv = p.inventory
        inv.heldItemSlot = 4
        inv.clear()
        if (job.tool != null) inv.setItem(0, job.tool!!.getTool())
        if (job.activeSkill != null) inv.setItem(1, job.activeSkill!!.getIcon())
        if (job.passiveSkill != null) inv.setItem(2, job.passiveSkill!!.getIcon())
        p.sendTitle("§9${job.name}", "§8${job.codeName}", 10, 30, 10)
    }

    fun removeClass(p: Player) {
        if (!playerCreature.contains(p.uniqueId)) return
        val job = playerCreature[p.uniqueId]
        if (job is Slayer) {
            HandlerList.unregisterAll(job.weapon)
            HandlerList.unregisterAll(job.activeSkill)
        }
        if (job is Sacrificer) {
            if (job.tool != null) HandlerList.unregisterAll(job.tool!!)
            if (job.activeSkill != null) HandlerList.unregisterAll(job.activeSkill!!)
        }
        playerCreature.remove(p.uniqueId)
    }

    private fun packageInClasses(packageName: String?): Set<Class<*>>? {
        val reflections = Reflections(packageName, SubTypesScanner(false))
        return reflections.getSubTypesOf(Any::class.java).stream().collect(Collectors.toSet())
    }

    fun getSlayerList(): List<String> {
        val pack = packageInClasses("kr.kro.minestar.murder.creature.slayer")!!.toTypedArray()
        val list: MutableList<String> = mutableListOf()
        for (c in pack) list.add(c.simpleName)
        return list
    }
    fun getSacrificerList(): List<String> {
        val pack = packageInClasses("kr.kro.minestar.murder.creature.sacrificer")!!.toTypedArray()
        val list: MutableList<String> = mutableListOf()
        for (c in pack) list.add(c.simpleName)
        return list
    }

    fun getSlayer(player: Player, slayerName: String): Slayer {
        var slayerClass: Class<*>? = Class.forName("kr.kro.minestar.murder.creature.slayer.$slayerName")
        var constructor = slayerClass!!.constructors
        return constructor[0].newInstance(player) as Slayer
    }

    fun getSacrificer(player: Player, sacrificerName: String): Sacrificer {
        var slayerClass: Class<*>? = Class.forName("kr.kro.minestar.murder.creature.sacrificer.$sacrificerName")
        var constructor = slayerClass!!.constructors
        return constructor[0].newInstance(player) as Sacrificer
    }

     fun randomSlayer(player: Player): Slayer {
        val pack = packageInClasses("kr.kro.minestar.murder.creature.slayer")!!.toTypedArray()
        val r = Random().nextInt(pack.size)
        var slayerClass: Class<*>? = Class.forName("kr.kro.minestar.murder.creature.slayer." + pack[r].simpleName)
        var constructor = slayerClass!!.constructors
        return constructor[0].newInstance(player) as Slayer
    }

     fun randomSacrificer(player: Player): Sacrificer {
        val pack = packageInClasses("kr.kro.minestar.murder.creature.sacrificer")!!.toTypedArray()
        val r = Random().nextInt(pack.size)
        var sacrificerClass: Class<*>? = Class.forName("kr.kro.minestar.murder.creature.sacrificer." + pack[r].simpleName)
        var constructor = sacrificerClass!!.constructors
        return constructor[0].newInstance(player) as Sacrificer
    }
}