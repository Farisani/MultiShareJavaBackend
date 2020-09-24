package za.co.multishare.web.domain.entity;

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
@Table(name = "user_role_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_info_id",
            nullable = false,
            unique = true,
            updatable = false)
    private Long userRoleInfoId;

    @Column(name = "user_role",
            nullable = false)
    private String userRole;

    @Column(name = "record_valid_from_date",
            nullable = false)
    private LocalDateTime recordValidFromDate;

    @Column(name = "record_valid_to_date")
    private LocalDateTime recordValidToDate;

    @ManyToOne
    @JoinColumn(name = "user_info_id",
            nullable = false,
            updatable = false)
    private UserInfo userInfo;


}
