package se.proxus.utils;

public class ChatLine
{
    /** The chat message. */
    public String message;

    /** Counts the number of screen updates. */
    public int updateCounter;

    public ChatLine(String par1Str)
    {
        message = par1Str;
        updateCounter = 0;
    }
}
