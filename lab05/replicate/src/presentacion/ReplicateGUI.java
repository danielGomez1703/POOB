package presentacion;

import aplicacion.*;

import java.awt.Dimension;
import java.awt.event.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.*;
import java.io.*;

import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.JOptionPane;

public class ReplicateGUI extends JFrame{
    
    private Image blackBrick;
    private Image whiteBrick;
	
	public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int ANCHO = screenSize.width / 2;
	public static final int ALTO = screenSize.height / 2;
	private JMenuBar menu;
	private JMenu archivo;
	private JMenuItem menuNuevo;
	private JMenuItem menuAbrir;
	private JMenuItem menuSalvar;
	private JMenuItem menuSalvarComo;
	private JMenuItem menuSalir;	
	
	private JPanel matrix;
	private JPanel opciones;
	private int dimension=10;
	private Color color;

        private JButton [][] casillas;
        private Container content;
        private JButton botonLlenar;
        private JButton botonReplicar;
        private JButton botonInformar;
        private JButton botonTerminar;
        private JButton botonColor;
        private JButton botonDimensiones;
        
        private Replicate juego;
        
	
	
	
	
	private JFileChooser fc = new JFileChooser();
	public ReplicateGUI() {
                color=Color.BLACK;
		prepareElementos();
		prepareAcciones();
	}
	
	public void prepareElementosMatrix(){
           
            casillas = new JButton[dimension][dimension];
            matrix = new JPanel();
            matrix.setLayout(new GridLayout (dimension,dimension,3,3));
            content.add(matrix,BorderLayout.CENTER);
            for (int i = 0; i<dimension; i++){
                for (int j =0; j<dimension;j++){
                    casillas[i][j] = new JButton();
                    casillas[i][j].setBackground(color);
                    matrix.add(casillas[i][j]);
		}
            }
            //matrix.updateUI();		
	}
        public void prepareElementosBotones(){
           
            opciones = new JPanel();
            opciones.setLayout(new GridLayout (2,3,3,3));
            content.add(opciones,BorderLayout.SOUTH);
                    botonLlenar = new JButton("LLENAR");
                    botonReplicar = new JButton("REPLICAR");
                    botonInformar = new JButton("REPLICACIONES");
                    botonColor = new JButton("COLOR");
                    botonDimensiones = new JButton("DIMENSIONES");
                    botonTerminar = new JButton("TERMINAR");
                    opciones.add(botonLlenar);
                    opciones.add(botonReplicar);
                    opciones.add(botonInformar);
                    opciones.add(botonColor);
                    opciones.add(botonDimensiones);
                    opciones.add(botonTerminar);
           // opciones.updateUI();     
        }
        
	public void prepareElementos() {
		setTitle("Replicate");
		setSize(ANCHO,ALTO);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		menu = new JMenuBar();
		setJMenuBar(menu);
                content = getContentPane();
		prepareElementosMenu();
                prepareElementosMatrix();
                prepareElementosBotones();
                juego = new Replicate();
	}
	
	private void prepareElementosMenu() {
            
		archivo = new JMenu("Archivo");
		menu.add(archivo);
		menuNuevo = new JMenuItem("Nuevo");
		menuAbrir = new JMenuItem("Abrir");
		menuSalvar = new JMenuItem("Salvar");
		menuSalvarComo = new JMenuItem("Salvar como");
		menuSalir = new JMenuItem("Salir");
		archivo.add(menuNuevo);
		archivo.add(menuAbrir);
		archivo.add(menuSalvar);
		archivo.add(menuSalvarComo);
		archivo.add(menuSalir);
	}
	
