package zw.co.fasoft.utils;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
/**
 * @author Fasoft
 * @date 2/May/2024
 */
@Data
public class Unique_Credentials_Generation{
        public static String generateRandomPassword(int length) {
            String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
            return RandomStringUtils.random(length, characters);
        }
        public static String generateMerchantID(){
        return String.valueOf("MID"+String.valueOf(System.currentTimeMillis()).substring(7));
    }
	}