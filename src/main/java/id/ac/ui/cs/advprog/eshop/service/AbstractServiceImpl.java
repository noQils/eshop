package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.repository.AbstractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

public abstract class AbstractServiceImpl<T, Repository extends AbstractRepository<T>> implements ObjectCUDService<T>, ObjectReadService<T> {
    protected final Repository repository;

    @Autowired
    public AbstractServiceImpl(Repository repository) {
        this.repository = repository;
    }

    @Override
    public T create(T object) {
        return repository.create(object);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T findById(String id) {
        return repository.findById(id);
    }

    @Override
    public T edit(String id, T updatedObject) {
        return repository.edit(id, updatedObject);
    }

    @Override
    public T delete(String id) {
        return repository.delete(id);
    }
}
