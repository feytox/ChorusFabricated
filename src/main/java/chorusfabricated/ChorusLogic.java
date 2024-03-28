package chorusfabricated;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;

import static chorusfabricated.ChorusFabricated.CONFIG;

public class ChorusLogic {

    public static boolean cancelChorusLimit = false;
    private static int oldRandomTickSpeed = 3;

    public static void onChorusPlace(BlockPos pos, World world, PlayerEntity player) {
        if (!(world instanceof ServerWorld serverWorld) || player == null) return;
        if (!player.getName().getString().equals(CONFIG.chorusActivator())) return;
        if (CONFIG.chorusPlaced()) return;
        CONFIG.chorusPlaced(true);

        cancelChorusLimit = true;
        GameRules.IntRule rule = world.getGameRules().get(GameRules.RANDOM_TICK_SPEED);
        oldRandomTickSpeed = rule.get();
        rule.set(CONFIG.chorusGrowSpeed(), serverWorld.getServer());

        int centerX = pos.getX();
        int centerZ = pos.getZ();
        placeEndStone(serverWorld, centerX, centerZ, 3);
        DelayedTask.schedule(() -> {
            rule.set(oldRandomTickSpeed, serverWorld.getServer());
            cancelChorusLimit = false;
            placeEndStone(serverWorld, centerX, centerZ, 5);
        }, 40);
        DelayedTask.schedule(() -> placeEndStone(serverWorld, centerX, centerZ, 7), 80);
        DelayedTask.schedule(() -> placeEndStone(serverWorld, centerX, centerZ, 9), 120);
    }

    private static void placeEndStone(ServerWorld world, int centerX, int centerZ, int radius) {
        Random random = world.getRandom();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                if (dx == 0 && dz == 0) continue;
                int topY = world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, centerX+dx, centerZ+dz);
                BlockPos pos = new BlockPos(centerX+dx, topY-1, centerZ+dz);
                BlockState state = world.getBlockState(pos);
                if (!state.getFluidState().isEmpty()) continue;
                if (random.nextFloat() > 0.3) continue;

                world.setBlockState(pos, Blocks.END_STONE.getDefaultState());
                spawnParticles(world, pos);
            }
        }
    }

    private static void spawnParticles(ServerWorld world, BlockPos pos) {
        double x = pos.getX() + 0.5d;
        double y = pos.getY() + 1.0d;
        double z = pos.getZ() + 0.5d;
        world.spawnParticles(ParticleTypes.PORTAL, x, y, z, 30, 0.5, 1.0, 0.5, 1);
    }
}
