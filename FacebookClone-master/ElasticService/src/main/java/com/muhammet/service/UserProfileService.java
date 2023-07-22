package com.muhammet.service;

import com.muhammet.dto.request.UserProfileDto;
import com.muhammet.mapper.IUserProfileMapper;
import com.muhammet.repository.IUserProfileRepository;
import com.muhammet.repository.entity.UserProfile;
import com.muhammet.utility.ServiceManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService extends ServiceManager<UserProfile,String> {
    private final IUserProfileRepository repository;
    public UserProfileService( IUserProfileRepository repository){
        super(repository);
        this.repository=repository;
    }

    public void save(UserProfileDto dto){
        UserProfile user = IUserProfileMapper.INSTANCE.toUserProfile(dto);
        save(user);
    }

    /**
     * Sayfalama işlemleri;
     * Bir kayıt bilgisinde çok fazla datanını olması nedeniyle bilgilerin belli
     * bir sayfalama yöntemi ile çağırılması.
     * Hangi Sayfadasın? Page
     * Bir Sayfada Kaç Kayıt Var? Size
     * Gösterdiğin Kayıtları Sıralayacak mısın? Sort
     */
    public Page<UserProfile> findAll(int currentPage,int size, String sortParameter,String sortDirection){
        /**
         * Sort  -> Sıralama işlemlerini belirtir.
         * Pageable -> Sayfalama işlemlerini belirtir.
         */
        Sort sort = null;
        Pageable pageable = null;

        if(sortParameter !=null)
            sort = Sort.by(Sort.Direction.fromString(sortDirection==null ? "ASC" : sortDirection),sortParameter);

        if(sort!=null){
            pageable = PageRequest.of(currentPage,size == 0 ? 10 : size, sort);
        }else{
            pageable = PageRequest.of(currentPage, size == 0 ? 10 : size);
        }
        return repository.findAll(pageable);
    }
}
