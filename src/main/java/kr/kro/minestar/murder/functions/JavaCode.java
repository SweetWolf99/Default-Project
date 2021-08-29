package kr.kro.minestar.murder.functions;

import org.bukkit.block.data.type.Door;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JavaCode {

    public void test(PlayerInteractEvent e){
        e.getPlayer().sendMessage("start");
        if(e.getClickedBlock().getBlockData() instanceof Door) e.getPlayer().sendMessage("end");
    }
}
