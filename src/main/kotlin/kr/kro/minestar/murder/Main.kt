package kr.kro.minestar.murder

import kr.kro.minestar.murder.functions.JavaCode
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    companion object {
        var pl: Main? = null
        const val prefix = "§f§7[§cMurder§7]§f"
    }

    override fun onEnable() {
        pl = this
        logger.info("$prefix §aEnable")
        getCommand("murder")?.setExecutor(CMD())
    }

    override fun onDisable() {

    }


}