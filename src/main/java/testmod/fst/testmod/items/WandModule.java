package testmod.fst.testmod.items;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface WandModule {
    int getManaCost();
    void applyEffect(Level level, Player player);
}
