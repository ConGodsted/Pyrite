package cc.cassian.pyrite.forge;

import cc.cassian.pyrite.forge.client.PyriteForgeClient;
import cc.cassian.pyrite.functions.forge.BlockCreatorImpl;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import cc.cassian.pyrite.Pyrite;

@Mod(Pyrite.modID)
public final class PyriteForge {
    public PyriteForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Run our common setup.
        Pyrite.init();
        PyriteForgeClient.init(modEventBus);
        BlockCreatorImpl.register(modEventBus);
    }
}

