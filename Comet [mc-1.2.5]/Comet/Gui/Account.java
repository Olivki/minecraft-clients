package Comet.Gui;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import net.minecraft.src.*;

public class Account extends GuiScreen
{
	private GuiScreen parentScreen;
	private GuiTextField usernameTextField;
	private PasswordField passwordTextField;
	private String error = null;
	private String Username;
	private String Password;
	private String status = "Null";

	private String toolTip;
	private int mouseX;
	private int mouseY;

	public Account(GuiScreen guiscreen)
	{
		parentScreen = guiscreen;
		toolTip = null;
	}

	public void updateScreen()
	{
		usernameTextField.updateCursorCounter();
		passwordTextField.updateCursorCounter();
	}
	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}

	protected void actionPerformed(GuiButton guibutton)
	{
		if (!guibutton.enabled)
		{
			return;
		}

		if (guibutton.id == 2)
		{
			usernameTextField.setText("");
			passwordTextField.setText("");
			status = "\2476Waiting...";
		}

		if (guibutton.id == 1)
		{
			mc.displayGuiScreen(parentScreen);
		}
		else if (guibutton.id == 0)
		{
			if (passwordTextField.getText().length() > 0)
			{
				Username = usernameTextField.getText();
				Password = passwordTextField.getText();

				try
				{
					String s2 = (new StringBuilder("user=")).append(URLEncoder.encode(Username, "UTF-8")).append("&password=").append(URLEncoder.encode(Password, "UTF-8")).append("&version=").append(13).toString();
					String s3 = excutePost("https://login.minecraft.net/", s2);

					if (s3 == null || !s3.contains(":"))
					{
						//status = "\247cLogin Failed: " + s3.trim() + "!";
						error = new StringBuilder().append(s3.trim()).append("!").toString();
						return;
					}

					String as[] = s3.split(":");
					mc.session = new Session(as[2].trim(), as[3].trim());
				}
				catch (Exception exception)
				{
					exception.printStackTrace();
				}
			}
			else
			{
				mc.session = new Session(usernameTextField.getText(), "");
			}
		}
	}

	protected void keyTyped(char c, int i)
	{
		usernameTextField.func_50037_a(c, i);
		passwordTextField.textboxKeyTyped(c, i);

		if (c == '\t')
		{
			if (usernameTextField.isFocused)
			{
				usernameTextField.isFocused = false;
				passwordTextField.isFocused = true;
			}
			else
			{
				usernameTextField.isFocused = false;
				passwordTextField.isFocused = true;
			}
		}

		if (c == '\r')
		{
			actionPerformed((GuiButton)controlList.get(0));
		}
	}
	protected void mouseClicked(int i, int j, int k)
	{
		super.mouseClicked(i, j, k);
		usernameTextField.mouseClicked(i, j, k);
		passwordTextField.mouseClicked(i, j, k);
	}

	public static String excutePost(String s, String s1)
	{
		HttpsURLConnection httpsurlconnection = null;

		try
		{
			try
			{
				URL url = new URL(s);
				httpsurlconnection = (HttpsURLConnection)url.openConnection();
				httpsurlconnection.setRequestMethod("POST");
				httpsurlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				httpsurlconnection.setRequestProperty("Content-Type", Integer.toString(s1.getBytes().length));
				httpsurlconnection.setRequestProperty("Content-Language", "en-US");
				httpsurlconnection.setUseCaches(false);
				httpsurlconnection.setDoInput(true);
				httpsurlconnection.setDoOutput(true);
				httpsurlconnection.connect();
				DataOutputStream dataoutputstream = new DataOutputStream(httpsurlconnection.getOutputStream());
				dataoutputstream.writeBytes(s1);
				dataoutputstream.flush();
				dataoutputstream.close();
				InputStream inputstream = httpsurlconnection.getInputStream();
				BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(inputstream));
				StringBuffer stringbuffer = new StringBuffer();
				String s2;

				while ((s2 = bufferedreader.readLine()) != null)
				{
					stringbuffer.append(s2);
					stringbuffer.append('\r');
				}

				bufferedreader.close();
				String s3 = stringbuffer.toString();
				String s4 = s3;
				return s3;
			}
			catch (Exception exception)
			{
				exception.printStackTrace();
			}

			return null;
		}
		finally
		{
			if (httpsurlconnection != null)
			{
				httpsurlconnection.disconnect();
			}
		}
	}

	public void initGui()
	{
		Keyboard.enableRepeatEvents(true);
		controlList.clear();
		controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96, 98, 20, "Done"));
		controlList.add(new GuiButton(2, width / 2 + 2, height / 4 + 96, 98, 20, "Clear"));
		controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120, 200, 20, "Back"));
		usernameTextField = new GuiTextField(fontRenderer, width / 2 - 99, height / 4 + 40, 196, 20);
		passwordTextField = new PasswordField(this, fontRenderer, width / 2 - 99, height / 4 + 66, 196, 20, "");
	}

	public void drawScreen(int i, int j, float f)
	{
		drawDefaultBackground();

		if (!(mc.session.username.toString().startsWith("Player")))
		{
			if (error == null)
			{
				status = "\2472Logged in as " + mc.session.username;
			}
			else
			{
				status = "\247cFailed to Login: " + error;
			}
		}

		drawString(fontRenderer, "\247fStatus: " + status, 2, 2, 0xffffff);
		drawCenteredString(fontRenderer, "Login to Minecraft", width / 2, (height / 4 - 60) + 20, 0xffffff);
		usernameTextField.drawTextBox();
		passwordTextField.drawTextBox();
		super.drawScreen(i, j, f);

		if (toolTip != null)
		{
			drawTooltip(toolTip, i, j);
		}
	}

	protected void drawTooltip(String par1Str, int par2, int par3)
	{
		if (par1Str == null)
		{
			return;
		}
		else
		{
			int i = par2 + 12;
			int j = par3 - 12;
			int k = fontRenderer.getStringWidth(par1Str);
			drawGradientRect(i - 3, j - 3, i + k + 3, j + 8 + 3, 0xc0000000, 0xc0000000);
			fontRenderer.drawStringWithShadow(par1Str, i, j, -1);
			return;
		}
	}
}