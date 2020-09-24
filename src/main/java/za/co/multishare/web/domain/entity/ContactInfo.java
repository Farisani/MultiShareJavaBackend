package za.co.multishare.web.domain.entity;

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
@Table(name = "contact_info")
@AllArgsConstructor
@NoArgsConstructor
public class ContactInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_info_id",
            unique = true,
            updatable = false,
            nullable = false)
    @Getter
    @Setter
    private Long contactInfo;

    @Column(name = "contact",
            unique = true,
            nullable = false)
    @Getter
    @Setter
    private String contact;

    @Column(name = "contact_type",
            nullable = false)
    @Getter
    @Setter
    private String contactType;

    @Column(name = "record_valid_from_date",
            nullable = false)
    @Getter
    @Setter
    private LocalDateTime recordValidFromDate;

    @Column(name = "record_valid_to_date")
    @Getter
    @Setter
    private LocalDateTime recordValidToDate;

    @ManyToOne
    @JoinColumn(name = "user_info_id")
    @Getter
    @Setter
    private UserInfo userInfo;
}
