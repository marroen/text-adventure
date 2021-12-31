
public class VeivalgSted extends Sted {
	private final int STEDER_PER_VEIVALG = 3;
	private VeivalgSted[] utveier = new VeivalgSted[STEDER_PER_VEIVALG];
	private String[] retninger = {"venstre", "rett frem", "hoeyre"};
	private String retning = "";

	public VeivalgSted(String beskrivelse) {
		super(beskrivelse);
	}
	
	/**
	 * Setter utveiene til et VeivalgSted-objekt (3 slike)
	 * @param veivalg som VeivalgSted-array
	 */
	public void settUtveier(VeivalgSted[] veivalg) {
		utveier = veivalg;
	}
	
	/**
	 * Gir hvert VeivalgSted blant utveiene en retning, ala "rett frem"
	 */
	public void lagRetning() {
		for (int i=0; i<STEDER_PER_VEIVALG; i++) {
			utveier[i].settRetning(retninger[i]);
		}
	}
	
	/**
	 * Henter de mulige retningene
	 * @return en String-array med retningene
	 */
	public String[] hentRetninger() {
		return retninger;
	}
	
	/**
	 * Setter retningen til en VeivalgSted-utvei
	 * Brukes kun på lagRetning-metoden
	 * @param retningen til utveien
	 */
	private void settRetning(String retning) {
		this.retning = retning;
	}
	
	/**
	 * Henter retningen til en utvei
	 * @return utveiens retning som String
	 */
	public String hentRetning() {
		return retning;
	}
	
	/**
	 * Gaar videre ettersom hvilken retning er valgt
	 * @param indeks retning som indeks i retninger[]
	 * @return retninger-arrayens motpart blant utveier-arrayen
	 */
	public VeivalgSted gaaVidere(int indeks) {
		if (indeks == 0) { 
			System.out.println();
			System.out.println("Du gaar til " + retninger[0] + ".");
			return utveier[0]; 
		}
		else if (indeks == 1) { 
			System.out.println();
			System.out.println("Du gaar til " + retninger[1] + ".");
			return utveier[1]; 
		}
		else if (indeks == 2) { 
			System.out.println();
			System.out.println("Du gaar til " + retninger[2] + ".");
			return utveier[2]; 
		}
		else { 
			return null; 
		}
	}

}