	public void prepareAcciones() {
		addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent ev) {
					salga();
				}
			});
		menuSalir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				salga();
			}
		});
		menuNuevo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				nuevo();

			}
		});
                
		menuSalvar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				salvar();

			}
		});
		menuSalvarComo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				salvar();

			}
		});
		menuAbrir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				abrir();

			}
		});
		botonColor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cambiarColor();

			}
		});
		botonReplicar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				replicar();

			}
		});
                for (int i = 0 ; i<dimension;i++){
                    for (int j = 0; j<dimension;j++){
                        int fila=i;
                        int columna = j;
                        casillas[i][j].addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {
                                    seleccionarCasilla(fila,columna);

                            }
                    });                        
                    }
                }
                
                botonInformar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				replicaciones();

			}
		});
                botonLlenar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				seleccionarCasilla();

			}
		});
                botonDimensiones.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resize();

			}
		});
                botonTerminar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				salga();

			}
		});
		
	}
        
        public void prepareAccionMatriz(){
            for (int i = 0 ; i<dimension;i++){
                    for (int j = 0; j<dimension;j++){
                        int fila=i;
                        int columna = j;
                        casillas[i][j].addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {
                                    seleccionarCasilla(fila,columna);

                            }
                    });                        
                    }
                }
        }
	
	public void salga(){
		if (JOptionPane.showConfirmDialog(null, "¿Esta seguro de salir de replicate?", "Salir",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			setVisible(false);
			System.exit(0);
		}
	}
	private void abrir() {
            int res = fc.showOpenDialog(this);
            if (res == JFileChooser.APPROVE_OPTION) {
                File archivo = fc.getSelectedFile();
                try{	
                    juego.abrir(archivo);
                    refresque();
                    
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null,"Funcion en costruccion\nAbrir archivo: " + fc.getSelectedFile().getName());
                }
            }
	}

	private void salvar() {
            int res = fc.showSaveDialog(this);
            if (res == JFileChooser.APPROVE_OPTION) {
                //JOptionPane.showMessageDialog(null,"Funcion en costruccion\nSalvar archivo: " + fc.getSelectedFile().getName());
                File archivo = fc.getSelectedFile();
                try	{	
                    juego.salvar(archivo);
                }catch(Exception e){
                    JOptionPane.showMessageDialog(null,"Funcion en costruccion\nAbrir archivo: " + fc.getSelectedFile().getName());
                }
            }
            
        }
        
        private void nuevo(){
            juego= new Replicate();
            dimension=juego.getDimension();
            color=Color.BLACK;
            actualizar();
            refresque();
        }

        private void cambiarColor() {
            color = JColorChooser.showDialog(null,"Escoja Color", color);
            if (color == null){
                color =Color.BLACK;
            }
            juego.setColor(color);
            actualizar();
        }
            
        private void seleccionarCasilla(int i, int j){
            if (!(juego.getValue(i,j))){
                casillas[i][j].setBackground(Color.WHITE);
                juego.seleccionarCasilla(i,j,true);
            }else{
                casillas[i][j].setBackground(color);
                juego.seleccionarCasilla(i,j,false);
            }
            actualizar();
        }
        
        private void seleccionarCasilla(){
            String fila = (String) JOptionPane.showInputDialog(new JFrame(), "ingrese la fila :", "fila", JOptionPane.QUESTION_MESSAGE);
            String columna = (String) JOptionPane.showInputDialog(new JFrame(), "ingrese la columna :", "columna", JOptionPane.QUESTION_MESSAGE);
            juego.seleccionarCasilla(Integer.parseInt(fila),Integer.parseInt(columna),!(juego.getValue(Integer.parseInt(fila),Integer.parseInt(columna))));
            actualizar();
        }
        
        private void replicar(){
            juego.replicar();
            actualizar(); 
            
        }
        
        
        private void refresque(){
            matrix.removeAll();
            this.remove(matrix);
            dimension=juego.getDimension();
            color=juego.getColor();
            prepareElementosMatrix();
            actualizar();
            prepareAccionMatriz();
            matrix.updateUI();
        
        }
        
        private void actualizar(){
            boolean aux [] [] = juego.getMatriz();
            for (int i = 0; i<dimension; i++){
                for (int j =0; j<dimension;j++){
                    if (aux[i][j]){
                        casillas[i][j].setBackground(Color.WHITE);
                    }else
                        casillas[i][j].setBackground(color);
                    }
		}
        }
       
        public void resize(){
            String dim = (String) JOptionPane.showInputDialog(new JFrame(), "ingrese Dimension :", "dimension", JOptionPane.QUESTION_MESSAGE);
            this.dimension=Integer.parseInt(dim);
            juego.resize(Integer.parseInt(dim));
            refresque();
            
        }
        
        private void replicaciones(){
            int replicas = juego.getReplicas();
            JOptionPane.showMessageDialog(null,"el numer de replicas es : "+String.valueOf(replicas) , "REPLICATE", JOptionPane.WARNING_MESSAGE);
        }
	public static void main(String args[]){
	    ReplicateGUI gui=new ReplicateGUI();
	    gui.setVisible(true);
	}
}  