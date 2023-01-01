package plmatmaplus.matmapluspl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plmatmaplus.matmapluspl.entity.Role;
import plmatmaplus.matmapluspl.repository.RoleRepository;

@Service
public class RoleService {

    private final static int USER_ROLE_INDEX = 1;
    private final RoleRepository roleRepository;


    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }



}
