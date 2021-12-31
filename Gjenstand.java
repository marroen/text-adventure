
public class Gjenstand {
	String navn = "";
	int verdi = 0;
	
	/**
	 * Konstruktoer for Gjenstand
	 * @param navn Navn på gjenstanden
	 * @param verdi Gjenstandens verdi/pris
	 */
	public Gjenstand(String navn, int verdi) {
		this.navn = navn;
		this.verdi = verdi;
	}
	
	/**
	 * Oversiktlig utskrift av Gjenstand-objekt.
	 * Mest for debug.
	 */
	public void lesEgenskaper() {
		System.out.println("Navn: " + navn);
		System.out.println("Verdi: " + verdi);
	}
	
	/**
	 * Henter gjenstandens verdi/pris
	 * @return Verdi som int
	 */
	public int hentVerdi() {
		return verdi;
	}
	
	/**
	 * Henter gjenstandens navn
	 * @return Navnet som String
	 */
	public String hentNavn() {
		return navn;
	}

}
