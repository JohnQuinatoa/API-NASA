package ec.edu.uce.Api_Mars_rover.view;

import ec.edu.uce.Api_Mars_rover.controller.Contenedor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Window extends JFrame {

    private String tipoCamara;
    private String tipoRover;
    private String numeroDeSol;

    int numFilas;
    int numImages;
    int labelWidth;
    int labelHeight;
    int gapWidth;
    int gapHeight;

    private BackgroundPanel panel;
    private JPanel imagePanel;
    Contenedor contenedor;

    public Window() {
        super("Mars Rover Photos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        try {
            BufferedImage img = ImageIO.read(getClass().getResource("/logo.png"));
            setIconImage(img);
        } catch (IOException e) {
            e.printStackTrace();
        }

        panel = new BackgroundPanel("/fondo total.png");
        panel.setLayout(null);

        Font font = new Font("Book Antiqua", Font.BOLD, 12);

        // Filtrado de Cámaras
        String[] filtrado1 = {"FHAZ", "RHAZ", "MAST", "CHEMCAM", "MAHLI", "MARDI", "NAVCAM", "PANCAM", "MINITES"};
        JComboBox<String> comboBoxfilter1 = new JComboBox<>(filtrado1);
        comboBoxfilter1.setBounds(150, 35, 100, 20);
        comboBoxfilter1.setFont(font);

        JLabel labelTipoDeCamara = new JLabel("TIPO DE CAMARA");
        labelTipoDeCamara.setBounds(143, 10, 115, 20);
        labelTipoDeCamara.setForeground(Color.WHITE);
        labelTipoDeCamara.setFont(font);

        panel.add(comboBoxfilter1);
        panel.add(labelTipoDeCamara);

        // Filtrado de tipo de Rover
        String[] filtrado2 = {"Curiosity", "Opportunity", "Spirit"};
        JComboBox<String> comboBoxfilter2 = new JComboBox<>(filtrado2);
        comboBoxfilter2.setBounds(300, 35, 100, 20);
        comboBoxfilter2.setFont(font);

        JLabel labelTipoROver = new JLabel("TIPO DE ROVER");
        labelTipoROver.setBounds(300, 10, 115, 20);
        labelTipoROver.setForeground(Color.WHITE);
        labelTipoROver.setFont(font);

        panel.add(comboBoxfilter2);
        panel.add(labelTipoROver);

        // Cuadro para insertar el valor de Sol
        JTextField textFieldSol = new JTextField();
        textFieldSol.setBounds(450, 35, 100, 20);
        textFieldSol.setFont(font);

        JLabel labelSol = new JLabel("SOL DE 1 A 4100");
        labelSol.setForeground(Color.WHITE);
        labelSol.setBounds(450, 10, 100, 20);
        labelSol.setFont(font);

        panel.add(labelSol);
        panel.add(textFieldSol);

        // Botón para filtrar
        JButton buttonFiltrar = new JButton("FILTRAR");
        buttonFiltrar.setBounds(600, 35, 100, 20);
        buttonFiltrar.setFont(font);

        panel.add(buttonFiltrar);

        // Panel para las imágenes, inicialmente vacío
        imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());
        imagePanel.setBounds(36, 80, 719, 450); // Ajustar el tamaño y la posición de imagePanel
        imagePanel.setBorder(BorderFactory.createLineBorder(new Color(245, 245, 220), 4));
        imagePanel.setOpaque(false);

        panel.add(imagePanel);

        // Acciones de los botones
        buttonFiltrar.addActionListener(e -> {
            tipoCamara = (String) comboBoxfilter1.getSelectedItem();
            tipoRover = (String) comboBoxfilter2.getSelectedItem();
            numeroDeSol = textFieldSol.getText();
            contenedor = new Contenedor();
            contenedor.obtenerUrls(tipoCamara, tipoRover, numeroDeSol);

            if (validarNumeroSol(numeroDeSol)) {
                if (contenedor.getImages().isEmpty()) {
                    mostrarMensaje("No se encontraron imágenes para los filtros seleccionados.");
                } else {
                    mostrarImagenes();
                }
            }
        });

        getContentPane().add(panel);
        this.setVisible(true);
    }

    private void mostrarImagenes() {
        try {
            numFilas = contenedor.obtenerFilas();
            numImages = 0;
            labelWidth = 220;
            labelHeight = 220;
            gapWidth = 14;
            gapHeight = 14;

            // Crear el panel de contenido
            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(null);
            contentPanel.setOpaque(false);

            // ExecutorService para manejar los hilos de descarga de imágenes
            ExecutorService executor = Executors.newFixedThreadPool(15);

            for (int i = 0; i < numFilas; i++) {
                for (int j = 0; j < 3; j++) {
                    if (numImages < contenedor.getImages().size()) {
                        // Obtener el enlace de la imagen
                        String imageUrl = contenedor.getImages().get(numImages).getLink();
                        int finalI = i;
                        int finalJ = j;

                        executor.submit(() -> {
                            try {
                                if (isValidImageUrl(imageUrl)) {
                                    // Si la URL es válida, cargar la imagen
                                    BufferedImage originalImage = ImageIO.read(new URL(imageUrl));
                                    Image scaledImage = originalImage.getScaledInstance(labelWidth - 1, labelHeight - 1, Image.SCALE_SMOOTH);
                                    ImageIcon imageIcon = new ImageIcon(scaledImage);

                                    JLabel label = new JLabel();
                                    label.setIcon(imageIcon);
                                    label.setBounds(1 + finalJ * (labelWidth + gapWidth), finalI * (labelHeight + gapHeight), labelWidth, labelHeight);
                                    label.setBorder(BorderFactory.createLineBorder(Color.lightGray, 4));

                                    SwingUtilities.invokeLater(() -> {
                                        contentPanel.add(label);
                                        contentPanel.revalidate();
                                        contentPanel.repaint();
                                    });

                                } else {
                                    // Si la URL no es válida, mostrar el enlace
                                    JLabel label = new JLabel();
                                    label.setText("<html><body><div style='margin:4px;'>El Link obtenido no redirecciona a</div> <div style='margin:4px;'>una imagen, sino a una pagina web.</div> <div style='margin:4px;'>Haga click para entrar en la pagina</div> <div style='margin:4px;'><br></div>"
                                            + "<a href='" + imageUrl + "'>" + imageUrl + "</a></body></html>");
                                    label.setBounds(1 + finalJ * (labelWidth + gapWidth), finalI * (labelHeight + gapHeight), labelWidth, labelHeight);
                                    label.setBorder(BorderFactory.createLineBorder(Color.lightGray, 4));
                                    label.setOpaque(true);
                                    label.setCursor(new Cursor(Cursor.HAND_CURSOR));
                                    label.setToolTipText(imageUrl);




                                    // Agregar el MouseListener para abrir el enlace en el navegador
                                    label.addMouseListener(new MouseAdapter() {
                                        @Override
                                        public void mouseClicked(MouseEvent e) {
                                            try {
                                                Desktop.getDesktop().browse(new URI(imageUrl));
                                            } catch (Exception ex) {
                                                ex.printStackTrace();
                                            }
                                        }
                                    });

                                    SwingUtilities.invokeLater(() -> {
                                        contentPanel.add(label);
                                        contentPanel.revalidate();
                                        contentPanel.repaint();
                                    });
                                }
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        });

                        numImages++;
                    }
                }
            }

            executor.shutdown();

            // Esperar a que todas las tareas se completen antes de continuar
            while (!executor.isTerminated()) {
                Thread.sleep(100);
            }

            // Configurar el tamaño del panel de contenido
            int panelWidth = 3 * (labelWidth + gapWidth);
            int panelHeight = numFilas * (labelHeight + gapHeight) - gapHeight + 2;
            contentPanel.setPreferredSize(new Dimension(panelWidth, panelHeight));

            // Crear el JScrollPane y agregar el contentPanel
            JScrollPane scrollPane = new JScrollPane(contentPanel);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.getVerticalScrollBar().setUnitIncrement(50);
            scrollPane.setOpaque(false);
            scrollPane.getViewport().setOpaque(false);

            // Remover el contenido previo de imagePanel
            imagePanel.removeAll();
            imagePanel.setLayout(new BorderLayout());
            imagePanel.add(scrollPane, BorderLayout.CENTER);
            imagePanel.revalidate();
            imagePanel.repaint();

        } catch (InterruptedException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar las imágenes.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static boolean isValidImageUrl(String urlString) {

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();

            String contentType = connection.getContentType();
            int responseCode = connection.getResponseCode();

            // Verifica que el tipo MIME sea de imagen y que el servidor devuelva un estado 200
            if (responseCode == 200 && contentType.startsWith("image")) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            return false;
        }

    }

    private boolean validarNumeroSol(String numeroDeSol) {

        try {
            int sol = Integer.parseInt(numeroDeSol);
            if (sol >= 1 && sol <= 4100) {
                return true;
            } else {
                mostrarMensaje("Por favor, ingresa un número entre 1 y 4100 para el número de Sol.");
            }
        } catch (NumberFormatException ex) {
            mostrarMensaje("Por favor, ingresa un número válido para el Sol.");
        }
        return false;

    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

}