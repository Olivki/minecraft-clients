package se.proxus.kanon.commands;

import lombok.Getter;
import lombok.Setter;
import se.proxus.kanon.Kanon;
import se.proxus.kanon.config.Entry;
import se.proxus.kanon.utils.lang.Stringz;
import se.proxus.kanon.utils.math.Mathz;
import se.proxus.kanon.utils.math.NumberRange;
import se.proxus.kanon.utils.math.Numberz;
import se.proxus.kanon.utils.minecraft.client.Colourz;
import se.proxus.kanon.wrapper.minecraft.Player;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

@CommandSignature(author = "Oliver Berg", date = "2018/09/10 (17:10)")
public abstract class Command {

	@Getter private Sender sender;
	
	@Getter private final String name;
	private final List<Argument> registeredArguments = new ArrayList<>();
	private final String[] aliases;
	
	@Getter @Setter private String currentName;

	public Command(final String name, @Nullable final String... aliases) {
		this.name = name;

		final List<String> tempList = new LinkedList<>();

		tempList.add(name);

		if (aliases != null)
            tempList.addAll(Arrays.asList(aliases));

		this.aliases = tempList.toArray(new String[0]);
		tempList.clear();

		currentName = name;

		initialize();
	}

	public abstract void initialize();

	public final Argument registerArgument(final Argument argument) {
		if (!registeredArguments.contains(argument)) {
			registeredArguments.add(argument);
		}
		
		return argument;
	}

	public final void unregisterArgument(final Argument argument) {
        registeredArguments.remove(argument);
	}

	final boolean nameMatch(final String name) {
		for (final String alias : aliases) {
			if (alias.equalsIgnoreCase(name)) {
			    currentName = name;
				return true;
			}
		}

		return false;
	}

	final boolean hasMatch(final String[] args) {
		for (final Argument argument : registeredArguments) {
			if (argument.isMatch(args)) {
				return true;
			}
		}

		throwSyntaxError();
		return false;
	}

	final void parseCommand(final String input, final String[] args, final Sender sender) {
		this.sender = sender;
		
		if (input.indexOf(' ') > 0) {
			currentName = input.substring(0, input.indexOf(' '));
		} else {
			currentName = input;
		}
        
        registeredArguments.stream()
                .filter(argument -> argument.isMatch(args))
                .forEachOrdered(argument -> argument.parseCommand(input, args));
    }

	private int getBiggestArguments() {
		if (registeredArguments.isEmpty()) {
			return 0;
		}
        
        final List<Integer> sizeList = registeredArguments.stream()
                .map(argument -> argument.getSyntaxArguments().length)
                .collect(Collectors.toCollection(LinkedList::new));
        
        return Collections.max(sizeList);
	}

	public final Argument[] getArguments() {
		return registeredArguments.toArray(new Argument[0]);
	}

	public String[] getUsage() {
		return new String[] { name };
	}

	public String getSyntax() {
		return getDefaultSyntax();
	}

	protected final String getDefaultSyntax() {
        String syntax = "." + getCurrentName() + " " + getJoinedArguments();

		if (getBiggestArguments() <= 0) {
			syntax = "." + getCurrentName();
		}

		return syntax;
	}

	private String getJoinedArguments() {
        final Map<Integer, String> syntaxMap = new LinkedHashMap<>();

		for (final Argument commandArgument : registeredArguments) {
		    if (!commandArgument.isVisible())
		        continue;
		    
			for (int i = 0; i < commandArgument.getSyntaxArguments().length; i++) {
                final String argument = commandArgument.getSyntaxArguments()[i];

				if (!syntaxMap.containsKey(i)) {
					syntaxMap.put(i, argument);
				} else {
                    final String existingArgument = syntaxMap.get(i);

					if (existingArgument.equalsIgnoreCase(argument))
						continue;

					if (org.apache.commons.lang3.StringUtils.containsIgnoreCase(existingArgument, argument))
						continue;

					syntaxMap.put(i, syntaxMap.get(i) + "/" + argument);
				}
			}
		}

		for (final Map.Entry<Integer, String> syntaxEntry : syntaxMap.entrySet()) {
            final String argument = syntaxEntry.getValue();

			if (argument.contains("/")) {
				syntaxEntry.setValue("<" + argument + ">");
			} else {
				syntaxEntry.setValue("[" + argument + "]");
			}
		}

		return Stringz.collectionToString(syntaxMap.values(), null);
	}
	
