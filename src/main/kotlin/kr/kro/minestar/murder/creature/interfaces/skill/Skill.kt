package kr.kro.minestar.murder.creature.interfaces.skill

import org.bukkit.entity.Player

interface Skill {
    var player: Player
    var number: Int
    var name: String
    var codeName: String
    var description: MutableList<String>
}