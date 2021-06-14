package ru.netology.web;

import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import org.openqa.selenium.Keys;

public class CardDeliveryTest {

    @Test
    void tryCorrectFilledForm() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Казань");
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Чан Джеки");
        $("[data-test-id=phone] input").setValue("+78005553535");
        $(".checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=notification]").shouldHave(text("Встреча успешно забронирована на " + date), Duration.ofSeconds(15));
    }

    @Test
    void tryCorrectFilledFormWithHyphen() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Казань");
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Чан-Иванов Джеки");
        $("[data-test-id=phone] input").setValue("+78005553535");
        $(".checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=notification]").shouldHave(text("Встреча успешно забронирована на " + date), Duration.ofSeconds(15));
    }

    @Test
    void tryEmptyForm() {
        open("http://localhost:9999/");
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void tryIncorrectCity() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Пекин");
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Чан Джеки");
        $("[data-test-id=phone] input").setValue("+78005553535");
        $(".checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(text("Доставка в выбранный город недоступна"));
    }

    @Test
    void tryIncorrectDate() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Казань");
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Чан Джеки");
        $("[data-test-id=phone] input").setValue("+78005553535");
        $(".checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(text("Заказ на выбранную дату невозможен"));
    }

    @Test
    void tryWithoutDate() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Казань");
        String Date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=name] input").setValue("Чан Джеки");
        $("[data-test-id=phone] input").setValue("+78005553535");
        $(".checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(text("Неверно введена дата"));
    }

    @Test
    void tryIncorrectName() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Казань");
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Chan Jackie");
        $("[data-test-id=phone] input").setValue("+78005553535");
        $(".checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void tryWithoutName() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Казань");
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=phone] input").setValue("+78005553535");
        $(".checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void tryIncorrectPhone() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Казань");
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Чан Джеки");
        $("[data-test-id=phone] input").setValue("88005553535");
        $(".checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void tryWithoutPhone() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Казань");
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Чан Джеки");
        $(".checkbox__box").click();
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    void tryWithoutCheckbox() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Казань");
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Чан Джеки");
        $("[data-test-id=phone] input").setValue("+78005553535");
        $$("button").find(exactText("Забронировать")).click();
        $(".input_invalid .checkbox__text").shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}