	protected final void print(final String message) {
		Player.addMessage(message);
	}

	public final void printf(final String message, final Object... args) {
		print(String.format(message, args));
	}

	protected final void send(final String message) {
		Player.sendMessage(message);
	}

	protected final void sendf(final String message, final Object... args) {
		send(String.format(message, args));
	}

	protected final void error(String message) {
		if (message.contains("[") && message.contains("]")) {
			message = message.replace("[", "[" + Colourz.CLIENT_COLOUR);
			message = message.replace("]", Colourz.GREY + "]");
		}

		print(message);
	}

	protected final void errorf(final String message, final Object... args) {
		error(String.format(message, args));
	}
    
    protected final void printc(String message) {
        if (message.contains("[") && message.contains("]")) {
            message = message.replace("[", "[" + Colourz.CLIENT_COLOUR);
            message = message.replace("]", Colourz.GREY + "]");
        }
        
        print(message);
    }
    
    public final void printcf(final String message, final Object... args) {
        printc(String.format(message, args));
    }

	protected final void throwSyntaxError() {
		printf("%sSyntax:%s %s",
               Colourz.CLIENT_COLOUR,
               Colourz.GREY,
               getSyntax());
	}

	@Override
	public String toString() {
	    if (hasAlias()) {
            return String.format("Command[%s, (%s), %s, %s]",
                                 name,
                                 Stringz.arrayToString(aliases, ", "),
                                 getSyntax(),
                                 registeredArguments.size());
        } else {
            return String.format("Command[%s, %s, %s]",
                                 name, getSyntax(),
                                 registeredArguments.size());
        }
	}

	public final String[] getAliases() {
		return aliases;
	}

	public final boolean hasAlias() {
		return aliases.length > 1;
	}

	protected final boolean isInteger(final String input, final boolean... silent) {
		if (Numberz.isNumber(input)) {
			if (Numberz.isDecimal(input)) {
			    if ((silent.length <= 1 || !silent[1])) {
                    error("Expected [INTEGER_VALUE], got [DECIMAL_VALUE]." + input);
                }
				return false;
			} else {
				return true;
			}
		} else {
			if ((silent.length <= 0 || !silent[0])) {
				errorf("Expected [INTEGER_VALUE], got [%s].", input.toUpperCase());
			}
			return false;
		}
	}

	protected final boolean isDecimal(final String input, final boolean... silent) {
		if (Numberz.isNumber(input)) {
			if (Numberz.isDecimal(input)) {
				return true;
			} else {
                if ((silent.length <= 1 || !silent[1])) {
                    errorf("Expected [DECIMAL_VALUE%s], got [%sINTEGER_VALUE].");
                }
				return false;
			}
		} else {
            if ((silent.length <= 0 || !silent[0])) {
				errorf("Expected [DECIMAL_VALUE], got [%s].", input.toUpperCase());
			}
			return false;
		}
	}

	protected final boolean isInRange(final double value, final double floor, final double ceiling,
            final boolean... silent) {
		final boolean inRange = Mathz.isInRange(value, floor, ceiling);

		if (!inRange && (silent.length <= 0 || !silent[0])) {
			errorf("Expected a value within the range of [%s-%s], got [%s].", floor, ceiling, value);
		}

		return inRange;
	}

	protected final boolean isInRange(final int value, final int floor, final int ceiling, final boolean... silent) {
		final boolean inRange = Mathz.isInRange(value, floor, ceiling);

		if (!inRange && (silent.length <= 0 || !silent[0])) {
			errorf("Expected a value within the range of [%s-%s], got [%s].", floor, ceiling, value);
		}

		return inRange;
	}
    
