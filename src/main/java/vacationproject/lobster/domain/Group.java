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

    //User table의 pk인 user_id를 참조하는 외래키
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "creator")
    private User creator;

    @OneToMany(mappedBy = "groupId")
    private List<Member> members = new ArrayList<>();

    public void update(String groupName) {
        this.groupName = groupName;
    }

    @Builder
    public Group(String groupName, int memberCnt, User creator) {
        this.groupName = groupName;
        this.memberCnt = memberCnt;
        this.creator = creator;
    }
}