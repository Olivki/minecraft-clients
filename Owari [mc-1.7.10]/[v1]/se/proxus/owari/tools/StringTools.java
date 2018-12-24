package se.proxus.owari.tools;

public class StringTools {

	private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static String numbersToLetters(String text) {
		if (isInteger(text)) {
			for (int numbers = 0; numbers < 10; numbers++) {
				text = text.replace("" + numbers, "" + ALPHABET.charAt(numbers));
			}
		}
		return text;
	}

	public static String lettersToNumbers(String text) {
		for (int numbers = 0; numbers < 10; numbers++) {
			text = text.replace("" + ALPHABET.charAt(numbers), "" + numbers);
		}
		return text;
	}

	public static String intToHex(final int integer) {
		return Integer.toHexString(integer);
	}

	public static String insertEveryLetter(final String text, final String extra) {
		String result = "";
		for (int letters = 0; letters < text.length(); letters++) {
			char character = text.charAt(letters);
			if (character != ' ') {
				result += extra + character;
			} else {
				result += " ";
			}
		}
		return result;
	}

	public static String arrayToString(final Object... array) {
		String result = "";
		for (Object element : array) {
			result += element.toString();
		}
		return result;
	}

	public static boolean isNumber(final String text) {
		return isInteger(text) || isLong(text) || isFloat(text) || isDouble(text) || isShort(text)
				|| isByte(text);
	}

	public static boolean isBoolean(final String text) {
		try {
			Boolean.parseBoolean(text);
			return true;
		} catch (Exception exception) {
			return false;
		}
	}

	public static boolean isInteger(final String text) {
		try {
			Integer.parseInt(text);
			return true;
		} catch (NumberFormatException exception) {
			return false;
		}
	}

	public static boolean isFloat(final String text) {
		try {
			Float.parseFloat(text);
			return true;
		} catch (NumberFormatException exception) {
			return false;
		}
	}

	public static boolean isLong(final String text) {
		try {
			Long.parseLong(text);
			return true;
		} catch (NumberFormatException exception) {
			return false;
		}
	}

	public static boolean isDouble(final String text) {
		try {
			Double.parseDouble(text);
			return true;
		} catch (NumberFormatException exception) {
			return false;
		}
	}

	public static boolean isShort(final String text) {
		try {
			Short.parseShort(text);
			return true;
		} catch (NumberFormatException exception) {
			return false;
		}
	}

	public static boolean isByte(final String text) {
		try {
			Byte.parseByte(text);
			return true;
		} catch (NumberFormatException exception) {
			return false;
		}
	}
}