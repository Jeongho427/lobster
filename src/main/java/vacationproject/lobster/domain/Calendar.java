package vacationproject.lobster.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "calendar")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calendar_id")
    private Long cId;

    @Column(name = "day_start")
    private String day_start;

    @Column(name = "day_end")
    private String day_end;

    @Column(name = "contents")
    private String contents;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User calendarOwner;

    public void update(String day_start, String day_end, String contents) {
        this.day_start = day_start;
        this.day_end = day_end;
        this.contents = contents;
    }

    @Builder
    public Calendar(String day_start, String day_end, String contents, User calendarOwner) {
        this.day_start = day_start;
        this.day_end = day_end;
        this.contents = contents;
        this.calendarOwner = calendarOwner;
    }
}
