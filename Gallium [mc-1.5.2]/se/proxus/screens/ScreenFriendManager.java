package se.proxus.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.GuiYesNo;
import net.minecraft.src.StringUtils;

import org.lwjgl.input.Keyboard;

import se.proxus.mods.list.none.Friends;
import se.proxus.screens.slots.SlotFriend;
import se.proxus.tools.ArrayHelper;

public class ScreenFriendManager extends GuiScreen {

    private SlotFriend guiSlot;
    private final GuiScreen parentScreen;
    private GuiButton buttonAddFriend;
    private GuiButton buttonRemoveFriend;
    private boolean deletePressed;

    public ScreenFriendManager(final GuiScreen parentScreen) {
	this.parentScreen = parentScreen;
    }

    @Override
    public void initGui() {
	Minecraft.getMinecraft().dp.getMods().sortMods(
		Minecraft.getMinecraft().dp.getMods().getRegisteredMods());
	guiSlot = new SlotFriend(this);
	guiSlot.registerScrollButtons(getButtonList(), 7, 8);
	getButtonList().add(
		buttonAddFriend = new GuiButton(0, width / 2 - 100,
			height - 48, 98, 20, "Add friend"));
	getButtonList().add(
		buttonRemoveFriend = new GuiButton(1, width / 2 + 2,
			height - 48, 98, 20, "Remove friend"));
	buttonRemoveFriend.enabled = mc.thePlayer != null;
	getButtonList().add(
		new GuiButton(2, width / 2 - 100, height - 26, "Done"));
    }

    @Override
    public void updateScreen() {
	getButtonList().clear();
	getButtonList().add(
		buttonAddFriend = new GuiButton(0, width / 2 - 100,
			height - 48, 98, 20, "Add Friend"));
	getButtonList().add(
		buttonRemoveFriend = new GuiButton(1, width / 2 + 2,
			height - 48, 98, 20, "Remove Friend"));
	buttonRemoveFriend.enabled = guiSlot.getSelected() >= 0
		&& guiSlot.getSelected() < ((Friends) Minecraft.getMinecraft().dp
			.getMods().getMod("Friends")).getLoadedFriends().size();
	getButtonList().add(
		new GuiButton(2, width / 2 - 100, height - 26, "Done"));
    }

    @Override
    protected void actionPerformed(final GuiButton button) {
	if (button.enabled)
	    switch (button.id) {
	    case 0:
		mc.displayGuiScreen(new ScreenFriendAdd(this));
		break;

	    case 1:
		String name = "˜0˜1˜2˜3˜4˜5˜6˜7˜8˜9˜y˜r"
			+ ((Friends) Minecraft.getMinecraft().dp.getMods()
				.getMod("Friends")).getLoadedFriends()
				.get(guiSlot.getSelected()).getKey();
		if (name != null) {
		    deletePressed = true;
		    String areYouSure = "Are you sure you want to delete this friend?";
		    String info = "\"" + StringUtils.stripControlCodes(name)
			    + "\" will be lost forever! (A long time!)";
		    String delete = "Delete";
		    String cancel = "Cancel";
		    GuiYesNo yesOrNo = new GuiYesNo(this, areYouSure, info,
			    delete, cancel, guiSlot.getSelected());
		    mc.displayGuiScreen(yesOrNo);
		}
		break;

	    case 2:
		mc.displayGuiScreen(parentScreen);
		break;
	    }

	guiSlot.actionPerformed(button);
    }

    @Override
    public void drawScreen(final int x, final int y, final float ticks) {
	try {
	    guiSlot.drawScreen(x, y, ticks);
	} catch (Exception exception) {

	}
	drawCenteredString(fontRenderer,
		"Friend Manager ("
			+ String.valueOf(guiSlot.getSelected() + 1)
			+ "/"
			+ ((Friends) Minecraft.getMinecraft().dp.getMods()
				.getMod("Friends")).getLoadedFriends().size()
			+ ")", width / 2, 10, 0xFFFFFFFF);
	super.drawScreen(x, y, ticks);
    }

