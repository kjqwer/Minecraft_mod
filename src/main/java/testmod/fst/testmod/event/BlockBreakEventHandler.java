package testmod.fst.testmod.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import testmod.fst.testmod.capability.myMP.PlayerMP;
import testmod.fst.testmod.capability.myMP.PlayerMpProvider;
import testmod.fst.testmod.command.GetMpCommand;
import testmod.fst.testmod.config.CommonConfig;

import static testmod.fst.testmod.TestMod.MODID;
import static testmod.fst.testmod.TestMod.myBlock;
import static testmod.fst.testmod.TestMod.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BlockBreakEventHandler {

    private static final ResourceLocation MY_BLOCK_RL = new ResourceLocation(MODID, "myblock");

    @SubscribeEvent
    public static void breakDOG(BlockEvent.BreakEvent event) {
        Block block = event.getState().getBlock();
        ResourceLocation blockRL = ForgeRegistries.BLOCKS.getKey(block);
        if (blockRL != null) {
            if (MY_BLOCK_RL.equals(blockRL)) { // 检查破坏的是否为特定方块
                ServerPlayer player = (ServerPlayer) event.getPlayer();
                player.getCapability(PlayerMpProvider.PLAYER_MP_CAPABILITY).ifPresent((mp) -> {
                    if (mp instanceof PlayerMP) {
                        PlayerMP playerMP = mp;
                        playerMP.setPlayer(player);
                        playerMP.increase(CommonConfig.mpincrease); // 增加法力
                    }
                });
            }
        }
    }
}