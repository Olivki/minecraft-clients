package se.proxus.kanon.config.handler;

import lombok.Getter;
import lombok.Setter;
import org.xml.sax.SAXException;
import se.proxus.kanon.config.Configuration;
import se.proxus.kanon.config.Entry;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public final class Handler {

    @Getter @Setter private Enum state = State.NOT_STARTED;
    @Getter private final Configuration config;
    @Getter private final File file;
    private final SaveHandler saveHandler;
    private final LoadHandler loadHandler;

    public Handler(final Configuration config, final File file) {
        this.config = config;
        this.file = file;
        this.saveHandler = new SaveHandler(this);
        this.loadHandler = new LoadHandler(this);
        setState(State.START);
    }

    public final void save() {
        try {
            saveHandler.perform();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void load() {
        if (!file.exists()) {
            save();
            return;
        }

        try {
            loadHandler.perform();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public final void notify(final Entry updatedEntry) {
        if (shouldAutoSave()) {
            save();
        }
    }

    private boolean shouldAutoSave() {
        return state.equals(State.STANDBY);
    }

    public enum State {
        NOT_STARTED, START, POPULATING, STANDBY, READING
    }
}
