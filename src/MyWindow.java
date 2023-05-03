import javax.swing.JPanel;                                  // Importa la clase JPanel para crear paneles.
import javax.swing.JSlider;                                // Importa la clase JSlider para crear controles deslizantes.
import javax.swing.event.ChangeEvent;                      // Importa la clase ChangeEvent para manejar eventos de cambio en controles deslizantes.
import javax.swing.event.ChangeListener;                   // Importa la clase ChangeListener para escuchar eventos de cambio en controles deslizantes.
import javax.swing.ImageIcon;
import javax.swing.JButton;                                // Importa la clase JButton para crear botones.
import javax.swing.JFrame;                                 // Importa la clase JFrame para crear ventanas.
import javax.swing.JLabel;                                  // Importa la clase JLabel para crear etiquetas de texto.
import javax.swing.JFileChooser;                           // Importa la clase JFileChooser para abrir cuadros de diálogo de archivos.
import javax.swing.filechooser.FileNameExtensionFilter;   // Importa la clase FileNameExtensionFilter para filtrar archivos por extensión.
import javax.imageio.ImageIO;                              // Importa la clase ImageIO para leer y escribir imágenes.

import java.awt.Color;                                     // Importa la clase Color para manejar colores.
import java.awt.Font;                                      // Importa la clase Font para manejar fuentes.
import java.awt.GradientPaint;                             // Importa la clase GradientPaint para crear degradados.
import java.awt.Graphics;                                  // Importa la clase Graphics para dibujar en componentes.
import java.awt.Graphics2D;                                // Importa la clase Graphics2D para dibujar con gráficos 2D.
import java.awt.Image;
import java.awt.event.ActionEvent;                        // Importa la clase ActionEvent para manejar eventos de acción.
import java.awt.event.ActionListener;                     // Importa la clase ActionListener para escuchar eventos de acción.
import java.awt.event.MouseEvent;                         // Importa la clase MouseEvent para manejar eventos de ratón.
import java.awt.event.MouseListener;                      // Importa la clase MouseListener para escuchar eventos de ratón.
import java.awt.image.BufferedImage;                      // Importa la clase BufferedImage para manejar imágenes.
import java.awt.Dimension;                                // Importa la clase Dimension para manejar dimensiones de componentes.

import java.io.File;                                      // Importa la clase File para manejar archivos.
import java.io.IOException;                                // Importa la clase IOException para manejar excepciones de entrada/salida.


public class MyWindow extends JFrame implements ActionListener, MouseListener, ChangeListener {
    // El código aquí define una ventana personalizada que hereda de JFrame e implementa ActionListener, MouseListener y ChangeListener.
    // A continuación, se definen y crean varios componentes y se configuran sus propiedades y acciones.

    JPanel contentPane;                                    // Panel de contenido principal
    Canvas canvas;                                         // Lienzo de dibujo personalizado
    JLabel lbl;                                            // Etiqueta para mostrar coordenadas
    static Graphics g;                                     // Objeto gráfico para dibujar en el lienzo
    static BufferedImage img;                              // Imagen para guardar y cargar en el lienzo
    int width, height, x, y;                               // Ancho y alto de la ventana, y coordenadas x e y del ratón
    
    // Declara las variables de instancia para los componentes de la interfaz de usuario
    ColorPalette colorPalette; // Paleta de colores para seleccionar colores
    JPanel selectedColorPanel; // Panel que muestra el color seleccionado
    JSlider redSlider, greenSlider, blueSlider; // Controles deslizantes para ajustar el color seleccionado
    JLabel createArtLabel, selectedColorLabel; // Etiquetas de texto
    JButton gridColorButton1, gridColorButton2, backgroundColorButton1, backgroundColorButton2; // Botones para seleccionar colores
    JButton saveButton, loadButton, clearButton; // Botones para guardar, cargar y borrar imágenes

    // Constructor de la ventana principal
    public MyWindow(int width, int height) {
        this.width = width;
        this.height = height;

        // Inicializa y configura los componentes
        components();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        setLayout(null);
        setTitle("My Drawing App");
        setVisible(true);
    }

