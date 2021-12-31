import java.util.ArrayList;
import java.util.Random;

public class VeivalgTerreng extends Terreng {
	private final static int STEDER_PER_VEIVALG = 3;
	private static VeivalgSted startsted = null;
	
	/**
	 * Static factory
	 * @param start startstedet til Terrenget som Sted-objekt
	 */
	private VeivalgTerreng(Sted start) {
		super(start);
	}
	
	/**
	 * Lager veivalgene og dermed et VeivalgTerreng som static factory method
	 * Kaller Terreng sin lesSteder-metode for aa opprette en liste med steder og gjenstander
	 * Fyller terrenget med VeivalgSted-objekter og plasserer en kiste paa hvert sted
	 * @return et VeivalgTerreng-objekt med VeivalgSted-ene og Skattkister
	 */
	public static VeivalgTerreng lagVeivalg() {
		lesSteder();
		ArrayList<VeivalgSted> alleVeivalg = new ArrayList<VeivalgSted>();
		for (Sted s : alleSteder) {
			String beskrivelse = s.hentBeskrivelse();
			
			VeivalgSted veivalg = new VeivalgSted(beskrivelse);
			
			veivalg.plasserKiste(s.hentKiste());
			
			alleVeivalg.add(veivalg);
		}
		
		for (VeivalgSted vs : alleVeivalg) {
			VeivalgSted[] utveier = new VeivalgSted[STEDER_PER_VEIVALG];
			for (int i=0; i<STEDER_PER_VEIVALG; i++) {
				Random random = new Random();
				int rn = random.nextInt(alleVeivalg.size());
				
				utveier[i] = alleVeivalg.get(rn);

			}
			
			if (utveier[0] == null || utveier[1] == null || utveier[2] == null) {
				//*
			}
			
			else {
				vs.settUtveier(utveier);
			}
			
		}
		
		startsted = alleVeivalg.get(0);
		
		VeivalgTerreng terreng = new VeivalgTerreng(startsted);
		
		return terreng;
	}
	
	/**
	 * Henter VeivalgTerreng-ets startsted
	 * @return startstedet som VeivalgSted-objekt
	 */
	public VeivalgSted hentVeivalgStart() {
		return startsted;
	}
	
}
