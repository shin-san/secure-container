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
//    private static final String INPUT_CHALLENGE = "134564-585159";
//    private static final String INPUT_CHALLENGE = "111110-111122";

    private static HashMap<Integer,Integer> sameDigitMapper = new HashMap<>();

    private static List<Integer> validPasswords = new ArrayList<>();

    public static void main(String[] args) {

        /*
         * Maximum 6 digits
         * Value within the input range
         * Two adjacent digits are the same
         * Digits never decrease from left to right
         */

        String[] inputRange = INPUT_CHALLENGE.split("-");

        int startRange = Integer.parseInt(inputRange[0]);
        int endRange = Integer.parseInt(inputRange[1]);

        for (; startRange <= endRange; startRange++) {

            /*
             * Set a boolean to determine which part needs to be run
             * true = run Part 1
             * false = run Part 2
             */
            if (isValidPassword(startRange, false)) {
                LOGGER.debug("Valid password: {}", startRange);
                validPasswords.add(startRange);
            }
        }

        LOGGER.info("Total amount of valid passwords: {}", validPasswords.size());

    }

    private static boolean isValidPassword(int password, boolean isPart1) {

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
            } else {
                if (firstDigit > secondDigit) {
                    return false;
                }
            }
        }

        for (Map.Entry<Integer,Integer> sameDigit : sameDigitMapper.entrySet()) {
            if (isPart1) {
                if (sameDigit.getValue() >= 1) {
                    return true;
                }
            } else {
                if (sameDigit.getValue() == 1) {
                    return true;
                }
            }
        }

        return false;
    }

    private static List<Integer> getDigits(int password) {

        List<Integer> digits = new ArrayList<>();

        while (password > 0) {
            digits.add(password % 10);
            password /= 10;
        }
        return digits;
    }
}
