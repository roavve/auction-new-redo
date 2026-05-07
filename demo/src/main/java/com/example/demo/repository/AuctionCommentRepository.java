package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.AuctionComment;
import java.util.List;

@Repository
public interface AuctionCommentRepository extends JpaRepository<AuctionComment, Integer> {

    @Query("SELECT c FROM AuctionComment c WHERE c.auction.id = :auctionId " +
            "AND c.status.key != 'key.comment.status.cancelled' " +
            "ORDER BY c.commCreated ASC")
    List<AuctionComment> findByAuctionId(@Param("auctionId") Integer auctionId);

    @Query("SELECT c FROM AuctionComment c WHERE c.auction.id = :auctionId " +
            "AND c.user.email = :email " +
            "AND c.status.key != 'key.comment.status.cancelled' " +
            "ORDER BY c.commCreated ASC")
    List<AuctionComment> findByAuctionIdAndUserEmail(@Param("auctionId") Integer auctionId,
                                                     @Param("email") String email);
}