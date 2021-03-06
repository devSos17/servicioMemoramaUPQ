import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

public class Main implements ActionListener{

	public static String PATH = "./";
	public static String FILESDIR = "media/img/";
	public static String SELECTEDDIR;
	public static JFrame frame;

	private static JLayeredPane base;
	private static JButton startB;
	private static LevelSelector lvlSelector ;
	private static JComboBox<String> dirSelector ;
	private static Color cBase = 		new Color(0x0C1E42);
	private boolean isDirSelected = false;

	public static String titleImagePath;
	private static String HOMEPATH;

	public static void main(String args[]){
		HOMEPATH = args.length!=0?args[0]:"./";
		new Main();
		frame.setVisible(true);
	}



	// Pantalla principal
	public Main (){
		// Set correct directory
		setDir();
		// Inicia la ventana
		setFrame();
		// Inicia el layout para la ventana
		initBase();
		// Add title
		initTitle();
		// Dificultad
		initLevel();
		// Distintis temas
		this.initDirSelector();
		// Botton de inicio
		this.initSButton();

	}

	private void setDir() {
		PATH = "./";
		FILESDIR = "media/img/";
		PATH=HOMEPATH;
		PATH.replace("/", File.separator);
		FILESDIR = PATH + FILESDIR;
		FILESDIR.replace("/", File.separator);
		titleImagePath = PATH+"media/icons/title.png";
		titleImagePath.replace("/",File.separator);
	}

	private static void setFrame(){
		frame = new JFrame();
		// técnico 
		frame.setTitle("Memorama"); //Nombre de la ventana
		frame.setResizable(false); // tamaño fijo
		frame.setSize(500,600); // x y 
		frame.setLocationRelativeTo(null); //aparece en el centro del moitor
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Termina cuando cierre
		
		// Apraciencia
		// El image hay que importarlo y tener un archivo
		frame.getContentPane().setBackground(cBase); //hacerle un color base
		frame.setLayout(null);
		
	}

	private static void initBase(){
		// layered
		base = new JLayeredPane();
		base.setBackground(cBase);
		frame.add(base);
	}

	private static void initTitle(){
		// Subtitle 
		JLabel AL = new JLabel("Algebra Lineal");
		AL.setFont(new Font("Arial", Font.PLAIN,20));
		AL.setForeground(Color.WHITE);
		AL.setHorizontalAlignment(JLabel.CENTER);
		AL.setBounds(100, 30, 300, 20);
		frame.add(AL);
		// Inicia el titulo principal
		ImageIcon tIcon = new ImageIcon(titleImagePath);
		// JLabel title = new JLabel("Algebra", JLabel.CENTER);
		JLabel title = new JLabel();
		title.setText("Memorama");
		title.setHorizontalTextPosition(JLabel.CENTER);
		title.setVerticalTextPosition(JLabel.TOP);
		title.setFont(new Font("Arial",Font.BOLD,40));
		title.setForeground(Color.WHITE);
		title.setIconTextGap(50);
		title.setIcon(tIcon);
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setBounds(50, 50, 400, 300);
		frame.add(title);
	}

	private void initDirSelector(){
		// Selector de Directorios 
		String[] dirs = getDirs(FILESDIR);
	 	dirSelector = new JComboBox<String>(dirs);
		dirSelector.setSelectedItem(null);
		dirSelector.setBounds(190, 350,120,20);
		dirSelector.addActionListener(this);
		frame.add(dirSelector);
	}

	private static void initLevel(){
		// Selector de dificultad
	 	lvlSelector = new LevelSelector();
		lvlSelector.setBounds(190, 380,120,20);
		frame.add(lvlSelector);
	}


	public static String[] getDirs(String dirName){
		String[] dirs = new File(dirName).list(); 
		return dirs;
	}

	private void initSButton(){
		// Buton de inicion
		startB = new JButton("Inicio");
		startB.setBounds(200, 420, 100, 50);
		startB.addActionListener(this);
		startB.setActionCommand("Start");
		frame.add(startB);
	}

	private static void startMemorama(int lvl,String lvlDir){
		// Inicia el Juego
		new Memorama(lvl, lvlDir).setVisible(true);
		frame.setVisible(false);
		frame.dispose();
	}

	private static boolean askName(){
		// POP up para pedir el nombre
		Memorama.userName = JOptionPane.showInputDialog("¿Como te llamas?");
		if(Memorama.userName == null) return false;
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == dirSelector){
			isDirSelected = true;
			Memorama.subject = dirSelector.getSelectedItem().toString();
			SELECTEDDIR = FILESDIR + Memorama.subject;
			lvlSelector.updateSelection(SELECTEDDIR);
		}

		// If button selected
		if(e.getSource() == startB){
			if(! isDirSelected) return;
			if(! askName()) return;
			int selectedLevel = lvlSelector.getSelectedLevel() +1;

			FILESDIR +=
				dirSelector.getSelectedItem().toString() +
				File.separator + 
				lvlSelector.getSelectedLevel()+
				File.separator;

			startMemorama(selectedLevel,FILESDIR);// start game
			frame.dispose();
		}
	}

}
