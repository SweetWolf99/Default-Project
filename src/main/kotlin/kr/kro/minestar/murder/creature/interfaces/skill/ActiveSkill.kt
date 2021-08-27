package kr.kro.minestar.murder.creature.interfaces.skill

import kr.kro.minestar.murder.Main
import kr.kro.minestar.murder.creature.interfaces.creature.Creature
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitTask

interface ActiveSkill : Skill {
    var coolTime: Long
    var coolDown: Long
    var coolDownTimer: BukkitTask?
    var duration: Long
    var type: Type

    fun active()

    fun coolDownLock(): Boolean {
        if(coolDown <= 0) return false
        player.sendTitle(" ", "§8재사용 대기시간: §7${coolDown / 20.0} §8초",5,10,5)
        return true
    }

    fun coolDownTimer() {
        coolDown = coolTime
        coolDownTimer = Bukkit.getScheduler().runTaskTimer(Main.pl!!, Runnable {
            if(coolDown > 0) --coolDown
            else coolDownTimer?.cancel()
        }, 1, 1)
    }

    fun getIcon(): ItemStack {
        val item = ItemStack(Material.IRON_INGOT)
        val itemMeta = item.itemMeta
        val lore = mutableListOf(" ")
        for (s in description) lore.add(s)
        lore.add(" ")
        lore.add("§f§7재사용 대기시간 : ${coolTime / 20} 초")
        itemMeta.setDisplayName("§f$name [§9ACTIVE§f]")
        itemMeta.lore = lore
        itemMeta.setCustomModelData(number)
        item.itemMeta = itemMeta
        return item
    }
}