package kr.kro.minestar.murder.interfaces.skill

import kr.kro.minestar.murder.interfaces.Type
import org.bukkit.Material
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitTask

interface PassiveSkill : Skill,Listener {
    var tick: BukkitTask?
    var type: Type

    fun getIcon(): ItemStack {
        val item = ItemStack(Material.GOLD_INGOT)
        val itemMeta = item.itemMeta
        val lore = mutableListOf(" ")
        for (s in description) lore.add("§f§7$s")
        itemMeta.setDisplayName("§f$name [§aPASSIVE§f]")
        itemMeta.lore = lore
        itemMeta.setCustomModelData(number)
        item.itemMeta = itemMeta
        return item
    }
}