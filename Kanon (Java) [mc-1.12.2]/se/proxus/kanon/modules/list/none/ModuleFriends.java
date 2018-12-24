package se.proxus.kanon.modules.list.none;

import se.proxus.kanon.modules.Module;

import java.util.*;

public final class ModuleFriends extends Module {

    public ModuleFriends() {
        super("Friends",
              "NONE",
              "Handles everything related to the friend system.",
              Controller.MONITOR);
    }

    @Override
    public void initialize() {
        getConfig().addEntry("Aliases", true)
                    .setDescription("Whether or not to replace the friends name with the set alias.");
        
        getConfig().addEntry("RGBA", new Short[]{ 255, 255, 255, 255 })
                    .setDescription("The colour that all friend related renders will use.")
                    .setRange(0, 255)
                    .setGeneric(Short.class);
        
        final List<String> testList =
                getConfig().addEntry("Test List", new ArrayList<String>())
                            .setDescription("My dick lol")
                            .setGeneric(String.class)
                            .getList(String.class);
    
        testList.add("Dab lol xD");
        testList.add("Daruda my DABAD XD");
        testList.add("dab");
        
        final Map<String, String> testMap =
                getConfig().addEntry("Test Map", new LinkedHashMap<String, String>())
                            .setDescription("Daruda sucks dick xD")
                            .setGeneric(String.class, String.class)
                            .getMap(String.class, String.class);
    
        testMap.put("daruda", "bad");
        testMap.put("darudax", "baruda");
        testMap.put("daruda2", "badruda");
        
        final Map<Integer, Boolean> testMap2 =
                getConfig().addEntry("Test Map 2", new TreeMap<Integer, Boolean>())
                            .setDescription("Daruda sucks dicker xD")
                            .setGeneric(Integer.class, Boolean.class)
                            .getMap(Integer.class, Boolean.class);
    
        testMap2.put(5, true);
        testMap2.put(16, false);
        testMap2.put(7, true);
        
    }
}