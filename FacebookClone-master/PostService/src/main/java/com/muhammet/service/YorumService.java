package com.muhammet.service;

import com.muhammet.repository.IYorumRepository;
import com.muhammet.repository.entity.Yorum;
import com.muhammet.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class YorumService  extends ServiceManager<Yorum,String> {
    private final IYorumRepository repository;

    public YorumService(IYorumRepository repository){
        super(repository);
        this.repository=repository;
    }
}
