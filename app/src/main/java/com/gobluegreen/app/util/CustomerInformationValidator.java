package com.gobluegreen.app.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by David on 7/12/17.
 */

public class CustomerInformationValidator {

    private static final String regexPattern = "[a-zA-Z0-9\\s!@#$%\\^&\\*\\(\\)_+-=\\[\\]{}\\\\ OR;:'\",.?<>/'~]{1,120}";
    private static final Pattern FIVE_DIGIT_ZIP = Pattern.compile("[0-9]{5}");
    private static final Pattern NINE_DIGIT_ZIP = Pattern.compile("[0-9]{9}");


    public static boolean isValidCharacters(String validateString) {

        if (StringUtils.isEmpty(validateString)) {
            return false;
        }

        return matches(validateString, regexPattern);
    }

    public static boolean isValidPostalCode(String postalCode) {
        return FIVE_DIGIT_ZIP.matcher(postalCode).matches() || NINE_DIGIT_ZIP.matcher(postalCode).matches();
    }

    private static boolean matches(String toValidate, String regexPattern) {
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(toValidate);
        return matcher.matches();
    }

    public static boolean isInvalidPhoneNumber(String phoneNumber) {
        if (StringUtils.isEmpty(phoneNumber)){
            return false;
        }

        Pattern sevenDigits = Pattern.compile("[0-9\\s\\\\(\\)-\\\\ ]{14}");
        Pattern tenDigits = Pattern.compile("[0-9\\s\\\\(\\)-\\\\ ]{8}");

        Matcher matcherSeven = sevenDigits.matcher(phoneNumber);
        Matcher matcherTen = tenDigits.matcher(phoneNumber);

        return matcherSeven.matches() || matcherTen.matches();

    }
}
