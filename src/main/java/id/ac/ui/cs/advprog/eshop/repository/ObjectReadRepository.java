package id.ac.ui.cs.advprog.eshop.repository;

import java.util.List;

public interface ObjectReadRepository<T> {
    public List<T> findAll();
    public T findById(String id);
}
