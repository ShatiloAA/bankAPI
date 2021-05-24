package DAO;

import model.AbstractDTO;

import java.sql.SQLException;

public abstract class AbstractDAO implements DAO {
    /**
     * Возвращает объект расширяющий AbstractDTO по ID
     *
     * @param ID  принимает ID по которому осуществляется поиск в БД
     * @param <T> объект расширяющий AbstractDTO
     * @return возвращает объект расширяющий AbstractDTO
     * @throws SQLException
     */
    abstract <T extends AbstractDTO> T getOneById(int ID) throws SQLException;

    /**
     * Создает строку в БД по ID из DTO, если не переопределен в наследнике, то по умолчанию возвращает -1
     *
     * @param dto принимает реализацию dto
     * @throws SQLException
     */

    int create(AbstractDTO dto) throws SQLException {
        return -1;
    }

    /**
     * Обновляет строку в БД по ID из DTO, если не переопределен в наследнике, то по умолчанию возвращает -1
     *
     * @param dto принимает реализацию dto
     * @throws SQLException
     */

    int update(AbstractDTO dto) throws SQLException {
        return -1;
    }
}