    // Método para inicializar y agregar los componentes a la ventana
    private void components() {
        contentPane = new JPanel();
        lbl = new JLabel("");
        canvas = new Canvas();

     // Inicializa y configura los botones para guardar, cargar y borrar imágenes
        saveButton = new JButton();
        ImageIcon saveIcon = new ImageIcon("images/save.png");
        Image scaledImage = saveIcon.getImage().getScaledInstance(170, 240, Image.SCALE_SMOOTH); //ancho largo
        saveButton.setIcon(new ImageIcon(scaledImage));
        saveButton.setBounds(canvas.getX() + canvas.getWidth() + 150, canvas.getY() + canvas.getHeight() + 120, 135, 60);
        saveButton.addActionListener(new SaveImageListener());
        saveButton.setBorderPainted(false);
        saveButton.setContentAreaFilled(false);

        // Create button for clearing the canvas
        clearButton = new JButton();
        ImageIcon clearIcon = new ImageIcon("images/eraser.png");
        Image scaledImage1 = clearIcon.getImage().getScaledInstance(170, 240, Image.SCALE_SMOOTH);
        clearButton.setIcon(new ImageIcon(scaledImage1));
        clearButton.setBounds(saveButton.getX(), saveButton.getY() + saveButton.getHeight() + 80, 135, 60);
        clearButton.addActionListener(new ClearCanvasListener());
        clearButton.setBorderPainted(false);
        clearButton.setContentAreaFilled(false);

        // Create button for loading the image
        loadButton = new JButton();
        ImageIcon loadIcon = new ImageIcon("images/load.png");
        Image scaledImage2 = loadIcon.getImage().getScaledInstance(170, 240, Image.SCALE_SMOOTH);
        loadButton.setIcon(new ImageIcon(scaledImage2));
        loadButton.setBounds(saveButton.getX(), clearButton.getY() + clearButton.getHeight() + 90, 135, 60);
        loadButton.addActionListener(new LoadImageListener());
        loadButton.setBorderPainted(false);
        loadButton.setContentAreaFilled(false);
        
        
       /* 
        saveButton = new JButton("Guardar");
        saveButton.setBounds(canvas.getX(), canvas.getY() + canvas.getHeight() + 100, 100, 30);
        saveButton.addActionListener(new SaveImageListener());

        loadButton = new JButton("Cargar");
        loadButton.setBounds(saveButton.getX() + saveButton.getWidth() + 10, saveButton.getY(), 100, 30);
        loadButton.addActionListener(new LoadImageListener());

       clearButton = new JButton("Borrar");
       clearButton.setBounds(loadButton.getX() + loadButton.getWidth() + 10, loadButton.getY(), 100, 30);
       clearButton.addActionListener(new ClearCanvasListener());*/

    contentPane = new JPanel() {
	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Graphics2D g2d = (Graphics2D) g;
	        
	        int width = getWidth();
	        int height = getHeight();
	       

	        Color color1 = new Color(230, 230, 230);
	        Color color2 = new Color(200, 200, 200);
	        Color color3 = new Color(150, 150, 150);
	        Color color4 = new Color(100, 100, 100);

	        int numSections = 3;
	        int sectionHeight = height / numSections;

	        GradientPaint gp1 = new GradientPaint(0, 0, color1, 0, sectionHeight, color2);
	        GradientPaint gp2 = new GradientPaint(0, sectionHeight, color2, 0, 2 * sectionHeight, color3);
	        GradientPaint gp3 = new GradientPaint(0, 2 * sectionHeight, color3, 0, 3 * sectionHeight, color4);

	        g2d.setPaint(gp1);
	        g2d.fillRect(0, 0, width, sectionHeight);

	        g2d.setPaint(gp2);
	        g2d.fillRect(0, sectionHeight, width, 2 * sectionHeight);


	        g2d.setPaint(gp3);
	        g2d.fillRect(0, 2 * sectionHeight, width, 3 * sectionHeight);
	    }
	};
	contentPane.setLayout(null); // Establece el layout del panel de contenido como null, lo que permite posicionar los componentes manualmente
	contentPane.setBounds(0, 0, width, height); // Establece los límites del panel de contenido
	contentPane.add(saveButton);
	contentPane.add(loadButton);
	contentPane.add(clearButton);

