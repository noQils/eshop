package id.ac.ui.cs.advprog.eshop.service;

import java.util.List;

public interface ObjectGetService<T> {
    List<T> findAll();
    T findById(String id);
}
