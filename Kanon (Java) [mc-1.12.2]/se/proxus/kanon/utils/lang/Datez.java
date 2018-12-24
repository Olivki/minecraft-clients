package se.proxus.kanon.utils.lang;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public final class Datez {

    /**
     * A method for getting the computers current date, without a default format.
     *
     * @param format
     *         The format for the SimpleDateFormat. <i>(Example: yyyy/mm/dd/hh:mm)</i>
     * @return Returns computers current date.
     *
     * @see Datez#getDate()
     */
    public static String getDate(final String format) {
        final DateFormat date = new SimpleDateFormat(format);
        final Calendar calendar = Calendar.getInstance();
        return date.format(calendar.getTime());
    }

    /**
     * A method for getting the computers current date, with a default format.
     *
     * @return Returns computers current date.
     *
     * @see Datez#getDate(String)
     */
    public static String getDate() {
        return getDate("yyyy/mm/dd/hh:mm");
    }
    
}
