package com.michal.services;


import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

public interface GenericService<T, ID extends Serializable>  {

    default <S extends T> S save(S entity) {
        return getRepository().save(entity);
    }

    default T findOne(ID id) {
        return getRepository().findOne(id);
    }

    default Iterable<T> findAll() {
        return getRepository().findAll();
    }

    default void delete(T entity) {
        getRepository().delete(entity);
    }

    PagingAndSortingRepository<T, ID> getRepository();
}
