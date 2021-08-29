package kr.kro.minestar.murder.interfaces.item

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

interface Weapon : Item, Listener {
    var damage: Double

    @EventHandler
    fun hit(e: EntityDamageByEntityEvent) {
        e.isCancelled = true
        val damaged = e.entity
        val damager = e.damager
        if (damaged !is Player) return
        if (damager !is Player) return
        if (damager != player) return
        if (damager.inventory.itemInMainHand.itemMeta == null) return
        if (!damager.inventory.itemInMainHand.itemMeta.displayName.contains(display)) return
        val damage = (e.damager as Player).attackCooldown * damage
        if ((e.entity as Player).health <= damage) {
            kill(e)
            return
        } else e.damage = damage
        e.isCancelled = false
        hitEffect(e)
    }

    fun kill(e: EntityDamageByEntityEvent) {
        (e.entity as Player).gameMode = GameMode.SPECTATOR
        killEffect(e)
    }

    fun hitEffect(e: EntityDamageByEntityEvent) {}

    fun killEffect(e: EntityDamageByEntityEvent) {}

    fun getWeapon(): ItemStack {
        val item = ItemStack(material)
        val itemMeta = item.itemMeta
        val lore = mutableListOf(" ")
        for (s in description) lore.add("§f§7$s")
        lore.add(" ")
        lore.add("§f§7대미지 : $damage")
        itemMeta.setDisplayName("§f$display [§cWEAPON§f]")
        itemMeta.lore = lore
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
        item.itemMeta = itemMeta
        return item
    }
}