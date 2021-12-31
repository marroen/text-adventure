import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

// ANTAGELSE: Hver kiste har 2 gjenstander
public class Skattkiste {
	private final String GJENSTANDER = "gjenstander.txt";
	private final File FIL = new File(GJENSTANDER);
	private ArrayList<Gjenstand> alleGjenstander = new ArrayList<Gjenstand>();
	private final int KISTE_STOERRELSE = 2;
	private Gjenstand[] kisteGjenstander = new Gjenstand[KISTE_STOERRELSE];
	private ReentrantLock ledighet = new ReentrantLock();
	private Condition sjekkOmBrukes = ledighet.newCondition();
	
	public Skattkiste() {
		lesGjenstander();
	}
	
	/**
	 * Metode for aa lese inn gjenstanders verdi,
	 * tar to tilfeldige gjenstander fra fil og setter inn i kista
	 */
	public void lesGjenstander() {
		Scanner sc;
		Random random = new Random();
		try {
			sc = new Scanner(FIL);
			while(sc.hasNextLine()) {
				String[] linjer = sc.nextLine().split(" ");
				String navn = linjer[0];
				int verdi = Integer.parseInt(linjer[1]);
				
				Gjenstand gjenstand = new Gjenstand(navn, verdi);
				alleGjenstander.add(gjenstand);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (alleGjenstander.size() >= KISTE_STOERRELSE) {
			for (int i = 0; i<KISTE_STOERRELSE; i++) {
				int rn = random.nextInt(alleGjenstander.size());
				Gjenstand gjenstand = alleGjenstander.get(rn);
				kisteGjenstander[i] = gjenstand;
			}
		}
	}
	
	/**
	 * Toemmer kista saa den kan brukes som ryggsekk
	 */
	public void toemKiste() {
		kisteGjenstander[0] = null;
		kisteGjenstander[1] = null;
	}
	
	/**
	 * Henter listen over kista sine gjenstander
	 * @return Gjenstand-liste som array
	 */
	public Gjenstand[] inventar() {
		return kisteGjenstander;
	}
	
	/**
	 * Skriver til terminal hvilke gjenstander som er i kisten,
	 * Mest for debug
	 */
	public void les() {
		for (Gjenstand g : kisteGjenstander) {
			g.lesEgenskaper();
			System.out.println();
		}
	}
	
	/**
	 * Kan ogsaa kalles "leggNed"
	 * Metode som tar inn en indeks til ett Gjenstand-objekt,
	 * og deretter returnerer en verdi fra dette objektet
	 * @param indeks Gjenstand[]-indeks til alleGjenstander
	 * @return Verdien til objektet som int
	 */
	public int leggInn(int indeks) {
		
		ledighet.lock();
		
		int rn = 0;
		
		try {
			Random random = new Random();
			
			int verdi = kisteGjenstander[indeks].hentVerdi();
			rn = 0;
		
			if (verdi <= 100) {
				rn = random.nextInt(verdi + 100);
			}
			else { rn = random.nextInt((verdi + 101) - (verdi - 100)) + (verdi - 100);
			}
		
		
		
			kisteGjenstander[indeks] = null;
			if (indeks == 0 && kisteGjenstander[1] != null) {
				kisteGjenstander[0] = kisteGjenstander[1];
				kisteGjenstander[1] = null;
			}
			
			sjekkOmBrukes.signalAll();
			
		} finally {
			ledighet.unlock();
		}
		
		return rn;
		
	}
	
	/**
	 * Tar ut et Gjenstand-objekt fra Skattkiste-objektet sin Gjenstand-array, kisteGjenstander
	 * Det utvalgte objektet er tilfeldig, altså 50/50 siden det er kun 2 objekter i kisten
	 * @return Gjenstand-objektet som ble tilfeldig valgt
	 */
	public Gjenstand taUt() {
		
		ledighet.lock();
		
		int rn = 0;
		
		try {
			Random random = new Random();
			rn = random.nextInt(kisteGjenstander.length);
		
			System.out.println();
		
			System.out.println("Du valgte " + kisteGjenstander[rn].hentNavn() + "!");
			System.out.println(kisteGjenstander[rn].hentNavn() + " legges i ryggsekken din.");
			int forsvunnet = -1;
			if (rn == 0) { forsvunnet = 1; }
			else { forsvunnet = 0; }
			System.out.println(kisteGjenstander[forsvunnet].hentNavn() + " forsvinner i moerket til den magiske kisten,");
			System.out.println("foer hele kisten selv fordufter.");
			
			sjekkOmBrukes.signalAll();
		} finally {
			ledighet.unlock();
		}
		
		return kisteGjenstander[rn];
	}

}
