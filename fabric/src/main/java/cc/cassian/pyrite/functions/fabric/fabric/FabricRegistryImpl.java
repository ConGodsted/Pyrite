package cc.cassian.pyrite.functions.fabric.fabric;

import cc.cassian.pyrite.blocks.ModSign;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;

public class FabricRegistryImpl {
    public static BlockEntityType<ModSign> registerSignBlockEntity(Block sign, Block wall_sign) {
        return FabricBlockEntityTypeBuilder.create(ModSign::new, sign, wall_sign).build();

    }
}
