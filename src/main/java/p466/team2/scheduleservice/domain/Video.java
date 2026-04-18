package p466.team2.scheduleservice.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.jspecify.annotations.NonNull;

public class Video implements Comparable {

    @NotBlank(message = "The video's Title must be defined.")
    String title;

    @NotBlank(message = "The video's Date must be defined.")
    @Pattern(
            regexp = "^(0[1-9]|1[0-2])([/-])(0[1-9]|[12][0-9]|3[01])\\2(19|20)\\d{2}$",
            message = "The video's Date format must be correct. (mm/dd/yyyy)"
    )
    String date;

    @NotBlank(message = "The video's Start time must be defined.")
    @Pattern(
            regexp = "^([01]\\d|2[0-3]):[0-5]\\d$",
            message = "The video's Start time format must be correct. (hh:mm)"
    )
    String start;

    @NotBlank(message = "The video's Duration must be defined.")
    @Pattern(
            regexp = "^([01]\\d|2[0-3]):[0-5]\\d$",
            message = "The video's Duration format must be correct. (hh:mm)"
    )
    String duration;

    @NotBlank(message = "The video's Link must be defined.")
    String link;

    public Video(String title, String date, String start, String duration, String link) {
        this.title = title;
        this.date = date;
        this.start = start;
        this.duration = duration;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Checks the ordering of two Videos. If the current instance of a Video's date is before the given Video's date,
     * the former should come before the latter. Otherwise, the former should come after the latter. If the Videos have
     * the same date, the same logic is used, but instead comparing time (military clock).
     *
     * @param other the object to be compared.
     * @return A negative number if the given Video should come after the current Video, a positive number if the given
     *         Video should come before the current Video, or a 0 if they are equivalent in ordering.
     */
    @Override
    public int compareTo(@NonNull Object other) {
        if (other instanceof Video v) {
            String[] thisDate = this.date.split("/"), thisTime = this.start.split(":"),
                    otherDate = v.date.split("/"), otherTime = v.start.split(":");
            int thisMonth = Integer.parseInt(thisDate[0]), thisDay = Integer.parseInt(thisDate[1]), thisYear = Integer.parseInt(thisDate[2]),
                    thisHour = Integer.parseInt(thisTime[0]), thisMinute = Integer.parseInt(thisTime[1]),
                    otherMonth = Integer.parseInt(otherDate[0]), otherDay = Integer.parseInt(otherDate[1]), otherYear = Integer.parseInt(otherDate[2]),
                    otherHour = Integer.parseInt(otherTime[0]), otherMinute = Integer.parseInt(otherTime[1]);
            if (thisYear != otherYear) {
                return Integer.compare(thisYear, otherYear);
            } else if (thisMonth != otherMonth) {
                return Integer.compare(thisMonth, otherMonth);
            } else if (thisDay != otherDay) {
                return Integer.compare(thisDay, otherDay);
            }
            else if (thisHour != otherHour) {
                return Integer.compare(thisHour, otherHour);
            }
            else if (thisMinute != otherMinute) {
                return Integer.compare(thisMinute, otherMinute);
            } else {
                return 0;
            }
        }
        return 0;
    }

    /**
     * Parses the String representations of the start time and duration, turning them into their Integer representations.
     * Checks if two dates are the same. If so, each Video's start time is determined and turned into a minute-based
     * representation. Then, using the minute-based representation of the durations, an end time, minute-based representation
     * is also calculated. Using these known start and end times, we can determine two Videos are overlapping if the current
     * Video's start time is before the other Video's end time, and the other Video's start time is before the current
     * Video's end time.
     * If two dates are not the same... edge case will cause errors... needs implementation.
     *
     * @param other - The other Video whose runtime will be compared with the current Video instance.
     * @return True if the two Videos' runtimes overlap. Otherwise, false.
     */
    public boolean overlappingTime(Video other) {
        String[] thisTime = this.start.split(":"), thisDuration = this.duration.split(":"),
                otherTime = other.start.split(":"), otherDuration = this.duration.split(":");

        int thisHour = Integer.parseInt(thisTime[0]), thisMinute = Integer.parseInt(thisTime[1]),
                thisDurationHour = Integer.parseInt(thisDuration[0]), thisDurationMinute = Integer.parseInt(thisDuration[1]),
                otherHour = Integer.parseInt(otherTime[0]), otherMinute = Integer.parseInt(otherTime[1]),
                otherDurationHour = Integer.parseInt(otherDuration[0]), otherDurationMinute = Integer.parseInt(otherDuration[1]);

        boolean overlaps = false;
        // This doesn't cover the edge case of one video airing near midnight and overlapping into the next day, etc.
        if (this.date.equals(other.date)) {
            int start1 = (thisHour * 60) + thisMinute, start2 = (otherHour * 60) + otherMinute;
            int end1 = start1 + (thisDurationHour * 60) + thisDurationMinute, end2 = start2 + (otherDurationHour * 60) + otherDurationMinute;

            overlaps = (start1 < end2) && (start2 < end1);
        }

        return overlaps;
    }
}
