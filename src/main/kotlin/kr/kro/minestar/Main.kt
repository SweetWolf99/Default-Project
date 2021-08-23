package kr.kro.minestar

import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    companion object {
        const val prefix = "§f§7[§9MineStar's Plugin§7] §f"
    }

    override fun onEnable() {
        logger.info("$prefix §aEnable")

        getCommand("MCDP")?.setExecutor(CMD())
    }

    override fun onDisable() {

    }


}