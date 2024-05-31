package proj.albamarcketV2.config;

import proj.albamarcketV2.domain.timetable.TimeSlot;

import java.util.List;
import java.util.stream.Collectors;

public class ThymeleafUtility {
    public static List<String> timeSlotIds(List<TimeSlot> timeSlots) {
        return timeSlots.stream()
                .map(ts -> ts.getDayOfWeek() + "-" + ts.getTime())
                .collect(Collectors.toList());
    }
}
