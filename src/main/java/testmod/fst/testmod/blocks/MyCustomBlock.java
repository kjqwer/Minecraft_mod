package testmod.fst.testmod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class MyCustomBlock extends Block {
    public MyCustomBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack itemInHand = player.getItemInHand(hand);

        if (itemInHand.is(Items.BONE)) {
            if (!level.isClientSide) {
                // 生成狗
                EntityType.WOLF.spawn((ServerLevel) level, itemInHand, player, pos, MobSpawnType.TRIGGERED, true, true);
                // 播放声音
                level.playSound(null, pos, SoundEvents.WOLF_AMBIENT, SoundSource.BLOCKS, 1.0F, 1.0F);

                // 消耗骨头
                if (!player.isCreative()) {
                    itemInHand.shrink(1);
                }
            }
            return InteractionResult.SUCCESS;
        }

        return super.use(state, level, pos, player, hand, hit);
    }
}
