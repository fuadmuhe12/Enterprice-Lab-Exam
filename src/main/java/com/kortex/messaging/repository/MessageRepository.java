package com.kortex.messaging.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kortex.messaging.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
