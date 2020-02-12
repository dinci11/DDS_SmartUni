package controller;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ControllerFrame extends JFrame {

    JPanel mainPanel;

    JPanel panelCurrentTemp;
    JPanel panelCurrentHumidity;
    JPanel panelTemp;
    JPanel panelHum;
    JPanel panelBottom;

    JTextField tfCurrentTemp;
    JTextField tfCurrentHum;

    JSpinner spinTemp;
    JSpinner spinHum;

    JButton btnSet;

    ButtonSet_OnClicked listener;

    public ControllerFrame(String title) {
        super(title);
        this.setSize(800, 600);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        GridLayout frameLayout = new GridLayout(2, 1);
        this.setLayout(frameLayout);

        mainPanel = new JPanel(new GridLayout(2, 2));

        panelCurrentHumidity = new JPanel(new GridLayout(2, 1));
        panelCurrentHumidity.add(getCenterLable("Current Humidity"));
        tfCurrentHum = new JTextField("No Data");
        tfCurrentHum.setEditable(false);
        panelCurrentHumidity.add(tfCurrentHum);

        panelCurrentTemp = new JPanel(new GridLayout(2, 1));
        panelCurrentTemp.add(getCenterLable("Current Temperature"));
        panelCurrentTemp.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 2, Color.GRAY));
        tfCurrentTemp = new JTextField("No Data");
        tfCurrentTemp.setEditable(false);
        panelCurrentTemp.add(tfCurrentTemp);

        panelTemp = new JPanel(new GridLayout(2, 1));
        panelTemp.add(getCenterLable("Temperature"));
        spinTemp = new JSpinner(new SpinnerNumberModel(25, 0, 30, 1));
        panelTemp.add(spinTemp);

        panelHum = new JPanel(new GridLayout(2, 1));
        panelHum.add(getCenterLable("Humidity"));
        panelHum.setBorder(BorderFactory.createMatteBorder(2, 2, 0, 0, Color.GRAY));
        spinHum = new JSpinner(new SpinnerNumberModel(50, 0, 100, 1));
        panelHum.add(spinHum);

        panelBottom = new JPanel(new GridLayout(3, 3));
        btnSet = new JButton("Set");
        btnSet.addActionListener(actionEvent -> {
            int temp = (int) spinTemp.getValue();
            int hum = (int) spinHum.getValue();
            listener.btnSetOnClicked(temp,hum);
        });
        panelBottom.add(new Panel());
        panelBottom.add(new Panel());
        panelBottom.add(new Panel());
        panelBottom.add(new Panel());
        panelBottom.add(btnSet);
        panelBottom.add(new Panel());
        panelBottom.add(new Panel());

        mainPanel.add(panelCurrentTemp);
        mainPanel.add(panelTemp);
        mainPanel.add(panelCurrentHumidity);
        mainPanel.add(panelHum);
        mainPanel.setMinimumSize(new Dimension(800, 700));
        mainPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.GRAY));

        this.add(mainPanel);
        this.add(panelBottom);
        this.setVisible(true);
    }

    public interface ButtonSet_OnClicked {
        void btnSetOnClicked(int temperature, int humidity);
    }

    public int GetCurrentTemperature() {
        return (int) spinTemp.getValue();
    }

    public int GetCurrentHumidity() {
        return (int) spinHum.getValue();
    }

    public void SetCurrentTemperature(int temperature) {
        if (temperature > -1) {
            tfCurrentTemp.setText(temperature + " Celsius");
        }
    }

    public void SetCurrentHumidity(int humidity){
        if (humidity > -1) {
            tfCurrentHum.setText(humidity + "%");
        }
    }

    public void setListener(ButtonSet_OnClicked listener) {
        this.listener = listener;
    }

    private JLabel getCenterLable(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setHorizontalAlignment(0);
        return lbl;
    }

}
