package id.ac.ui.cs.advprog.eshop.repository;

public interface ObjectCUDRepository<T> {
    public T create(T item);
    public T edit(String id, T updatedItem);
    public T delete(String id);
}
