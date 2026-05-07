package com.example.demo.repository;

import com.example.demo.entity.AuctionInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuctionInvitationRepository extends JpaRepository<AuctionInvitation, Integer> {

    Optional<AuctionInvitation> findByRecordKey(String recordKey);

    List<AuctionInvitation> findByCompany_Id(Integer companyId);

    @Query("SELECT i FROM AuctionInvitation i WHERE i.company.id = :companyId " +
            "AND i.status.key IN ('key.auctionInvitation.invited', 'key.auctionInvitation.accepted') " +
            "AND i.auction.status.key = 'key.auctionStatus.active'")
    List<AuctionInvitation> findActiveInvitationsByCompany(@Param("companyId") Integer companyId);

    @Query("SELECT i FROM AuctionInvitation i WHERE i.company.id = :companyId " +
            "AND i.auction.status.key = 'key.auctionStatus.completed'")
    List<AuctionInvitation> findFinishedInvitationsByCompany(@Param("companyId") Integer companyId);

    @Query("SELECT i FROM AuctionInvitation i WHERE i.company.id = :companyId " +
            "AND i.status.key = 'key.auctionInvitation.invited'")
    List<AuctionInvitation> findNewInvitationsByCompany(@Param("companyId") Integer companyId);

    @Query("SELECT COUNT(i) FROM AuctionInvitation i WHERE i.auction.id = :auctionId " +
            "AND i.company.id = :companyId " +
            "AND i.status.key != 'key.auctionInvitation.cancelled'")
    Long countActiveInvitation(@Param("auctionId") Integer auctionId,
                               @Param("companyId") Integer companyId);

    @Query("SELECT COUNT(i) FROM AuctionInvitation i WHERE i.company.id = :companyId " +
            "AND i.status.key = 'key.auctionInvitation.invited'")
    Long countNewInvitations(@Param("companyId") Integer companyId);
}