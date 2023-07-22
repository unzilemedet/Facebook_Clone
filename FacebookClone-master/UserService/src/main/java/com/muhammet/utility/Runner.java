package com.muhammet.utility;

import com.muhammet.manager.IElasticServiceManager;
import com.muhammet.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Component olarak işaretlenen bu sınıf, Spring Context'i başlatıldığında çalışır.
 */
@Component
@RequiredArgsConstructor
public class Runner {
    private final UserProfileService userProfileService;
    private final IElasticServiceManager elasticServiceManager;

    /**
     * @PostConstruct anotasyonu ile işaretlenen bu
     * method bu sınıf tan bir nesne oluşturulurken çalışır. böylece
     * spring uygulaması ayağa kalkarken bu method çalışır.
     */
    ///@PostConstruct
    public void init(){
        userProfileService.findAll().forEach(x->{
            elasticServiceManager.save(x);
        });
    }
}
