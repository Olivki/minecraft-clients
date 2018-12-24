package se.proxus.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import se.proxus.*;

public class Key extends GodlikeCraft {
	
	public String MD5(String md5) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();

			for(int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
			}

			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {}
		return null;
	}

	public String getHWID() {
		String HWID = System.getProperty("java.version", "UNKNOWN" + System.getProperty("java.version", "UNKNOWN") + System.getProperty("os.version", "UNKNOWN") + System.getProperty("user.name", "UNKNOWN") + System.getProperty("user.home") + System.getProperty("user.dir"));
		return MD5(HWID);
	}
	
	public String getKey() {
		return "Prox_" + crypt.encryptText(System.getProperty("user.name")) + MD5(getHWID());
	}
}