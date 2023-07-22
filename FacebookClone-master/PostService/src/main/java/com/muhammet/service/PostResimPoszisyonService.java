package com.muhammet.service;

import com.muhammet.repository.IPostResimPoszisyonRepository;
import com.muhammet.repository.entity.PostResimPozisyon;
import com.muhammet.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class PostResimPoszisyonService  extends ServiceManager<PostResimPozisyon,String> {
    private final IPostResimPoszisyonRepository repository;

    public PostResimPoszisyonService(IPostResimPoszisyonRepository repository){
        super(repository);
        this.repository=repository;
    }
}
