package testmod.fst.testmod.event;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static testmod.fst.testmod.TestMod.MODID;
import static testmod.fst.testmod.TestMod.myBlock;
import static testmod.fst.testmod.TestMod.STAFF_ITEM;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MODID)
public class EventListener{
    @SubscribeEvent
    public static void addCreativeTab(BuildCreativeModeTabContentsEvent event ){
        if(event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS){
            event.accept(myBlock);
        }
        if(event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES){
            event.accept(STAFF_ITEM);
        }
    }
}