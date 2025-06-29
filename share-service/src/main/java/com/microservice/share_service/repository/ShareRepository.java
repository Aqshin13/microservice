package com.microservice.share_service.repository;

import com.microservice.share_service.model.Share;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShareRepository extends JpaRepository<Share, Long> {


    @Transactional
    void  deleteAllByAuthenticatedUserId(Long id);
}
