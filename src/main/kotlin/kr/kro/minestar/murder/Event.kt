package kr.kro.minestar.murder

import kr.kro.minestar.murder.creature.interfaces.creature.Sacrificer
import kr.kro.minestar.murder.creature.interfaces.creature.Slayer
import kr.kro.minestar.murder.functions.Function
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

class Event : Listener {
    @EventHandler
    fun useActive(e: PlayerInteractEvent) {
        if (e.action != Action.RIGHT_CLICK_AIR && e.action != Action.RIGHT_CLICK_BLOCK) return
        if (e.action == Action.RIGHT_CLICK_BLOCK && e.hand == EquipmentSlot.HAND) return
        val item = e.player.inventory.itemInMainHand
        val itemMeta = item.itemMeta
        if (item == null) return
        if (itemMeta == null) return
        if (itemMeta.displayName == null) return
        if (!itemMeta.displayName.contains("ACTIVE")) return
        if (Function.playerCreature[e.player.uniqueId] == null) return
        if (Function.playerCreature[e.player.uniqueId] is Slayer) (Function.playerCreature[e.player.uniqueId] as Slayer).activeSkill.active()
        if (Function.playerCreature[e.player.uniqueId] is Sacrificer) (Function.playerCreature[e.player.uniqueId] as Sacrificer).activeSkill?.active()
    }
}