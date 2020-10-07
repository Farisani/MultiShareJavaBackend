package za.co.multishare.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Data
public class UserInfoDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_info_detail_id",
            unique = true,
            updatable = false,
            nullable = false)
    private Long userDetailDetailId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "legalIdentityNumber",
            unique = true,
            nullable = false)
    private String legalIdentityNumber;

    @Column(name = "record_valid_from_date",
            nullable = false)
    private LocalDateTime recordValidFromDate;

    @Column(name = "record_valid_to_date")
    private LocalDateTime recordValidToRecord;

    @JoinColumn(name = "user_info_id")
    @ManyToOne
    private UserInfo userInfo;
}
