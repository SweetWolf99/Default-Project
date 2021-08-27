package kr.kro.minestar.murder

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
        server.pluginManager.registerEvents(Event(),this)
    }

    override fun onDisable() {

    }


}