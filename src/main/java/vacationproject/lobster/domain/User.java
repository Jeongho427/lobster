package vacationproject.lobster.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "user")
@NoArgsConstructor
@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long uId;

    @Column(name = "login_id")
    private String userId;

    @Column(name = "password")
    private String password;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_num")
    private String phoneNum;

    @Column(name = "is_login")
    private boolean is_login;

    @Column(name = "profile_img")
    private String profile_img;


    @OneToOne(mappedBy = "calendarOwner")
    @JsonBackReference
    private Calendar calendar;

    // 애랑
    @OneToOne(mappedBy = "creator")

    private Group group;

    // 애가 각각 그룹과 멤버 테이블이랑 양방퍙 매핑이 되어있음.
    @OneToOne(mappedBy = "userId")
    @JsonBackReference
    private Member member;

    @Builder
    public User(String userId, String password, String userName, String email, String phoneNum) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.phoneNum = phoneNum;
    }
}