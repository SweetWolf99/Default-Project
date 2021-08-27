package kr.kro.minestar.murder.creature.interfaces.item

import kr.kro.minestar.murder.creature.interfaces.skill.Type
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

interface Tool {
    var material: Material
    var number: Int
    var display: String
    var lore: List<String>
    var type: Type

    fun getTool(): ItemStack {
        val item = ItemStack(material)
        val itemMeta = item.itemMeta
        itemMeta.setDisplayName("§f$display [§cTOOL§f]")
        itemMeta.lore = lore
        item.itemMeta = itemMeta
        return item
    }
}