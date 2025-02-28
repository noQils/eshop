package id.ac.ui.cs.advprog.eshop.service;

public interface ObjectPostService<T> {
    T create(T item);
    T edit(String id, T item);
    T delete(String id);
}