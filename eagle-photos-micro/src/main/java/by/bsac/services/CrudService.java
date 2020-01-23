package by.bsac.services;

public interface CrudService<T, ID> {

    T create(T entity);

    T get(ID entity_id);

    void delete(T entity);

}
