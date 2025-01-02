package cc.cassian.pyrite.registry.neoforge;

import net.minecraft.block.Block;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.Collection;

public class PyriteItemGroupsImpl {
	@SubscribeEvent
	public static void buildContents(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == ItemGroups.REDSTONE) {
			event.addAll(getNeoBlockCollectionList(BlockCreatorImpl.REDSTONE_BLOCKS));
		}
	}

	public static Collection<ItemStack> getNeoBlockCollectionList(ArrayList<DeferredHolder<Block, ?>> items) {
		ArrayList<ItemStack> stacks = new ArrayList<>();
		for (DeferredHolder<Block, ?> block : items) {
			stacks.add(block.get().asItem().getDefaultStack());
		}
		return stacks;
	}
}
