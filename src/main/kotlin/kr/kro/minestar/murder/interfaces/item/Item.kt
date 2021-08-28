package kr.kro.minestar.murder.interfaces.item

import org.bukkit.Material
import org.bukkit.entity.Player

interface Item {
    var player: Player
    var material: Material
    var number: Int
    var display: String
    var description: List<String>
}