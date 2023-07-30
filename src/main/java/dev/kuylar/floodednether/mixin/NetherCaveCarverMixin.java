package dev.kuylar.floodednether.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.carver.CarverContext;
import net.minecraft.world.gen.carver.CarvingMask;
import net.minecraft.world.gen.carver.CaveCarverConfig;
import net.minecraft.world.gen.chunk.AquiferSampler;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

@Mixin(net.minecraft.world.gen.carver.NetherCaveCarver.class)
public class NetherCaveCarverMixin {
	@Inject(method = "carveAtPoint(Lnet/minecraft/world/gen/carver/CarverContext;Lnet/minecraft/world/gen/carver/CaveCarverConfig;Lnet/minecraft/world/chunk/Chunk;Ljava/util/function/Function;Lnet/minecraft/world/gen/carver/CarvingMask;Lnet/minecraft/util/math/BlockPos$Mutable;Lnet/minecraft/util/math/BlockPos$Mutable;Lnet/minecraft/world/gen/chunk/AquiferSampler;Lorg/apache/commons/lang3/mutable/MutableBoolean;)Z", at = @At("HEAD"), cancellable = true)
	public void carveAtPoint(CarverContext carverContext,
	                         CaveCarverConfig caveCarverConfig,
	                         Chunk chunk,
	                         Function<BlockPos, RegistryEntry<Biome>> function,
	                         CarvingMask carvingMask,
	                         BlockPos.Mutable mutable,
	                         BlockPos.Mutable mutable2,
	                         AquiferSampler aquiferSampler,
	                         MutableBoolean mutableBoolean,
	                         CallbackInfoReturnable<Boolean> cir) {
		if (chunk.getBlockState(mutable).isIn(caveCarverConfig.replaceable)) {
			BlockState blockState;
			if (mutable.getY() <= carverContext.getMinY() + 31) {
				blockState = Fluids.LAVA.getDefaultState().getBlockState();
			} else {
				blockState = Blocks.WATER.getDefaultState();
			}

			chunk.setBlockState(mutable, blockState, false);
			cir.setReturnValue(true);
		} else {
			cir.setReturnValue(false);
		}
	}
}
