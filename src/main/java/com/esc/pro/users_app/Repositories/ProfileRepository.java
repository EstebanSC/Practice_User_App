package com.esc.pro.users_app.Repositories;

import com.esc.pro.users_app.entities.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends CrudRepository<Profile,Integer> {

    @Query("SELECT p FROM Profile p WHERE p.user.id=?1 AND p.id=?2")
    public Optional<Profile> getByUserIdAndByProfileId(Integer userId, Integer profileId);
}
