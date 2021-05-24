package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Date time.
 */
public class DateTime {
    private LocalDateTime time;


    /**
     * Constructor a new Date time.
     */
    public DateTime() {
        this.time = LocalDateTime.now();
    }

    /**
     * Constructor a new Date time.
     *
     * @param time the time
     */
    public DateTime(LocalDateTime time) {
        this.time = time;
    }

    /**
     * Gets formatted time stamp.
     *
     * @return formatted timestamp
     */
    public String getTimestamp() {
        DateTimeFormatter dtf;
        dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return time.format(dtf);
    }


    @Override
    public String toString() {
        return getTimestamp();
    }
}