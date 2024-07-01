package testmod.fst.testmod.gui.overlay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import testmod.fst.testmod.TestMod;
import testmod.fst.testmod.capability.myMP.PlayerMP;
import testmod.fst.testmod.capability.myMP.PlayerMpProvider;

@Mod.EventBusSubscriber(modid = TestMod.MODID, value = Dist.CLIENT)
public class MpOverlay {

    @SubscribeEvent
    public static void onRenderGuiOverlay(RenderGuiOverlayEvent.Post event) {
        renderMP(event.getGuiGraphics());
    }

    private static void renderMP(GuiGraphics guiGraphics) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null) {
            minecraft.player.getCapability(PlayerMpProvider.PLAYER_MP_CAPABILITY).ifPresent(mp -> {
                if (mp instanceof PlayerMP) {
                    int currentMP = ((PlayerMP) mp).getMp();
                    int mp_x = 10; // 固定位置X
                    int mp_y = 10; // 固定位置Y
                    int mp_color = 0xFFFFFF; // 固定颜色白色

                    guiGraphics.drawString(minecraft.font, "MP: " + currentMP, mp_x, mp_y, mp_color);
                }
            });
        }
    }
}
