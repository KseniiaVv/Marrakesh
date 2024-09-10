package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.CreditPage;
import ru.netology.page.PurchasePage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;


public class PurchaseTestCredit {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void setUp() {
        open("http://localhost:8080");
        SQLHelper.clearPaymentTable();
        SQLHelper.clearCreditTable();
    }

    // Заполнение формы «Выдача кредита по данным банковской карты» со статусом карты «APPROVED»
    @Test
    @DisplayName("Should approved card payment by credit")
    void shouldCreditPaymentApproved() {
        var cardinfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(cardinfo);
        form.paymentApproved();
        assertEquals("APPROVED", SQLHelper.getCreditRequestStatus());
    }


    // Заполнение формы «Выдача кредита по данным банковской карты» со статусом карты «DECLINED»

    @Test
    @DisplayName("Should declined payment by credit")
    void shouldCreditPaymentDeclined() {
        var cardinfo = new DataHelper.CardInfo(getDeclinedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(cardinfo);
        form.paymentDeclined();
        assertEquals("DECLINED", SQLHelper.getCreditRequestStatus());
    }


    //  Поле «Номер карты» не заполнено:
    @Test
    public void shouldCardIfEmptyByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getCardNumberIfEmpty());
        form.incorrectCardNumberVisible();
    }

    // Поле «Номер карты» заполнено нулями:
    @Test
    public void shouldCardNumberOfZerosByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getCardNumberOfZeros());
        form.incorrectCardNumberVisible();
    }

    // Заполнение поля «Номер карты» номером, состоящим из 1 цифры:
    @Test
    public void shouldCardNumberOfOneDigitByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getCardNumberOfOneDigit());
        form.incorrectCardNumberVisible();
    }

    // Заполнение поля «Номер карты» номером, состоящим из 15 цифр:
    @Test
    public void shouldCardNumberOfFifteenDigitsByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getCardNumberOfFifteenDigits());
        form.incorrectCardNumberVisible();
    }

    // Заполнение поля «Номер карты» номером, состоящим из 17 цифр:
    @Test
    public void shouldCardNumberOfSeventeenDigitsByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getCardNumberOfSeventeenDigits());
        form.paymentApproved();
    }

    // Заполнение поля «Номер карты»  номером карты, не зарегистрированным  в базе данных:
    @Test
    public void shouldCardNumberNotRegisteredByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getCardNumberNotRegistered());
        form.paymentDeclined();
    }

    // Заполнение поля «Номер карты» нечисловым значением:
    @Test
    public void shouldCardNonNumericValueByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getCardNumberNonNumericValue());
        form.incorrectCardNumberVisible();
    }

    // Поле «Месяц» не заполнено:
    @Test
    public void shouldMonthIfEmptyByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getMonthIfEmpty());
        form.incorrectMonthVisible("Неверный формат");
    }

    // аполнение поля «Месяц»  нулями:
    @Test
    public void shouldMonthWithZerosByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getMonthWithZeros());
        form.incorrectMonthVisible("Неверный формат");
    }

    // Заполнение поля «Месяц» несуществующим месяцем, граничные значения
    @Test
    public void shouldMonthIfNotExistBoundaryByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getMonthIfNotExistBoundary());
        form.incorrectMonthVisible("Неверно указан срок действия карты");
    }

    // Заполнение поля «Месяц» несуществующим месяцем:
    @Test
    public void shouldMonthIfNotExistByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getMonthIfNotExist());
        form.incorrectMonthVisible("Неверно указан срок действия карты");
    }

    // Заполнение поля «Месяц» значением из 1 цифры:
    @Test
    public void shouldMonthOfOneDigitByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getMonthOfOneDigit());
        form.incorrectMonthVisible("Неверный формат");
    }

    // Заполнение поля «Месяц» значением из 3 цифр:
    @Test
    public void shouldMonthOfThreeDigitsByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getMonthOfThreeDigits());
        form.paymentApproved();
    }

    //Заполнение поля «Месяц» нечисловым значением:
    @Test
    public void shouldMonthNonNumericValueByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getMonthNonNumericValue());
        form.incorrectMonthVisible("Неверный формат");
    }

    //  Поле  «Год» не заполнено:
    @Test
    public void shouldYearIfEmptyByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getYearIfEmpty());
        form.incorrectYearVisible("Неверный формат");
    }

    // Заполнение поля «Год» нулями:
    @Test
    public void shouldYearIfZerosByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getYearIfZeros());
        form.incorrectYearVisible("Истёк срок действия карты");
    }

    // Заполнение поля «Год» значением из 1 цифры:
    @Test
    public void shouldYearOfOneDigitByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getYearOfOneDigit());
        form.incorrectYearVisible("Неверный формат");
    }

    // Заполнение поля «Год» значением из 3 цифр:
    @Test
    public void shouldYearOfThreeDigitsByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getYearOfThreeDigits());
        form.paymentApproved();
    }

    // Заполнение поля «Год» значением прошедшего года:
    @Test
    public void shouldLastYearByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getLastYear());
        form.incorrectYearVisible("Истёк срок действия карты");
    }

    // Заполнение поля «Год» значением, на 10 лет превышающего текущий год:
    @Test
    public void shouldYear10YearsMoreByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getYear10YearsMore());
        form.incorrectYearVisible("Неверно указан срок действия карты");
    }

    // Заполнение поля «Год» нечисловым  значением:
    @Test
    public void shouldYearNonNumericValueByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getYearNonNumericValue());
        form.incorrectYearVisible("Неверный формат");
    }

    // Поле «Владелец» не заполнено:
    @Test
    public void shouldHolderIfEmptyByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getHolderIfEmpty());
        form.incorrectHolderVisible();
    }

    // Заполнение поля «Владелец» значением из 1 буквы:
    @Test
    public void shouldHolderOfOneLetterByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getHolderOfOneLetter());
        form.incorrectHolderVisible();
    }

    // Заполнение поля «Владелец» значением из 50 букв:
    @Test
    public void shouldHolderOfFiftyLettersByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getHolderOfFiftyLetters());
        form.incorrectHolderVisible();
    }

    // Заполнение поля «Владелец» значением на кириллице:
    @Test
    public void shouldHolderWithCyrillicByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getHolderWithCyrillic());
        form.incorrectHolderVisible();
    }

    // Заполнение поля «Владелец» цифрами:
    @Test
    public void shouldHolderWithDigitsByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getHolderWithDigits());
        form.incorrectHolderVisible();
    }

    //  Заполнение поля «Владелец» специальными символами:
    @Test
    public void shouldHolderSpecialSymbolsByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getHolderWithSpecialSymbols());
        form.incorrectHolderVisible();
    }

    // Заполнение поля «Владелец» арабской вязью:
    @Test
    public void shouldHolderArabicLigatureByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getHolderWithArabicLigature());
        form.incorrectHolderVisible();
    }

    // Заполнение поля «Владелец» иероглифами:
    @Test
    public void shouldHolderHieroglyphsByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getHolderWithHieroglyphs());
        form.incorrectHolderVisible();
    }

    // Не заполнение поля «CVC/CVV»:
    @Test
    public void shouldCVCCVVIfEmptyByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getCVCCVVIfEmpty());
        form.incorrectCodeVisible();
    }

    // Заполнение поля «CVC/CVV» значением из 1 цифры:
    @Test
    public void shouldCVCCVVOnOneDigitByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getCVCCVVOnOneDigit());
        form.incorrectCodeVisible();
    }

    // Заполнение поля «CVC/CVV» значением из 2 цифр:
    @Test
    public void shouldCVCCVVOnTwoDigitByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getCVCCVVOnTwoDigits());
        form.incorrectCodeVisible();
    }

    //  Заполнение поля «CVC/CVV» значением из 4 цифр:
    @Test
    public void shouldCVCCVVOnFourDigitByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getCVCCVVOnFourDigits());
        form.paymentApproved();
    }


    // Заполнение поля «CVC/CVV»  нечисловым значением:
    @Test
    public void shouldCVCCVVNonNumericValueByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getCVCCVVNonNumericValue());
        form.incorrectCodeVisible();
    }


    // Не заполнение формы «Оплата по карте»:
    @Test
    void shouldFormIfEmptyByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.emptyForm();
    }
}
