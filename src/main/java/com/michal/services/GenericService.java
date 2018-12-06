package com.michal.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface GenericService<T, ID extends Serializable>  {

    default <S extends T> S save(S entity) {
        return getRepository().save(entity);
    }

    default Optional<T> findOne(ID id) {
        return getRepository().findById(id);
    }

    default List<T> findAll() {
        return (List<T>) getRepository().findAll();
    }

    default void delete(T entity) {
        getRepository().delete(entity);
    }

    default void deleteById(ID id){getRepository().deleteById(id);}

    default void delete(List<ID> ids) {ids.stream().filter(getRepository()::existsById).forEach(getRepository()::deleteById);}

    default Page<T> findAllPaginated(Pageable pageable){
        return getRepository().findAll(pageable);
    }

    PagingAndSortingRepository<T, ID> getRepository();


}
