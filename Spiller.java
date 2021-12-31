import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Spiller {
	protected String navn = "";
	protected Brukergrensesnitt bg; //*
	protected Sted start = null;
	private Skattkiste ryggsekk = null;
	protected Sted naa = null;
	private int formue = 0;
	protected VeivalgSted veivalg = null;
	protected boolean valg = false;
	protected Scanner sc = null;
	
	/**
	 * Konstruktoer for Spiller-klassen
	 * tar et Skattkiste-objekt, toemmer den og bruker som ryggsekk
	 * @param navn Navnet på spilleren som String
	 * @param start Stedet hvor spillet startes, det foerste stedet
	 * @param bg Et objekt som implementerer interfacet Brukergrensesnitt
	 */
	public Spiller(String navn, Sted start, Brukergrensesnitt bg) { //*
		this.navn = navn;
		this.bg = bg;
		this.start = start;
		ryggsekk = new Skattkiste();
		ryggsekk.toemKiste();
	}
	
	public String resultatStreng() {
		
		String res = "";
			
		res = "Spiller " + navn + " fikk " + formue + " poeng!";
		
		return res;
	}
	
	public int hentFormue() {
		return formue;
	}
	
	//*
	/**
	 * Kommuniserer med Brukergrensesnitt-klassene
	 * For hvert trekk, altsaa for hvert Sted med hver sin Skattkiste,
	 * saa skaffer den enten svar fra bruker via alternativer,
	 * eller saa kjoerer en Robot igjennom alle trekkene med tilfeldige valg
	 * @param naa Naavaerende Sted-objekt
	 * @return Neste Sted-objekt
	 */
	public Sted nyttTrekk(Sted naa) {
		
		if (bg instanceof Terminal) {
			sc = ((Terminal) bg).hentScanner();
		}
		
		this.naa = naa;
		
		Gjenstand[] inventar = ryggsekk.inventar();
		
		if (inventar[0] != null || inventar[1] != null) { // hvis ryggsekk har minst 1 item
			System.out.println();
			System.out.println("Dine gjenstander: ");
			int teller = 1;
			for (Gjenstand gjen : inventar) { 
				if (gjen != null) { 
					System.out.println(teller + ". " + gjen.hentNavn()); 
					teller++;
				}
			}
			System.out.println();
		}
		
		int svar = -1;
		String[] jaNei = {"Ja", "Nei"};
		if (inventar[0] != null || inventar[1] != null) { // hvis ryggsekk har minst 1 item
			svar = bg.beOmKommando("Vil du selge en gjenstand?", jaNei);
			if (svar == 0) {
				if (inventar[0] == null || inventar[1] == null) { // hvis ryggsekk har minst 1 spot
					String[] enkel = new String[1];
					if (inventar[0] != null) { enkel[0] = inventar[0].hentNavn(); }
					else { enkel[0] = inventar[1].hentNavn(); }
					
					svar = bg.beOmKommando("Hvilken vil du selge?", enkel);
					
					System.out.println("Du la " + enkel[0] + " ned i kisten.");
					
					int valgt = svar;
					
					int verdi = ryggsekk.leggInn(valgt);
					
					formue += verdi;
					System.out.println(verdi + " legges til din formue.");
					System.out.println();
				}
				else {  // hvis ryggsekk er full
					String[] hvilke = new String[2];
					int teller = 0;
					for (Gjenstand g : inventar) {
						if (g != null) { hvilke[teller] = g.hentNavn(); }
						teller++;
					}
					svar = bg.beOmKommando("Hvilken vil du selge?", hvilke);
					
					System.out.println("Du la " + inventar[svar].hentNavn() + " ned i kisten.");

					int valgt = svar;
					
					int verdi = ryggsekk.leggInn(valgt);
					
					formue += verdi;
					System.out.println(verdi + " legges til din formue.");
					System.out.println();
				}
			}
		}
		
		if (inventar[0] == null || inventar[1] == null) { // hvis ryggsekk har minst 1 spot
			svar = bg.beOmKommando("Vil du ta en gjenstand fra kisten?", jaNei);
			if (svar == 0) {
				if (inventar[0] == null) {
					Skattkiste naaKiste = naa.hentKiste();
					
					inventar[0] = naaKiste.taUt(); 
					
				}
				else if (inventar[0] != null && inventar[1] == null) { inventar[1] = naa.hentKiste().taUt(); }
			}
		}
		
		if (valg == false) {
			return naa.gaaVidere();
		}
		
		else { return veivalg; }
		
	}

}
