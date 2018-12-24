package se.proxus.kanon;

public final class KanonException extends Exception {
    
    public KanonException () { }
    
    public KanonException (String message) {
        super (message);
    }
    
    public KanonException (Throwable cause) {
        super (cause);
    }
    
    public KanonException (String message, Throwable cause) {
        super (message, cause);
    }
}
