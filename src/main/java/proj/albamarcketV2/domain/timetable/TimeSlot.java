package proj.albamarcketV2.domain.timetable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class TimeSlot {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int dayOfWeek;
    private String time;

    @ManyToOne
    @JoinColumn(name = "timetable_id")
    private Timetable timetable;
}
