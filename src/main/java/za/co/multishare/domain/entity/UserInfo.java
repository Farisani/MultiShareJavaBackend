package za.co.multishare.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Data
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_info_id",
            unique = true,
            nullable = false,
            updatable = false)
    private Long userInfoId;

    @Column(name = "record_valid_from_date",
            nullable = false)
    private LocalDateTime recordValidFromDate;

    @Column(name = "record_valid_to_date")
    private LocalDateTime recordValidToDate;
}
