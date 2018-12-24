package se.proxus.kanon.config.handler;

public class ConversionException extends Exception {
	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -5167943099293540392L;

	/**
	 * Constructs a new {@code ConversionException} without specified detail
	 * message.
	 */
	public ConversionException() {
		super();
	}

	/**
	 * Constructs a new {@code ConversionException} with specified detail
	 * message.
	 *
	 * @param message
	 *            the error message
	 */
	public ConversionException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new {@code ConversionException} with specified nested
	 * {@code Throwable}.
	 *
	 * @param cause
	 *            the exception or error that caused this exception to be thrown
	 */
	public ConversionException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new {@code ConversionException} with specified detail
	 * message and nested {@code Throwable}.
	 *
	 * @param message
	 *            the error message
	 * @param cause
	 *            the exception or error that caused this exception to be thrown
	 */
	public ConversionException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
