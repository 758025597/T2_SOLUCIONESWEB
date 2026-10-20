package com.alvarez_asencio.service.implementation;

import com.alvarez_asencio.repository.IGenericRepository;
import com.alvarez_asencio.service.IGenericService;

import java.util.List;
import java.util.NoSuchElementException;

public abstract class GenericService<T, ID> implements IGenericService<T, ID> {

    protected abstract IGenericRepository<T, ID> getRepo();

    @Override
    public T save(T t) throws Exception {
        return getRepo().save(t);
    }

    @Override
    public T update(ID id, T t) throws Exception {
        // La subclase/controlador asigna el id a la entidad antes de llamar a update
        findById(id);
        return getRepo().save(t);
    }

    @Override
    public List<T> findAll() throws Exception {
        return getRepo().findAll();
    }

    @Override
    public T findById(ID id) throws Exception {
        return getRepo().findById(id)
                .orElseThrow(() -> new NoSuchElementException("Recurso no encontrado con id: " + id));
    }

    @Override
    public void delete(ID id) throws Exception {
        findById(id);
        getRepo().deleteById(id);
    }
}
