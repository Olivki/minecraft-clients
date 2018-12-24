package se.proxus.DreamPvP.Gui.Screens;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import se.proxus.DreamPvP.DreamPvP;
import net.minecraft.src.*;

public class AltList extends GuiScreen {

	private AltListSlot mSlot;
	public GuiScreen parentScreen;
	
	public boolean deletePressed = false;
	public String err = "N/A";
	
	public AltList(GuiScreen parentScreen) {
		this.parentScreen = parentScreen;
	}

	@Override
	public void initGui() {
		controlList.add(new GuiButton(0, width / 2 - 100, height - 48, 56, 20, "Add alt"));
		controlList.add(new GuiButton(1, width / 2 - 42, height - 48, 56, 20, "Delete alt"));
		controlList.add(new GuiButton(2, width / 2 + 16, height - 48, 84, 20, "Login"));
		controlList.add(new GuiButton(100, width / 2 - 100, height - 26, "Done."));

		mSlot = new AltListSlot(this);
		mSlot.registerScrollButtons(controlList, 7, 8);
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if(button.enabled) {
			if(button.id == 0) {
				DreamPvP.mc.displayGuiScreen(new AltAdd());
			} if(button.id == 1) {
				String s = mc.dp.settings.altArray.get(mSlot.getSelected()).name;

				if (s != null) {
					deletePressed = true;
					StringTranslate stringtranslate = StringTranslate.getInstance();
					String s1 = "Are you sure you want to delete this alt?";
					String s2 = "\"" + s + "\" will be lost forever! (A long time!)";
					String s3 = "Delete.";
					String s4 = "Cancel.";
					GuiYesNo guiyesno = new GuiYesNo(this, s1, s2, s3, s4, mSlot.getSelected());
					mc.displayGuiScreen(guiyesno);
					mc.dp.files.saveAlts();
				}
			} if(button.id == 2) {
                try {
                    String s2 = (new StringBuilder("user=")).append(URLEncoder.encode(mSlot.getName(), "UTF-8")).append("&password=").append(URLEncoder.encode(mSlot.getPass(), "UTF-8")).append("&version=").append(13).toString();
                    String s3 = excutePost("https://login.minecraft.net/", s2);
                    
                    if (s3 == null || !s3.contains(":")) {
                        err = new StringBuilder().append(s3.trim()).append("!").toString();
                        return;
                    }
                    
                    String as[] = s3.split(":");
                    mc.session = new Session(as[2].trim(), as[3].trim());
                    mc.dp.main.bot.changeNick(mc.session.username);
                } catch (Exception e) {
                	e.printStackTrace();
                }
			} if(button.id == 100) {
				DreamPvP.mc.displayGuiScreen(parentScreen);
				DreamPvP.files.saveAlts();
			} else {
				mSlot.actionPerformed(button);
			}
		}
	}

	@Override
	public void drawScreen(int I1, int I2, float I3) {
		mSlot.drawScreen(I1, I2, I3);

		drawCenteredString(fontRenderer, "Alt list. [§e" + mc.dp.settings.altArray.size() + "§r]", width / 2, 10, 0xFFFFFFFF);
		fontRenderer.drawStringWithShadow("Username : " + mc.session.username, 4, 4, 0xFFFFFFFF);
		fontRenderer.drawStringWithShadow(err, 4, 14, 0xFFFFFFFF);
		super.drawScreen(I1, I2, I3);
	}

	@Override
	public void confirmClicked(boolean flag, int i1) {
		if(deletePressed) {
			deletePressed = false;

			if(flag) {
				mc.dp.settings.altArray.remove(i1);
				DreamPvP.files.saveAlts();
			}

			mc.displayGuiScreen(this);
		}
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
		} finally {
			if (httpsurlconnection != null)
			{
				httpsurlconnection.disconnect();
			}
		}
	}
}