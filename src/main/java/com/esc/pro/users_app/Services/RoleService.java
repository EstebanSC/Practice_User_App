package com.esc.pro.users_app.Services;

import com.esc.pro.users_app.Repositories.RoleRepository;
import com.esc.pro.users_app.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {


    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getRoles(){
        return roleRepository.findAll();
    }

    public Role createRole (Role role) {
        return roleRepository.save(role);
    }

    public Role updateRole (Integer roleId, Role role) {
        Optional<Role> result = roleRepository.findById(roleId);
        if(result.isPresent()) {
            return roleRepository.save(role);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Role ID %d doesnt exist", roleId));
        }
    }

    public void deleteRole(Integer roleId) {
        Optional<Role> result = roleRepository.findById(roleId);
        if(result.isPresent()) {
            roleRepository.delete(result.get());
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Role ID %d doesnt exist", roleId));
        }
    }
}
