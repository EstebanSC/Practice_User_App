package com.esc.applications.users.Repositories;

import com.esc.applications.users.entities.UserInRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.esc.applications.users.entities.User;

import java.util.List;
import java.util.Optional;


public interface UserInRoleRepository extends CrudRepository<UserInRole,Integer> {

    @Query("SELECT u FROM UserInRole u where u.role.id = ?1")
    Optional<List<UserInRole>> getUserInRoleByRoleId(Integer roleId);

    List<UserInRole> findByUser(User user);
}
