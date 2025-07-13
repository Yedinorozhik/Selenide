package ru.netology;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;


public class CardDeliveryOrderFormsTest {

    @BeforeEach
    void setup() {
        Selenide.open("http://localhost:9999/");
    }

    private String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void positiveEntryForm() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String date = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT,Keys.HOME, Keys.DELETE)).setValue(date);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79508504334");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text("Встреча успешно забронирована на " + date));
    }

    @Test
    void entryFormWrongCity() {
        $("[data-test-id='city'] input").setValue("Ереван");
        String date = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT,Keys.HOME, Keys.DELETE)).setValue(date);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79508504334");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='city'].input_invalid .input__sub")
                .should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text("Доставка в выбранный город недоступна"));
    }

    @Test
    void entryFormEngCity() {
        $("[data-test-id='city'] input").setValue("Rostov-on-Don");
        String date = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT,Keys.HOME, Keys.DELETE)).setValue(date);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79508504334");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='city'].input_invalid .input__sub")
                .should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text("Доставка в выбранный город недоступна"));
    }

    @Test
    void entryFormWrongDate() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String date = generateDate(2, "dd.MM.yyyy");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT,Keys.HOME, Keys.DELETE)).setValue(date);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79508504334");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='date'] .input__sub")
                .should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text("Заказ на выбранную дату невозможен"));
    }

    @Test
    void entryFormWrongEngName() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String date = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT,Keys.HOME, Keys.DELETE)).setValue(date);
        $("[data-test-id='name'] input").setValue("Ivanov");
        $("[data-test-id='phone'] input").setValue("+79508504334");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='name'] .input__sub")
                .should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void entryFormWrongNameNumber() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String date = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT,Keys.HOME, Keys.DELETE)).setValue(date);
        $("[data-test-id='name'] input").setValue("1234 2345");
        $("[data-test-id='phone'] input").setValue("+79508504334");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='name'] .input__sub")
                .should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }
    @Test
    void entryFormWrongNameSymbol() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String date = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT,Keys.HOME, Keys.DELETE)).setValue(date);
        $("[data-test-id='name'] input").setValue("?:; №№");
        $("[data-test-id='phone'] input").setValue("+79508504334");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='name'] .input__sub")
                .should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void entryFormWrongPhone() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String date = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT,Keys.HOME, Keys.DELETE)).setValue(date);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+семь восемьдесят восемь");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='phone'] .input__sub")
                .should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void entryFormWrongPhoneSymbol() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String date = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT,Keys.HOME, Keys.DELETE)).setValue(date);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+$$33^^7&&947");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='phone'] .input__sub")
                .should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void entryFormWrong() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String date = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.DELETE)).setValue(date);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79508504334");
//        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='agreement'].input_invalid .checkbox__text")
                .should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

}
