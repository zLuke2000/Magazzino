package magazzino;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

@SuppressWarnings({"unused", "resource"})
class LeggiDaFile {

	static int ID;
	private static int i;
	private static String temp;
	private static String prevTemp;
	private static String attributiArticolo[];
	private final static File f = new File(controlloConfig.filePATH);
	private static List<Articolo> articoliLetti = new ArrayList<Articolo>();

	static int maxID() {
		prevTemp = "0";
		temp = "0";
		if(f.exists()) {
			FileReader fr;
			try {
				fr = new FileReader(controlloConfig.filePATH);
				BufferedReader br = new BufferedReader(fr);
				br.readLine();
				while ((temp = br.readLine()) != null) {
					if(Integer.parseInt(temp.split(ScriviSuFile.sep)[0]) > Integer.parseInt(prevTemp.split(ScriviSuFile.sep)[0])) {
				        prevTemp = temp;
					}
			    }
				attributiArticolo = prevTemp.split(ScriviSuFile.sep);
				ID = Integer.parseInt(attributiArticolo[0])+1;
				return ID;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return ID;
		}
		else {
			return 0;
		}
	}

	static List<Articolo> leggiArticoli(boolean add) {
		if(f.exists()) {
			FileReader fr;
			try {
				fr = new FileReader(controlloConfig.filePATH);
				BufferedReader br = new BufferedReader(fr);
				i = 0;
				br.readLine();
				while ((temp = br.readLine()) != null) {
					attributiArticolo = temp.split(ScriviSuFile.sep);
					articoliLetti.add(new Articolo(Integer.parseInt(attributiArticolo[0]),
												   attributiArticolo[1],
												   attributiArticolo[2],
												   attributiArticolo[3],
												   attributiArticolo[4],
												   attributiArticolo[5],
												   Integer.parseInt(attributiArticolo[6]),
												   attributiArticolo[7],
												   Double.parseDouble(attributiArticolo[8])));
					i++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return articoliLetti;
		}
		else {
			if(!add) {
				JOptionPane.showMessageDialog(null, "Nessun file trovato" + "\n" + "\u00C8 necessario inserire almeno un articolo", "Errore! #001", JOptionPane.ERROR_MESSAGE);
			}
			return null;
		}
	}
}