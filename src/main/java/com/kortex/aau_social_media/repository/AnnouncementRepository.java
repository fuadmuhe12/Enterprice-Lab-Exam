package com.kortex.aau_social_media.repository;

import com.kortex.aau_social_media.model.Announcement;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    @Query("SELECT DISTINCT a FROM Announcement a LEFT JOIN FETCH a.comments LEFT JOIN FETCH a.likedBy ORDER BY a.createdAt DESC")
    List<Announcement> findAllWithCommentsAndLikes();
}
