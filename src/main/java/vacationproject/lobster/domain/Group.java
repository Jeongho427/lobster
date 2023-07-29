package vacationproject.lobster.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    //유저 테이블의 user_id와 연결되어있는 creator
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "creator")
    @JsonManagedReference
    private User creator;

    // 멤버 테이블의 모든 정보 불러오기
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