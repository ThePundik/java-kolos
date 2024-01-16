import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class AplikacjaRysowaniaKol extends JFrame {
    private JPanel panelRysowania;
    private JButton przyciskRysuj;
    private JLabel etykietaInformacyjna;

    public AplikacjaRysowaniaKol() {
        setTitle("Aplikacja Rysowania Kol");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
    }

    private void initComponents() {
        panelRysowania = new JPanel();
        przyciskRysuj = new JButton("Rysuj");
        etykietaInformacyjna = new JLabel("Liczba zapisanych wierszy: 0");

        setLayout(new BorderLayout());
        add(panelRysowania, BorderLayout.CENTER);
        add(przyciskRysuj, BorderLayout.SOUTH);
        add(etykietaInformacyjna, BorderLayout.NORTH);

        przyciskRysuj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rysujKolaAsynchronicznie();
            }
        });
    }

    private void rysujKolaAsynchronicznie() {
        new Thread(() -> {
            int liczbaWatki = new Random().nextInt(5) + 1;

            for (int i = 1; i <= liczbaWatki; i++) {
                int polozenieX = new Random().nextInt(panelRysowania.getWidth());
                int polozenieY = new Random().nextInt(panelRysowania.getHeight());
                int srednica = new Random().nextInt(50) + 10;

                rysujKolo(polozenieX, polozenieY, srednica, i);

                zapiszDoPlikuTekstowego(i, polozenieX, polozenieY, srednica);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    private void rysujKolo(int x, int y, int srednica, int numerWatku) {
        Graphics g = panelRysowania.getGraphics();
        g.drawOval(x, y, srednica, srednica);
        g.dispose();

        etykietaInformacyjna.setText("Liczba zapisanych wierszy: " + (numerWatku * 3));
    }

    private void zapiszDoPlikuTekstowego(int numerWatku, int x, int y, int srednica) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("wyjscie.txt", true))) {
            writer.write("Numer watku: " + numerWatku +
                    ", Wspolrzedne kola: (" + x + ", " + y + "), Srednica: " + srednica + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AplikacjaRysowaniaKol aplikacja = new AplikacjaRysowaniaKol();
            aplikacja.setVisible(true);
        });
    }
}