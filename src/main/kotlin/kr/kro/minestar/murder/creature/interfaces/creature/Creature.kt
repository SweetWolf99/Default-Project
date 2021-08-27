package kr.kro.minestar.murder.creature.interfaces.creature

import org.bukkit.entity.Player

interface Creature {
    var player: Player?
    var number: Int
    var name: String
    var codeName: String

    fun addPlayer(p: Player){
        if(player == null) player = p!!
    }
}