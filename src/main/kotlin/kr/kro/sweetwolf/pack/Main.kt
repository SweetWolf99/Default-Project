package kr.kro.sweetwolf.pack

import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    companion object {
        var pl: Main? = null
        const val prefix = "§f§7[§9MineCraftDefaultProject§7]§f"
    }

    override fun onEnable() {
        pl = this
        logger.info("$prefix §aEnable")
        getCommand("cmd")?.setExecutor(CMD())
    }

    override fun onDisable() {
    }
}