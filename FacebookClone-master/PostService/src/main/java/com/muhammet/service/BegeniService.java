package com.muhammet.service;

import com.muhammet.repository.IBegeniRepository;
import com.muhammet.repository.entity.Begeni;
import com.muhammet.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class BegeniService extends ServiceManager<Begeni,String> {
    private final IBegeniRepository repository;
    public BegeniService(IBegeniRepository repository){
        super(repository);
        this.repository=repository;
    }
}
