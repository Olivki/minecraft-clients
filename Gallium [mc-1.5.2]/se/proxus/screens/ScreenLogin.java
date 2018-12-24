package se.proxus.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;

import org.lwjgl.input.Keyboard;

/** Ghetto ass login screen. **/
public class ScreenLogin extends GuiScreen {

    protected GuiTextField usernameField;
    protected GuiTextField passwordField;
    protected GuiScreen parentScreen;

    public ScreenLogin(final GuiScreen parentScreen) {
	setParentScreen(parentScreen);
    }

    @Override
    public void initGui() {
	Keyboard.enableRepeatEvents(true);

	setUsernameField(new GuiTextField(fontRenderer, width / 2 - 100, 76,
		200, 20));
	setPasswordField(new GuiTextField(fontRenderer, width / 2 - 100, 116,
		200, 20));
	getUsernameField().setText(Minecraft.getMinecraft().session.username);

	getButtonList().add(
		new GuiButton(0, width / 2 - 100, height - 58, "Login"));
	getButtonList().add(
		new GuiButton(1, width / 2 - 100, height - 36, "Cancel"));
    }

    @Override
    public void onGuiClosed() {
	Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void updateScreen() {
	getUsernameField().updateCursorCounter();
	getPasswordField().updateCursorCounter();
    }

    @Override
    protected void actionPerformed(final GuiButton button) {
	switch (button.id) {
	case 0:
	    if (!getUsernameField().getText().isEmpty()
		    && !getPasswordField().getText().isEmpty()) {
		Minecraft.dp.loginToAccount(getUsernameField().getText(),
			getPasswordField().getText());
		mc.displayGuiScreen(getParentScreen());
	    } else if (getPasswordField().getText().isEmpty()) {
		Minecraft.getMinecraft().session.username = getUsernameField()
			.getText();
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

	getUsernameField().drawTextBox();
	getPasswordField().drawTextBox();

	drawCenteredString(fontRenderer, "Login Screen", width / 2, 10,
		0xFFFFFFFF);
	drawString(fontRenderer, "Username:", width / 2 - 100, 63, 0xFFFFFFFF);
	drawString(fontRenderer, "Password:", width / 2 - 100, 104, 0xFFFFFFFF);
	super.drawScreen(x, y, ticks);
    }

    @Override
    protected void mouseClicked(final int x, final int y, final int type) {
	super.mouseClicked(x, y, type);

	getUsernameField().mouseClicked(x, y, type);
	getPasswordField().mouseClicked(x, y, type);
    }

    @Override
    protected void keyTyped(final char keyChar, final int keyID) {
	getUsernameField().textboxKeyTyped(keyChar, keyID);
	getPasswordField().textboxKeyTyped(keyChar, keyID);

	if (keyChar == '\t')
	    if (getUsernameField().isFocused()) {
		getUsernameField().setFocused(false);
		getPasswordField().setFocused(true);
	    } else {
		getUsernameField().setFocused(true);
		getPasswordField().setFocused(false);
	    }
    }

    public GuiTextField getUsernameField() {
	return usernameField;
    }

    public void setUsernameField(final GuiTextField usernameField) {
	this.usernameField = usernameField;
    }

    public GuiTextField getPasswordField() {
	return passwordField;
    }

    public void setPasswordField(final GuiTextField passwordField) {
	this.passwordField = passwordField;
    }

    public GuiScreen getParentScreen() {
	return parentScreen;
    }

    public void setParentScreen(final GuiScreen parentScreen) {
	this.parentScreen = parentScreen;
    }
}