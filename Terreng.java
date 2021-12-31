import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Terreng {
	private static final String STEDER = "steder.txt";
	private static final File FIL = new File(STEDER);
	protected static ArrayList<Sted> alleSteder = new ArrayList<Sted>();
	private static Sted start = null;
	
	/**
	 * Static factory
	 */
	protected Terreng(Sted start) {
		Terreng.start = start;
	}
	
	/**
	 * Fyller Terreng-objektet med Sted-er, static factory method
	 * Plasserer kister paa hvert Sted, legger til stedene i en ArrayList
	 * @return Returnerer start som Sted-objekt
	 */
	public static Terreng lesSteder() {
		Scanner sc;
		try {
			sc = new Scanner(FIL);
			while(sc.hasNextLine()) { // Lage liste med steder, sette utvei
				
				String beskrivelse = sc.nextLine();
				
				Sted sted = new Sted(beskrivelse);
				
				Skattkiste kiste = new Skattkiste();
				
				sted.plasserKiste(kiste);
				
				alleSteder.add(sted);
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if (alleSteder != null) { // setter utveiene, og setter siste Sted sin utvei til null
			start = alleSteder.get(0);
			for (int i=0; i<alleSteder.size(); i++) {
				Sted naa = null;
				Sted neste = null;
				naa = alleSteder.get(i);
				if (i+1 < alleSteder.size()) {
					neste = alleSteder.get(i+1);
				}
				naa.settUtvei(neste);
			}
		}
		Terreng t = new Terreng(start);
		return t;
	}
	
	public Sted hentTerrengStart() {
		return start;
	}
	
	public ArrayList<Sted> hentSteder(){
		return alleSteder;
	}

}
