package kr.kro.minestar.murder.interfaces.item

import org.bukkit.GameMode
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

interface Weapon : Item, Listener {
    var damage: Double
    var hitEffect: List<String>
    var killEffect: List<String>

    @EventHandler
    fun hit(e: EntityDamageByEntityEvent) {
        e.isCancelled = true
        val target = e.entity
        val damager = e.damager
        if (target !is Player) return
        if (damager !is Player) return
        if (damager != player) return
        if (damager.inventory.itemInMainHand.itemMeta == null) return
        if (!damager.inventory.itemInMainHand.itemMeta.displayName.contains(display)) return
        val damage = (e.damager as Player).attackCooldown * damage
        if ((e.entity as Player).health <= damage) kill(e)
        e.damage = damage
        e.isCancelled = false
        hitEffect(e)
    }

    fun kill(e: EntityDamageByEntityEvent) {
        player!!.world.playSound(e.entity.location, Sound.ENTITY_PLAYER_DEATH, SoundCategory.MASTER, 1.0F, 1.0F)
        player!!.world.playSound(e.entity.location, Sound.ENTITY_PLAYER_ATTACK_CRIT, SoundCategory.MASTER, 1.0F, 1.0F)
        killEffect(e)
    }

    fun hitEffect(e: EntityDamageByEntityEvent) {}

    fun killEffect(e: EntityDamageByEntityEvent) {}

    fun getWeapon(): ItemStack {
        val item = ItemStack(material)
        val itemMeta = item.itemMeta
        val lore = mutableListOf(" ")
        lore.add("§f§7§l:공격 효과:")
        for (s in hitEffect) lore.add("§f§7$s")
        lore.add(" ")
        lore.add("§f§7§l:처치 효과:")
        for (s in killEffect) lore.add("§f§7$s")
        lore.add(" ")
        lore.add("§f§7대미지 : $damage")
        itemMeta.setDisplayName("§f$display [§cWEAPON§f]")
        itemMeta.lore = lore
        for (flag in ItemFlag.values()) itemMeta.addItemFlags(flag)
        itemMeta.isUnbreakable = true
        item.itemMeta = itemMeta
        return item
    }
}