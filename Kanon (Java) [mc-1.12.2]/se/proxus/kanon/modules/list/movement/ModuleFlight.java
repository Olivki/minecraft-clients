package se.proxus.kanon.modules.list.movement;

import org.lwjgl.input.Keyboard;
import se.proxus.kanon.event4j.EventSubscribe;
import se.proxus.kanon.event4j.events.player.EventPlayerUpdate;
import se.proxus.kanon.modules.Module;
import se.proxus.kanon.modules.ModuleSignature;
import se.proxus.kanon.wrapper.minecraft.Minekraft;
import se.proxus.kanon.wrapper.minecraft.Player;

@ModuleSignature(author = "Oliver Berg", date = "2018/09/12 (10:31)")
public final class ModuleFlight extends Module {

    public ModuleFlight() {
        super("Flight",
              "R",
              "Allows you to fly.",
              Controller.TOGGLE,
              Category.MOVEMENT);
    }

    @Override
    public void initialize() {
        getConfig().addEntry("Speed", 1.0D, true)
                    .setDescription("The speed at which you fly at.")
                    .setRange(0.5D, 2.5D);
    
        getConfig().addEntry("Fall Damage", false, true)
                .setDescription("Whether you want to take fall damage when landing or not.");
    }

    @EventSubscribe
    public final void handleFlight(final EventPlayerUpdate event) {
        final float speed = (float) getConfig().getDouble("Speed");
        final boolean inGameHasFocus = Minekraft.inGameHasFocus();

        Player.setMotionX(0.0D);
        Player.setMotionY(0.0D);
        Player.setMotionZ(0.0D);

        Player.setJumpMovementFactor(speed / 2);

        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && inGameHasFocus)
            Player.setMotionY(Player.getMotionY() + (speed / 2 + 0.2F));

        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && inGameHasFocus) {
            Player.setMotionY(Player.getMotionY() - speed);

            if (!getConfig().getBoolean("Fall Damage"))
                Player.setOnGround(true);
        }
    }
}