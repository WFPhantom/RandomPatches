/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020-2021 TheRandomLabs
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.therandomlabs.randompatches.mixin;

import com.therandomlabs.randompatches.RandomPatches;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.world.IBlockReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WalkNodeProcessor.class)
public final class WalkNodeProcessorMixin {
	@Inject(
			method = "getPathNodeType(Lnet/minecraft/world/IBlockReader;" +
					"IIILnet/minecraft/entity/MobEntity;IIIZZ)" +
					"Lnet/minecraft/pathfinding/PathNodeType;",
			at = @At("RETURN"),
			cancellable = true
	)
	private void getNodeType(
			IBlockReader world, int x, int y, int z, MobEntity mob, int sizeX, int sizeY, int sizeZ,
			boolean canOpenDoors, boolean canEnterOpenDoors,
			CallbackInfoReturnable<PathNodeType> info
	) {
		if (RandomPatches.config().misc.bugFixes.fixMobsNotCrossingRails &&
				info.getReturnValue() == PathNodeType.UNPASSABLE_RAIL) {
			info.setReturnValue(PathNodeType.RAIL);
		}
	}
}
