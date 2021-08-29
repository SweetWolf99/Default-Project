package kr.kro.minestar.murder.interfaces.skill

import kr.kro.minestar.murder.Main
import kr.kro.minestar.murder.functions.CreatureClass
import kr.kro.minestar.murder.interfaces.Type
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitTask

interface ActiveSkill : Skill, Listener {
    var coolTime: Long
    var coolDown: Long
    var coolDownTimer: BukkitTask?
    var duration: Long
    var type: Type

    @EventHandler
    fun use(e: PlayerSwapHandItemsEvent) {
        if(e.player != player) return
        e.isCancelled = true
        active()
    }

    fun active()

    fun coolDownLock(): Boolean {
        if (coolDown < 1) return false
        player.sendTitle(" ", "§8재사용 대기시간: §7${coolDown / 20.0} §8초", 5, 10, 5)
        return true
    }

    fun coolDownTimer() {
        coolDown = coolTime
        coolDownTimer = Bukkit.getScheduler().runTaskTimer(Main.pl!!, Runnable {
            if (coolDown > 1) --coolDown
            else {
                player.sendTitle(" ", "§9액티브 스킬 준비완료", 5, 10, 5)
                Bukkit.getScheduler().runTask(Main.pl!!, Runnable { coolDownTimer?.cancel() })
                --coolDown
            }
        }, 1, 1)
    }

    fun startCoolDownTimer() {
        if(coolDown == 0L) return
        coolDownTimer = Bukkit.getScheduler().runTaskTimer(Main.pl!!, Runnable {
            if (coolDown > 1) --coolDown
            else {
                player.sendTitle(" ", "§9액티브 스킬 준비완료", 5, 10, 5)
                Bukkit.getScheduler().runTask(Main.pl!!, Runnable { coolDownTimer?.cancel() })
                --coolDown
            }
        }, 1, 1)
    }

    fun getIcon(): ItemStack {
        val item = ItemStack(Material.IRON_INGOT)
        val itemMeta = item.itemMeta
        val lore = mutableListOf(" ")
        for (s in description) lore.add("§f§7$s")
        lore.add(" ")
        lore.add("§f§7재사용 대기시간 : ${coolTime / 20} 초")
        itemMeta.setDisplayName("§f$name [§9ACTIVE§f]")
        itemMeta.lore = lore
        itemMeta.setCustomModelData(number)
        item.itemMeta = itemMeta
        return item
    }
}