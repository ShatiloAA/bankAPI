package security;

import com.sun.net.httpserver.BasicAuthenticator;
import service.UserService;

/**
 * Класс расширяет BasicAuthenticator для реализации аутентефикации в API.
 * Конструктор принимает текущий Realm, задается resources\configuration.properties
 */
public class Authenticator extends BasicAuthenticator {
    private final UserService userService = new UserService();

    public Authenticator(String realm) {
        super(realm);
    }

    /**
     * Метод принимает логин и пароль в виде String.
     * @param login не зашифрован
     * @param password принимается зашифрованным при помощи Base64 в виде String
     * @return
     */
    @Override
    public boolean checkCredentials(String login, String password) {
        boolean result = userService.authentication(login, password);
        return result;
    }

}
