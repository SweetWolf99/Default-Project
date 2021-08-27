package kr.kro.minestar.murder.creature.interfaces.skill

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitTask

interface PassiveSkill : Skill {
    var tick: BukkitTask?
    var type: Type

    fun getIcon(): ItemStack {
        val item = ItemStack(Material.GOLD_INGOT)
        val itemMeta = item.itemMeta
        val lore = mutableListOf(" ")
        for(s in description) lore.add(s)
        itemMeta.setDisplayName("§f$name [§aPASSIVE§f]")
        itemMeta.lore = lore
        itemMeta.setCustomModelData(number)
        item.itemMeta = itemMeta
        return item
    }
}