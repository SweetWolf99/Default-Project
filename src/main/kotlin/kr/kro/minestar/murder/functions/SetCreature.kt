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


class SetCreature {
    companion object {
        val playerCreature: MutableMap<UUID, Creature> = HashMap()
    }

    private fun findAllClassesUsingReflectionsLibrary(packageName: String?): Set<Class<*>>? {
        val reflections = Reflections(packageName, SubTypesScanner(false))
        return reflections.getSubTypesOf(Any::class.java).stream().collect(Collectors.toSet())
    }

    private fun randomSlayer(player: Player): Slayer {
        val pack = findAllClassesUsingReflectionsLibrary("kr.kro.minestar.murder.creature.slayer")!!.toTypedArray()
        val r = Random().nextInt(pack.size)
        var testClass: Class<*>? = Class.forName("kr.kro.minestar.murder.creature.slayer." + pack[r].simpleName)
        var constructor = testClass!!.constructors
        return constructor[0].newInstance(player) as Slayer
    }

    private fun randomSacrificer(player: Player): Sacrificer {
        val pack = findAllClassesUsingReflectionsLibrary("kr.kro.minestar.murder.creature.sacrificer")!!.toTypedArray()
        val r = Random().nextInt(pack.size)
        var testClass: Class<*>? = Class.forName("kr.kro.minestar.murder.creature.sacrificer." + pack[r].simpleName)
        var constructor = testClass!!.constructors
        return constructor[0].newInstance(player) as Sacrificer
    }

    fun setSlayer(p: Player) {
        removeClass(p)
        val slayer: Slayer = randomSlayer(p)
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

    fun setSacrificer(p: Player) {
        removeClass(p)
        val sacrificer: Sacrificer = randomSacrificer(p)
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
}