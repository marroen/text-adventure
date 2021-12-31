import javafx.geometry.Insets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;
import java.util.List;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// TO-DO //
// FULLFOERE TERRENG
// FULLFOERE SPILL, SPILLER

// HVERT STED SKAL HA EN SKATTKISTE
// HVER SKATTKISTE HAR TO GJENSTANDER
// HVERT TERRENG HAR EN START

// HVORDAN FAA STED HIT

// FIGURE OUT VEIVALGTERRENG
// TERRENG-STED-VEIVALGTERRENG-VEIVALGSTED

// OBS! FORANDRE TANKEMAATE, FORANDRE LESMEG ANG. STATIC FACTORY

// TENK FRA STARTSTED (1) OG TIL 2, 3 ELLER 4.

// BARE ENDRE PAA VEIVALGSPILLER SLIK AT VEIVALGSTED-
// OG TERRENG IMPLEMENTERES

// TILSLUTT SOERGE FOR AT BRUKER KAN VELGE MELLOM ENKEL-
// OG MULTITERRENG

public class Spill extends Application{
	
	private Button avslutt = null;
	private static Spiller spiller = null;
	private static int antSpillere = 0;
	static ArrayList<Spiller> spillere = new ArrayList<Spiller>();
	
	private static Scanner sc = new Scanner(System.in);
	private static Terminal terminal = new Terminal(sc);
	private static Robot robot = new Robot();
	
	private ReentrantLock lock = new ReentrantLock();
	
