package se.proxus.kanon.modules.list.world;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;
import se.proxus.kanon.commands.Command;
import se.proxus.kanon.commands.ConfigCommand;
import se.proxus.kanon.config.Entry;
import se.proxus.kanon.event4j.EventSubscribe;
import se.proxus.kanon.event4j.events.player.EventPlayerBlockClicked;
import se.proxus.kanon.event4j.events.player.EventPlayerUpdate;
import se.proxus.kanon.event4j.events.render.EventRender3D;
import se.proxus.kanon.modules.Module;
import se.proxus.kanon.modules.ModuleSignature;
import se.proxus.kanon.utils.lang.Stringz;
import se.proxus.kanon.utils.minecraft.client.Colourz;
import se.proxus.kanon.utils.minecraft.world.Blockz;
import se.proxus.kanon.utils.templates.BlockTemplate;
import se.proxus.kanon.utils.templates.Location;
import se.proxus.kanon.utils.system.Timer;
import se.proxus.kanon.wrapper.minecraft.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ModuleSignature(author = "Oliver Berg", date = "2018/09/10 (20:41)")
public final class ModuleNuker extends Module {

    private final Timer timer = new Timer(400L);
    
    public ModuleNuker() {
        super("Nuker",
              "Y",
              "Automagically destroys blocks around you.",
              Controller.TOGGLE,
              Category.WORLD);
    }

    @Override
    public void initialize() {
        for (final Mode mode : Mode.values()) {
            mode.getNuker().setModule(this);
        }
    
        getConfig().addEntry("Mode", Mode.SINGLE, true)
                .setDescription("What mode the nuker currently is in.");
    
        getConfig().addEntry("Range", 4.0D, true)
                .setDescription("The range of the nuker.")
                .setRange(1.0D, 16.0D);
    
        getConfig().addEntry("Target", "AIR", true)
                .setDescription("The current target block, for the SINGLE mode.")
                .setCommandMutable(false);
    
        getConfig().addEntry("Whitelist", new ArrayList<String>(Block.REGISTRY.getKeys().size()), true)
                .setDescription("A events of all the blocks that the nuker will \"update\".")
                .setGeneric(String.class)
                .setCommandMutable(false);
    
        getConfig().addEntry("Blacklist", new ArrayList<String>(Block.REGISTRY.getKeys().size()), true)
                .setDescription("A events of all the blocks that the nuker will ignore when in creative mode.")
                .setGeneric(String.class)
                .setCommandMutable(false);
    }
    
    @Override
    public void onToggle() {
        getMode().getNuker().setCurrent(null);
    }
    
    @Override
    public void registerCommands() {
        registerGetter(getConfigCommand(), getName(), Mode.MULTIPLE.getNuker().getEntry());
        registerGetter(getConfigCommand(), getName(), Mode.CREATIVE.getNuker().getEntry());
    }
    
