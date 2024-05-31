package proj.albamarcketV2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proj.albamarcketV2.domain.timetable.TimeSlot;
import proj.albamarcketV2.domain.timetable.Timetable;

import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    List<TimeSlot> findByTimetable(TimeSlot timeSlot);

    List<TimeSlot> findByTimetableId(Long timetableId);
    void deleteByTimetable(Timetable timetable);
}
