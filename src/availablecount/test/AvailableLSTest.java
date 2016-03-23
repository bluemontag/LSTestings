package availablecount.test;

import jacomatt.model.IncidenceCube;
import availablecount.SimpleGenWithAvailableCount;
import availablecount.SimpleGenWithAvailableCountRestart;
import commons.model.ILatinSquare;

public class AvailableLSTest {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		int n = Integer.parseInt(args[0]);
		
		SimpleGenWithAvailableCountRestart generator = new SimpleGenWithAvailableCountRestart(n);
		
		ILatinSquare ls = generator.generateLS();
		ILatinSquare ls2 = generator.getLs2();
		
		System.out.println(ls);
		System.out.println(ls2);
		
//		ls2.writeToFile("c:\\Users\\ignacio\\Documents\\IGS Research\\ls.txt");
		
//		double prom = 0.0;
//		double posib = 1;
//		
//		for (int i=0; i<n; i++) {
//			for (int j=0; j<n; j++) {
//				prom = prom + ls2.getValueAt(i, j);
//				if (i==250)
//					posib = posib * ls2.getValueAt(i, j);
//			}
//		}
//		System.out.println(prom/(n*n));
//		System.out.println(posib);
//		
//		IncidenceCube ic = new IncidenceCube(ls2);
//		
//		ic.drawIncidenceCube();
	}

}