	lbl.setBounds(0, 0, width, height); // Establece los límites de la etiqueta 'lbl'
	lbl.setForeground(Color.white); // Establece el color de primer plano de la etiqueta 'lbl' en blanco
	lbl.setFont(new Font("Serif", Font.PLAIN, 50)); // Establece la fuente de la etiqueta 'lbl'

	canvas.setBounds((width / 2) - 250, (height / 2) - 300, 500, 500); // Establece los límites del lienzo
	
	createArtLabel = new JLabel("CREA TU PROPIO ARTE...");
	createArtLabel.setBounds(canvas.getX() -100, canvas.getY() - 80, 800, 40); // Establece los límites de la etiqueta 'createArtLabel'
	createArtLabel.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 50));// Establece la fuente para la etiqueta 'createArtLabel'
	
	selectedColorLabel = new JLabel("Color elegido"); // Crea una nueva etiqueta con el texto "Color elegido"
	selectedColorLabel.setBounds(canvas.getX() + canvas.getWidth() + 20, canvas.getY() + canvas.getHeight() + 30, 150, 20); // Establece los límites de la etiqueta 'selectedColorLabel'
	selectedColorLabel.setFont(new Font("Lucida Calligraphy", Font.PLAIN, 20));// Establece la fuente para la etiqueta 'createArtLabel'
	
	
	colorPalette = new ColorPalette(new ColorSelectionListener(), 4, 4); // Crea una nueva instancia de 'ColorPalette'
	colorPalette.setBounds(canvas.getX() + canvas.getWidth() + 20, canvas.getY() + 50, 170, 250); // Establece los límites de 'colorPalette'

	backgroundColorButton1 = createColorButton(Color.BLACK, canvas.getX() + canvas.getWidth() + 50, colorPalette.getY() + colorPalette.getHeight() + 100); // Crea el primer botón de selección de color de fondo
	backgroundColorButton1.addActionListener(new BackgroundColorSelectionListener()); // Agrega un oyente de acción al botón

	backgroundColorButton2 = createColorButton(Color.WHITE, backgroundColorButton1.getX() + backgroundColorButton1.getWidth() + 10, backgroundColorButton1.getY()); // Crea el segundo botón de selección de color de fondo
	backgroundColorButton2.addActionListener(new BackgroundColorSelectionListener()); // Agrega un oyente de acción al botón

	selectedColorPanel = new JPanel(); // Crea un nuevo panel para mostrar el color seleccionado
	selectedColorPanel.setBackground(Color.WHITE); // Establece el color de fondo del panel en blanco
	selectedColorPanel.setBounds(canvas.getX() + canvas.getWidth() + 20, canvas.getY() + canvas.getHeight() + 60, 100, 50); // Establece los límites del panel 'selectedColorPanel'

	redSlider = createColorSlider(Color.RED, canvas.getX() + canvas.getWidth() / 2 - 150, canvas.getY() + canvas.getHeight() + 8); // Crea el deslizador para el color rojo
	greenSlider = createColorSlider(Color.GREEN, redSlider.getX(), redSlider.getY() + redSlider.getHeight() + 18); // Crea el deslizador para el color verde
	blueSlider = createColorSlider(Color.BLUE, greenSlider.getX(), greenSlider.getY() + greenSlider.getHeight() + 21); // Crea el deslizador para el color azul

	canvas.addMouseListener(this); // Agrega un oyente de eventos del ratón al lienzo
	contentPane.add(canvas); // Añade el lienzo al panel de contenido
	contentPane.add(lbl); // Añade la etiqueta 'lbl' al panel de contenido
	contentPane.add(colorPalette); // Añade la paleta de colores al panel de contenido
	contentPane.add(selectedColorPanel); // Añade el panel del color seleccionado al panel de contenido
	contentPane.add(redSlider); // Añade el deslizador rojo al panel de contenido
	contentPane.add(greenSlider); // Añade el deslizador verde al panel de contenido
	contentPane.add(blueSlider); // Añade el deslizador azul al panel de contenido
	contentPane.add(createArtLabel); // Añade la etiqueta 'createArtLabel' al panel de contenido
	contentPane.add(selectedColorLabel); // Añade la etiqueta 'selectedColorLabel' al panel de contenido
	contentPane.add(backgroundColorButton1); // Añade el primer botón de selección de color de fondo al panel de contenido
	contentPane.add(backgroundColorButton2); // Añade el segundo botón de selección de color de fondo al panel de contenido
	contentPane.add(saveButton); // Añade el botón de guardar al panel de contenido
	contentPane.add(loadButton); // Añade el botón de cargar al panel de contenido
	contentPane.add(clearButton); // Añade el botón de borrar al panel de contenido

	add(contentPane); // Añade el panel de contenido al marco de la ventana
	}

	private JSlider createColorSlider(Color color, int x, int y) {
	    JSlider slider = new JSlider(0, 255); // Crea un nuevo deslizador con un rango de 0 a 255
	    slider.setPaintTicks(false); // Desactiva la visualización de marcas en el deslizador
	    slider.setPaintLabels(false); // Desactiva la visualización de etiquetas en el deslizador
	    slider.setMajorTickSpacing(50); // Establece el espaciado entre las marcas principales en el deslizador
	    slider.setMinorTickSpacing(10); // Establece el espaciado entre las marcas secundarias en el deslizador
	    slider.setForeground(color); // Establece el color de primer plano del deslizador
	    slider.setBackground(color); // Establece el color de fondo del deslizador
	    slider.setBounds(x, y, 300, 30); // Establece los límites del deslizador
	    slider.addChangeListener(new ChangeListener() { // Añade un oyente de cambio al deslizador
	        @Override
	        public void stateChanged(ChangeEvent e) {
	        	actualizarColorSeleccionadoDesdeDeslizadores(); // Actualiza el color seleccionado en función de los valores de los deslizadores
	        }
	    });
	    return slider; // Devuelve el deslizador creado
	}
	

	private void actualizarColorSeleccionadoDesdeDeslizadores() {
	    Color colorSeleccionado = new Color(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue());
	    selectedColorPanel.setBackground(colorSeleccionado);
	    canvas.setColor(colorSeleccionado);
	}

	public void actionPerformed(ActionEvent event) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	    x = e.getX();
	    y = e.getY();
	    lbl.setText(x + " " + y);
	}
    
    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    // Método que se ejecuta cuando se cambia el valor del control deslizante 'one'
    @Override
    public void stateChanged(ChangeEvent e) {
    }

    // Método para crear botones de color
    private JButton createColorButton(Color color, int x, int y) {
        JButton button = new JButton(); // Crea un nuevo botón
        button.setBackground(color); // Establece el color de fondo del botón al color especificado
        button.setBounds(x, y, 30, 30); // Establece la posición y el tamaño del botón
        button.setPreferredSize(new Dimension(30, 30)); // Establece las dimensiones preferidas del botón
        return button; // Devuelve el botón creado
    }
    

    // Clase interna para manejar la selección de colores de fondo
    private class BackgroundColorSelectionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource(); // Obtiene el botón que generó el evento
            Color selectedColor = source.getBackground(); // Obtiene el color de fondo del botón
            canvas.setBackground(selectedColor); // Establece el color de fondo del lienzo al color seleccionado
        }
    }

    // Clase interna para manejar la selección de colores de la paleta
    private class ColorSelectionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource(); // Obtiene el botón que generó el evento
            Color selectedColor = source.getBackground(); // Obtiene el color de fondo del botón
            canvas.setColor(selectedColor); // Establece el color de dibujo del lienzo al color seleccionado
            selectedColorPanel.setBackground(selectedColor); // Establece el color de fondo del panel de color seleccionado
            redSlider.setValue(selectedColor.getRed()); // Establece el valor del control deslizante de rojo al valor de rojo del color seleccionado
            greenSlider.setValue(selectedColor.getGreen()); // Establece el valor del control deslizante de verde al valor de verde del color seleccionado
            blueSlider.setValue(selectedColor.getBlue()); // Establece el valor del control deslizante de azul al valor de azul del color seleccionado
        }
    }

    private class SaveImageListener implements ActionListener { // Clase interna privada para manejar el evento de guardar imagen
        @Override
        public void actionPerformed(ActionEvent e) { // Método que se invoca cuando se dispara el evento de acción
            JFileChooser fileChooser = new JFileChooser(); // Crea un objeto JFileChooser para seleccionar un archivo donde guardar
            fileChooser.setDialogTitle("Guardar imagen como"); // Establece el título del cuadro de diálogo
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop/")); // Establece el directorio inicial en el escritorio del usuario
            
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JPG Image", "jpg")); // Añade filtro para archivos JPG
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG Image", "png")); // Añade filtro para archivos PNG
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("GIF Image", "gif")); // Añade filtro para archivos GIF

            int returnVal = fileChooser.showSaveDialog(MyWindow.this); // Muestra el cuadro de diálogo y guarda el resultado
            if (returnVal == JFileChooser.APPROVE_OPTION) { // Si se aprueba la selección de archivo
                File file = fileChooser.getSelectedFile(); // Obtiene el archivo seleccionado
                String filePath = file.getAbsolutePath(); // Obtiene la ruta absoluta del archivo
                
                FileNameExtensionFilter selectedFilter = (FileNameExtensionFilter) fileChooser.getFileFilter(); // Obtiene el filtro de archivo seleccionado
                String extension = selectedFilter.getExtensions()[0]; // Obtiene la extensión del archivo seleccionado

                if (!filePath.toLowerCase().endsWith("." + extension)) { // Si la ruta del archivo no tiene la extensión correcta
                    file = new File(filePath + "." + extension); // Añade la extensión al archivo
                }

                try {
                    BufferedImage imgToSave = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB); // Crea una imagen de buffer
                    Graphics2D g2d = imgToSave.createGraphics(); // Crea un objeto Graphics2D para dibujar en la imagen

                    g2d.setColor(canvas.getBackground()); // Establece el color de fondo
                    g2d.fillRect(0, 0, canvas.getWidth(), canvas.getHeight()); // Rellena el rectángulo de fondo
                    g2d.drawImage(canvas.getImg(), 0, 0, null); // Dibuja la imagen del lienzo en el buffer

                    g2d.setColor(Color.GRAY); // Establece el color de la cuadrícula
                    for (int row = 0; row < canvas.ROWS; row++) { // Itera a través de las filas
                        for (int col = 0; col < canvas.COLS; col++) { // Itera a través de las columnas
                            g2d.drawRect(row * canvas.CELL_SIZE, col * canvas.CELL_SIZE, canvas.CELL_SIZE, canvas.CELL_SIZE); // Dibuja la cuadrícula
                        }
                    }

                    ImageIO.write(imgToSave, extension, file); // Guarda la imagen en el archivo
                    g2d.dispose(); // Libera los recursos de Graphics2D
                } catch (IOException ex) { // Maneja la excepción de entrada/salida
                    ex.printStackTrace(); // Imprime la traza de la excepción
                }
            }
        }
        
    }
    // Clase interna para manejar la acción de cargar la imagen
    private class LoadImageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser(); // Crea un nuevo selector de archivos
            FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, GIF & PNG Images", "jpg", "gif", "png"); // Crea un filtro para archivos de imagen
            fileChooser.setFileFilter(filter); // Establece el filtro de archivos en el selector de archivos
            int returnVal = fileChooser.showOpenDialog(MyWindow.this); // Muestra el cuadro de diálogo de abrir archivo y guarda el resultado
            if (returnVal == JFileChooser.APPROVE_OPTION) { // Si el usuario aprueba la selección de archivos
                File file = fileChooser.getSelectedFile(); // Obtiene el archivo seleccionado
                try {
                    BufferedImage image = ImageIO.read(file); // Lee la imagen del archivo
                    canvas.setImage(image); // Establece la imagen en el lienzo
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    // Clase interna para manejar la acción de borrar el área de dibujo
    private class ClearCanvasListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            canvas.clear(); // Limpia el área de dibujo en el lienzo
        }
    }

    } // Fin de la clase