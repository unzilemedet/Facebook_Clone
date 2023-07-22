package com.muhammet.service;

import com.muhammet.repository.IPostRepository;
import com.muhammet.repository.IPostResimRepository;
import com.muhammet.repository.entity.PostResim;
import com.muhammet.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostResimService  extends ServiceManager<PostResim,String> {
    private final IPostResimRepository repository;

    public PostResimService(IPostResimRepository repository){
        super(repository);
        this.repository=repository;
    }

    public List<String> getUrlsByPostId(String postId){
        /**
         * Burada resimlerin listesi string olarak dönmüyor. enttiy olarak
         * datalar dönüyor. bu nedenle buradaki bilgileri bir String listesi haline
         * çevirmemiz gerekli.
         */
        List<PostResim> postResimList = repository.findAllByPostid(postId);
        /**
         * bir adet boş String listesi oluşturdum
         */
        List<String> urlList = new ArrayList<>();
        /**
         * ilgili post a ait resim listsi entity sini foreach ile dönerek
         * oluşturduğum listeye resimlerin url lerini tek tek ekledim.
         */
        postResimList.forEach(x->{
            urlList.add(x.getUrl());
        });
        return urlList;
    }
}
