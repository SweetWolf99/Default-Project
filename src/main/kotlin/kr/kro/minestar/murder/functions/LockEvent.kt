package kr.kro.minestar.murder.functions

import kr.kro.minestar.murder.Main
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerInteractEvent

class LockEvent : Listener {
    var damageCancel = true
    init {
        Bukkit.getPluginManager().registerEvents(this, Main.pl!!)
    }

    @EventHandler
    fun blockPlace(e: BlockPlaceEvent) {
        e.isCancelled = true
    }

    @EventHandler
    fun blockBreak(e: BlockBreakEvent) {
        e.isCancelled = true
    }

    @EventHandler
    fun blockInteract(e: PlayerInteractEvent) {
        if (e.action == Action.RIGHT_CLICK_BLOCK) e.isCancelled = true
    }

    @EventHandler
    fun damageCancel(e: EntityDamageEvent) {
        if (damageCancel) e.isCancelled = true
    }
}