package security;


public class PasswordEncryptor {

    public static boolean checkPassword(String candidate, String password) {
        return candidate.equals(password);
    }
}
