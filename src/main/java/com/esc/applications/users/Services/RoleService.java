package com.esc.applications.users.Services;

import com.esc.applications.users.Repositories.RoleRepository;
import com.esc.applications.users.entities.Role;
import com.esc.applications.users.models.AuditDetails;
import com.esc.applications.users.models.SecurityRule;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@SecurityRule //Todos los metodos de esta clase deben estar asegurados.
public class RoleService {

    private static final Logger log = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    public List<Role> getRoles(){
        log.info("Getting Roles.");
        return roleRepository. findAll();
    }

    public Role createRole (Role role) {

        Role roleCreated = roleRepository.save(role);
        AuditDetails auditDetails = new AuditDetails(SecurityContextHolder.getContext().getAuthentication().getName(), role.getName());
        try{
            kafkaTemplate.send("practice_users-topic", objectMapper.writeValueAsString(auditDetails));
        }catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error parsind message.");
        }
        return roleCreated;
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