	class Klikkbehandler implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent e) {
			Platform.exit();
		}
		
	}
	
	class Avslutter implements Runnable {

		@Override
		public void run() {
			try {
				Thread.sleep(5000);
				Platform.exit();
			} catch (InterruptedException e) {} 
		}
		
	}
	
	class Kjoerer implements Runnable {

		@Override
		public void run() {
			try {
				
				lock.lock();
				
				kjoering();
				
				lock.unlock();
				
				Thread.sleep(1000);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private final static int TREKK = 8;
	
	public static void main(String[] args) {
		
		/*Scanner sc = new Scanner(System.in);
		Terminal terminal = new Terminal(sc);
		Robot robot = new Robot();*/
		
		System.out.println("Hvor mange spillere?");
		antSpillere = sc.nextInt();
		System.out.println();
		
		launch(args);
		
	}
	
	public void startTraad() {
		
		ArrayList<Thread> traadListe = new ArrayList<Thread>();
		
		for (int i=0; i<antSpillere; i++) {
			
			Runnable kjoer = new Kjoerer();
			
			Thread traad = new Thread(kjoer);
			
			traadListe.add(traad);
			
			traad.start();
			
			// System.out.println("Aktive traader mens kjoering: " + Thread.activeCount());
			
		}
		
		for (Thread t : traadListe) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// System.out.println("Aktive traader etter kjoering: " + Thread.activeCount());
		
	}
	
	public static void kjoering() {
		//for (int i=0; i<antSpillere; i++) {
			
			System.out.println("Hva er ditt navn?");
			String brukernavn = sc.next();
			System.out.println();
		
			System.out.println("Vil du ta dine egne valg eller ta tilfeldige valg?");
			System.out.println("1. Egne valg ");
			System.out.println("2. Tilfeldige valg ");
			int type = sc.nextInt();
		
			System.out.println("Velg en vanskelighetsgrad: ");
			System.out.println("1. Enkel ");
			System.out.println("2. Vanskelig ");
			int vanskelighet = sc.nextInt();
			System.out.println();
		
			Sted start = null;
			Sted naa = null;
		
			if (vanskelighet == 1) {
				Terreng t = Terreng.lesSteder();
				start = t.hentTerrengStart();
				naa = start;
				if (type == 1) {
					spiller = new Spiller(brukernavn, start, terminal); 
					spillere.add(spiller);
				}
				else if (type == 2) {
					spiller = new Spiller(brukernavn, start, robot); 
					spillere.add(spiller);
				}
			}
			else if (vanskelighet == 2) {
				VeivalgTerreng t = VeivalgTerreng.lagVeivalg();
				start = t.hentVeivalgStart();
				naa = (VeivalgSted) start;
				if (type == 1 ) {
					spiller = new VeivalgSpiller(brukernavn, (VeivalgSted) start, terminal);
					spillere.add(spiller);
				}
				else if (type == 2) {
					spiller = new VeivalgSpiller(brukernavn, (VeivalgSted) start, robot);
					spillere.add(spiller);
				}
			}
			
			System.out.println("Spiller: " + brukernavn);
			System.out.println("----------------");
			System.out.println();
		
			for (int j=0; j<TREKK; j++) {
				terminal.giStatus(naa.hentBeskrivelse());
				naa = spiller.nyttTrekk(naa);
			}
		
			System.out.println("----------------");
			System.out.println();
			
		}
	//}

	/**
	 * Lager stage, pane og scene, setter scenen
	 * Starter stage (nytt vindu) og lager en autoexit
	 * paa 5 sekunder som en traad og starter traaden
	 */
	@Override
	public void start(Stage arg0) throws Exception {
		
		startTraad();
		
		Stage stage = arg0;
		// --------
		
		StackPane pane = new StackPane();
		
		pane.getChildren().add(lagResultat());
		
		pane.getChildren().add(lagAvslutt());
		pane.setPrefSize(320, 320);
		
		Scene scene = new Scene(pane);
		
		stage.setTitle("Resultat");
		stage.setScene(scene);
		stage.show();
		
		Runnable autoexit = new Avslutter();
		Thread t = new Thread(autoexit);
		//t.start();
	}
	
	/**
	 * Sorterer spillerenes poengsummer,
	 * kaller i grunn bare sorterResultater-metoden men
	 * elimnerer behovet for aa kalle sorterResultater med dens argumenter
	 * Blir dermed et mer elegant metodekall i lagResultat-metoden
	 * @return sortert ArrayList<Spiller> fra hoyest til lavest poengsum
	 */
	public ArrayList<Spiller> resultat() {
		
		Map<Spiller, Integer> usortert = new HashMap<Spiller, Integer>();
		
		for (Spiller s : spillere) {
			usortert.put(s, s.hentFormue());
		}
		
		ArrayList<Spiller> sortert = sorterResultater(usortert);
		
		return sortert;

	}
	
	/**
	 * I stor grad hentet fra https://stackoverflow.com/questions/7965132/java-sort-hashmap-by-value
	 * Bruker Map/LinkedList som jeg ikke har brukt mye personlig
	 * Tar vare paa en noekkel (Spiller) og en verdi (formue)
	 * Kan dermed sortere hvilken spiller som har vunnet spillet
	 * @param map over hver spill og dens formue
	 * @return en ArrayList<Spiller> i sortert rekkefoelge
	 */
	public ArrayList<Spiller> sorterResultater(Map<Spiller, Integer> map) {
		List<Map.Entry<Spiller, Integer>> list = new LinkedList<Map.Entry<Spiller, Integer>>(map.entrySet());
		
		Collections.sort(list, new Comparator<Map.Entry<Spiller, Integer>>(){
			
			public int compare(Map.Entry<Spiller, Integer> m1,
					Map.Entry<Spiller, Integer> m2) {
				return (m2.getValue()).compareTo(m1.getValue());
			}
			
		});
		
		Map<Spiller, Integer> sortert = new LinkedHashMap<Spiller, Integer>();
		
		for (Map.Entry<Spiller, Integer> entry : list) {
			sortert.put(entry.getKey(), entry.getValue());
		}
		
		ArrayList<Spiller> bareSpillerne = new ArrayList<Spiller>();
		
		for (Map.Entry<Spiller, Integer> entry : list) {
			bareSpillerne.add(entry.getKey());
		}
		
		return bareSpillerne;
	}
	
	
	public VBox lagResultat() {
		VBox box = new VBox();
		Text text = null;
		
		box.setPadding(new Insets(10));
		
		box.setAlignment(Pos.CENTER);
		
		box.setSpacing(8);
		
		//ArrayList<Label> labelListe = new ArrayList<Label>();
		
		ArrayList<Spiller> resultat = resultat();
		
		for (Spiller s : resultat) {
			
			text = new Text(s.resultatStreng());
			
			box.getChildren().add(text);
			
		}
		
		return box;
	}
	
	public HBox lagAvslutt() {
		HBox box = new HBox();
		
		avslutt = new Button("Avslutt");
		avslutt.setOnAction(new Klikkbehandler());
		
		box.getChildren().add(avslutt);
		
		box.setAlignment(Pos.BOTTOM_CENTER);
		
		return box;
	}
	
}