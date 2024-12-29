package cc.cassian.pyrite.forge;

import cc.cassian.pyrite.functions.forge.BlockCreatorImpl;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import cc.cassian.pyrite.Pyrite;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Pyrite.MOD_ID)
public final class PyriteForge {
    public PyriteForge() {
        // Get mod event bus so we can register our content on the right time.
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Run our common setup.
        Pyrite.init();
        BlockCreatorImpl.register(modEventBus);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.addListener(PyriteForgeClient::registerBlockColors);
            modEventBus.addListener(PyriteForgeClient::registerItemColorHandlers);
        }
        modEventBus.addListener(BlockCreatorImpl::commonSetup);



    }
}

