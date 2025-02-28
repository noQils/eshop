package id.ac.ui.cs.advprog.eshop.service;

import java.util.List;

public interface ObjectReadService<T> {
    List<T> findAll();
    T findById(String id);
}
