package main.java.conexao;


import main.java.dao.intefaces.Base;

import java.util.List;
import java.util.Optional;

public abstract class DAO<T, ID> implements Base<T, ID> {

    protected final DBConn conn;

    public DAO(DBConn conn) {
        this.conn = conn;
    }

    public Optional<T> buscarPeloId(ID id) {
        throw new UnsupportedOperationException();
    }

    public List<T> listar() {
        throw new UnsupportedOperationException();
    }

    public ID insert(T obj) {
        throw new UnsupportedOperationException();
    }

    public void deletarPeloId(ID id) {
        throw new UnsupportedOperationException();
    }

    public void update(T obj) {
        throw new UnsupportedOperationException();
    }

    public List<T> consulta(String sql) {
        throw new UnsupportedOperationException();
    }
}