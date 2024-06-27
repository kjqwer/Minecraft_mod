package testmod.fst.testmod.event;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import testmod.fst.testmod.TestMod;

import static testmod.fst.testmod.TestMod.MODID;
import static testmod.fst.testmod.TestMod.STAFF_ITEM;
import static testmod.fst.testmod.TestMod.myBlockItem;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreativeModeTabListener {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<CreativeModeTab> MY_TAB = CREATIVE_MODE_TABS.register("mytab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("mytabname"))
                    .icon(() -> new ItemStack(STAFF_ITEM.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(myBlockItem.get());
                        output.accept(STAFF_ITEM.get());
                    })
                    .build());

    public static void register(IEventBus bus) {
        CREATIVE_MODE_TABS.register(bus);
    }
}
