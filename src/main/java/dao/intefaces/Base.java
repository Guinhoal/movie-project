package main.java.dao.intefaces;

import java.util.List;
import java.util.Optional;

public interface Base<T, ID> {
    Optional<T> buscarPeloId(ID id);
    List<T> listar();
    ID insert(T obj);
    void deletarPeloId(ID id);
    void update(T obj);
    List<T> consulta(String sql);
}
