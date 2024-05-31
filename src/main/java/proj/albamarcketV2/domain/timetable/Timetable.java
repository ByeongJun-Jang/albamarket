package proj.albamarcketV2.domain.timetable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import proj.albamarcketV2.domain.member.Member;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter @Getter
public class Timetable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "timetable", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TimeSlot> timeSlots;
}
