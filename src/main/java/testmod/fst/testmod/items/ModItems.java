package testmod.fst.testmod.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static testmod.fst.testmod.TestMod.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final RegistryObject<Item> MAGIC_WAND = ITEMS.register("magic_wand",
            () -> new MagicWand(new Item.Properties().stacksTo(1).durability(500)));

    public static final RegistryObject<Item> FIREBALL_MODULE = ITEMS.register("fireball_module",
            () -> new Item(new Item.Properties().stacksTo(16)));


public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }

    @SubscribeEvent
    public static void addCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(MAGIC_WAND.get());
            event.accept(FIREBALL_MODULE.get());
        }
    }
}
