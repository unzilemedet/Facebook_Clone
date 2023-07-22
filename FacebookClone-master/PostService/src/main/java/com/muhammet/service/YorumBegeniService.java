package com.muhammet.service;

import com.muhammet.repository.IYorumBegeniRepository;
import com.muhammet.repository.entity.YorumBegeni;
import com.muhammet.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class YorumBegeniService  extends ServiceManager<YorumBegeni,String> {
    private final IYorumBegeniRepository repository;
    public YorumBegeniService(IYorumBegeniRepository repository){
        super(repository);
        this.repository=repository;
    }
}
