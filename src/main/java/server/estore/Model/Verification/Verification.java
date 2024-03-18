package server.estore.Model.Verification;

import jakarta.persistence.*;
import lombok.*;
import server.estore.Model.BaseEntity;
import server.estore.Model.User.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "verifications")
public class Verification extends BaseEntity {
    @Column(name = "key", nullable = false, unique = true)
    private String key;

    @Column(name = "validated", nullable = false)
    @Builder.Default
    private Boolean validated = false;

    @Column(name = "expired_at", nullable = false)
    @Builder.Default
    private LocalDateTime expiredAt = LocalDateTime.now().plusWeeks(1);

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}