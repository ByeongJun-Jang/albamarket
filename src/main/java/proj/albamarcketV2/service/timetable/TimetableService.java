package proj.albamarcketV2.service.timetable;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proj.albamarcketV2.domain.member.Member;
import proj.albamarcketV2.domain.timetable.TimeSlot;
import proj.albamarcketV2.domain.timetable.Timetable;
import proj.albamarcketV2.repository.TimeSlotRepository;
import proj.albamarcketV2.repository.TimetableRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimetableService {
    private final TimetableRepository timetableRepository;
    private final TimeSlotRepository timeSlotRepository;

    public List<Timetable> getTimetablesByMemberId(Long memberId) {
        return timetableRepository.findByMemberId(memberId);
    }


    public List<TimeSlot> getTimeSlotsByTimetableId(Long timetableId) {
        return timeSlotRepository.findByTimetableId(timetableId);
    }

    public Timetable getTimetableByMember1(Member member) {
        return timetableRepository.findByMember(member).orElse(null);
    }

    public Optional<Timetable> getTimetableByMember(Member member) {
        return timetableRepository.findByMember(member);
    }

    @Transactional
    public void saveTimetable(Member member, List<String> selectedSlots) {
        Optional<Timetable> timetableOpt = timetableRepository.findByMember(member);
        Timetable timetable;
        if (timetableOpt.isPresent()) {
            timetable = timetableOpt.get();
            timeSlotRepository.deleteByTimetable(timetable);
        } else {
            timetable = new Timetable();
            timetable.setMember(member);
            timetableRepository.save(timetable);
        }

        List<TimeSlot> timeSlots = selectedSlots.stream()
                .map(slot -> {
                    String[] parts = slot.split("-");
                    int dayOfWeek = Integer.parseInt(parts[0]);
                    String time = parts[1];
                    TimeSlot timeSlot = new TimeSlot();
                    timeSlot.setDayOfWeek(dayOfWeek);
                    timeSlot.setTime(time);
                    timeSlot.setTimetable(timetable);
                    return timeSlot;
                })
                .collect(Collectors.toList());

        timeSlotRepository.saveAll(timeSlots);
    }


    public List<Timetable> getTimetables() {
        return timetableRepository.findAll();
    }
}
