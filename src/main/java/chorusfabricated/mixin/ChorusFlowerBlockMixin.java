package chorusfabricated.mixin;

import chorusfabricated.ChorusLogic;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChorusFlowerBlock.class)
public class ChorusFlowerBlockMixin {

    @ModifyReturnValue(method = "hasRandomTicks", at = @At("RETURN"))
    private boolean cancelChorusGrowLimit(boolean original, @Local(argsOnly = true) BlockState state) {
        if (!ChorusLogic.cancelChorusLimit) return original;
        return state.get(ChorusFlowerBlock.AGE) < 500;
    }

    @Inject(method = "die", at = @At("HEAD"), cancellable = true)
    private void cancelDying(World world, BlockPos pos, CallbackInfo ci) {
        if (ChorusLogic.cancelChorusLimit) ci.cancel();
    }

    @WrapOperation(method = "grow", at = @At(value = "INVOKE", target = "Ljava/lang/Integer;valueOf(I)Ljava/lang/Integer;"))
    private Integer cancelStateGrowing(int i, Operation<Integer> original) {
        if (ChorusLogic.cancelChorusLimit) return original.call(1);
        return original.call(i);
    }
}
