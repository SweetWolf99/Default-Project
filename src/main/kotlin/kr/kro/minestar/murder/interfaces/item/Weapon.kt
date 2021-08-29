package kr.kro.minestar.murder.interfaces.item

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
        if (e.entity !is Player) return
        if (e.damager !is Player) return
        if ((e.damager as Player).inventory.itemInMainHand.itemMeta == null) return
        if (!(e.damager as Player).inventory.itemInMainHand.itemMeta.displayName.contains(display)) return
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