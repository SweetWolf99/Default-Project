package kr.kro.minestar.murder.interfaces.item

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

interface Weapon :Item, Listener{
    var damage: Double

    @EventHandler
    fun hit(e: EntityDamageByEntityEvent) {
        if (e.entity !is Player) {
            e.isCancelled = true
            return
        }
        if (e.damager !is Player) {
            e.isCancelled = true
            return
        }
        if (!(e.damager as Player).inventory.itemInMainHand.itemMeta.displayName.contains(display)) {
            e.isCancelled = true
            return
        }
        var damage = (e.damager as Player).attackCooldown * damage
        if ((e.entity as Player).health <= damage) {
            kill(e)
            return
        } else e.damage = damage
        hitEffect(e)
    }

    fun kill(e: EntityDamageByEntityEvent) {
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