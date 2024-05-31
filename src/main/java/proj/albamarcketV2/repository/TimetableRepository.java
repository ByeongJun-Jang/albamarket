package proj.albamarcketV2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import proj.albamarcketV2.domain.member.Member;
import proj.albamarcketV2.domain.timetable.Timetable;

import java.util.List;
import java.util.Optional;

public interface TimetableRepository extends JpaRepository<Timetable, Long> {
//    @Query("SELECT t FROM Timetable t LEFT JOIN FETCH t.timeSlots WHERE t.member = :member")
//    Optional<Timetable> findByMember(@Param("member") Member member);

//    @Query("SELECT t FROM Timetable t LEFT JOIN FETCH t.timeSlots WHERE t.member = :member")
//    Optional<Timetable> findByMember(@Param("member") Member member);

    Optional<Timetable> findByMember(Member member);

//    Optional<Timetable> findByMember(Member member);

    List<Timetable> findByMemberId(Long memberId);
}
