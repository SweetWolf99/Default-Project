package kr.kro.minestar.murder.functions

import kr.kro.minestar.murder.Main
import kr.kro.minestar.murder.interfaces.creature.Slayer
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerAttemptPickupItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerPickupItemEvent

class GameEvent : Listener {
    var damageCancel = true
    init {
        Bukkit.getPluginManager().registerEvents(this, Main.pl!!)
    }

    @EventHandler
    fun blockInteract(e: PlayerInteractEvent) {
        if (e.action == Action.RIGHT_CLICK_BLOCK) e.isCancelled = true
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
    fun damageCancel(e: EntityDamageEvent) {
        if (damageCancel) e.isCancelled = true
    }

    @EventHandler
    fun hitCancel(e: EntityDamageByEntityEvent) {
        if(CreatureClass.playerCreature[e.damager.uniqueId] !is Slayer) e.isCancelled = true
    }
}