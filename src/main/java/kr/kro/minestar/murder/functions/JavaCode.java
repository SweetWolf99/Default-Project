package kr.kro.minestar.murder.functions;

import org.bukkit.block.data.type.Door;
import org.bukkit.event.player.PlayerInteractEvent;

public class JavaCode {

    public void test(PlayerInteractEvent e) {
        e.getPlayer().sendMessage("start");
        if (e.getClickedBlock().getBlockData() instanceof Door) e.getPlayer().sendMessage("end");
        if ((1 % 1) == 0) return;
    }
}
