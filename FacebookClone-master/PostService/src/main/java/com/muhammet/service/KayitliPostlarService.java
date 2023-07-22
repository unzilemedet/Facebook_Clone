package com.muhammet.service;

import com.muhammet.repository.IKayitliPostlarRepository;
import com.muhammet.repository.entity.KayitliPostlar;
import com.muhammet.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class KayitliPostlarService extends ServiceManager<KayitliPostlar,String> {
    private final IKayitliPostlarRepository repository;

    public KayitliPostlarService(IKayitliPostlarRepository repository){
        super(repository);
        this.repository=repository;
    }
}
