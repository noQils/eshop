package id.ac.ui.cs.advprog.eshop.repository;

import java.util.*;

public abstract class AbstractRepository<T> implements ObjectCUDRepository<T>, ObjectReadRepository<T> {
    protected List<T> data = new ArrayList<>();

    @Override
    public T create(T object) {
        if (getId(object) == null) {
            UUID uuid = UUID.randomUUID();
            setId(object, uuid.toString());
        }
        data.add(object);
        return object;
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(data);
    }

    @Override
    public T findById(String id) {
        return data.stream()
                .filter(Objects::nonNull) // ignore null elements in the list
                .filter(o -> id.equals(getId(o)))
                .findFirst()
                .orElse(null);
    }

    @Override
    public T edit(String id, T updatedObject) {
        return data.stream()
                .filter(Objects::nonNull) // Ignore null elements in the list
                .filter(o -> id.equals(getId(o)))
                .findFirst()
                .map(o -> {
                    edit(o, updatedObject);
                    return o;
                })
                .orElse(null);
    }

    @Override
    public T delete(String id) {
        return data.stream()
                .filter(o -> id.equals(getId(o)))
                .findFirst()
                .map(o -> {
                    data.remove(o);
                    return o;
                })
                .orElse(null);
    }

    protected abstract String getId(T item);
    protected abstract void setId(T item, String id);
    protected abstract void edit(T existingItem, T newItem);
}
