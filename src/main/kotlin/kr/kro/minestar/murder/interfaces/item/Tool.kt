package kr.kro.minestar.murder.interfaces.item

import kr.kro.minestar.murder.functions.CreatureClass
import kr.kro.minestar.murder.interfaces.Type
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

interface Tool :Item,Listener{
    var type: Type

    @EventHandler
    fun use(e: PlayerInteractEvent) {
        if (e.action != Action.RIGHT_CLICK_AIR && e.action != Action.RIGHT_CLICK_BLOCK) return
        if (e.action == Action.RIGHT_CLICK_BLOCK && e.hand == EquipmentSlot.HAND) return
        val item = e.player.inventory.itemInMainHand
        val itemMeta = item.itemMeta
        if (item == null) return
        if (itemMeta == null) return
        if (itemMeta.displayName == null) return
        if (!itemMeta.displayName.contains(display)) return
        if (CreatureClass.playerCreature[e.player.uniqueId] == null) return
        active()
    }

    fun active()

    fun getTool(): ItemStack {
        val item = ItemStack(material)
        val itemMeta = item.itemMeta
        val lore = mutableListOf(" ")
        for (s in description) lore.add("§f§7$s")
        itemMeta.setDisplayName("§f$display [§cTOOL§f]")
        itemMeta.lore = lore
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        item.itemMeta = itemMeta
        return item
    }
}