package vacationproject.lobster.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "calender")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Calender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calender_id")
    private Long cId;

    @Column(name = "day_start")
    private String day_start;

    @Column(name = "day_end")
    private String day_end;

    @Column(name = "contents")
    private String contents;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User calenderOwner;

    @Builder
    public Calender(String day_start, String day_end, String contents, User calenderOwner) {
        this.day_start = day_start;
        this.day_end = day_end;
        this.contents = contents;
        this.calenderOwner = calenderOwner;
    }
}

