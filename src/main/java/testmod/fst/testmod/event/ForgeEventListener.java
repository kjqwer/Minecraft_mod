package testmod.fst.testmod.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import testmod.fst.testmod.capability.myMP.PlayerMpProvider;
import testmod.fst.testmod.command.GetMpCommand;
import testmod.fst.testmod.config.CommonConfig;

import static testmod.fst.testmod.TestMod.MODID;
import static testmod.fst.testmod.TestMod.myBlock;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventListener {
    @SubscribeEvent
    public static void preventStem(BonemealEvent event){
        if (event.getBlock().getBlock() instanceof StemBlock){
            event.setCanceled(true);
        }
    }
    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event){
        GetMpCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void playerClone(PlayerEvent.Clone event){
        event.getOriginal().getCapability(PlayerMpProvider.PLAYER_MP_CAPABILITY).ifPresent(old ->
                event.getEntity().getCapability(PlayerMpProvider.PLAYER_MP_CAPABILITY).ifPresent(mp ->
                        mp.setMp(old.getMp())));
    }
}
