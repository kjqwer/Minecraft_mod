package testmod.fst.testmod.capability.myMP;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;
import testmod.fst.testmod.networking.ModMessages;
import testmod.fst.testmod.networking.SyncMPPacket;

public class PlayerMP {
    private int mp;
    private ServerPlayer player;

    public PlayerMP() {
        this.mp = 10; // 设置初始法力值
    }

    public void setPlayer(ServerPlayer player) {
        this.player = player;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
        syncMP();
    }

    public void increase(int i) {
        int oldMp = mp;
        mp += i;
        syncMP();
        checkAndNotifyMp(oldMp, mp);
    }

    public void increase() {
        this.increase(1);
    }

    public boolean decrease(int d) {
        if (mp >= d) {
            int oldMp = mp;
            mp -= d;
            syncMP();
            checkAndNotifyMp(oldMp, mp);
            return true;
        } else {
            return false;
        }
    }

    private void syncMP() {
        if (player != null && !player.level().isClientSide) {
            ModMessages.send(PacketDistributor.PLAYER.with(() -> player), new SyncMPPacket(this.mp));
        }
    }

    private void checkAndNotifyMp(int oldMp, int newMp) {
        if (shouldNotify(oldMp, newMp, 20)) {
            notifyPlayer(newMp);
        } else if (shouldNotify(oldMp, newMp, 200)) {
            notifyPlayer(newMp);
        } else if (shouldNotify(oldMp, newMp, 1000)) {
            notifyPlayer(newMp);
        }
    }

    private boolean shouldNotify(int oldMp, int newMp, int threshold) {
        if (newMp < threshold) {
            return (newMp / 20) != (oldMp / 20);
        } else if (newMp < 1000) {
            return (newMp / 200) != (oldMp / 200);
        } else {
            return (newMp / 1000) != (oldMp / 1000);
        }
    }

    private void notifyPlayer(int currentMp) {
        if (player != null) {
            player.displayClientMessage(Component.literal("Current MP: " + currentMp), true);
        }
    }

    public void saveNBTData(CompoundTag compoundTag) {
        compoundTag.putInt("mp", mp);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        mp = compoundTag.getInt("mp");
    }
}
