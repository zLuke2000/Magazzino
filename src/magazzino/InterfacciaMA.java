package magazzino;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
class InterfacciaMA extends JFrame{
	
	private final int LARGHEZZA = 300;
	private final int ALTEZZA = 250;
	private List<Articolo> articoli = new ArrayList<Articolo>();
	private List<Articolo> articoliTrovati = new ArrayList<Articolo>();
	private Articolo articoloTrovato = new Articolo();
	private int numArticoli;
	private int risposta;
	private String articoloCorrente;
	private String errore;
	private String riepilogo;
	
	
	// LAYOUT
	private final Container container;
	private GridBagLayout layout = new GridBagLayout();
	private GridBagConstraints constraints = new GridBagConstraints();
	//FONT
	private final Font bigFont = new Font("Tahoma", Font.PLAIN, 50);
	//BORDO
	private final EtchedBorder tipoBordo = new EtchedBorder(EtchedBorder.LOWERED);
	private final TitledBorder bordo = new TitledBorder(tipoBordo);
	//ICONE
	private final ImageIcon iconaCerca = new ImageIcon(new ImageIcon(controlloConfig.iconPATH).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
	//DIMENSION
	private final Dimension textAreaDim = new Dimension(100, 300);
	// JLABEL
	private final JLabel[] l_Parametri = new JLabel[controlloConfig.NUM_PARAMETRI];
	// JTEXTFIELD
	final static JTextField[] v_Parametro = new JTextField[controlloConfig.NUM_PARAMETRI];
	// JTEXTAREA
	private final JTextArea v_Descrizione = new JTextArea();
	// JBUTTON
	private final JButton b_Conferma = new JButton("CONFERMA");
	private final JButton b_Indietro = new JButton("Indietro");
	private final JButton b_ConsiglioFornitore = new JButton(iconaCerca);
	private final JButton b_ConsiglioCostruttore = new JButton(iconaCerca);
	//JRADIOBUTTON
	private final JRadioButton rb_meccanico = new JRadioButton("Meccanico");
	private final JRadioButton rb_elettrico = new JRadioButton("Elettrico");
	private final JRadioButton rb_officina = new JRadioButton("Officina");
	private final JRadioButton rb_magazzino = new JRadioButton("Magazzino");
	private final JRadioButton rb_ufficio = new JRadioButton("Ufficio");
	private final JRadioButton rb_campata = new JRadioButton("Campata");
	//JBUTTONGROUP
	private final ButtonGroup bg_tipologia = new ButtonGroup();
	private final ButtonGroup bg_ubicazione = new ButtonGroup();
	
	InterfacciaMA() {
		super("Modifica articolo");
		container = getContentPane();
		container.setLayout(layout);
		constraints = new GridBagConstraints();
		container.setLayout(layout);
		
		if(trovaArticolo()) {		
			for(int i=0; i<controlloConfig.NUM_PARAMETRI; i++) {
				l_Parametri[i] = new JLabel(controlloConfig.NOME_PARAMETRO[i] + ": ");
				l_Parametri[i].setFont(bigFont);
				addComponent(l_Parametri[i], i, 0, 1, 1);
				if(controlloConfig.NOME_PARAMETRO[i] == "Descrizione") {
					v_Descrizione.setPreferredSize(textAreaDim);
					v_Descrizione.setLineWrap(true);
					v_Descrizione.setFont(bigFont);
					v_Descrizione.setBorder(bordo);
					v_Descrizione.setText(articoloTrovato.getDescrizione());
					addComponent(v_Descrizione, i, 1, 5, 1);
				}
				else if(controlloConfig.NOME_PARAMETRO[i] == "Tipologia") {
					bg_tipologia.add(rb_meccanico);
					bg_tipologia.add(rb_elettrico);
					rb_meccanico.setFont(bigFont);
					rb_elettrico.setFont(bigFont);
					addComponent(rb_meccanico, i, 1, 1, 1);
					addComponent(rb_elettrico, i, 2, 1, 1);
					if(articoloTrovato.getTipologia().equals("Meccanico")) {
						rb_meccanico.setSelected(true);
					}
					else if (articoloTrovato.getTipologia().equals("Elettrico")) {
						rb_elettrico.setSelected(true);
					}
				}
				else if(controlloConfig.NOME_PARAMETRO[i] == "Ubicazione") {
					bg_ubicazione.add(rb_officina);
					bg_ubicazione.add(rb_magazzino);
					bg_ubicazione.add(rb_ufficio);
					bg_ubicazione.add(rb_campata);
					rb_officina.setSize(50,50);
					rb_magazzino.setSize(50,50);
					rb_ufficio.setSize(50,50);
					rb_campata.setSize(50,50);
					rb_officina.setFont(bigFont);
					rb_magazzino.setFont(bigFont);
					rb_ufficio.setFont(bigFont);
					rb_campata.setFont(bigFont);
					addComponent(rb_officina, i, 1, 1, 1);
					addComponent(rb_magazzino, i, 2, 1, 1);
					addComponent(rb_ufficio, i, 3, 1, 1);
					addComponent(rb_campata, i, 4, 1, 1);
					if(articoloTrovato.getUbicazione().equals("Officina")) {
						rb_officina.setSelected(true);
					}
					else if(articoloTrovato.getUbicazione().equals("Magazzino")){
						rb_magazzino.setSelected(true);
					}
					else if(articoloTrovato.getUbicazione().equals("Ufficio")){
						rb_ufficio.setSelected(true);
					}
					else if(articoloTrovato.getUbicazione().equals("Campata")){
						rb_campata.setSelected(true);
					}
				}
				else if(controlloConfig.NOME_PARAMETRO[i] == "Costruttore") {v_Parametro[i] = new JTextField();
					v_Parametro[i].setFont(bigFont);
					v_Parametro[i].setBorder(bordo);
					addComponent(v_Parametro[i], i, 1, 5, 1);
					addComponent(b_ConsiglioCostruttore, i, 6, 1, 1);
					v_Parametro[i].setText(articoloTrovato.getCostruttore());
					b_ConsiglioCostruttore.addActionListener(event -> InterfacciaAA.consiglioCostruttoreBH(event));
					
				}
				else if(controlloConfig.NOME_PARAMETRO[i] == "Fornitore") {v_Parametro[i] = new JTextField();
					v_Parametro[i].setFont(bigFont);
					v_Parametro[i].setBorder(bordo);
					addComponent(v_Parametro[i], i, 1, 5, 1);
					addComponent(b_ConsiglioFornitore, i, 6, 1, 1);
					v_Parametro[i].setText(articoloTrovato.getFornitore());
					b_ConsiglioFornitore.addActionListener(event -> InterfacciaAA.consiglioFornitoreBH(event));
				}
				else {
					v_Parametro[i] = new JTextField();
					v_Parametro[i].setFont(bigFont);
					v_Parametro[i].setBorder(bordo);
					addComponent(v_Parametro[i], i, 1, 5, 1);
				}
			}
			v_Parametro[1].setText(articoloTrovato.getCodiceArticolo());
			v_Parametro[5].setText("" + articoloTrovato.getQuantita());
			v_Parametro[7].setText("" + articoloTrovato.getPrezzo());
	
			addComponent(b_Conferma, controlloConfig.NUM_PARAMETRI, 4, 1, 1);
			b_Conferma.setFont(bigFont);
			b_Conferma.addActionListener(event -> confermaBH(event));
			addComponent(b_Indietro, controlloConfig.NUM_PARAMETRI, 0, 1, 1);
			b_Indietro.setFont(bigFont);
			b_Indietro.addActionListener(event -> indietroBH(event));
			
			setSize(LARGHEZZA, ALTEZZA);
			setExtendedState(JFrame.MAXIMIZED_BOTH);
			setResizable(true);
			setVisible(true);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		else {
			@SuppressWarnings("unused")
			int res = JOptionPane.showOptionDialog(this, "Nessun articolo presente", "Errore", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
			InterfacciaAvvio.GUIavvio.setVisible(true);
			dispose();
		}
			
	}
	void modificaArticolo(String s) {
	}
	boolean trovaArticolo() {
		articoli = LeggiDaFile.leggiArticoli(false);
		if(articoli != null && articoli.size() > 0) {
			numArticoli = articoli.size();
			articoloCorrente = (String)JOptionPane.showInputDialog(container, "Immettere codice articolo:", "Selezione articolo", JOptionPane.PLAIN_MESSAGE, null, null, null);
			articoloCorrente = articoloCorrente.toUpperCase();
			if ((articoloCorrente != null) && (articoloCorrente.length() > 0)) {
				for(int i=0; i<numArticoli; i++) {
					if(articoli.get(i).getCodiceArticolo().contains(articoloCorrente)) {
						articoliTrovati.add(articoli.get(i));
					}
				}
				numArticoli = articoliTrovati.size();
				Object[] aTrovati = new Object[numArticoli];
				for(int i=0; i<numArticoli; i++) {
					aTrovati[i] = articoliTrovati.get(i).getCodiceArticolo();
				}
				articoloCorrente = (String)JOptionPane.showInputDialog(container, "Selezionare articolo da modificare:", "Selezione articolo", JOptionPane.PLAIN_MESSAGE, null, aTrovati, null);
				for(Articolo a: articoli) {
					if(a.getCodiceArticolo() == articoloCorrente) {
						articoloTrovato = a;
					}
				}
			}
			articoli.clear();
			return true;
		}
		else {
			return false;
		}
	}
	
	private void confermaBH(ActionEvent event) {
		riepilogo = "";
		controlloParametri();
		if(errore.length() > 0) {
			JOptionPane.showMessageDialog(this, errore, "Errore\u0021", JOptionPane.ERROR_MESSAGE);
		}
		else {
			riepilogo += "\n" + "Aggiornare l'articolo\u003F";
			risposta = JOptionPane.showConfirmDialog(this, riepilogo, "Riepilogo", JOptionPane.YES_NO_OPTION);
			if(risposta == JOptionPane.YES_OPTION) {
				InterfacciaAvvio.GUIavvio.setVisible(true);
				dispose();
				
				Articolo articoloCorrente = new Articolo();
				//ID
				articoloCorrente.setID();
				//TIPOLOGIA
				if(rb_meccanico.isSelected()) {
					articoloCorrente.setTipologia("Meccanico");
				}
				else if(rb_elettrico.isSelected()) {
					articoloCorrente.setTipologia("Elettrico");
				}
				//CODICE ARTICOLO
				articoloCorrente.setCodiceArticolo(v_Parametro[1].getText());
				//DESCRIZIONE
				articoloCorrente.setDescrizione(v_Descrizione.getText());
				//COSTRUTTORE
				articoloCorrente.setCostruttore(v_Parametro[3].getText());
				//FORNITORE
				articoloCorrente.setFornitore(v_Parametro[4].getText());
				//QUANTITA
				articoloCorrente.setQuantita(Integer.parseInt(v_Parametro[5].getText()));
				//UBICAZIONE
				if(rb_officina.isSelected()) {
					articoloCorrente.setUbicazione("Officina");
				}
				else if(rb_magazzino.isSelected()) {
					articoloCorrente.setUbicazione("Magazzino");
				}
				else if(rb_ufficio.isSelected()) {
					articoloCorrente.setUbicazione("Ufficio");
				}
				else if(rb_campata.isSelected()) {
					articoloCorrente.setUbicazione("Campata");
				}
				//PREZZO
				articoloCorrente.setPrezzo(Double.parseDouble(v_Parametro[7].getText()));
				new ScriviSuFile(articoloCorrente);
				ScriviSuFile.EliminaDaFile(articoloTrovato.getID());
			}
		}
	}
	
	private void controlloParametri() {
		errore = "";
		for(int i=0; i<controlloConfig.NUM_PARAMETRI; i++) {
			switch(controlloConfig.NOME_PARAMETRO[i]) {
			case "Tipologia":
				if(rb_meccanico.isSelected()) {
					riepilogo += l_Parametri[i].getText() + " --> " + rb_meccanico.getText() + "\n";
				}
				else if(rb_elettrico.isSelected()) {
					riepilogo += l_Parametri[i].getText() + " --> " + rb_elettrico.getText() + "\n";
				}
				break;
			case "Codice Articolo":
				if(v_Parametro[i].getText().equals("")) {
					errore += l_Parametri[i].getText() + " non pu\u00F2 essere vuoto" + "\n";
				}
				else {
					riepilogo += l_Parametri[i].getText() + " --> " + v_Parametro[i].getText() + "\n";
				}
				break;
			case "Descrizione":
				if(v_Descrizione.getText().equals("")) {
					errore += l_Parametri[i].getText() + " non pu\u00F2 essere vuota" + "\n";
				}
				else {
					riepilogo += l_Parametri[i].getText() + " --> " + v_Descrizione.getText() + "\n";
				}
				break;
			case "Costruttore":
				v_Parametro[i].setText(v_Parametro[i].getText().toLowerCase());
				riepilogo += l_Parametri[i].getText() + " --> " + v_Parametro[i].getText() + "\n";
				break;
			case "Fornitore":
				v_Parametro[i].setText(v_Parametro[i].getText().toLowerCase());
				riepilogo += l_Parametri[i].getText() + " --> " + v_Parametro[i].getText() + "\n";
				break;
			case "Quanti�":
				if(v_Parametro[i].getText().equals("")) {
					errore += l_Parametri[i].getText() + " non pu� essere vuota" + "\n";
				}
				else {
					try{
					    Integer.parseInt(v_Parametro[i].getText());
					    riepilogo += l_Parametri[i].getText() + " --> " + v_Parametro[i].getText() +"\n";
					}
					catch (Exception e){
						errore += l_Parametri[i].getText() + " non compilata correttamente (solo numeri)" + "\n";
					}
				}
				break;
			case "Ubicazione":
				if(rb_officina.isSelected()) {
					riepilogo += l_Parametri[i].getText() + " --> " + rb_officina.getText() + "\n";
				}
				else if(rb_magazzino.isSelected()) {
					riepilogo += l_Parametri[i].getText() + " --> " + rb_magazzino.getText() + "\n";
				}
				else if(rb_ufficio.isSelected()) {
					riepilogo += l_Parametri[i].getText() + " --> " + rb_ufficio.getText() + "\n";
				}
				else if(rb_campata.isSelected()) {
					riepilogo += l_Parametri[i].getText() + " --> " + rb_campata.getText() + "\n";
				}
				break;
			case "Prezzo":
				if(v_Parametro[i].getText().equals("")) {
					v_Parametro[i].setText("0");
					riepilogo += l_Parametri[i].getText() + " --> " + v_Parametro[i].getText() +"\n";
				}
				else {
					try{
					    Double.parseDouble(v_Parametro[i].getText());
					    riepilogo += l_Parametri[i].getText() + " --> " + v_Parametro[i].getText() +"\n";
					}
					catch (Exception e){
						errore += l_Parametri[i].getText() + " non compilato correttamente (Esempio 3.14)" + "\n";
					}
				}
				break;
			default:
				System.err.println("ERRORE - parametrto non controllato");
				break;
			}
		}
	}
	
	private void indietroBH(ActionEvent event) {
		InterfacciaAvvio.GUIavvio.setVisible(true);
		dispose();
	}
	
	private void addComponent(Component componente, int riga, int colonna, int larghezza, int altezza) {
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = colonna;
		constraints.gridy = riga;
		constraints.gridwidth = larghezza;
		constraints.gridheight = altezza;
		layout.setConstraints(componente, constraints);
		container.add(componente);
	}
}
