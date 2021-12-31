import java.util.Random;

public class Robot implements Brukergrensesnitt{

	/**
	 * Faar inn status paa naavaerende Sted
	 * For aa kommunisere mellom bruker og loekken paa Spill
	 */
	public void giStatus(String status) {
		System.out.println(status);
		System.out.println("Du ser en kiste foran deg.");
	}

	/**
	 * Metode for aa ta inn et input svar fra bruker,
	 * og deretter sende svaret som int tilbake til Spill-loekken
	 * @return svar Tilfeldig indeks-svar som int
	 */
	public int beOmKommando(String spoersmaal, String[] alternativer) {
		
		System.out.println(spoersmaal); //*
		int ind = 1;
		for (String s : alternativer) {
			System.out.println(ind + ". " + s);
			ind++;
		}
		
		Random rn = new Random();
		int svar = rn.nextInt(alternativer.length);
		
		return svar;
	}
}
