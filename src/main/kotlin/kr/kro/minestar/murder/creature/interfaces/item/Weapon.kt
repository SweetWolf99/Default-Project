package kr.kro.minestar.murder.creature.interfaces.item

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.inventory.ItemStack

interface Weapon {
    var material: Material
    var number: Int
    var display: String
    var lore: List<String>
    var damage: Double

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
        itemMeta.setDisplayName("§f$display [§cWEAPON§f]")
        itemMeta.lore = lore
        item.itemMeta = itemMeta
        return item
    }
}