package testmod.fst.testmod.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import testmod.fst.testmod.capability.myMP.PlayerMP;
import testmod.fst.testmod.capability.myMP.PlayerMpProvider;

import java.util.function.Supplier;

public class SyncMPPacket {
    private final int mp;

    public SyncMPPacket(int mp) {
        this.mp = mp;
    }

    public SyncMPPacket(FriendlyByteBuf buf) {
        this.mp = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(mp);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // 客户端同步 MP 逻辑
            // 例如更新客户端的 HUD 显示
            // 请确保以下代码在客户端执行
            if (context.getDirection().getReceptionSide().isClient()) {
                // 获取当前玩家
                var player = net.minecraft.client.Minecraft.getInstance().player;
                if (player != null) {
                    player.getCapability(PlayerMpProvider.PLAYER_MP_CAPABILITY).ifPresent((mpCapability) -> {
                        if (mpCapability instanceof PlayerMP) {
                            ((PlayerMP) mpCapability).setMp(mp);
                        }
                    });
                }
            }
        });
        return true;
    }
}
