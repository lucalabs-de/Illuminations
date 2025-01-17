package ladysnake.illuminations.common.blocks;

import ladysnake.illuminations.common.entities.FireflyEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BoundingBox;
import net.minecraft.world.ViewableWorld;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class FireflyTallGrassBlock extends TallPlantBlock {
    public static final int MIN_FIREFLIES = FireflyTallGrassBlock.MIN_FIREFLIES;
    public static final int MAX_FIREFLIES = FireflyTallGrassBlock.MAX_FIREFLIES;

    public FireflyTallGrassBlock(Settings block$Settings_1) {
        super(block$Settings_1);
    }

    @Override
    public boolean hasRandomTicks(BlockState blockState_1) {
        return true;
    }

    @Override
    public int getTickRate(ViewableWorld viewableWorld_1) {
        return 1;
    }

    @Override
    public void onRandomTick(BlockState blockState, World world, BlockPos blockPos, Random random) {
        if (!world.isClient && !world.isDaylight()) {
            boolean arePlayersNear = world.isPlayerInRange((double) blockPos.getX(), (double) blockPos.getY(), (double) blockPos.getZ(), 32);
            List<FireflyEntity> firefliesInRadius = world.getEntities(FireflyEntity.class, new BoundingBox(blockPos).expand(32));

            if (arePlayersNear) {
                int firefliesToSpawn = 0;
                if (firefliesInRadius.size() == 0) {
                    firefliesToSpawn = ThreadLocalRandom.current().nextInt(MIN_FIREFLIES, MAX_FIREFLIES + 1);
                } else if (firefliesInRadius.size() < MAX_FIREFLIES) {
                    firefliesToSpawn = 1;
                }
                for (int i = 0; i < firefliesToSpawn; i++) {
                    Entity firefly = new FireflyEntity(world, blockPos.getX(), blockPos.getY(), blockPos.getZ());
                    world.spawnEntity(firefly);
                }
            }
        }
    }
}
