package mcjty.deepresonance.blocks.lens;

import mcjty.deepresonance.blocks.tank.TankSetup;
import mcjty.lib.container.GenericBlock;
import mcjty.lib.container.GenericItemBlock;
import mcjty.lib.varia.Logging;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LensItemBlock extends GenericItemBlock {
    public LensItemBlock(Block block) {
        super(block);
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing direction, float hitX, float hitY, float hitZ, IBlockState newState) {
        if (direction == EnumFacing.UP || direction == EnumFacing.DOWN) {
            if (world.isRemote) {
                Logging.warn(player, "You can only place this vertical!");
            }
            return false;
        }
        direction = direction.getOpposite();
        Block block = world.getBlockState(pos.offset(direction)).getBlock();
        if (block != TankSetup.tank) {
            if (world.isRemote) {
                Logging.warn(player, "You can only place this against a tank!");
            }
            return false;
        }

        return super.placeBlockAt(stack, player, world, pos, direction, hitX, hitY, hitZ, newState.withProperty(GenericBlock.FACING_HORIZ, direction));
    }
}
