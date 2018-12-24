package se.proxus.kanon.commands;

import se.proxus.kanon.utils.minecraft.client.Colourz;

import javax.annotation.Nullable;

@CommandSignature(author = "Oliver Berg", date = "2018/09/10 (17:08)")
public abstract class IterableListCommand extends Command {

    public IterableListCommand(final String name, @Nullable final String... aliases) {
        super(name, aliases);
    }

    @Override
    public void initialize() {
        registerArgument(new Argument(this) {
            @Override
            public void onCommand(final String input, final String[] args) {
                if (args.length > 0)
                    return;
    
                populateEntries();
                displayPage(1);
            }
        });

        registerArgument(new Argument(this) {
            @Override
            public void onCommand(final String input, final String[] args) {
                if (args.length <= 0)
                    return;

                if (!isInteger(args[0], true))
                    return;
    
                populateEntries();
                displayPage(Integer.parseInt(args[0]));
            }
        });
    }

    protected final void displayPage(final int page) {
        populateEntries();
        final int entriesPerPage = getEntriesPerPage();
        final String[] entries = getEntries();
        final int pages = getMaxPages();

        if (!isInRange(page, 1, pages)) {
            return;
        }

        if (pages > 1) {
            CommandUtils.printPageHeadLine(this, getListName(), page, pages);
        } else {
            CommandUtils.printHeadLine(this, getListName());
        }

        for (int i = 0; i < entriesPerPage; i++) {
            if (i + (page - 1) * entriesPerPage + 1 > entries.length) {
                break;
            }

            printf("%s%s",
                   Colourz.CLIENT_COLOUR,
                   entries[i + (page - 1) * entriesPerPage]);
        }
    }

    public int getMaxPages() {
        final int entriesLength = getEntries().length;
        return entriesLength / getEntriesPerPage() + (entriesLength % getEntriesPerPage() > 0 ? 1 : 0);
    }

    public abstract String getListName();

    public abstract String[] getEntries();

    public abstract int getEntriesPerPage();

    public abstract void populateEntries();

}