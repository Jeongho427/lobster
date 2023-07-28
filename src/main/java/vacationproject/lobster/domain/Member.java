package vacationproject.lobster.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long mId;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group groupId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User userId;

   /* @Column(name = "group_id")
    private Long groupId;

    @Column(name = "user_id")
    private Long userId;*/

    @Column(name = "color")
    private String color;

    public void update(Group groupId, User userId, String color) {
        this.groupId = groupId;
        this.userId = userId;
        this.color = color;
    }

    @Builder
    public Member(Group groupId, User userId, String color) {
        this.groupId = groupId;
        this.userId = userId;
        this.color = color;
    }
}