    @EventSubscribe
    public final void onRender3D(final EventRender3D event) {
        try {
            getMode().getNuker().render(event);
        } catch (final NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @EventSubscribe
    public final void onBlockClicked(final EventPlayerBlockClicked event) {
        if (timer.pass()) {
            getMode().getNuker().interact(event);
            
            timer.update();
        }
    }
    
    @EventSubscribe
    public void onPlayerUpdate(final EventPlayerUpdate event) {
        getMode().getNuker().preUpdate();
    }
    
    private void registerGetter(final ConfigCommand parent, final String name, final Entry entry) {
        final String entryName = entry.getKey().toLowerCase().replace(" ", "");
        
        parent.registerArgument(new Command.Argument(parent, entryName, "get") {
            @Override
            public void onCommand(final String input, final String[] args) {
                if (args.length > 0)
                    return;
                
                final List<String> list = entry.getList(String.class);
    
                printf("%s[%s%s%s]: [%s]",
                       getName().replace(" ", ""),
                       Colourz.CLIENT_COLOUR,
                       entry.getKey(),
                       Colourz.GREY,
                       Stringz.listToStringFancy(list, ", "));
            }
        });
    }
    
    private Mode getMode() {
        return getConfig().getEnum("Mode", Mode.class);
    }
    
    public enum Mode {
        SINGLE(new Singular()),
        MULTIPLE(new Multiple()),
        CREATIVE(new Creative());
    
        @Getter private final Nuker nuker;
    
        Mode(final Nuker nuker) {
            this.nuker = nuker;
        }
    }
    
    private static abstract class Nuker {
        @Getter @Setter private ModuleNuker module;
        @Getter @Setter private BlockTemplate current;
        @Getter @Setter private Iterable<BlockPos> blocks;
        abstract protected void interact(final EventPlayerBlockClicked event);
        abstract protected void update();
        abstract protected void render(final EventRender3D event) throws NoSuchFieldException, IllegalAccessException;
        abstract protected void collect();
        abstract protected Entry getEntry();
        
        protected final double getRange() {
            return module.getConfig().getDouble("Range");
        }
    
        void addTo(final Block block, final Entry entry) {
            final List<String> list = entry.getList(String.class);
            final String storeName = block.getUnlocalizedName();
            final String printName = block.getLocalizedName();
        
            if (!list.contains(storeName)) {
                list.add(storeName);
                module.printcf("Block[%s%s#%s%s] contains been added to %s[%s].",
                               printName,
                               Colourz.GREY,
                               Colourz.CLIENT_COLOUR,
                               Block.getIdFromBlock(block),
                               module.getName().replace(" ", ""),
                               entry.getKey().replace(" ", ""));
                module.getConfig().getHandler().notify(entry);
            } else {
                removeFrom(block, entry);
            }
        }
    
        private void removeFrom(final Block block, final Entry entry) {
            final List<String> list = entry.getList(String.class);
            final String storeName = block.getUnlocalizedName();
            final String printName = block.getLocalizedName();
        
            if (list.contains(storeName)) {
                list.remove(storeName);
                module.printcf("Block[%s%s#%s%s] contains been removed from %s[%s].",
                               printName,
                               Colourz.GREY,
                               Colourz.CLIENT_COLOUR,
                               Block.getIdFromBlock(block),
                               module.getName().replace(" ", ""),
                               entry.getKey().replace(" ", ""));
                module.getConfig().getHandler().notify(entry);
            }
        }
        
        final void renderCurrentBreakingAnimation(final EventRender3D event)
        throws NoSuchFieldException, IllegalAccessException {
            if (getCurrent().isAir())
                return;
            
            final double damageDone = (double) Player.getCurrentBlockDamage();
            final double percent = (damageDone / 2);
            final Location renderLocation = event.getLocation(getCurrent().getLocation());
            final double x = renderLocation.getX();
            final double y = renderLocation.getY();
            final double z = renderLocation.getZ();
    
            GL11.glPushMatrix();
            event.renderFilledBox((x + percent), (y + percent), (z + percent),
                                  ((x + 1.0D) - percent), ((y + 1.0D) - percent), ((z + 1.0D) - percent),
                                  0x40055555);
            event.renderBoxOutline((x + percent), (y + percent), (z + percent),
                                   ((x + 1.0D) - percent), ((y + 1.0D) - percent), ((z + 1.0D) - percent),
                                    1.5F, 0xFFFFFFFF);
            GL11.glPopMatrix();
        }
    
        final void renderCurrent(final EventRender3D event) {
            if (getCurrent().isAir())
                return;
            
            final Location renderLocation = event.getLocation(getCurrent().getLocation());
            final double x = renderLocation.getX();
            final double y = renderLocation.getY();
            final double z = renderLocation.getZ();
        
            GL11.glPushMatrix();
            event.renderFilledBox(x, y, z, (x + 1.0D), (y + 1.0D), (z + 1.0D), 0x40F06261);
            event.renderBoxOutline(x, y, z, (x + 1.0D), (y + 1.0D), (z + 1.0D), 1.5F, 0xFFFFFFFF);
            GL11.glPopMatrix();
        }
    
        final void renderOutline(final EventRender3D event, final BlockPos pos) {
            final BlockTemplate template = new BlockTemplate(pos);
    
            if (!template.isVisible())
                return;
    
            if (template.equals(getCurrent()))
                return;
    
            final Location renderLocation = event.getLocation(template.getLocation());
            final double x = renderLocation.getX();
            final double y = renderLocation.getY();
            final double z = renderLocation.getZ();
    
            GL11.glPushMatrix();
            event.renderFilledBox(x, y, z, (x + 1.0D), (y + 1.0D), (z + 1.0D), 0x40055555);
            event.renderBoxOutline(x, y, z, (x + 1.0D), (y + 1.0D), (z + 1.0D), 1.5F, 0xFFFFFFFF);
            GL11.glPopMatrix();
        }
        
        protected final void preUpdate() {
            if (!Objects.isNull(getCurrent()))
                if (getCurrent().getLocation().distanceToPlayer() > getRange())
                    getCurrent().setDone(true);
                
            update();
        }
    }
    
    private static class Singular extends Nuker {
    
        @Override
        protected void interact(final EventPlayerBlockClicked event) {
            final Block block = event.getBlock();
            
            final boolean unique = getModule().getConfig().setValue("Target", block.getUnlocalizedName());
    
            if (unique) {
                getModule().printcf("%s[Target] contains been set to [%s%s#%s%s].",
                                    getModule().getName().replace(" ", ""),
                                    block.getLocalizedName(),
                                    Colourz.GREY,
                                    Colourz.CLIENT_COLOUR,
                                    Block.getIdFromBlock(block));
            } else {
                getModule().getConfig().setValue("Target", "AIR");
                getModule().printcf("%s[Target] contains been nulled.",
                                    getModule().getName().replace(" ", ""));
            }
        }
    
        @Override
        protected void update() {
            if (Objects.isNull(getCurrent()) || getCurrent().isDone())
                collect();
            
            if (Objects.isNull(getCurrent()))
                return;
    
            getCurrent().breakBlock(BlockTemplate.Type.METHOD);
    
            if (getCurrent().isAir())
                getCurrent().setDone(true);
        }
        
        @Override
        protected void render(final EventRender3D event)
        throws NoSuchFieldException, IllegalAccessException {
            if (Objects.isNull(getCurrent()))
                return;
            
            renderCurrentBreakingAnimation(event);
            
            for (final BlockPos block : getBlocks()) {
                renderOutline(event, block);
            }
        }
    
        @Override
        protected void collect() {
            setBlocks(Blockz.getValidBlocksByDistance(getRange(), false, getEntry().getValue(String.class)));
            
            for(final BlockPos pos : getBlocks()) {
                final BlockTemplate template = new BlockTemplate(pos);
        
                if (!template.isVisible())
                    continue;
        
                setCurrent(template);
                getCurrent().setDone(false);
                break;
            }
        }
    
        @Override
        protected Entry getEntry() {
            return getModule().getConfig().getEntry("Target");
        }
    }
    
    private static class Multiple extends Nuker {
    
        @Override
        protected void interact(final EventPlayerBlockClicked event) {
            addTo(event.getBlock(), getEntry());
        }
    
        @Override
        protected void update() {
            if (Objects.isNull(getCurrent()) || getCurrent().isDone())
                collect();
    
            if (Objects.isNull(getCurrent()))
                return;
            
            getCurrent().breakBlock(BlockTemplate.Type.METHOD);
        
            if (getCurrent().isAir())
                getCurrent().setDone(true);
        }
    
        @Override
        protected void render(final EventRender3D event)
        throws NoSuchFieldException, IllegalAccessException {
            if (Objects.isNull(getCurrent()))
                return;
        
            renderCurrentBreakingAnimation(event);
        
            for (final BlockPos pos : getBlocks()) {
                renderOutline(event, pos);
            }
        }
    
        @Override
        protected void collect() {
            if (getEntry().isEmpty())
                return;
            
            setBlocks(Blockz.getValidBlocksByDistance(getRange(), false,
                                                      getEntry().getList(String.class).toArray(new String[0])));
        
            for(final BlockPos pos : getBlocks()) {
                final BlockTemplate template = new BlockTemplate(pos);
            
                if (!template.isVisible())
                    continue;
            
                setCurrent(template);
                getCurrent().setDone(false);
                break;
            }
        }
    
        @Override
        protected Entry getEntry() {
            return getModule().getConfig().getEntry("Whitelist");
        }
    }
    
    private static class Creative extends Nuker {
    
        @Override
        protected void interact(final EventPlayerBlockClicked event) {
            addTo(event.getBlock(), getEntry());
        }
    
        @Override
        protected void update() {
            collect();
            
            for (final BlockPos pos : getBlocks()) {
                final BlockTemplate template = new BlockTemplate(pos);
                
                template.breakBlock(BlockTemplate.Type.PACKET);
            }
        }
    
        @Override
        protected void render(final EventRender3D event) {
            if (Objects.isNull(getCurrent()))
                return;
        
            renderCurrent(event);
        
            for (final BlockPos block : getBlocks()) {
                renderOutline(event, block);
            }
        }
    
        @Override
        protected void collect() {
            setBlocks(Blockz.getFilteredBlocksByDistance(getRange(), false,
                                                         getEntry().getList(String.class).toArray(new String[0])));
    
            for(final BlockPos pos : getBlocks()) {
                final BlockTemplate template = new BlockTemplate(pos);
        
                if (!template.isVisible())
                    continue;
        
                setCurrent(template);
                getCurrent().setDone(false);
                break;
            }
        }
    
        @Override
        protected Entry getEntry() {
            return getModule().getConfig().getEntry("Blacklist");
        }
    }
}