package com.muhammet.utility;

import java.util.List;
import java.util.Optional;

/**
 *
 * @param <T> -> Entity nin adı Type
 * @param <ID> -> Entity nin @Id ile işaretlenmiş alanını DataType ı
 */
public interface Iservice<T, ID> {
    T save(T t);
    /**
     * Belli bir entity listesini kaydetmek için kullanırız. kayıt işleminden sonra
     * kaydedilen tüm kayıtların almış oldukları id ler ile  listesini döneriz.
     */
    Iterable<T> saveAll(Iterable<T> entities);
    T update(T t);
    void delete(T t);
    void deleteById(ID id);
    Optional<T> findById(ID id);
    List<T> findAll();
}
