package repository;

import java.util.List;
import java.util.Optional;

public interface IRepository<T> {

    void create(T t) throws Exception;

    Optional<T> searchById(int id) throws Exception;

    List<T> listAll() throws Exception;

    void update(T t) throws Exception;

    void delete(int id) throws Exception;

}
