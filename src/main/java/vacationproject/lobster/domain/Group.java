package vacationproject.lobster.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "my_group")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long gId;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "member_cnt")
    private int memberCnt;

    /*@Column(name = "creator")
    private Long creator;*/

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User creator;

    @OneToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

//    private List<Member> members = new ArrayList<>();

    @Builder
    public Group(String groupName, int memberCnt, User creator) {
        this.groupName = groupName;
        this.memberCnt = memberCnt;
        this.creator = creator;
    }
}
