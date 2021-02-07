package magazzino;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

class ScriviSuFile {
	
	static String sep = ";";
	private String tempCreazione;
	private final File f = new File(controlloConfig.filePATH);
	
	ScriviSuFile(Articolo a) {
		if(!f.exists()) {
			BufferedWriter bw;
			try {
				bw = new BufferedWriter(new FileWriter(f, true));
				tempCreazione = "ID";
				for(int i=0; i<controlloConfig.NUM_PARAMETRI; i++) {
					tempCreazione += sep + controlloConfig.NOME_PARAMETRO[i];
				}
				bw.append(tempCreazione);	
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(f, true));
			bw.append("\n" + 
					  a.getID() + sep + 
					  a.getTipologia() + sep + 
					  a.getCodiceArticolo() + sep + 
					  a.getDescrizione() + sep + 
					  a.getCostruttore() + sep + 
					  a.getFornitore() + sep +
					  a.getQuantita() + sep +
					  a.getUbicazione() + sep +
					  a.getPrezzo());
			bw.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "File aperto in un'altra applicazione" + "\n" + "chiuderla per aggiungere nuovi articoli", "Errore!", JOptionPane.ERROR_MESSAGE);
		}
	}
	static void EliminaDaFile(int oldID) {
		File inputFile = new File(controlloConfig.filePATH);
		File tempFile = new File(controlloConfig.tempFilePATH);

		try {
			BufferedReader br = new BufferedReader(new FileReader(inputFile));
			BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
			String currentLine = br.readLine();
			
			bw.append(currentLine);
			while((currentLine = br.readLine()) != null) {
				if(Integer.parseInt(currentLine.split(ScriviSuFile.sep)[0]) == oldID) {
				}
				else {					
					bw.append("\n" + currentLine);
				}
			}
			tempFile.delete();
			tempFile.renameTo(inputFile);
			/*
			 * DA SISTEMARE
			 * NON ELIMINA E NON RINOMINA
			 */
			bw.close();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
