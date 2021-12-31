
public class VeivalgSpiller extends Spiller {
	
	public VeivalgSpiller(String navn, Sted start, Brukergrensesnitt bg) { //*
		super(navn, start, bg);
	}
	
	/**
	 * Kaller Spiller sin nyttTrekk-metode og legger til spoersmaalet
	 * om hvor brukeren/roboten vil gaa neste gang
	 * @param et Sted-objekt som viser hvilke Sted bruker/robot er paa naa
	 * @return et VeivalgSted-objekt som er det neste (valgte) stedet etter naavaerende sted
	 */
	public VeivalgSted nyttTrekk(Sted naa) {

		veivalg = (VeivalgSted) naa;
		
		valg = true;
		
		super.nyttTrekk(naa);
		
		String[] alternativer = veivalg.hentRetninger();
		
		int svar = -1;
		System.out.println();
		svar = bg.beOmKommando("Hvilken retning vil du gaa?", alternativer);
		if (svar == 0) {
			// gaa venstre
			return veivalg.gaaVidere(0);
		}
		else if (svar == 1) {
			// gaa rett frem
			return veivalg.gaaVidere(1);
		}
		else if (svar == 2) {
			// gaa hoeyre
			return veivalg.gaaVidere(2);
		}
		
		return null; // return veivalgsted sin gaavidere
		
	}

}
