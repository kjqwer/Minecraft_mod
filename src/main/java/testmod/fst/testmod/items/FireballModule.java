package testmod.fst.testmod.items;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.particles.ParticleTypes;

import java.util.Random;

public class FireballModule implements WandModule {
    private final Random random = new Random();
    private final int manaCost;

    public FireballModule(int manaCost) {
        this.manaCost = manaCost;
    }

    @Override
    public int getManaCost() {
        return manaCost;
    }

    @Override
    public void applyEffect(Level level, Player player) {
        Vec3 look = player.getLookAngle();
        Vec3 eyePosition = player.getEyePosition(1.0F);
        double offsetX = look.x * 0.5;
        double offsetY = look.y * 0.5;
        double offsetZ = look.z * 0.5;

        SmallFireball fireball = new SmallFireball(level, player, look.x, look.y, look.z);
        fireball.setPos(eyePosition.x + offsetX, eyePosition.y + offsetY, eyePosition.z + offsetZ);
        level.addFreshEntity(fireball);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);

        for (int i = 0; i < 8; ++i) {
            level.addParticle(ParticleTypes.LARGE_SMOKE,
                    fireball.getX() + (random.nextDouble() - 0.5) * 0.5,
                    fireball.getY() + (random.nextDouble() - 0.5) * 0.5,
                    fireball.getZ() + (random.nextDouble() - 0.5) * 0.5,
                    0.0, 0.0, 0.0);
        }
    }
}
