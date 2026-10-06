package com.esc.pro.users_app.Repositories;

import com.esc.pro.users_app.entities.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends CrudRepository<Address,Integer> {
    @Query("SELECT a from Address a WHERE a.profile.user.id = ?1 AND a.profile.id = ?2")
    public List<Address> findByUserIdAndProfileId(Integer userId, Integer profileId);
}
