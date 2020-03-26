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
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_info")
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_info_id",
            unique = true,
            nullable = false,
            updatable = false)
    @Getter
    @Setter
    private Long userInfoId;

    @Column(name = "record_valid_from_date",
            nullable = false)
    @Getter
    @Setter
    private LocalDateTime recordValidFromDate;

    @Column(name = "record_valid_to_date")
    @Getter
    @Setter
    private LocalDateTime recordValidToDate;
}
