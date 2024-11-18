package cc.cassian.pyrite.neoforge;


import cc.cassian.pyrite.Pyrite;
import cc.cassian.pyrite.functions.neoforge.BlockCreatorImpl;
import net.minecraft.SharedConstants;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

import static cc.cassian.pyrite.functions.neoforge.BlockCreatorImpl.*;


@Mod(Pyrite.modID)
public final class PyriteNeoForge {
    public PyriteNeoForge(IEventBus eventBus, ModContainer container) {
        // Run our common setup.
        Pyrite.init();
        eventBus.addListener(PyriteClient::registerBlockColors);
        eventBus.addListener(PyriteClient::registerItemColorHandlers);
        pyriteBlocks.register(eventBus);
        pyriteItems.register(eventBus);
        pyriteTabs.register(eventBus);
        eventBus.addListener(BlockCreatorImpl::signBlockEntityType);

    }
}
