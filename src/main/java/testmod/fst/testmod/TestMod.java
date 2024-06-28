package testmod.fst.testmod;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import testmod.fst.testmod.blocks.MyCustomBlock;
import testmod.fst.testmod.capability.myMP.PlayerMpProvider;
import testmod.fst.testmod.config.CommonConfig;
import testmod.fst.testmod.event.BlockBreakEventHandler;
import testmod.fst.testmod.event.CreativeModeTabListener;
import testmod.fst.testmod.items.StaffItem;
import testmod.fst.testmod.networking.ModMessages;

@Mod(TestMod.MODID)
public class TestMod {
    public static final String MODID = "testmod";
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    // 注册方块
    public static final RegistryObject<Block> myBlock = BLOCKS.register("myblock",
            () -> new MyCustomBlock(BlockBehaviour.Properties.of().strength(3.0f).sound(SoundType.CROP)));
    public static final RegistryObject<Item> myBlockItem = ITEMS.register("myblock",
            () -> new BlockItem(myBlock.get(), new Item.Properties()));
    public static final RegistryObject<Item> STAFF_ITEM = ITEMS.register("staff",
            () -> new StaffItem(new Item.Properties()));
    public TestMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        CreativeModeTabListener.CREATIVE_MODE_TABS.register(bus);
        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, this::attachCapability);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);
        MinecraftForge.EVENT_BUS.register(BlockBreakEventHandler.class);

        // 注册消息
        bus.addListener(this::setup);
    }
    private void setup(final FMLCommonSetupEvent event) {
        ModMessages.register();
    }
    public void attachCapability(AttachCapabilitiesEvent<Entity> event){
        if(event.getObject() instanceof Player player){
            if(!player.getCapability(PlayerMpProvider.PLAYER_MP_CAPABILITY).isPresent()){
                event.addCapability(new ResourceLocation(MODID, "mp"), new PlayerMpProvider());
            }
        }
    }
}