    @Override
    public void confirmClicked(final boolean flag, final int index) {
	if (deletePressed) {
	    deletePressed = false;
	    if (flag) {
		Friends friend = (Friends) Minecraft.getMinecraft().dp
			.getMods().getMod("Friends");
		friend.getLoadedFriends().remove(index);
		friend.saveFriends();
	    }
	    mc.displayGuiScreen(this);
	}
    }

    class ScreenFriendAdd extends GuiScreen {

	protected GuiTextField nameField;
	protected GuiTextField aliasField;
	protected GuiScreen parentScreen;

	public ScreenFriendAdd(final GuiScreen parentScreen) {
	    setParentScreen(parentScreen);
	}

	@Override
	public void initGui() {
	    Keyboard.enableRepeatEvents(true);
	    setNameField(new GuiTextField(fontRenderer, width / 2 - 100, 76,
		    200, 20));
	    setAliasField(new GuiTextField(fontRenderer, width / 2 - 100, 116,
		    200, 20));
	    getButtonList()
		    .add(new GuiButton(0, width / 2 - 100, height - 58,
			    "Add Friend"));
	    getButtonList().add(
		    new GuiButton(1, width / 2 - 100, height - 36, "Cancel"));
	}

	@Override
	public void onGuiClosed() {
	    Keyboard.enableRepeatEvents(false);
	}

	@Override
	public void updateScreen() {
	    getNameField().updateCursorCounter();
	    getAliasField().updateCursorCounter();
	}

	@Override
	protected void actionPerformed(final GuiButton button) {
	    switch (button.id) {
	    case 0:
		if (!getNameField().getText().isEmpty()
			&& !getAliasField().getText().isEmpty()
			&& getNameField().getText().indexOf(' ') < 0) {
		    Friends friend = (Friends) Minecraft.getMinecraft().dp
			    .getMods().getMod("Friends");
		    friend.getLoadedFriends().add(
			    new ArrayHelper(getNameField().getText(),
				    getAliasField().getText()));
		    friend.saveFriends();
		    mc.displayGuiScreen(getParentScreen());
		}
		break;
	    case 1:
		mc.displayGuiScreen(getParentScreen());
		break;
	    }
	}

	@Override
	public void drawScreen(final int x, final int y, final float ticks) {
	    drawBackground(0);
	    getNameField().drawTextBox();
	    getAliasField().drawTextBox();
	    drawCenteredString(fontRenderer, "Add a Friend", width / 2, 10,
		    0xFFFFFFFF);
	    drawString(fontRenderer, "Name:", width / 2 - 100, 63, 0xFFFFFFFF);
	    drawString(fontRenderer, "Alias:", width / 2 - 100, 104, 0xFFFFFFFF);
	    super.drawScreen(x, y, ticks);
	}

	@Override
	protected void mouseClicked(final int x, final int y, final int type) {
	    super.mouseClicked(x, y, type);

	    getNameField().mouseClicked(x, y, type);
	    getAliasField().mouseClicked(x, y, type);
	}

	@Override
	protected void keyTyped(final char keyChar, final int keyID) {
	    getNameField().textboxKeyTyped(keyChar, keyID);
	    getAliasField().textboxKeyTyped(keyChar, keyID);

	    if (keyChar == '\t')
		if (getNameField().isFocused()) {
		    getNameField().setFocused(false);
		    getAliasField().setFocused(true);
		} else {
		    getNameField().setFocused(true);
		    getAliasField().setFocused(false);
		}
	}

	public GuiTextField getNameField() {
	    return nameField;
	}

	public void setNameField(final GuiTextField nameField) {
	    this.nameField = nameField;
	}

	public GuiTextField getAliasField() {
	    return aliasField;
	}

	public void setAliasField(final GuiTextField aliasField) {
	    this.aliasField = aliasField;
	}

	public GuiScreen getParentScreen() {
	    return parentScreen;
	}

	public void setParentScreen(final GuiScreen parentScreen) {
	    this.parentScreen = parentScreen;
	}
    }
}