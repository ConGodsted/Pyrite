package cc.cassian.pyrite.forge;

import cc.cassian.pyrite.functions.forge.BlockCreatorImpl;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import cc.cassian.pyrite.Pyrite;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Pyrite.modID)
public final class PyriteForge {
    public PyriteForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(Pyrite.modID, FMLJavaModLoadingContext.get().getModEventBus());
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Run our common setup.
        Pyrite.init();
        BlockCreatorImpl.register(modEventBus);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.addListener(PyriteClient::registerBlockColors);
            modEventBus.addListener(PyriteClient::registerItemColorHandlers);
        }



    }
}

