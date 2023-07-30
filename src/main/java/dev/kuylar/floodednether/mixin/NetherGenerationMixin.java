package dev.kuylar.floodednether.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.world.gen.chunk.AquiferSampler;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.world.gen.chunk.NoiseChunkGenerator.class)
public class NetherGenerationMixin {
	@Inject(method = "createFluidLevelSampler", at = @At("HEAD"), cancellable = true)
	private static void createFluidLevelSampler(ChunkGeneratorSettings settings, CallbackInfoReturnable<AquiferSampler.FluidLevelSampler> cir) {
		if (settings.defaultBlock() == Blocks.NETHERRACK.getDefaultState() && settings.defaultFluid() == Blocks.LAVA.getDefaultState()) {
			int seaLevel = settings.seaLevel();
			AquiferSampler.FluidLevel seaFluidLevel = new AquiferSampler.FluidLevel(seaLevel, settings.defaultFluid());
			AquiferSampler.FluidLevel water = new AquiferSampler.FluidLevel(256, Blocks.WATER.getDefaultState());
			cir.setReturnValue((x, y, z) -> y < seaLevel ? seaFluidLevel : water);
		}
	}
}
