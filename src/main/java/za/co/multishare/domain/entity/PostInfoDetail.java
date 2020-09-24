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
@Table(name = "post_info_detail")
@AllArgsConstructor
@NoArgsConstructor
public class PostInfoDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_info_detail_id",
            nullable = false,
            updatable = false,
            unique = true)
    @Getter
    @Setter
    private Long postInfoDetailId;

    @Column(name = "title",
            nullable = false)
    @Getter
    @Setter
    private String title;

    @Column(name = "post_body",
            nullable = false,
            unique = true)
    @Getter
    @Setter
    private String postBody;

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
    @JoinColumn(name = "post_info_id",
            nullable = false)
    @Getter
    @Setter
    private PostInfo postInfo;
}
