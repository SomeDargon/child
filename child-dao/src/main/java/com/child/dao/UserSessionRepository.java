package com.child.dao;

import com.child.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession,Long> {

    UserSession findByAuthToken(String authToken);

}
