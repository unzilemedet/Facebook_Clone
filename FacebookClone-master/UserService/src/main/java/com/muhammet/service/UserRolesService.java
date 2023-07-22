package com.muhammet.service;

import com.muhammet.repository.IUserRolesRepository;
import com.muhammet.repository.entity.UserRoles;
import com.muhammet.utility.ServiceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class UserRolesService extends ServiceManager<UserRoles,String> {
    private final IUserRolesRepository repository;
    public UserRolesService(IUserRolesRepository repository){
        super(repository);
        this.repository=repository;
    }

}
