package com.michal.services;


import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;
import java.util.List;

public interface GenericService<T, ID extends Serializable>  {

    default <S extends T> S save(S entity) {
        return getRepository().save(entity);
    }

    default T findOne(ID id) {
        return getRepository().findOne(id);
    }

    default List<T> findAll() {
        return (List<T>) getRepository().findAll();
    }

    default void delete(T entity) {
        getRepository().delete(entity);
    }

    default void deleteById(ID id){getRepository().delete(id);}

    default void delete(List<ID> ids) {ids.stream().filter(getRepository()::exists).forEach(getRepository()::delete);}

    PagingAndSortingRepository<T, ID> getRepository();


}
