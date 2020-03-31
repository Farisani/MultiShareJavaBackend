package za.co.multishare.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_info_detail")
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_info_detail_id",
            unique = true,
            updatable = false,
            nullable = false)
    @Getter
    @Setter
    private Long userDetailDetailId;

    @Column(name = "title", nullable = false)
    @Getter
    @Setter
    private String title;

    @Column(name = "name", nullable = false)
    @Getter
    @Setter
    private String name;

    @Column(name = "surname", nullable = false)
    @Getter
    @Setter
    private String surname;

    @Column(name = "gender", nullable = false)
    @Getter
    @Setter
    private String gender;

    @Column(name = "legalIdentityNumber",
            unique = true,
            nullable = false)
    @Getter
    @Setter
    private String legalIdentityNumber;

    @Column(name = "record_valid_from_date",
            nullable = false)
    @Getter
    @Setter
    private LocalDateTime recordValidFromDate;

    @Column(name = "record_valid_to_date")
    @Getter
    @Setter
    private LocalDateTime recordValidToRecord;

    @JoinColumn(name = "user_info_id")
    @ManyToOne
    private UserInfo userInfo;
}
