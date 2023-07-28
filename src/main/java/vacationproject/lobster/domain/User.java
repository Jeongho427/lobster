package vacationproject.lobster.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

@Table(name = "user")
@NoArgsConstructor
@Getter
@Setter
@Entity
@Component
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "u_id")
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

    @Column(name = "phone_img")
    private String profile_img;

    @OneToOne(mappedBy = "calenderOwner")
    private Calender calender;

    @OneToMany(mappedBy = "creator")
    private List<Group> groups = new ArrayList<>();

//    @OneToOne(mappedBy = "userId")
//    private Member member;

    @OneToOne(mappedBy = "user")
    private Group group;

    @Builder
    public User(String userId, String password, String userName, String email, String phoneNum) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.phoneNum = phoneNum;
    }
}