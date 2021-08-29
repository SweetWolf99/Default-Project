package kr.kro.minestar.murder.interfaces.creature

import kr.kro.minestar.murder.Main
import kr.kro.minestar.murder.interfaces.item.Tool
import kr.kro.minestar.murder.interfaces.skill.ActiveSkill
import kr.kro.minestar.murder.interfaces.skill.PassiveSkill
import org.bukkit.*
import org.bukkit.block.data.BlockData
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitTask

interface Sacrificer : Creature, Listener {
    var tool: Tool?
    var passiveSkill: PassiveSkill?
    var activeSkill: ActiveSkill?

    var coolTime: Long
    var coolDown: Long
    var coolDownTimer: BukkitTask?

    fun init() {
        coolTime = 20 * 15
        coolDown = 0
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
                var b = true //착탄여부
                val loc: Location = player.location.add(0.0, 1.65, 0.0)
                var locD = loc.toVector().add(loc.direction.multiply(d)).toLocation(player.world)
                while (b) {
                    d += 0.1
                    ++count
                    if(count == 7){
                        player.world.spawnParticle(Particle.SMOKE_NORMAL, locD, 1, 0.0, 0.0, 0.0, 0.0)
                        count = 0
                    }
                    if (locD.block.type != Material.AIR) {
                        b = false
                        player.world.spawnParticle<BlockData>(Particle.BLOCK_CRACK, locD, 20, 0.05, 0.05, 0.05, 0.01, Bukkit.createBlockData(locD.block.type))
                        player.world.spawnParticle(Particle.SMOKE_NORMAL, locD, 1, 0.0, 0.0, 0.0, 0.0)
                    }
                    val players = locD.getNearbyPlayers(0.0)
                    if (players.toTypedArray().isNotEmpty()) for (target in players) if (target != player) if (target.gameMode != GameMode.SPECTATOR) {
                        b = false
                        target.gameMode = GameMode.SPECTATOR
                        locD.world.spawnParticle<BlockData>(Particle.BLOCK_CRACK, locD, 30, 0.2, 0.2, 0.2, 0.1, Bukkit.createBlockData(Material.RED_CONCRETE))
                    }
                    locD = loc.toVector().add(loc.direction.multiply(d)).toLocation(player.world)
                }
                coolDownTimer()
                return
            }
    }

    fun getPistol(): ItemStack {
        val item = ItemStack(Material.FLINT)
        val itemMeta = item.itemMeta
        itemMeta.setDisplayName("§f권총 [§cWEAPON§f]")
        item.itemMeta = itemMeta
        return item
    }
}