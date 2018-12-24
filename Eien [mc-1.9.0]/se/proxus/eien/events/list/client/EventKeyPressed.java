package se.proxus.eien.events.list.client;

import org.lwjgl.input.Keyboard;

import se.proxus.eien.events.EventCancellable;

/**
 * 
 * @see net.minecraft.client.Minecraft#func_152348_aa()
 * 
 **/
public class EventKeyPressed extends EventCancellable {

	private int keyId;
	private String keyName;
	private boolean inGame;

	public EventKeyPressed(final int keyId, final boolean inGame) {
		super("Event Key Pressed");
		setKeyId(keyId);
		setKeyName(Keyboard.getKeyName(keyId));
		setInGame(inGame);
	}

	public int getKeyId() {
		return keyId;
	}

	public void setKeyId(final int keyId) {
		this.keyId = keyId;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(final String keyName) {
		this.keyName = keyName;
	}

	public boolean isInGame() {
		return inGame;
	}

	public void setInGame(final boolean inGame) {
		this.inGame = inGame;
	}
}
