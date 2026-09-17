package com.esc.pro.users_app.Repositories;

import com.esc.pro.users_app.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    public Optional<User> findByUserName (String userName);

    /**
     This is not SQL is JPQL
     **/
    @Query("SELECT u.userName FROM User u WHERE u.userName like '%s'")
    public Page<String> findUserNames(Pageable pageable);
}
