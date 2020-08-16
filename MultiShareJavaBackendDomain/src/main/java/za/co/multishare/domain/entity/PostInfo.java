package za.co.multishare.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "post_info")
@AllArgsConstructor
@NoArgsConstructor
public class PostInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_info_id",
            nullable = false,
            updatable = false,
            unique = true)
    @Getter
    @Setter
    private Long postInfoId;

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
    @JoinColumn(name = "user_info_id",
            nullable = false)
    @Getter
    @Setter
    private UserInfo userInfo;

    @Override
    public String toString() {
        return "PostInfo{" +
                "postInfoId=" + postInfoId +
                ", recordValidFromDate=" + recordValidFromDate +
                ", recordValidToDate=" + recordValidToDate +
                ", userInfo=" + userInfo +
                '}';
    }
}
