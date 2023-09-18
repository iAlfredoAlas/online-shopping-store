package com.kodigo.shopping.online.store.service;

import java.util.List;

public interface CrudGenericService <T, ID> {

    public List<T> getAll();

    public T findById(Long id);

    public T add(T model);

    public T update(T model, Long id);

    public void deleteLog(Long id);

}
