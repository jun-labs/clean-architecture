package project.architecture.remittance.account.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

interface ActivityRepository extends JpaRepository<ActivityJpaEntity, Long> {

    @Query("SELECT a FROM ActivityJpaEntity a " +
            "WHERE a.ownerAccountId = :ownerAccountId " +
            "AND a.timestamp <= :timestamp")
    List<ActivityJpaEntity> findByOwnerAccountIdAndTimestamp(
            @Param("ownerAccountId") Long ownerAccountId,
            @Param("timestamp") LocalDateTime timestamp
    );

    @Query("SELECT SUM(a.amount) FROM ActivityJpaEntity a " +
            "WHERE a.targetAccountId = :accountId " +
            "AND a.ownerAccountId = :accountId " +
            "AND a.timestamp <= :until")
    Long getDepositBalanceUntil(
            @Param("accountId") Long accountId,
            @Param("until") LocalDateTime until
    );

    @Query("SELECT SUM (a.amount) FROM ActivityJpaEntity a " +
            "WHERE a.sourceAccountId = :accountId " +
            "AND a.ownerAccountId = :accountId " +
            "AND a.timestamp <= :until")
    Long getWithdrawalBalanceUntil(
            @Param("accountId") Long accountId,
            @Param("until") LocalDateTime until
    );

}
