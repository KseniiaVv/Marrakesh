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
    @DisplayName ("Should display an error if card empty ")
    public void shouldCardIfEmptyByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getCardNumberIfEmpty());
        form.incorrectCardNumberVisible();
    }

    // Поле «Номер карты» заполнено нулями:
    @Test
    @DisplayName ("Should display an error if card number of Zeros ")
    public void shouldCardNumberOfZerosByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getCardNumberOfZeros());
        form.incorrectCardNumberVisible();
    }

    // Заполнение поля «Номер карты» номером, состоящим из 1 цифры:
    @Test
    @DisplayName ("Should display an error if card number of one digit")
    public void shouldCardNumberOfOneDigitByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getCardNumberOfOneDigit());
        form.incorrectCardNumberVisible();
    }

    // Заполнение поля «Номер карты» номером , состоящим из 15 цифр:
    @Test
    @DisplayName ("Should display an error if card number of fifteen digit")
    public void shouldCardNumberOfFifteenDigitsByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getCardNumberOfFifteenDigits());
        form.incorrectCardNumberVisible();
    }

    // Заполнение поля «Номер карты» номером, состоящим из 17 цифр:
    @Test
    @DisplayName ("Should display an error if card number of seventeen digit")
    public void shouldCardNumberOfSeventeenDigitsByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getCardNumberOfSeventeenDigits());
        form.paymentApproved();
    }

    // Заполнение поля «Номер карты»  номером карты, не зарегистрированным  в базе данных:
    @Test
    @DisplayName ("Should display an error if card number not registered")
    public void shouldCardNumberNotRegisteredByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getCardNumberNotRegistered());
        form.paymentDeclined();
    }

    // Заполнение поля «Номер карты» нечисловым значением:
    @Test
    @DisplayName ("Should display an error if card non numeric value")
    public void shouldCardNonNumericValueByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getCardNumberNonNumericValue());
        form.incorrectCardNumberVisible();
    }

    // Поле «Месяц» не заполнено:
    @Test
    @DisplayName ("Should display an error if month is empty")
    public void shouldMonthIfEmptyByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getMonthIfEmpty());
        form.incorrectMonthVisible("Неверный формат");
    }

    // аполнение поля «Месяц»  нулями:
    @Test
    @DisplayName ("Should display an error if month  with zeros")
    public void shouldMonthWithZerosByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getMonthWithZeros());
        form.incorrectMonthVisible("Неверный формат");
    }

    // Заполнение поля «Месяц» несуществующим месяцем, граничные значения
    @Test
    @DisplayName ("Should display an error if month not exist boundary")
    public void shouldMonthIfNotExistBoundaryByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getMonthIfNotExistBoundary());
        form.incorrectMonthVisible("Неверно указан срок действия карты");
    }

    // Заполнение поля «Месяц» несуществующим месяцем:
    @Test
    @DisplayName ("Should display an error if month not exist")
    public void shouldMonthIfNotExistByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getMonthIfNotExist());
        form.incorrectMonthVisible("Неверно указан срок действия карты");
    }

    // Заполнение поля «Месяц» значением из 1 цифры:
    @Test
    @DisplayName ("Should display an error if month of one digit")
    public void shouldMonthOfOneDigitByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getMonthOfOneDigit());
        form.incorrectMonthVisible("Неверный формат");
    }

    // Заполнение поля «Месяц» значением из 3 цифр:
    @Test
    @DisplayName ("Should display an error if month of three digit")
    public void shouldMonthOfThreeDigitsByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getMonthOfThreeDigits());
        form.paymentApproved();
    }

    //Заполнение поля «Месяц» нечисловым значением:
    @Test
    @DisplayName ("Should display an error if month non numeric value")
    public void shouldMonthNonNumericValueByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getMonthNonNumericValue());
        form.incorrectMonthVisible("Неверный формат");
    }

    //  Поле  «Год» не заполнено:
    @Test
    @DisplayName ("Should display an error if year is empty")
    public void shouldYearIfEmptyByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getYearIfEmpty());
        form.incorrectYearVisible("Неверный формат");
    }

    // Заполнение поля «Год» нулями:
    @Test
    @DisplayName ("Should display an error if year with zeros")
    public void shouldYearIfZerosByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getYearIfZeros());
        form.incorrectYearVisible("Истёк срок действия карты");
    }

    // Заполнение поля «Год» значением из 1 цифры:
    @Test
    @DisplayName ("Should display an error if year is one digit")
    public void shouldYearOfOneDigitByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getYearOfOneDigit());
        form.incorrectYearVisible("Неверный формат");
    }

    // Заполнение поля «Год» значением из 3 цифр:
    @Test
    @DisplayName ("Should display an error if year is three digit")
    public void shouldYearOfThreeDigitsByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getYearOfThreeDigits());
        form.paymentApproved();
    }

    // Заполнение поля «Год» значением прошедшего года:
    @Test
    @DisplayName ("Should display an error if last year")
    public void shouldLastYearByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getLastYear());
        form.incorrectYearVisible("Истёк срок действия карты");
    }

    // Заполнение поля «Год» значением, на 10 лет превышающего текущий год:
    @Test
    @DisplayName ("Should display an error if  year 10years more")
    public void shouldYear10YearsMoreByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getYear10YearsMore());
        form.incorrectYearVisible("Неверно указан срок действия карты");
    }

    // Заполнение поля «Год» нечисловым  значением:
    @Test
    @DisplayName ("Should display an error if  year is non numeric value")
    public void shouldYearNonNumericValueByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getYearNonNumericValue());
        form.incorrectYearVisible("Неверный формат");
    }

    // Поле «Владелец» не заполнено:
    @Test
    @DisplayName ("Should display an error if  holder empty")
    public void shouldHolderIfEmptyByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getHolderIfEmpty());
        form.incorrectHolderVisible();
    }

    // Заполнение поля «Владелец» значением из 1 буквы:
    @Test
    @DisplayName ("Should display an error if  holder one letter")
    public void shouldHolderOfOneLetterByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getHolderOfOneLetter());
        form.incorrectHolderVisible();
    }

    // Заполнение поля «Владелец» значением из 50 букв:
    @Test
    @DisplayName ("Should display an error if  holder fifty letter")
    public void shouldHolderOfFiftyLettersByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getHolderOfFiftyLetters());
        form.incorrectHolderVisible();
    }

    // Заполнение поля «Владелец» значением на кириллице:
    @Test
    @DisplayName ("Should display an error if  holder with cyrillic")
    public void shouldHolderWithCyrillicByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getHolderWithCyrillic());
        form.incorrectHolderVisible();
    }

    // Заполнение поля «Владелец» цифрами:
    @Test
    @DisplayName ("Should display an error if  holder with digits")
    public void shouldHolderWithDigitsByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getHolderWithDigits());
        form.incorrectHolderVisible();
    }

    //  Заполнение поля «Владелец» специальными символами:
    @Test
    @DisplayName ("Should display an error if  holder with special symbols")
    public void shouldHolderSpecialSymbolsByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getHolderWithSpecialSymbols());
        form.incorrectHolderVisible();
    }

    // Заполнение поля «Владелец» арабской вязью:
    @Test
    @DisplayName ("Should display an error if  holder with arabic ligature")
    public void shouldHolderArabicLigatureByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getHolderWithArabicLigature());
        form.incorrectHolderVisible();
    }

    // Заполнение поля «Владелец» иероглифами:
    @Test
    @DisplayName ("Should display an error if  holder with hieroglyphs")
    public void shouldHolderHieroglyphsByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getHolderWithHieroglyphs());
        form.incorrectHolderVisible();
    }

    // Не заполнение поля «CVC/CVV»:
    @Test
    @DisplayName ("Should display an error if CVC/CVV empty")
    public void shouldCVCCVVIfEmptyByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getCVCCVVIfEmpty());
        form.incorrectCodeVisible();
    }

    // Заполнение поля «CVC/CVV» значением из 1 цифры:
    @Test
    @DisplayName ("Should display an error if CVC/CVV is one digit")
    public void shouldCVCCVVOnOneDigitByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getCVCCVVOnOneDigit());
        form.incorrectCodeVisible();
    }

    // Заполнение поля «CVC/CVV» значением из 2 цифр:
    @Test
    @DisplayName ("Should display an error if CVC/CVV is two digit")
    public void shouldCVCCVVOnTwoDigitByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getCVCCVVOnTwoDigits());
        form.incorrectCodeVisible();
    }

    //  Заполнение поля «CVC/CVV» значением из 4 цифр:
    @Test
    @DisplayName ("Should display an error if CVC/CVV is four digit")
    public void shouldCVCCVVOnFourDigitByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getCVCCVVOnFourDigits());
        form.paymentApproved();
    }


    // Заполнение поля «CVC/CVV»  нечисловым значением:
    @Test
    @DisplayName ("Should display an error if CVC/CVV non numeric value")
    public void shouldCVCCVVNonNumericValueByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.completedForm(DataHelper.getCVCCVVNonNumericValue());
        form.incorrectCodeVisible();
    }


    // Не заполнение формы «Оплата по карте»:
    @Test
    @DisplayName ("Should declined payment if form is empty")
    void shouldFormIfEmptyByCredit() {
        var purchasepage = new PurchasePage();
        var form = purchasepage.buyByCreditCard();
        form.emptyForm();
    }
}
