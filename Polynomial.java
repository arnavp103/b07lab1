import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class Polynomial {
	// These are the non-zero coefficients of the polynomial
	double[] coef;
	// These represent the integer power of the term of the polynomial, [0, 1, 3]
	// would refer to a constant + x + x^3. Not necessarily in ascending order.
	int[] exp;

	public Polynomial() {
		this.coef = new double[1];
		this.exp = new int[1];
	}

	public Polynomial(double[] coef, int[] exp) {
		this.coef = coef;
		this.exp = exp;
	}

	// a constructor that takes an argument of type File and reads the one line of
	// the file to create a polynomial
	public Polynomial(File poly) throws FileNotFoundException {
		try {
			Scanner sc = new Scanner(poly);
			String line = sc.nextLine();
			String[] terms = line.split("(?=[-+])");
			this.coef = new double[terms.length];
			this.exp = new int[terms.length];
			for (int i = 0; i < terms.length; i++) {
				String[] twonums = terms[i].split("x");
				if (twonums.length == 1) {
					this.coef[i] = Double.parseDouble(twonums[0]);
					this.exp[i] = 0;
				} else {
					this.coef[i] = Double.parseDouble(twonums[0]);
					this.exp[i] = Integer.parseInt(twonums[1]);
				}
			}

			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			this.coef = new double[1];
			this.exp = new int[1]; // if theres as blank file
		}
	}


	public void saveToFile(String fname) {
		try {
			FileWriter output = new FileWriter(fname, false);
			String[] temp = new String[coef.length * 5];
			int counter = 0;
			for (int i = 0; i < coef.length; i++) {
				if (coef[i] < 0 && i == 0) {
					temp[counter] = "-";
					counter++;
				} else if (coef[i] < 0) {
					temp[counter-1] = " - ";
				}

				temp[counter] = String.valueOf(coef[i]);
				counter++;
				if (exp[i] == 1) {
					temp[counter] = "x";
					counter++;
				} else if (exp[i] > 1) {
					temp[counter] = "x";
					counter++;
					temp[counter] = String.valueOf(exp[i]);
					counter++;
				}

				temp[counter] = "+";
				counter++;
			}
			String[] fin = new String[counter-1];
			for (int j = 0; j < counter - 1; j++) {
				fin[j] = temp[j];
			}

			String line = String.join("", fin);
			output.write(line);

			output.close();
		} catch (Exception e) {
			System.out.println("File not found");
		}

	}

	// a method to add a polynomial to the current polynomial
	public Polynomial add(Polynomial p) {
		int len = this.coef.length + p.coef.length;
		double[] tempCoef = new double[len];
		int[] tempExp = new int[len];

		for (int i = 0; i < this.exp.length; i++) {
			tempCoef[i] = this.coef[i];
			tempExp[i] = this.exp[i];
		}

		for (int j = 0; j < p.exp.length; j++) {
			boolean found = false;
			for (int k = 0; k < tempExp.length; k++) {
				if (p.exp[j] == tempExp[k]) {
					tempCoef[k] += p.coef[j];
					found = true;
					break;
				}
			}
			if (!found) {
				tempCoef[this.exp.length + j] = p.coef[j];
				tempExp[this.exp.length + j] = p.exp[j];
			}
		}

		// Now we need to remove the zero terms from the array
		for (int i = 0; i < tempExp.length; i++) {
			if (tempCoef[i] == 0) {
				len--;
			}
		}

		double newCoef[] = new double[len];
		int newExp[] = new int[len];
		int counter = 0;
		for (int i = 0; i < tempExp.length; i++) {
			if (tempCoef[i] != 0) {
				newCoef[counter] = tempCoef[i];
				newExp[counter] = tempExp[i];
				counter++;
			}
		}
		return new Polynomial(newCoef, newExp);
	}

	// a method to evalute a polynomial for a given value of x
	public double evaluate(double x) {
		double result = 0;
		for (int i = 0; i < this.coef.length; i++) {
			result += this.coef[i] * Math.pow(x, this.exp[i]);
		}
		return result;
	}

	public boolean hasRoot(double x) {
		return this.evaluate(x) == 0;
	}

	// Returns the result of multiplying the current polynomial by the polynomial p,
	// should not contain redundant exponents.
	public Polynomial multiply(Polynomial p) {
		int len = this.coef.length * p.coef.length;
		double[] tempCoef = new double[len];
		int[] tempExp = new int[len];
		int counter = 0;

		for (int i = 0; i < this.coef.length; i++) {
			for (int j = 0; j < p.coef.length; j++) {
				double product = this.coef[i] * p.coef[j];
				int degree = this.exp[i] + p.exp[j];
				boolean hasDegree = false;
				for (int k = 0; k < tempExp.length; k++) {
					if (degree == tempExp[k]) {
						tempCoef[k] += product;
						hasDegree = true;
						break;
					}
				}
				if (!hasDegree) {
					tempCoef[counter] = product;
					tempExp[counter] = degree;
					counter++;
				}
			}
		}

		// Now we need to remove the zero terms from the array
		for (int i = 0; i < tempExp.length; i++) {
			if (tempCoef[i] == 0) {
				len--;
			}
		}

		double newCoef[] = new double[len];
		int newExp[] = new int[len];
		int newCounter = 0;
		for (int i = 0; i < tempExp.length; i++) {
			if (tempCoef[i] != 0) {
				newCoef[newCounter] = tempCoef[i];
				newExp[newCounter] = tempExp[i];
				newCounter++;
			}
		}
		return new Polynomial(newCoef, newExp);

	}

}
