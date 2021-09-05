package kr.kro.minestar.murder.interfaces.creature

import kr.kro.minestar.murder.Main
import kr.kro.minestar.murder.functions.GameSystem
import kr.kro.minestar.murder.interfaces.item.Tool
import kr.kro.minestar.murder.interfaces.skill.ActiveSkill
import kr.kro.minestar.murder.interfaces.skill.PassiveSkill
import org.bukkit.*
import org.bukkit.block.data.BlockData
import org.bukkit.entity.LivingEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitTask
import java.util.*

interface Sacrificer : Creature, Listener {
    var tool: Tool?
    var passiveSkill: PassiveSkill?
    var activeSkill: ActiveSkill?

    var coolTime: Long
    var coolDown: Long
    var coolDownTimer: BukkitTask?
    var spawnParts: BukkitTask?


    fun init() {
        coolTime = 20 * 15
        coolDown = 0
        spawnParts()
    }

    fun coolDownLock(): Boolean {
        if (coolDown < 1) return false
        player.sendTitle(" ", "§8재사용 대기시간: §7${coolDown / 20.0} §8초", 5, 10, 5)
        return true
    }

    fun coolDownTimer() {
        coolDown = coolTime
        coolDownTimer = Bukkit.getScheduler().runTaskTimer(Main.pl!!, Runnable {
            if (coolDown > 1) --coolDown
            else {
                player.sendTitle(" ", "§9권총 준비완료", 5, 10, 5)
                Bukkit.getScheduler().runTask(Main.pl!!, Runnable { coolDownTimer?.cancel() })
                --coolDown
            }
        }, 1, 1)
    }

    @EventHandler
    fun use(e: PlayerInteractEvent) {
        if (e.action != Action.RIGHT_CLICK_AIR && e.action != Action.RIGHT_CLICK_BLOCK) return
        if (e.action == Action.RIGHT_CLICK_BLOCK && e.hand == EquipmentSlot.HAND) return
        if (e.player != player) return
        if (e.player.inventory.itemInMainHand.itemMeta == null) return
        val item = e.player.inventory.itemInMainHand
        val itemMeta = item.itemMeta
        val display = itemMeta.displayName
        if (display.contains("PARTS"))
            if (display.contains("권총 부품")) {
                if (item.amount < 10) return
                item.amount -= 10
                e.player.inventory.addItem(getPistol())
                return
            }
        if (display.contains("WEAPON"))
            if (display.contains("권총")) {
                if (coolDownLock()) return
                player.world.playSound(player.location, Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER, 1F, 2F)
                var d = 0.0
                var count = 0
                val loc: Location = player.eyeLocation
                var locD = loc.toVector().add(loc.direction.multiply(d)).toLocation(player.world)
                while (true) {
                    if (d > 50) break
                    d += 0.1
                    ++count
                    if (count == 7) {
                        player.world.spawnParticle(Particle.SMOKE_NORMAL, locD, 1, 0.0, 0.0, 0.0, 0.0)
                        count = 0
                    }
                    if (locD.block.type != Material.AIR) {
                        player.world.spawnParticle<BlockData>(Particle.BLOCK_CRACK, locD, 20, 0.05, 0.05, 0.05, 0.01, Bukkit.createBlockData(locD.block.type))
                        player.world.spawnParticle(Particle.SMOKE_NORMAL, locD, 1, 0.0, 0.0, 0.0, 0.0)
                        break
                    }
                    val players = locD.getNearbyPlayers(0.0)
                    if (players.toTypedArray().isNotEmpty()) {
                        val target = players.toTypedArray()[0]
                        if (target != player) if (target.gameMode != GameMode.SPECTATOR) {
                            if (target.health <= 10.0) target.gameMode = GameMode.SPECTATOR
                            else (target as LivingEntity).damage(10.0)
                            locD.world.spawnParticle<BlockData>(Particle.BLOCK_CRACK, locD, 30, 0.2, 0.2, 0.2, 0.1, Bukkit.createBlockData(Material.RED_CONCRETE))
                            break
                        }
                    }
                    locD = loc.toVector().add(loc.direction.multiply(d)).toLocation(player.world)
                }
                coolDownTimer()
                return
            }
    }

    fun spawnParts() {
        spawnParts = Bukkit.getScheduler().runTaskTimer(Main.pl!!, Runnable {
            if (GameSystem.sacrificer == 0) spawnParts!!.cancel()
            else {
                val r = Random().nextInt(GameSystem.sacrificer)
                if (r == 0) {
                    var rx = Random().nextInt(5) + 5
                    var rz = Random().nextInt(5) + 5
                    if (Random().nextBoolean()) rx *= -1
                    if (Random().nextBoolean()) rz *= -1
                    player.world.dropItem(player.location.clone().add(rx.toDouble(), 0.0, rz.toDouble()), getParts())
                }
            }
        }, 1, 20 * 10)
    }

    fun getParts(): ItemStack {
        val item = ItemStack(Material.FLINT)
        val itemMeta = item.itemMeta
        itemMeta.setDisplayName("§f권총 부품 [§cPARTS§f]")
        val lore = mutableListOf(" ")
        lore.add("§f§710개를 모아 권총을 만들 수 있습니다")
        itemMeta.lore = lore
        item.itemMeta = itemMeta
        return item
    }

    fun getPistol(): ItemStack {
        val item = ItemStack(Material.FLINT)
        val itemMeta = item.itemMeta
        itemMeta.setDisplayName("§f권총 [§cWEAPON§f]")
        val lore = mutableListOf(" ")
        lore.add("§f§7대미지 : 10.0")
        lore.add(" ")
        lore.add("§f§7재사용 대기시간 : 15초")
        itemMeta.lore = lore
        item.itemMeta = itemMeta
        return item
    }
}