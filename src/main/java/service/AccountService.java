package service;

import DAO.AccountDAO;
import model.Account;
import model.Balance;

import java.sql.SQLException;
import java.util.Objects;

/**
 * Сервис для работы со счетом.
 * Реализованный функционал:
 * 1) Увеличение баланса
 * 2) Проверка баланса
 */

public class AccountService extends AbstractService {
    public AccountService() {
        super(new AccountDAO());
    }

    /**
     * Метод увеличения баланса, находит и возвращает объект Account, увеличивает у него поле Balance и отправляет
     * обратно в базу обновленный объект, затем создает DTO Balance и возвращает его.
     *
     * @param accountID           принимает ID счета
     * @param replenishmentAmount сумма пополнения баланса
     * @return возвращает DTO Balance
     * @throws SQLException
     */

    public Balance increaseBalance(int accountID, double replenishmentAmount) throws SQLException {
        Balance balance = null;
        Account account = ((AccountDAO) dao).getOneById(accountID);
        if (Objects.nonNull(account)) {
            int id = account.getID();
            double newBalance = account.getBalance() + replenishmentAmount;
            account.setBalance(newBalance);
            if (((AccountDAO) dao).update(account) <= 0) {
                return null;
            } else {
                account = ((AccountDAO) dao).getOneById(accountID);
                balance = new Balance(account.getID(), account.getBalance());
                return balance;
            }
        } else {
            return balance;
        }


    }

    /**
     * Метод получения размера баланса
     *
     * @param accountID принимает ID счета
     * @return возвращает DTO Balance
     * @throws SQLException
     */

    public Balance getBalance(int accountID) throws SQLException {
        Account account = ((AccountDAO) dao).getOneById(accountID);
        return new Balance(account.getID(), account.getBalance());
    }
}
