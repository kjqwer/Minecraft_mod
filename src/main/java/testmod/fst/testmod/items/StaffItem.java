package testmod.fst.testmod.items;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import testmod.fst.testmod.capability.myMP.PlayerMP;
import testmod.fst.testmod.capability.myMP.PlayerMpProvider;
import testmod.fst.testmod.config.CommonConfig;

import java.util.Random;

public class StaffItem extends Item {
    private final Random random = new Random();

    public StaffItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        player.getCapability(PlayerMpProvider.PLAYER_MP_CAPABILITY).ifPresent((mp) -> {
            if (mp instanceof PlayerMP) {
                PlayerMP playerMP = (PlayerMP) mp;
                if (player instanceof ServerPlayer) {
                    playerMP.setPlayer((ServerPlayer) player); // 设置玩家实例
                }

                System.out.println("Current MP: " + playerMP.getMp());
                if (playerMP.getMp() >= CommonConfig.mpdecrease) { // 确保法力足够
                    System.out.println("MP is sufficient, attempting to decrease MP by " + CommonConfig.mpdecrease);
                    if (playerMP.decrease(CommonConfig.mpdecrease)) { // 如果法力足够，减少法力并发射火球
                        System.out.println("MP decreased successfully. Current MP: " + playerMP.getMp());
                        if (!level.isClientSide) {
                            Vec3 look = player.getLookAngle();
                            Vec3 eyePosition = player.getEyePosition(1.0F);
                            double offsetX = look.x * 0.5;
                            double offsetY = look.y * 0.5;
                            double offsetZ = look.z * 0.5;

                            SmallFireball fireball = new SmallFireball(level, player, look.x, look.y, look.z);
                            fireball.setPos(eyePosition.x + offsetX, eyePosition.y + offsetY, eyePosition.z + offsetZ);
                            level.addFreshEntity(fireball);
                            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);

                            // 添加粒子效果
                            for (int i = 0; i < 8; ++i) {
                                level.addParticle(ParticleTypes.LARGE_SMOKE,
                                        fireball.getX() + (random.nextDouble() - 0.5) * 0.5,
                                        fireball.getY() + (random.nextDouble() - 0.5) * 0.5,
                                        fireball.getZ() + (random.nextDouble() - 0.5) * 0.5,
                                        0.0, 0.0, 0.0);
                            }
                        }
                    }
                } else {
                    System.out.println("Not enough MP! Current MP: " + playerMP.getMp());
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
