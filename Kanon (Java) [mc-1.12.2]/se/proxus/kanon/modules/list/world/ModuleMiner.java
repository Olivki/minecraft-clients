package se.proxus.kanon.modules.list.world;

import se.proxus.kanon.event4j.EventSubscribe;
import se.proxus.kanon.event4j.events.player.EventPlayerBlockDamaged;
import se.proxus.kanon.modules.Module;
import se.proxus.kanon.modules.ModuleSignature;

@ModuleSignature(author = "Oliver Berg", date = "2018/09/10 (20:40)")
public final class ModuleMiner extends Module {

    public ModuleMiner() {
        super("Miner",
              "M",
              "Makes you mine faster.",
              Controller.TOGGLE,
              Category.WORLD);
    }

    @Override
    public void initialize() {
        getConfig().addEntry("Speed", 0.3D, true)
                .setDescription("How fast to mine.")
                .setRange(0.1D, 0.6D);
    
        getConfig().addEntry("Delay", 5, true)
                .setDescription("The block break delay.")
                .setRange(0, 5);
    }

    @EventSubscribe
    public final void onBlockDamaged(final EventPlayerBlockDamaged event) {
        event.setThreshold(event.getThreshold() - (float) getConfig().getDouble("Speed"));
        event.setDelay(getConfig().getInteger("Delay"));
    }
}