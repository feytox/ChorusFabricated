package chorusfabricated.mixin;

import chorusfabricated.ChorusLogic;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class BlockItemMixin {

    @Inject(method = "postPlacement", at = @At("HEAD"))
    private void listenToChorusPlace(BlockPos pos, World world, PlayerEntity player, ItemStack stack, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (!(state.getBlock() instanceof ChorusFlowerBlock)) return;
        ChorusLogic.onChorusPlace(pos, world, player);
    }
}
