package kr.kro.minestar.murder.`object`.item.weapon

import kr.kro.minestar.murder.Main
import kr.kro.minestar.murder.interfaces.item.Weapon
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class MurderAxe(override var player: Player) : Weapon {
    init {
        Bukkit.getPluginManager().registerEvents(this, Main.pl!!)
    }

    override var material: Material = Material.IRON_AXE
    override var number: Int = 1
    override var display: String = "살인 도끼"
    override var damage: Double = 12.0
    override var hitEffect: List<String> = listOf("대상에게 '구속 I'을 3초 부여 합니다")
    override var killEffect: List<String> = listOf("'신속 III'을 5초간 받습니다")


    override fun hitEffect(e: EntityDamageByEntityEvent) {
        val target = e.entity as Player
        val damager = e.damager as Player
        target.addPotionEffect(PotionEffect(PotionEffectType.SLOW,20 * 3,0))
    }

    override fun killEffect(e: EntityDamageByEntityEvent) {
        val target = e.entity as Player
        val damager = e.damager as Player
        damager.addPotionEffect(PotionEffect(PotionEffectType.SPEED,20 * 5,2))
    }
}