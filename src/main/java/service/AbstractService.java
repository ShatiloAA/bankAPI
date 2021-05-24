package service;

import DAO.DAO;

/**
 * Абстрактная реализация интерфейса @Service, содержит поле DAO и конструктор принимающий необходимую реализацию DAO
 */

public abstract class AbstractService implements Service{
    protected DAO dao;

    public AbstractService(DAO dao) {
        this.dao = dao;
    }
}
