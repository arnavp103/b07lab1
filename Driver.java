import java.io.File;
import java.io.FileNotFoundException;

public class Driver {
	public static void main(String[] args) {
		Polynomial p = new Polynomial();
		System.out.println(p.evaluate(3));
		double[] c1 = { 6, 1, 4, 5 };
		int[] e1 = { 0, 1, 2, 3 };
		Polynomial p1 = new Polynomial(c1, e1);
		double[] c2 = { 1, -2, 5, 10, -9 };
		int[] e2 = { 4, 2, 1, 0, 3 };
		Polynomial p2 = new Polynomial(c2, e2);
		Polynomial s = p1.add(p2); // s = 1.0x^4 + 2.0x^2 + 6.0x^1 + 16.0x^0 + -4.0x^3
		// printPol(s);
		System.out.println("s(0.1) = " + s.evaluate(0.1));
		if (s.hasRoot(1))
			System.out.println("1 is a root of s");
		else
			System.out.println("1 is not a root of s");

		Polynomial p11 = new Polynomial(new double[] { 8, 3, 1 }, new int[] { 4, 2, 1 });
		Polynomial p22 = new Polynomial(new double[] { 4, 3, 1 }, new int[] { 3, 2, 1 });

		Polynomial p33 = p11.multiply(p22);
		printPol(p33); // 32.0x^7 + 24.0x^6 + 20.0^5 + 13.0x^4 + 6.0x^3 + 1x^2

		Polynomial p3 = new Polynomial(new double[] { 5, 4, 3, 1 }, new int[] { 8, 3, 2, 10 });
		Polynomial p4 = new Polynomial(new double[] { 10, 2, 3, 1 }, new int[] { 10, 8, 6, 1 });

		Polynomial p5 = p3.add(p4);
		// printPol(p5); // 7x^8 + 4x^3 + 3x^2 + 11x^10 + 3x^6 + x

		File poly = new File("poly.txt");
		try {
			Polynomial pf = new Polynomial(poly);
			printPol(pf);
		} catch (FileNotFoundException ok) {
			System.out.println("File not found");
		}

		p5.saveToFile("poly.txt");
	}

	private static void printPol(Polynomial p) {
		for (int i = 0; i < p.coef.length; i++) {
			System.out.print(p.coef[i] + "x^" + p.exp[i] + " + ");
		}
		System.out.println();
	}
}