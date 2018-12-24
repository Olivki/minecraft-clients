package se.proxus.DreamPvP.Utils;

public class ChatLineOld {
	
    /** The chat message. */
    public String message, name;

    /** Counts the number of screen updates. */
    public int updateCounter;

    public ChatLineOld(String par1Str, String name) {
        message = par1Str;
        this.name = name;
        updateCounter = 0;
    }
}