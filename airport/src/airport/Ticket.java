package airport;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Month;

public class Ticket extends JFrame {

    private static final long serialVersionUID = 1L;
    JPanel contentPane;
    JTextField nameField;
    JComboBox<String> cityComboBox;
    JComboBox<String> countryComboBox;
    JRadioButton businessClassRadioButton;
    JRadioButton economyClassRadioButton;
    JSpinner dateSpinner;

    private String[] countries = {"Беларусь", "Япония", "Казахстан"};
    private String[][] cities = {
        {"Минск", "Гродно", "Брест"},
        {"Токио", "Осака", "Киото"},
        {"Астана", "Алматы", "Шымкент"}
    };
    private double[][] economyPrice = {
        {100, 120, 110},
        {200, 250, 230},
        {150, 160, 140}
    };
    private double[][] businessPrice = {
        {200, 240, 220},
        {400, 500, 460},
        {300, 320, 280}
    };

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Ticket frame = new Ticket();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Ticket() {
        setTitle("Аэропорт");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 450);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel nameLabel = new JLabel("Имя:");
        nameLabel.setBounds(10, 20, 80, 20);
        contentPane.add(nameLabel);

        nameField = new JTextField();
        nameField.setToolTipText("Введите ваше имя");
        nameField.setBounds(100, 20, 150, 20);
        contentPane.add(nameField);
        nameField.setColumns(10);

        JLabel countryLabel = new JLabel("Страна:");
        countryLabel.setBounds(10, 60, 80, 20);
        contentPane.add(countryLabel);

        countryComboBox = new JComboBox<>(countries);
        countryComboBox.setBounds(100, 60, 150, 20);
        contentPane.add(countryComboBox);

        JLabel cityLabel = new JLabel("Город:");
        cityLabel.setBounds(10, 100, 80, 20);
        contentPane.add(cityLabel);

        cityComboBox = new JComboBox<>();
        cityComboBox.setBounds(100, 100, 150, 20);
        contentPane.add(cityComboBox);

        countryComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });

        update();

        JLabel classLabel = new JLabel("Класс:");
        classLabel.setBounds(10, 140, 80, 20);
        contentPane.add(classLabel);

        businessClassRadioButton = new JRadioButton("Бизнес");
        businessClassRadioButton.setBounds(100, 140, 100, 20);
        contentPane.add(businessClassRadioButton);

        economyClassRadioButton = new JRadioButton("Эконом");
        economyClassRadioButton.setSelected(true);
        economyClassRadioButton.setBounds(200, 140, 100, 20);
        contentPane.add(economyClassRadioButton);

        ButtonGroup classGroup = new ButtonGroup();
        classGroup.add(businessClassRadioButton);
        classGroup.add(economyClassRadioButton);

        JLabel dateLabel = new JLabel("Дата полета:");
        dateLabel.setBounds(10, 180, 80, 20);
        contentPane.add(dateLabel);

        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
        dateSpinner.setEditor(dateEditor);
        dateSpinner.setBounds(100, 180, 150, 20);
        contentPane.add(dateSpinner);

        JButton calculateButton = new JButton("Рассчитать");
        calculateButton.setBounds(100, 220, 150, 30);
        contentPane.add(calculateButton);

        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculate();
            }
        });
    }

    public void update() {
        int countryIndex = countryComboBox.getSelectedIndex();
        cityComboBox.setModel(new DefaultComboBoxModel<>(cities[countryIndex]));
    }
    
    private double calculatedPrice;

    public double getCalculatedPrice() {
        return calculatedPrice;
    }

    public void calculate() {
        String name = nameField.getText();
        if (name.isEmpty()) {
        
//            JOptionPane.showMessageDialog(this, "Введите ваше имя", "Ошибка", JOptionPane.WARNING_MESSAGE);
//            return;
        }

        int countryIndex = countryComboBox.getSelectedIndex();
        int cityIndex = cityComboBox.getSelectedIndex();
        boolean isBusiness = businessClassRadioButton.isSelected();
        LocalDate date = ((java.util.Date) dateSpinner.getValue()).toInstant()
                .atZone(java.time.ZoneId.systemDefault()).toLocalDate();

        double price;
        if (isBusiness) {
            price = businessPrice[countryIndex][cityIndex];
        } else {
            price = economyPrice[countryIndex][cityIndex];
        }

        if (date.getMonth() == Month.JUNE || date.getMonth() == Month.JULY || date.getMonth() == Month.AUGUST) {
            price *= 1.1;
        }

        calculatedPrice = Math.round(price);
        JOptionPane.showMessageDialog(this, "Здравствуйте, " + name + "! Стоимость вашего билета: " + calculatedPrice + " $");
    }
}
