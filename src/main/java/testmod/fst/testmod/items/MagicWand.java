package testmod.fst.testmod.items;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerPlayer;
import testmod.fst.testmod.capability.myMP.PlayerMP;
import testmod.fst.testmod.capability.myMP.PlayerMpProvider;
import testmod.fst.testmod.config.CommonConfig;

import java.util.ArrayList;
import java.util.List;

public class MagicWand extends Item {
    private List<WandModule> modules = new ArrayList<>();

    public MagicWand(Properties properties) {
        super(properties);
    }

    public void addModule(WandModule module) {
        modules.add(module);
    }

    public void removeModule(WandModule module) {
        modules.remove(module);
    }

    private int getTotalManaCost() {
        int totalManaCost = 0;
        for (WandModule module : modules) {
            totalManaCost += module.getManaCost();
        }
        return totalManaCost;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        player.getCapability(PlayerMpProvider.PLAYER_MP_CAPABILITY).ifPresent((mp) -> {
            if (mp instanceof PlayerMP) {
                PlayerMP playerMP = (PlayerMP) mp;
                if (player instanceof ServerPlayer) {
                    playerMP.setPlayer((ServerPlayer) player);
                }

                if (playerMP.getMp() >= getTotalManaCost()) {
                    if (playerMP.decrease(getTotalManaCost())) {
                        for (WandModule module : modules) {
                            module.applyEffect(level, player);
                        }
                    }
                } else {
                    player.displayClientMessage(Component.literal("Not enough MP!"), true);
                }
            }
        });

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }
}
