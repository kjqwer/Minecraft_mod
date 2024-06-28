package testmod.fst.testmod.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import testmod.fst.testmod.TestMod;
import testmod.fst.testmod.networking.SyncMPPacket;

public class ModMessages {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(TestMod.MODID, "messages"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void register() {
        int id = 0;
        INSTANCE.registerMessage(id++, SyncMPPacket.class, SyncMPPacket::toBytes, SyncMPPacket::new, SyncMPPacket::handle);
    }

    public static <T> void send(PacketDistributor.PacketTarget target, T message) {
        INSTANCE.send(target, message);
    }
}
