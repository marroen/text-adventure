
public class Sted {
	private Skattkiste kiste = null;
	private String beskrivelse = "";
	private Sted utvei = null; //*
	
	public Sted(String beskrivelse) {
		this.beskrivelse = beskrivelse;
	}
	
	/**
	 * Setter et Sted-objekts utvei, i oppgave 2 altsaa neste steg
	 * @param utvei Utvei som Sted-objekt
	 */
	public void settUtvei(Sted utvei) {
		this.utvei = utvei;
	}
	
	/**
	 * Plasserer en Skattkiste på Sted-et
	 * @param kiste Skattkiste-objektet som skal plasseres
	 */
	public void plasserKiste(Skattkiste kiste) {
		this.kiste = kiste;
	}
	
	/**
	 * Henter Skattkiste-objektet til dette Sted-et
	 * @return Skattkiste-objektet
	 */
	public Skattkiste hentKiste() {
		return kiste;
	}
	
	/**
	 * Henter beskrivelsen, altsaa den linje-lange teksten
	 * som beskriver og gjoer hvert Sted unikt
	 * @return Beskrivelsen som String
	 */
	public String hentBeskrivelse() {
		return beskrivelse;
	}
	
	/**
	 * Henter i praksis neste Steg (i oppgave 2)
	 * Kan forandres til neste oppgave for aa haandtere
	 * et spesifikt Sted naar flere utveier er mulige
	 * @return Returnerer utveien som Sted-objekt
	 */
	public Sted gaaVidere() {
		if (!(this instanceof VeivalgSted)) {
			System.out.println();
			System.out.println("Du gaar videre.");
		}
		System.out.println();
		return utvei;
	}

}
