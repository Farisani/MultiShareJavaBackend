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
@Table(name = "friendship_info")
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friendship_info_id",
            nullable = false,
            unique = true,
            updatable = false)
    @Getter
    @Setter
    private Long friendshipInfoId;

    @Column(name = "record_valid_from_date",
            nullable = false)
    @Getter
    @Setter
    private LocalDateTime recordValidFromDate;

    @Column(name = "record_valid_to_date")
    @Getter
    @Setter
    private LocalDateTime recordValidToDate;

    @Column(name = "friendship_info_status",
            nullable = false,
            updatable = false)
    @Getter
    @Setter
    private String friendshipInfoStatus;

    @ManyToOne
    @JoinColumn(name = "src_friendship_info_id",
            nullable = false)
    @Getter
    @Setter
    private UserInfo srcFriendshipUserInfo;

    @ManyToOne
    @JoinColumn(name = "dest_friendship_info_id",
            nullable = false)
    @Getter
    @Setter
    private UserInfo destFriendshipUserInfo;

    @Override
    public String toString() {
        return "FriendshipInfo{" +
                "friendshipInfoId=" + friendshipInfoId +
                ", recordValidFromDate=" + recordValidFromDate +
                ", recordValidToDate=" + recordValidToDate +
                ", friendshipInfoStatus='" + friendshipInfoStatus + '\'' +
                ", srcFriendshipUserInfo=" + srcFriendshipUserInfo +
                ", destFriendshipUserInfo=" + destFriendshipUserInfo +
                '}';
    }
}