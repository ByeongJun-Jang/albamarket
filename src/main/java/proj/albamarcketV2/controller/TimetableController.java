package proj.albamarcketV2.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import proj.albamarcketV2.config.ThymeleafUtility;
import proj.albamarcketV2.domain.member.Member;
import proj.albamarcketV2.domain.member.MemberRepository;
import proj.albamarcketV2.domain.timetable.TimeSlot;
import proj.albamarcketV2.domain.timetable.Timetable;
import proj.albamarcketV2.service.timetable.TimetableService;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class TimetableController {

    private final TimetableService timetableService;
    private final MemberRepository memberRepository;

    private final List<String> daysOfWeek = Arrays.asList("월", "화", "수", "목", "금", "토", "일");
    private final List<String> timeSlots = Arrays.asList(
            "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00",
            "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00"
    );

    @GetMapping("/timetable")
    public String showTimetable(HttpSession httpSession, Model model) {
        Member member = (Member) httpSession.getAttribute("member");
        if (member == null) {
            return "redirect:/login";
        }

        Optional<Timetable> timetableOpt = timetableService.getTimetableByMember(member);
        if (timetableOpt.isPresent()) {
            Timetable timetable = timetableOpt.get();
            model.addAttribute("timetable", timetable);
            List<String> timeSlotIds = timetable.getTimeSlots().stream()
                    .map(ts -> ts.getDayOfWeek() + "-" + ts.getTime())
                    .collect(Collectors.toList());
            model.addAttribute("timeSlotIds", timeSlotIds);
        } else {
            model.addAttribute("timetable", new Timetable());
            model.addAttribute("timeSlotIds", Collections.emptyList());
        }
        model.addAttribute("daysOfWeek", daysOfWeek);
        model.addAttribute("timeSlots", timeSlots);

        return "timetable/timetable";
    }

//    @GetMapping("/timetable")
    public String showTimetable1(HttpSession httpSession, Model model) {
        Member member = (Member) httpSession.getAttribute("member");
        if (member == null) {
            return "redirect:/login";
        }

        Optional<Timetable> timetableOpt = timetableService.getTimetableByMember(member);
        if (timetableOpt.isPresent()) {
            Timetable timetable = timetableOpt.get();
            model.addAttribute("timetable", timetable);
        } else {
            model.addAttribute("timetable", new Timetable());
        }
        model.addAttribute("daysOfWeek", daysOfWeek);
        model.addAttribute("timeSlots", timeSlots);

        return "timetable/timetable";
    }

    @GetMapping("/timetable/edit")
    public String editTimetable(HttpSession httpSession, Model model) {
        Member member = (Member) httpSession.getAttribute("member");
        if (member == null) {
            return "redirect:/login";
        }

        Optional<Timetable> timetableOpt = timetableService.getTimetableByMember(member);
        if (timetableOpt.isPresent()) {
            Timetable timetable = timetableOpt.get();
            model.addAttribute("timetable", timetable);
            List<String> timeSlotIds = timetable.getTimeSlots().stream()
                    .map(ts -> ts.getDayOfWeek() + "-" + ts.getTime())
                    .collect(Collectors.toList());
            model.addAttribute("timeSlotIds", timeSlotIds);
        } else {
            model.addAttribute("timetable", new Timetable());
            model.addAttribute("timeSlotIds", Collections.emptyList());
        }
        model.addAttribute("daysOfWeek", daysOfWeek);
        model.addAttribute("timeSlots", timeSlots);

        return "timetable/timetableEditForm";
    }

    @PostMapping("/timetable/save")
    public String saveTimetable(HttpSession httpSession, @RequestParam("timeslots") List<String> selectedSlots, Model model) {
        Member member = (Member) httpSession.getAttribute("member");
        if (member == null) {
            return "redirect:/login";
        }

        timetableService.saveTimetable(member, selectedSlots);

        return "redirect:/timetable";
    }
}
