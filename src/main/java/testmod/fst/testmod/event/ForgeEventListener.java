package testmod.fst.testmod.event;

import net.minecraft.world.level.block.StemBlock;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static testmod.fst.testmod.TestMod.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventListener {
    @SubscribeEvent
    public static void preventStem(BonemealEvent event){
        if (event.getBlock().getBlock() instanceof StemBlock){
            event.setCanceled(true);
        }
    }
}
