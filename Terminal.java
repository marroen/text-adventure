import java.util.Scanner;

public class Terminal implements Brukergrensesnitt{
	Scanner sc = null;
	
	public Terminal(Scanner sc) {
		this.sc = sc;
	}
	
	/**
	 * Henter scanneren til Terminal-objektet
	 * @return Scanner sc
	 */
	public Scanner hentScanner() {
		return sc;
	}
	
	/**
	 * Tar inn en beskrivelse String av naavaerende Sted
	 * Siden hvert Sted har en Skattkiste, foelger ogsaa
	 * melding om status for hva spilleren ser foran seg
	 */
	public void giStatus(String status) {
		System.out.println(status);
		System.out.println("Du ser en kiste foran deg.");
	}

	/**
	 * Metode for aa ta inn et input svar fra bruker,
	 * og deretter sende svaret som int tilbake til Spill-loekken
	 * @return svar Indeks-svaret som int
	 */
	public int beOmKommando(String spoersmaal, String[] alternativer) {
		
		System.out.println(spoersmaal);
		int ind = 1;
		for (String s : alternativer) {
			System.out.println(ind + ". " + s);
			ind++;
		}
		
		int svar = sc.nextInt() - 1;
		
		return svar;
	}

}
