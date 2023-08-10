package vacationproject.lobster.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(name = "important")
    private boolean important;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User calenderOwner;

    public void update(String day_start, String day_end, String contents) {
        this.day_start = day_start;
        this.day_end = day_end;
        this.contents = contents;
    }

    @Builder
    public Calender(String day_start, String day_end, String contents, boolean important, User calenderOwner) {
        this.day_start = day_start;
        this.day_end = day_end;
        this.contents = contents;
        this.important = important;
        this.calenderOwner = calenderOwner;
    }

}
