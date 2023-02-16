package project.architecture.remittance.account.adapter.out.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "activity")
class ActivityJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime timestamp;

    @Column
    private Long ownerAccountId;

    @Column
    private Long sourceAccountId;

    @Column
    private Long targetAccountId;

    @Column
    private Long amount;

    /**
     * @Nullary-Constructor. JPA 기본 생성자로 persistence 패키지 외부에서 호출하지 말 것.
     */
    protected ActivityJpaEntity() {
    }

    public ActivityJpaEntity(
            Long id,
            LocalDateTime timestamp,
            Long ownerAccountId,
            Long sourceAccountId,
            Long targetAccountId,
            Long amount
    ) {
        this.id = id;
        this.timestamp = timestamp;
        this.ownerAccountId = ownerAccountId;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Long getOwnerAccountId() {
        return ownerAccountId;
    }

    public Long getSourceAccountId() {
        return sourceAccountId;
    }

    public Long getTargetAccountId() {
        return targetAccountId;
    }

    public Long getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActivityJpaEntity)) return false;
        ActivityJpaEntity that = (ActivityJpaEntity) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "ActivityJpaEntity{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", ownerAccountId=" + ownerAccountId +
                ", sourceAccountId=" + sourceAccountId +
                ", targetAccountId=" + targetAccountId +
                ", amount=" + amount +
                '}';
    }

    //    @Override
//    public String toString() {
//        return id.toString();
//    }
}
