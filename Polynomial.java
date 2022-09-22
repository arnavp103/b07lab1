public class Polynomial {
	// the 0th index is the constant term, then the indices match the power of x
	double[] coef;

	public Polynomial() {
		this.coef = new double[1];
	}

	public Polynomial(double[] coef) {
		this.coef = coef;
	}

	// a method to add a polynomial to the current polynomial
	public Polynomial add(Polynomial p) {
		double[] newCoef = new double[Math.max(this.coef.length, p.coef.length)];
		for (int i = 0; i < this.coef.length; i++)
			newCoef[i] += this.coef[i];
		for (int i = 0; i < p.coef.length; i++)
			newCoef[i] += p.coef[i];
		return new Polynomial(newCoef);
	}

	// a method to evalute a polynomial for a given value of x
	public double evaluate(double x) {
		double sum = 0;
		for (int i = 0; i < this.coef.length; i++) {
			sum += this.coef[i] * Math.pow(x, i);
		}
		return sum;
	}

	public boolean hasRoot(double x) {
		return this.evaluate(x) == 0;
	}

}