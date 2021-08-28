package kr.kro.minestar.murder.interfaces.creature

import org.bukkit.entity.Player

interface Creature {
    var player: Player
    var number: Int
    var name: String
    var codeName: String
}