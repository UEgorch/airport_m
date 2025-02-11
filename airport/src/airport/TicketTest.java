package airport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

class TicketTest {

    private Ticket ticket;

    @BeforeEach
    void setUp() {
        ticket = new Ticket();
    }

    @Test
    void testCalculateEconomyPriceNoSeasonalIncrease() {
        ticket.nameField.setText("Иван");
        ticket.countryComboBox.setSelectedIndex(0); // Беларусь
        ticket.cityComboBox.setSelectedIndex(0); // Минск
        ticket.economyClassRadioButton.setSelected(true);
        ticket.dateSpinner.setValue(java.sql.Date.valueOf(LocalDate.of(2024, Month.MAY, 15)));

        ticket.calculate();

        // Ожидаемая цена эконом-класса для Минска в Беларуси без надбавки
        assertEquals(100, ticket.getCalculatedPrice());
    }

    @Test
    void testCalculateBusinessPriceWithSeasonalIncrease() {
        ticket.nameField.setText("Анна");
        ticket.countryComboBox.setSelectedIndex(1); // Япония
        ticket.cityComboBox.setSelectedIndex(1); // Осака
        ticket.businessClassRadioButton.setSelected(true);
        ticket.dateSpinner.setValue(java.sql.Date.valueOf(LocalDate.of(2024, Month.JULY, 20)));

        ticket.calculate();

        // Ожидаемая цена бизнес-класса для Осака в Японии с 10% надбавкой
        assertEquals(550, ticket.getCalculatedPrice());
    }

    @Test
    void testCalculateEconomyPriceWithSeasonalIncrease() {
        ticket.nameField.setText("Сергей");
        ticket.countryComboBox.setSelectedIndex(2); // Казахстан
        ticket.cityComboBox.setSelectedIndex(2); // Шымкент
        ticket.economyClassRadioButton.setSelected(true);
        ticket.dateSpinner.setValue(java.sql.Date.valueOf(LocalDate.of(2024, Month.AUGUST, 5)));

        ticket.calculate();

        // Ожидаемая цена эконом-класса для Шымкента в Казахстане с 10% надбавкой
        assertEquals(154, ticket.getCalculatedPrice());
    }

    @Test
    void testEmptyNameFieldThrowsError() {
        ticket.nameField.setText(""); // Пустое имя
        ticket.countryComboBox.setSelectedIndex(0); // Беларусь
        ticket.cityComboBox.setSelectedIndex(0); // Минск
        ticket.economyClassRadioButton.setSelected(true);
        ticket.dateSpinner.setValue(java.sql.Date.valueOf(LocalDate.of(2024, Month.MAY, 15)));

  
        assertThrows(IllegalArgumentException.class, () -> {
            ticket.calculate();
        });
    }

    @Test
    void testCityComboBoxUpdateWithCountrySelection() {
        ticket.countryComboBox.setSelectedIndex(1); // Япония
        ticket.update();


        assertEquals("Токио", ticket.cityComboBox.getItemAt(0));
        assertEquals("Осака", ticket.cityComboBox.getItemAt(1));
        assertEquals("Киото", ticket.cityComboBox.getItemAt(2));
    }
}
