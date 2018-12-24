package bt.Olivki.Biti.Gui;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;

import org.lwjgl.input.Keyboard;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.security.PublicKey;
import java.security.cert.Certificate;
import javax.net.ssl.HttpsURLConnection;
import java.net.URLEncoder;
import java.util.*;

// Referenced classes of package net.minecraft.src:
//            GuiScreen, GuiTextField, StringTranslate, GuiButton, 
//            GameSettings, GuiConnecting

public class Account extends GuiScreen
{
	
	private String state="N/A";
	private int color=0xffffff;
	
	private int colGreen=0x37F216;
	private int colRed=0xCF0A0A;

    public Account(GuiScreen guiscreen)
    {
        parentScreen = guiscreen;
    }

    public void updateScreen()
    {
    	fieldNick.updateCursorCounter();
    	fieldPass.updateCursorCounter();
    }

    public void initGui()
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents(true);
        controlList.clear();
        controlList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12, "Change"));
        controlList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, "Back"));
        String s = mc.session.username;//mc.gameSettings.lastServer.replaceAll("_", ":");
        ((GuiButton)controlList.get(0)).enabled = s.length() > 0;
        
        fieldNick = new GuiTextField(fontRenderer, width / 2 - 100, (height / 4 - 10) + 20, 200, 20);
        fieldPass = new PasswordField(fontRenderer, width / 2 - 100, (height / 4 - 10) + 50 + 12, 200, 20);
        
        fieldNick.setMaxStringLength(128);
        fieldNick.setText(mc.session.username);
        
        fieldPass.setMaxStringLength(128);
    }

    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton guibutton)
    {
        if(!guibutton.enabled)
        {
            return;
        }
        if(guibutton.id == 1)
        {
            mc.displayGuiScreen(parentScreen);
        } else
        if(guibutton.id == 0)
        {
        	if (fieldPass.getText().length() > 0)
        	{
        		login(fieldNick.getText(),fieldPass.getText());
        		fieldPass.setText("");
        	}
        	else
        	{
            String s = fieldNick.getText().trim();
            s=s.replaceAll("&", "\247");
            mc.session.username=s;
            mc.session.sessionId="-";
            state = "Changed nick to "+s+".";
        	}
        }
    }

    protected void keyTyped(char c, int i)
    {
    	fieldNick.textboxKeyTyped(c, i);
        if(c == '\r')
        {
        	
            actionPerformed((GuiButton)controlList.get(0));
        }
        ((GuiButton)controlList.get(0)).enabled = fieldNick.getText().length() > 0;
        fieldPass.func_50037_a(c, i);
    }
    
    public String excutePost(String targetURL, String username, String password)
    {
    	try {
    	    // Construct data
    	    String data = URLEncoder.encode("user", "UTF-8") +     "=" + URLEncoder.encode(username, "UTF-8");
    	    data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
    	    data += "&" + URLEncoder.encode("version", "UTF-8") +  "=" + URLEncoder.encode("13", "UTF-8");
                
            // Send the request
            URL url = new URL("https://login.minecraft.net");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                
            //write parameters
            writer.write(data);
            writer.flush();
                
            // Get the response
            StringBuffer answer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                answer.append(line);
            }
            writer.close();
            reader.close();
                
            //Output the response
            return answer.toString();
            
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
                return null;
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
    }

	public void login(String username, String password)
    {
        String result;
        result =excutePost("https://login.minecraft.net/", username, password);
        System.out.println(result);
        if(result == null)
        {
            state="Can't connect to minecraft.net";
            return;
        }
        if(!result.contains(":"))
        {
            if(result.trim().equals("Bad login"))
                state="Login failed";
            else
            if(result.trim().equals("Old version"))
            {
            	state="Outdated launcher";
            } else
            {
            	state=result;
            }
            return;
        }
        try
        {
            String values[] = result.split(":");
            mc.session.username=values[2].trim();
        	mc.session.sessionId=values[3].trim();
        	state = "Logged in as "+mc.session.username+".";
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return;
    }

    protected void mouseClicked(int i, int j, int k)
    {
        super.mouseClicked(i, j, k);
        fieldNick.mouseClicked(i, j, k);
        fieldPass.mouseClicked(i, j, k);
    }

    public void drawScreen(int i, int j, float f)
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        drawDefaultBackground();
        //drawCenteredString(fontRenderer, stringtranslate.translateKey("multiplayer.title"), width / 2, (height / 4 - 60) + 20, 0xffffff);
        fontRenderer.drawStringWithShadow("Username:", width / 2 - 100, (height / 4 - 10)+10, 0xffffff);
        fieldNick.drawTextBox();
        fontRenderer.drawStringWithShadow("Password:", width / 2 - 100, (height / 4 - 10)+52, 0xffffff);
        fieldPass.drawTextBox();
        
        boolean status=false;
        if (mc.session.sessionId!="-") {status=true;}
        String Prem;
        if (status) {Prem="Connected as a premium user.";} else {Prem="Connected as an offline user.";}
        {fontRenderer.drawStringWithShadow(Prem, width / 2 - 100, (height / 4 - 10)+92+12, 0xffffff);}
        fontRenderer.drawStringWithShadow("Status: " + state, width / 2 - 100, (height / 4 - 10)+92, 0xffffff);
        super.drawScreen(i, j, f);
    }

    private GuiScreen parentScreen;
    private GuiTextField field_22111_h;
    private GuiTextField fieldNick;
    private PasswordField fieldPass;
}

