package au.com.advent.securecontainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecureContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecureContainer.class);

    private static final String INPUT_CHALLENGE = "171309-643603";

    private static HashMap<Integer,Integer> sameDigitMapper = new HashMap<>();

    private static List<Integer> validPasswords = new ArrayList<>();

    public static void main(String[] args) {

        String[] inputRange = INPUT_CHALLENGE.split("-");

        int startRange = Integer.parseInt(inputRange[0]);
        int endRange = Integer.parseInt(inputRange[1]);

        for (; startRange <= endRange; startRange++) {

            if (isValidPassword(startRange)) {
                LOGGER.info("Valid password: {}", startRange);
                validPasswords.add(startRange);
            }
        }

        LOGGER.info("Total amount of valid passwords: {}", validPasswords.size());

    }

    private static boolean isValidPassword(int password) {

        // initialise same digit mapper
        for (int i = 0; i <= 9; i++) {
            sameDigitMapper.put(i,0);
        }

        boolean isValid = true;
        boolean sameDigits = false;

        List<Integer> digits = getDigits(password);

        for (int i = digits.size()-2; i >= 0; i--) {
            int firstDigit = digits.get(i+1);
            int secondDigit = digits.get(i);

            if (firstDigit == secondDigit) {
                sameDigitMapper.put(firstDigit, sameDigitMapper.get(firstDigit)+1);
//                sameDigits = true;
            } else {
                if (firstDigit > secondDigit) {
                    return false;
                }
            }
        }

        for (Map.Entry digit : sameDigitMapper.entrySet()) {
            if ((int) digit.getValue() == 1) {
                return true;
            }
        }

        return false;
    }

    private static List<Integer> getDigits(int password) {

        List<Integer> digits = new ArrayList<>();

        while (password > 0) {
            int digit = password % 10;
            password /= 10;

            digits.add(digit);
        }

        return digits;
    }
}
