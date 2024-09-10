package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.PaymentPage;
import ru.netology.page.PurchasePage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PurchaseTestPayment {
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

    // Заполнение формы «Оплата по  дебетовой карте» со статусом карты «APPROVED»
    @Test
    @DisplayName("Should approved card payment")
    void shouldCardPaymentApproved() {
        var cardinfo = new DataHelper.CardInfo(getApprovedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(cardinfo);
        form.paymentApproved();
        assertEquals("APPROVED", SQLHelper.getPaymentStatus());
    }


    // Заполнение формы «Оплата по дебетовой карте»  со статусом карты «DECLINED»

    @Test
    @DisplayName("Should declined payment")
    void shouldCardPaymentDeclined() {
        var cardinfo = new DataHelper.CardInfo(getDeclinedCardNumber(), getValidMonth(), getValidYear(), getValidHolder(), getValidCVCCVV());
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(cardinfo);
        form.paymentDeclined();
        assertEquals("DECLINED", SQLHelper.getPaymentStatus());
    }


    //  Поле «Номер карты» не заполнено:
    @Test
    public void shouldCardIfEmpty() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getCardNumberIfEmpty());
        form.incorrectCardNumberVisible();
    }

    // Поле «Номер карты» заполнено нулями:
    @Test
    public void shouldCardNumberOfZeros() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getCardNumberOfZeros());
        form.incorrectCardNumberVisible();
    }

    // Заполнение поля «Номер карты» номером, состоящим из 1 цифры:
    @Test
    public void shouldCardNumberOfOneDigit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getCardNumberOfOneDigit());
        form.incorrectCardNumberVisible();
    }

    // Заполнение поля «Номер карты» номером, состоящим из 15 цифр:
    @Test
    public void shouldCardNumberOfFifteenDigits() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getCardNumberOfFifteenDigits());
        form.incorrectCardNumberVisible();
    }

    // Заполнение поля «Номер карты» номером, состоящим из 17 цифр:
    @Test
    public void shouldCardNumberOfSeventeenDigits() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getCardNumberOfSeventeenDigits());
        form.paymentApproved();
    }

    // Заполнение поля «Номер карты»  номером карты, не зарегистрированным  в базе данных:
    @Test
    public void shouldCardNumberNotRegistered() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getCardNumberNotRegistered());
        form.paymentDeclined();
    }

    // Заполнение поля «Номер карты» нечисловым значением:
    @Test
    public void shouldCardNonNumericValue() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getCardNumberNonNumericValue());
        form.incorrectCardNumberVisible();
    }

    // Поле «Месяц» не заполнено:
    @Test
    public void shouldMonthIfEmpty() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getMonthIfEmpty());
        form.incorrectMonthVisible("Неверный формат");
    }

    // аполнение поля «Месяц»  нулями:
    @Test
    public void shouldMonthWithZeros() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getMonthWithZeros());
        form.incorrectMonthVisible("Неверный формат");
    }

    // Заполнение поля «Месяц» несуществующим месяцем, граничные значения
    @Test
    public void shouldMonthIfNotExistBoundary() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getMonthIfNotExistBoundary());
        form.incorrectMonthVisible("Неверно указан срок действия карты");
    }

    // Заполнение поля «Месяц» несуществующим месяцем:
    @Test
    public void shouldMonthIfNotExist() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getMonthIfNotExist());
        form.incorrectMonthVisible("Неверно указан срок действия карты");
    }

    // Заполнение поля «Месяц» значением из 1 цифры:
    @Test
    public void shouldMonthOfOneDigit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getMonthOfOneDigit());
        form.incorrectMonthVisible("Неверный формат");
    }

    // Заполнение поля «Месяц» значением из 3 цифр:
    @Test
    public void shouldMonthOfThreeDigits() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getMonthOfThreeDigits());
        form.paymentApproved();
    }

    //Заполнение поля «Месяц» нечисловым значением:
    @Test
    public void shouldMonthNonNumericValue() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getMonthNonNumericValue());
        form.incorrectMonthVisible("Неверный формат");
    }

    //  Поле  «Год» не заполнено:
    @Test
    public void shouldYearIfEmpty() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getYearIfEmpty());
        form.incorrectYearVisible("Неверный формат");
    }

    // Заполнение поля «Год» нулями:
    @Test
    public void shouldYearIfZeros() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getYearIfZeros());
        form.incorrectYearVisible("Истёк срок действия карты");
    }

    // Заполнение поля «Год» значением из 1 цифры:
    @Test
    public void shouldYearOfOneDigit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getYearOfOneDigit());
        form.incorrectYearVisible("Неверный формат");
    }

    // Заполнение поля «Год» значением из 3 цифр:
    @Test
    public void shouldYearOfThreeDigits() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getYearOfThreeDigits());
        form.paymentApproved();
    }

    // Заполнение поля «Год» значением прошедшего года:
    @Test
    public void shouldLastYear() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getLastYear());
        form.incorrectYearVisible("Истёк срок действия карты");
    }

    // Заполнение поля «Год» значением, на 10 лет превышающего текущий год:
    @Test
    public void shouldYear10YearsMore() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getYear10YearsMore());
        form.incorrectYearVisible("Неверно указан срок действия карты");
    }

    // Заполнение поля «Год» нечисловым  значением:
    @Test
    public void shouldYearNonNumericValue() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getYearNonNumericValue());
        form.incorrectYearVisible("Неверный формат");
    }

    // Поле «Владелец» не заполнено:
    @Test
    public void shouldHolderIfEmpty() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getHolderIfEmpty());
        form.incorrectHolderVisible();
    }

    // Заполнение поля «Владелец» значением из 1 буквы:
    @Test
    public void shouldHolderOfOneLetter() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getHolderOfOneLetter());
        form.incorrectHolderVisible();
    }

    // Заполнение поля «Владелец» значением из 50 букв:
    @Test
    public void shouldHolderOfFiftyLetters() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getHolderOfFiftyLetters());
        form.incorrectHolderVisible();
    }

    // Заполнение поля «Владелец» значением на кириллице:
    @Test
    public void shouldHolderWithCyrillic() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getHolderWithCyrillic());
        form.incorrectHolderVisible();
    }

    // Заполнение поля «Владелец» цифрами:
    @Test
    public void shouldHolderWithDigits() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getHolderWithDigits());
        form.incorrectHolderVisible();
    }

    //  Заполнение поля «Владелец» специальными символами:
    @Test
    public void shouldHolderSpecialSymbols() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getHolderWithSpecialSymbols());
        form.incorrectHolderVisible();
    }

    // Заполнение поля «Владелец» арабской вязью:
    @Test
    public void shouldHolderArabicLigature() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getHolderWithArabicLigature());
        form.incorrectHolderVisible();
    }

    // Заполнение поля «Владелец» иероглифами:
    @Test
    public void shouldHolderHieroglyphs() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getHolderWithHieroglyphs());
        form.incorrectHolderVisible();
    }

    // Не заполнение поля «CVC/CVV»:
    @Test
    public void shouldCVCCVVIfEmpty() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getCVCCVVIfEmpty());
        form.incorrectCodeVisible();
    }

    // Заполнение поля «CVC/CVV» значением из 1 цифры:
    @Test
    public void shouldCVCCVVOnOneDigit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getCVCCVVOnOneDigit());
        form.incorrectCodeVisible();
    }

    // Заполнение поля «CVC/CVV» значением из 2 цифр:
    @Test
    public void shouldCVCCVVOnTwoDigit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getCVCCVVOnTwoDigits());
        form.incorrectCodeVisible();
    }

    //  Заполнение поля «CVC/CVV» значением из 4 цифр:
    @Test
    public void shouldCVCCVVOnFourDigit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getCVCCVVOnFourDigits());
        form.paymentApproved();
    }


    // Заполнение поля «CVC/CVV»  нечисловым значением:
    @Test
    public void shouldCVCCVVNonNumericValue() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.completedForm(DataHelper.getCVCCVVNonNumericValue());
        form.incorrectCodeVisible();
    }


    // Не заполнение формы «Оплата по карте»:
    @Test
    void shouldFormIfEmpty() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCard();
        form.emptyForm();
    }
}