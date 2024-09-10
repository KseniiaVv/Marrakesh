package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static java.lang.String.format;

public class DataHelper {
    public static final Faker faker = new Faker(new Locale("en"));

    private DataHelper() {
    }

    public static String getApprovedCardNumber() {
        return ("4444 4444 4444 4441");
    }

    public static String getDeclinedCardNumber() {
        return ("4444 4444 4444 4442");
    }

    public static String getValidMonth() {
        LocalDate localDate = LocalDate.now();
        return format("%02d", localDate.getMonthValue());
    }

    public static String getValidYear() {
        return LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getValidHolder() {
        return faker.name().firstName();
    }

    public static String getValidCVCCVV() {
        int result = (int) (Math.random() * 999) + 1;
        return String.format("%03d", result);
    }

    //Поле "Номер карты":

    //Не заполнение поля
    public static CardInfo getCardNumberIfEmpty() {
        return new CardInfo("", getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля номером, состоящим из нулей
    public static CardInfo getCardNumberOfZeros() {
        return new CardInfo("0000000000000000", getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля номером, состоящим из 1 цифры
    public static CardInfo getCardNumberOfOneDigit() {
        return new CardInfo("5", getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
    }


    //Заполнение поля номером, состоящим из 15 цифр
    public static CardInfo getCardNumberOfFifteenDigits() {
        return new CardInfo("123412341234123", getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля номером, состоящим из 17 цифр
    public static CardInfo getCardNumberOfSeventeenDigits() {
        return new CardInfo("12341234123412341", getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
    }


    //Заполнение поля номером, не зарегистрированным в базе данных
    public static CardInfo getCardNumberNotRegistered() {
        return new CardInfo("1234123412341234", getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля нечисловым значением
    public static CardInfo getCardNumberNonNumericValue() {
        return new CardInfo("картакартакарта!", getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
    }


    //Поле "Месяц":

    //Не заполнение поля
    public static CardInfo getMonthIfEmpty() {
        return new CardInfo(getApprovedCardNumber(), "", getValidYear(), getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля нулями
    public static CardInfo getMonthWithZeros() {
        return new CardInfo(getApprovedCardNumber(), "00", getValidYear(), getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля несуществующим месяцем, граничное значение
    public static CardInfo getMonthIfNotExistBoundary() {
        return new CardInfo(getApprovedCardNumber(), "13", getValidYear(), getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля несуществующим месяцем
    public static CardInfo getMonthIfNotExist() {
        return new CardInfo(getApprovedCardNumber(), "14", getValidYear(), getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля значением из 1 цифры
    public static CardInfo getMonthOfOneDigit() {
        return new CardInfo(getApprovedCardNumber(), "5", getValidYear(), getValidHolder(), getValidCVCCVV());
    }


    //Заполнение поля значением из 3 цифр
    public static CardInfo getMonthOfThreeDigits() {
        return new CardInfo(getApprovedCardNumber(), "111", getValidYear(), getValidHolder(), getValidCVCCVV());
    }


    //Заполнение поля нечисловым значением
    public static CardInfo getMonthNonNumericValue() {
        return new CardInfo(getApprovedCardNumber(), "л!", getValidYear(), getValidHolder(), getValidCVCCVV());
    }


    //Поле "Год":

    //Не заполнение поля
    public static CardInfo getYearIfEmpty() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "", getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля нулями
    public static CardInfo getYearIfZeros() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "00", getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля значением из 1 цифры
    public static CardInfo getYearOfOneDigit() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "5", getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля значением из 3 цифр
    public static CardInfo getYearOfThreeDigits() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "277", getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля значением прошедшего года
    public static CardInfo getLastYear() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "10", getValidHolder(), getValidCVCCVV());
    }

    //Заполнение поля значением, на 10 лет превышающего текущий год
    public static CardInfo getYear10YearsMore() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "34", getValidHolder(), getValidCVCCVV());
    }


    //Заполнение поля нечисловым значением
    public static CardInfo getYearNonNumericValue() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), "!д", getValidHolder(), getValidCVCCVV());
    }


    //Поле "Владелец":

    //Не заполнение поля
    public static CardInfo getHolderIfEmpty() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "", getValidCVCCVV());
    }

    //Заполнение поля значением из 1 буквы
    public static CardInfo getHolderOfOneLetter() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "A", getValidCVCCVV());
    }

    //Заполнение поля значением из 50 букв
    public static CardInfo getHolderOfFiftyLetters() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA", getValidCVCCVV());
    }

    //Заполнение поля кирилицей
    public static CardInfo getHolderWithCyrillic() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "Александр Горкький)", getValidCVCCVV());
    }

    //Заполнение поля цифрами
    public static CardInfo getHolderWithDigits() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "555", getValidCVCCVV());
    }

    //Заполнение поля с использованием специальных символов
    public static CardInfo getHolderWithSpecialSymbols() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "!!!", getValidCVCCVV());
    }

    //Заполнение поля с использованием арабской вязи
    public static CardInfo getHolderWithArabicLigature() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "ﺞﺠ)", getValidCVCCVV());
    }

    //Заполнение поля с использованием иероглифов
    public static CardInfo getHolderWithHieroglyphs() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), "葉号", getValidCVCCVV());
    }



    //Поле "CVC/CVV":

    //Не заполнение поля
    public static CardInfo getCVCCVVIfEmpty() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), "");
    }

    //Заполнение поля значением из 1 цифры
    public static CardInfo getCVCCVVOnOneDigit() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), "5");
    }

    //Заполнение поля значением из 2 цифр
    public static CardInfo getCVCCVVOnTwoDigits() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), "55");
    }

    //Заполнение поля значением из 4 цифр
    public static CardInfo getCVCCVVOnFourDigits() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), "5551");
    }


    //Заполнение поля нечисловым значением
    public static CardInfo getCVCCVVNonNumericValue() {
        return new CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), "ммм");
    }



    //Реквизиты карты:
    @Value
    public static class CardInfo {
        String cardNumber;
        String month;
        String year;
        String holder;
        String CVCCVV;
    }
}