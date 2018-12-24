package se.proxus.kanon.commands;

import com.google.common.base.Enums;
import lombok.Getter;
import se.proxus.kanon.config.Configuration;
import se.proxus.kanon.config.Entry;
import se.proxus.kanon.config.handler.ConversionException;
import se.proxus.kanon.config.handler.ConversionHandler;
import se.proxus.kanon.utils.lang.Stringz;
import se.proxus.kanon.utils.math.Numberz;
import se.proxus.kanon.utils.minecraft.client.Colourz;

import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.substring;

@CommandSignature(author = "Oliver Berg", date = "2018/09/10 (17:10)")
public class ConfigCommand extends Command {

    @Getter private final Configuration config;

    public ConfigCommand(final Configuration config) {
        super(Stringz.toCamelCase(config.getName(),false ));

        this.config = config;
        initialize();
    }

    @Override
    public void initialize() {
        if (config == null)
            return;
    
        for (final Entry entry : config.getEntries()) {
            if (entry.isCommandMutable() && entry.isMutable()) {
                final String name = Stringz.toCamelCase(entry.getKey(), false);
                final Object value = entry.getValue();
                if (value instanceof Number) {
                    registerGet(config, entry, name);
                    registerNumbers(config, entry, name);
                } else if (value instanceof Boolean) {
                    registerGet(config, entry, name);
                    registerBooleans(config, entry, name);
                } else if (value instanceof String) {
                    registerGet(config, entry, name);
                    registerStrings(config, entry, name);
                } else if (value.getClass().isArray()) {
                    registerGet(config, entry, name);
                    registerArray(config, entry, name);
                } else if (value.getClass().isEnum()) {
                    registerGet(config, entry, name);
                    registerEnums(config, entry, name);
                }
            }
        }
    }

    @Override
    public String getDescription() {
        return "Handles all the settings contained within the " + config.getName() + " config.";
    }

    private void registerNumbers(final Configuration config, final Entry entry, final String name) {
        registerArgument(new Argument(this, name, "set") {
            @Override
            protected Argument initialize() {
                if (Numberz.isDecimalInstance(entry.getValue())) {
                    addCosmeticArgument(String.format("(%s-%s)",
                                                      (entry.getMin().doubleValue() == 0
                                                        ? "0"
                                                        : entry.getMin()),
                                                      entry.getMax().doubleValue()));
                } else {
                    addCosmeticArgument("(${entry.getMin()}-${entry.getMax()})");
                }

                return this;
            }

            @Override
            public void onCommand(final String input, final String[] args) {
                if (Numberz.isDecimalInstance(entry.getValue())) {
                    final float parsedValue = Float.parseFloat(args[0]);
                    if (isInRange(parsedValue, (float) entry.getMin(), (float) entry.getMax())) {
                        config.parse(entry, args[0]);
                        printValueUpdate(config, entry, args[0]);
                    }
                } else {
                    if (isInteger(args[0])) {
                        final int parsedIntegerValue = Integer.parseInt(args[0]);
                        if (isInRange(parsedIntegerValue, (int) entry.getMin(), (int) entry.getMax())) {
                            config.parse(entry, args[0]);
                            printValueUpdate(config, entry, args[0]);
                        }
                    }
                }
            }
        });
    }

    private void registerBooleans(final Configuration config, final Entry entry, final String name) {
        registerArgument(new Argument(this, name, "toggle") {
            @Override
            public void onCommand(final String input, final String[] args) {
                printValueUpdate(config, entry, config.toggle(entry));
            }
        });
    }

    private void registerStrings(final Configuration config, final Entry entry, final String name) {
        registerArgument(new Argument(this, name, "set") {
            @Override
            protected Argument initialize() {
                cosmeticArguments.add("{\"string\"}");

                return this;
            }

            @Override
            public void onCommand(final String input, final String[] args) {
                if (Objects.nonNull(entry.getRange())) {
                    if (isInRange(input.length(), entry)) {
                        config.setValue(entry.getKey(), input);
                        printValueUpdate(config, entry, input);
                    }
                } else {
                    config.setValue(entry.getKey(), input);
                    printValueUpdate(config, entry, input);
                }
            }
        });
    }
    