    protected final boolean isInRange(final Number value, final Entry entry, final boolean... silent) {
        if (Objects.isNull(entry.getRange()))
            return false;
        
        final NumberRange range = entry.getRange();
        final boolean inRange = range.contains(value);
        
        if (!inRange && (silent.length <= 0 || !silent[0])) {
            errorf("Expected a value within the range of [%s-%s], got [%s].",
                   range.getMinimum(),
                   range.getMaximum(),
                   value);
        }
        
        return inRange;
    }

	protected final Kanon getKanon() {
		return Kanon.getInstance();
	}

	protected final void initializeArguments() {
		for (final Argument argument : getArguments()) {
			argument.initialize();
		}
	}

	public abstract String getDescription();

	public abstract static class Argument {

		protected final List<String> arguments;
		final List<String> cosmeticArguments = new LinkedList<>();
		private final Command parent;
		@Getter @Setter private boolean visible = true;

		public Argument(final Command parent, @Nullable final String... arguments) {
			this.parent = parent;
			if (arguments == null) {
				this.arguments = new ArrayList<>();
			} else {
				this.arguments = new LinkedList<>(Arrays.asList(arguments));
			}

			initialize();
		}

		protected Argument initialize() {
			return this;
		}

		/**
		 * Parses the received input to check if it matches with any of the
		 * registered arguments.
		 *
		 * @param args
		 *            The split version of the input, it's split every space
		 *            occurrence.
		 * @return Whether or not the given input matches correctly with the
		 *         arguments contained within.
		 */
		boolean isMatch(final String[] args) {
			if (isEmpty() && args.length <= 0) {
				return true;
			} else if (args.length <= 0) {
				return false;
			}
			
			for (int index = 0; index < arguments.size(); index++) {
			    //if (index > args.length - 1)
			    //    continue;
			    
				final String inputArgument = args[index];
				final String registeredArgument = arguments.get(index);

				if (registeredArgument == null || inputArgument == null) {
					return false;
				}

				if (!inputArgument.equalsIgnoreCase(registeredArgument)) {
					return false;
				}
			}

			return true;
		}

		void parseCommand(String input, final String[] args) {
			if (isEmpty() && args.length == 0) {
				onCommand(input, new String[0]);
			} else {
				final List<String> tempList = new LinkedList<>();
                
                for (final String argument : args) {
                    if (!arguments.contains(argument)) {
                        tempList.add(argument);
                    }
                }

				final String[] croppedArgs = tempList.toArray(new String[0]);

				input = input.substring(parent.getCurrentName().length()).trim();

				if (arguments.size() > 0)
					input = input.substring(toString().length());

				if (input.startsWith(" ")) {
					input = input.substring(1);
				}

				onCommand(input, croppedArgs);
			}
		}

		protected final void addCosmeticArgument(final String argument) {
			if (!cosmeticArguments.contains(argument)) {
				cosmeticArguments.add(argument);
			}
		}

		protected final void deleteCosmeticArgument(final String argument) {
            cosmeticArguments.remove(argument);
		}

		// The args array should start after the last matched input
		public abstract void onCommand(final String input, final String[] args);

		public final boolean isEmpty() {
			return arguments.isEmpty() && cosmeticArguments.isEmpty();
		}

		public final String[] getArguments() {
			return isEmpty() ? new String[0] : arguments.toArray(new String[0]);
		}

		final String[] getSyntaxArguments() {
			final List<String> tempList = new LinkedList<>();

			if (!arguments.isEmpty()) {
				tempList.addAll(arguments);
			}

			if (!cosmeticArguments.isEmpty()) {
				tempList.addAll(cosmeticArguments);
			}

			return isEmpty() ? new String[0] : tempList.toArray(new String[tempList.size()]);
		}

		@Override
		public String toString() {
			return Stringz.listToString(arguments, null);
		}

		public final Command getParent() {
			return parent;
		}

		public final String[] getCosmeticArguments() {
			return cosmeticArguments.toArray(new String[cosmeticArguments.size()]);
		}
	}

	public enum Sender {
		CHAT, CONSOLE, MACRO
	}
}