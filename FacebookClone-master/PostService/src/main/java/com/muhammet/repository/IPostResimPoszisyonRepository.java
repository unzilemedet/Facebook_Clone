package com.muhammet.repository;

import com.muhammet.repository.entity.PostResimPozisyon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPostResimPoszisyonRepository extends MongoRepository<PostResimPozisyon,String> {
}
