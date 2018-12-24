package se.proxus.kanon.event4j.events.client;

import org.lwjgl.input.Keyboard;
import se.proxus.kanon.event4j.events.EventCancellable;

public final class EventKeyPressed extends EventCancellable {

    private final int keyID;
    private final String keyName;
    private final boolean inGame;

    public EventKeyPressed(final int keyID, final boolean inGame) {
        this.keyID = keyID;
        this.keyName = Keyboard.getKeyName(keyID);
        this.inGame = inGame;
    }

    public int getKeyID() {
        return keyID;
    }

    public String getKeyName() {
        return keyName;
    }

    public boolean isInGame() {
        return inGame;
    }
}
