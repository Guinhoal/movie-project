package main.java.dao.intefaces;

import main.java.cinema.Cinema;

import java.util.List;
import java.util.Optional;

public interface CinemaDAO extends Base<Cinema, Integer> {
    Optional<Cinema> buscarPeloId(Integer id);
    List<Cinema> listar();
    Integer insert(Cinema cinema);
    void deletarPeloId(Integer id);
    void update(Cinema cinema);
    List<Cinema> consulta(String sql);
}