package testmod.fst.testmod.capability.myMP;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerMpProvider implements ICapabilityProvider, INBTSerializable {
    private PlayerMP playerMP;

    public static final Capability<PlayerMP> PLAYER_MP_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    private final LazyOptional<PlayerMP> lazyOptional = LazyOptional.of(() -> this.playerMP);
    public PlayerMpProvider(){
        this.playerMP = new PlayerMP();
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return getCapability(cap);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if(cap == PLAYER_MP_CAPABILITY){
            return lazyOptional.cast();
        }else {
            return lazyOptional.empty();
        }
    }

    @Override
    public Tag serializeNBT() {
        var tag = new CompoundTag();
        playerMP.saveNBTData(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        playerMP.loadNBTData((CompoundTag) nbt);
    }
}