    private void registerArray(final Configuration config, final Entry entry, final String name) {
        registerArgument(new Argument(this, name, "set") {
            @Override
            protected Argument initialize() {
                final Class<?> cls = entry.getGenericType()[0];
                
                addCosmeticArgument(String.format("(0-%s)", entry.getArray().length));
                
                if (String.class.isAssignableFrom(cls)) {
                    addCosmeticArgument("\"text\"");
                } else if (Number.class.isAssignableFrom(cls)) {
                    if (Numberz.isDecimalClass(cls)) {
                        addCosmeticArgument(String.format("(%s-%s)",
                                                          (entry.getMin().doubleValue() == 0
                                                           ? "0"
                                                           : entry.getMin()),
                                                          entry.getMax()));
                    } else {
                        addCosmeticArgument(String.format("(%s-%s)",
                                                          (int) entry.getMin(),
                                                          (int) entry.getMax()));
                    }
                } else if (Boolean.class.isAssignableFrom(cls)) {
                    addCosmeticArgument("true/false");
                } else {
                    addCosmeticArgument("value");
                }
        
                return this;
            }
            
            @Override
            public void onCommand(final String input, final String[] args) {
                if (!isInteger(args[0], true))
                    return;
                
                final int index = Integer.parseInt(args[0]);
    
                if (isInRange(index, 0, entry.getArray().length)) {
                    try {
                        final Class<?> cls = entry.getGenericType()[0];
                        final Object[] array = entry.getArray();
                        final String parsedValue = args[1];
                        
                        if (String.class.isAssignableFrom(cls)) {
                            if (input.indexOf("\"") <= 0) {
                                error("The string needs to be enclosed in quotation marks.");
                                return;
                            }
    
                            if (input.lastIndexOf("\"") == input.indexOf("\"")) {
                                errorf("The string needs to be %senclosed%s in quotation marks.",
                                       Colourz.UNDERLINE + Colourz.BOLD,
                                       Colourz.RESET + Colourz.GREY);
                                return;
                            }
                            
                            final String value = substring(input,
                                                           input.indexOf("\"") + 1,
                                                           input.lastIndexOf("\""));
                            
                            if (Objects.nonNull(entry.getRange())) {
                                if (isInRange(value.length(), entry)) {
                                    array[index] = value;
                                    printArrayUpdate(config, entry, value, index);
                                }
                            } else {
                                array[index] = value;
                                printArrayUpdate(config, entry, value, index);
                            }
                        } else if (Number.class.isAssignableFrom(cls)) {
                            final Object value = ConversionHandler.to(cls, parsedValue);
                            
                            if (Numberz.isDecimalInstance(entry.getValue())) {
                                final double doubleValue = (double) value;
                                if (isInRange(doubleValue, entry)) {
                                    array[index] = value;
                                    printArrayUpdate(config, entry, value, index);
                                }
                            } else {
                                if (isInteger(args[0], true)) {
                                    final int integerValue = Numberz.toInteger(value);
                                    if (isInRange(integerValue, entry)) {
                                        array[index] = value;
                                        
                                        printArrayUpdate(config, entry, value, index);
                                    }
                                }
                            }
                        } else if (Boolean.class.isAssignableFrom(cls)) {
                            final Object value = ConversionHandler.to(cls, parsedValue);
                            array[index] = value;
                            
                            printArrayUpdate(config, entry, input, index);
                        } else {
                            throwSyntaxError();
                        }
                    } catch (final ConversionException e) {
                        throwSyntaxError();
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    
    private void registerEnums(final Configuration config, final Entry entry, final String name) {
        registerArgument(new Argument(this, name, "set") {
            /*@Override
            protected Argument initialize() {
                final Enum valueEnum = entry.getEnum();
                final Object[] values = valueEnum.getDeclaringClass().getEnumConstants();
            
                addCosmeticArgument(String.format("(0-%s)", values.length));
            
                return this;
            }*/
        
            @Override
            public void onCommand(final String input, final String[] args) {
                if (!isInteger(args[0], true, true))
                    return;
                
                final Enum valueEnum = config.getEnum(entry);
                final Class<?> enumClass = valueEnum.getDeclaringClass();
                final Object[] values = enumClass.getEnumConstants();
                final int index = Integer.parseInt(args[0]);
                
                if (!isInRange(index, 0, values.length))
                    return;
                
                final Enum parsedEnum = (Enum) ConversionHandler.convertToEnum(valueEnum.getDeclaringClass(), index);
                
                if (parsedEnum == null) {
                    errorf("%s[%s] does not exist, please enter a valid name or index.",
                           entry.getKey().replace(" ", ""),
                           index);
                    return;
                }
                
                final String enumName = parsedEnum.name().toUpperCase();
    
                config.setValue(entry, parsedEnum);
                printValueUpdate(config, entry, enumName);
            }
        });
        registerArgument(new Argument(this, name, "set") {
            @Override
            protected Argument initialize() {
                final Enum valueEnum = config.getEnum(entry);
                final Object[] values = valueEnum.getDeclaringClass().getEnumConstants();
                
                addCosmeticArgument(String.format("[%s]", Stringz.arrayToString(values, "/").toLowerCase()));
        
                return this;
            }
            
            @Override
            @SuppressWarnings("unchecked")
            public void onCommand(final String input, final String[] args) {
                if (args.length <= 0)
                    return;
                
                if (isInteger(args[0], true, true))
                    return;
                
                final Enum valueEnum = config.getEnum(entry);
                final String outputArg = args[0].toUpperCase();
                final Enum parsedEnum = (Enum) Enums.getIfPresent(valueEnum.getDeclaringClass(), outputArg).orNull();
                
                if (parsedEnum == null) {
                    errorf("%s[%s] does not exist, please enter a valid name or index.",
                           entry.getKey().replace(" ", ""),
                           outputArg);
                    return;
                }
                
                config.setValue(entry, parsedEnum);
                printValueUpdate(config, entry, outputArg);
            }
        });
    }
    
    private void registerGet(final Configuration config, final Entry entry, final String name) {
        final String clientColour = Colourz.CLIENT_COLOUR;
        final String grey = Colourz.GREY;

        registerArgument(new Argument(this, name, "get") {
            @Override
            public void onCommand(final String input, final String[] args) {
                if (args.length > 0) {
                    throwSyntaxError();
                }
                
                if (entry.getValue().getClass().isArray()) {
                    final Object[] array = entry.getArray();
                    
                    printf("%s[%s%s%s]: [%s]",
                           config.getName().replace(" ",
                                                    ""),
                           Colourz.CLIENT_COLOUR,
                           entry.getKey(),
                           Colourz.GREY,
                           Stringz.arrayToStringFancy(array, ", "));
                } else {
                    printf("%s[%s%s%s]: %s%s%s.",
                           config.getName().replace(" ",
                                                    ""),
                           clientColour,
                           entry.getKey(),
                           grey,
                           clientColour,
                           entry.getValue(),
                           grey);
                }
            }
        });
    }

    private void printValueUpdate(final Configuration config, final Entry entry, final Object output) {
        if (output.getClass().isArray()) {
            final Object[] array = entry.getArray();
        
            printf("%s[%s%s%s] contains been set to: [%s]",
                   config.getName().replace(" ", ""),
                   Colourz.CLIENT_COLOUR,
                   entry.getKey(),
                   Colourz.GREY,
                   Stringz.arrayToStringFancy(array, ", "));
        } else {
            printf("%s[%s%s%s] contains been set to [%s%s%s].",
                   config.getName().replace(" ", ""),
                   Colourz.CLIENT_COLOUR,
                   entry.getKey(),
                   Colourz.GREY,
                   Colourz.CLIENT_COLOUR,
                   output,
                   Colourz.GREY);
        }
    }
    
    private void printArrayUpdate(final Configuration config, final Entry entry, final Object output,
                                  final int index) {
        printf("%s[%s%s%s] (%s) contains been set to [%s%s%s].",
               config.getName().replace(" ", ""),
               Colourz.CLIENT_COLOUR,
               entry.getKey(),
               Colourz.GREY,
               index,
               Colourz.CLIENT_COLOUR,
               output,
               Colourz.GREY);
    }
}
