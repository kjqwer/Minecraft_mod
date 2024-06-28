package testmod.fst.testmod.config;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static testmod.fst.testmod.TestMod.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.IntValue MPINCREASE;
    public static final ForgeConfigSpec.IntValue MPDECREASE;
    public static ForgeConfigSpec SPEC;

    public static int mpincrease, mpdecrease;
    public static Set<Block> blocksGetmp;

    static {
        MPINCREASE = BUILDER.defineInRange("mpincrease", 1, 0, Integer.MAX_VALUE);
        MPDECREASE = BUILDER.defineInRange("mpdecrease", 1, 0, Integer.MAX_VALUE);
        SPEC = BUILDER.build();
    }

    private static boolean allow(Object obj) {
        return ForgeRegistries.BLOCKS.containsKey(new ResourceLocation((String) obj));
    }

    public static void loadConfig() {
        mpincrease = MPINCREASE.get();
        mpdecrease = MPDECREASE.get();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading configEvent) {
        if (configEvent.getConfig().getSpec() == SPEC) {
            loadConfig();
        }
    }

    @SubscribeEvent
    public static void onReload(final ModConfigEvent.Reloading configEvent) {
        if (configEvent.getConfig().getSpec() == SPEC) {
            loadConfig();
        }
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        loadConfig();
    }
}
