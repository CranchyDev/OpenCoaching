package com.opencoaching.opencoaching;

import com.opencoaching.opencoaching.modelos.CoachingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachingSessionRepository extends JpaRepository<CoachingSession, Long> {
    
}

