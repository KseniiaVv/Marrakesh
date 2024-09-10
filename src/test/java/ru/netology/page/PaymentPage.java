package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;
import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class PaymentPage {
    private final SelenideElement paymentByCard = $(byText("Оплата по карте"));

    public PaymentPage() {
        paymentByCard.shouldBe(visible);
    }

    private SelenideElement cardNumber = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement month = $(byText("Месяц")).parent().$(".input__control");
    private SelenideElement year = $(byText("Год")).parent().$(".input__control");
    private SelenideElement holder = $(byText("Владелец")).parent().$(".input__control");
    private SelenideElement cvccvv = $(byText("CVC/CVV")).parent().$(".input__control");
    private SelenideElement button = $(byText("Продолжить"));

    private SelenideElement incorrectCardNumber = $(byText("Номер карты")).parent().$(".input__sub");
    private SelenideElement incorrectMonth = $(byText("Месяц")).parent().$(".input__sub");
    private SelenideElement incorrectYear = $(byText("Год")).parent().$(".input__sub");
    private SelenideElement incorrectHolder = $(byText("Владелец")).parent().$(".input__sub");
    private SelenideElement incorrectCode = $(byText("CVC/CVV")).parent().$(".input__sub");

    public void completedForm(DataHelper.CardInfo cardInfo) {
        cardNumber.setValue(cardInfo.getCardNumber());
        month.setValue(cardInfo.getMonth());
        year.setValue(cardInfo.getYear());
        holder.setValue(cardInfo.getHolder());
        cvccvv.setValue(cardInfo.getCVCCVV());
        button.click();
    }

    public void emptyForm() {
        button.click();
        incorrectCardNumberVisible();
        incorrectMonthVisible("Неверный формат");
        incorrectYearVisible("Неверный формат");
        incorrectHolderVisible();
        incorrectCodeVisible();
    }

    public void paymentApproved() {
        $(".notification_status_ok")
                .shouldBe(Condition.visible, Duration.ofSeconds(35))
                .$(byCssSelector(".notification__content"))
                .shouldHave(text("Операция одобрена Банком."));
    }

    public void paymentDeclined() {
        $(".notification_status_error").shouldBe(Condition.visible, Duration.ofSeconds(25));
    }

    public void incorrectCardNumberVisible() {
        incorrectCardNumber
                .shouldBe(Condition.visible)
                .shouldHave(text("Неверный формат"));
    }

    public void incorrectMonthVisible(String expectedText) {
        incorrectMonth.shouldBe(Condition.visible)
                .shouldHave(Condition.text(expectedText));
    }

    public void incorrectYearVisible(String expectedText) {
        incorrectYear.shouldBe(Condition.visible)
                .shouldHave(Condition.text(expectedText));
    }

    public void incorrectHolderVisible() {
        incorrectHolder
                .shouldBe(Condition.visible)
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    public void incorrectCodeVisible() {
        incorrectCode
                .shouldBe(Condition.visible)
                .shouldHave(text("Неверный формат"));
    }
}