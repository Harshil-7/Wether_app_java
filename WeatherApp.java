import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherApp extends JFrame {

    private JTextField locationField;
    private JTextArea weatherTextArea;

    public WeatherApp() {
        setTitle("Weather App");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        locationField = new JTextField(15);
        JButton fetchButton = new JButton("Fetch Weather");
        fetchButton.addActionListener(new FetchButtonListener());

        weatherTextArea = new JTextArea(8, 20);
        weatherTextArea.setEditable(false);

        panel.add(new JLabel("Enter City:"), BorderLayout.NORTH);
        panel.add(locationField, BorderLayout.CENTER);
        panel.add(fetchButton, BorderLayout.SOUTH);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(weatherTextArea), BorderLayout.CENTER);
    }

    private class FetchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String apiKey = "d6d69b6fb92f36fe8a7253b75d8f3df4";  // Replace with your actual API key
            String location = locationField.getText();

            try {
                URL url = new URL("http://api.weatherstack.com/current?access_key=" + apiKey + "&query=" + location);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                InputStream responseStream = connection.getInputStream();
                Scanner scanner = new Scanner(responseStream).useDelimiter("\\A");
                String response = scanner.hasNext() ? scanner.next() : "";

                // Parse the JSON response and extract weather information
                // Update the weatherTextArea with the information
                System.out.println(response);
                responseStream.close();
                connection.disconnect();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WeatherApp weatherApp = new WeatherApp();
            weatherApp.setVisible(true);
        });
    }
}
