package se.proxus.kanon.utils.system;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public final class Timer {
    
    private final long threshold;
    private long lastPoll = 0;
    
    public final void update() {
        lastPoll = Timerz.getCurrentMilliseconds();
    }
    
    public final boolean pass() {
        return (Timerz.getCurrentMilliseconds() - lastPoll) >= threshold;
    }
    
}
