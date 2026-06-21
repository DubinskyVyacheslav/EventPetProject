package util;

import lombok.experimental.UtilityClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@UtilityClass
public class CipherPasswordBCrypt extends BCrypt {

    public String hashingPassword(String password) {

        Properties saltProperties = new Properties();

        try (FileInputStream myFile = new FileInputStream("src/main/resources/key.properties")) {
            saltProperties.load(myFile);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        String salt = saltProperties.getProperty("regularSalt");

        return BCrypt.hashpw(password, salt);

    }

}