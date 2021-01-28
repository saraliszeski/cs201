

/**
 * Celestial Body class for NBody
 * @author ola
 *
 */
public class CelestialBody {

	private double myXPos;
	private double myYPos;
	private double myXVel;
	private double myYVel;
	private double myMass;
	private String myFileName;

	/**
	 * Create a Body from parameters
	 *
	 * @param xp       initial x position
	 * @param yp       initial y position
	 * @param xv       initial x velocity
	 * @param yv       initial y velocity
	 * @param mass     of object
	 * @param filename of image for object animation
	 */
	public CelestialBody(double xp, double yp, double xv,
						 double yv, double mass, String filename) {
		myXPos = xp;
		myYPos = yp;
		myXVel = xv;
		myYVel = yv;
		myMass = mass;
		myFileName = filename;

	}

	/**
	 * Copy constructor: copy instance variables from one
	 * body to this body
	 *
	 * @param b used to initialize this body
	 */
	public CelestialBody(CelestialBody b) {
//		CelestialBody obj = new CelestialBody(b);
		myFileName = b.myFileName;
		myMass = b.myMass;
		myXPos = b.myXPos;
		myYPos = b.myYPos;
		myXVel = b.myXVel;
		myYVel = b.myYVel;
	}

	public double getX() {
		return myXPos;
	}

	public double getY() {
		return myYPos;
	}

	public double getXVel() {
		return myXVel;
	}

	public double getYVel() {
		return myYVel;
	}

	public double getMass() {
		return myMass;
	}

	public String getName() {
		return myFileName;
	}

	/**
	 * Return the distance between this body and another
	 *
	 * @param b the other body to which distance is calculated
	 * @return distance between this body and b
	 */
	public double calcDistance(CelestialBody b) {
		double dx = this.myXPos - b.myXPos;
		double dy = this.myYPos - b.myYPos;
		double rsq = dx * dx + dy * dy;
		double r = Math.sqrt(rsq);
		return r;
	}

	public double calcForceExertedBy(CelestialBody b) {
		double g = 6.67 * 1e-11;
		double rst = this.calcDistance(b);
		double masses = (this.myMass * b.myMass) / (rst * rst);
		return masses * g;
	}

	public double calcForceExertedByX(CelestialBody b) {
		double dxx = (b.myXPos - this.myXPos);
		double r = this.calcDistance(b);
		double f = this.calcForceExertedBy(b);
		double fx = (f * dxx) / r;
		return fx;
	}

	public double calcForceExertedByY(CelestialBody b) {
		double yxx = (b.myYPos - this.myYPos);
		double r = this.calcDistance(b);
		double f = this.calcForceExertedBy(b);
		double fy = (f * yxx) / r;
		return fy;
	}

	public double calcNetForceExertedByX(CelestialBody[] bodies) {
		double sum = 0.0;
		for (CelestialBody b : bodies) {
			if (!b.equals(this)) {
				sum += this.calcForceExertedByX(b);
			}
		}
		return sum;
	}


	public double calcNetForceExertedByY(CelestialBody[] bodies) {
		double sum = 0.0;
		for (CelestialBody b : bodies) {
			if (!b.equals(this)) {
				sum += this.calcForceExertedByY(b);

			}
		}
		return sum;
	}

	public void update(double deltaT, double xforce, double yforce) {
		double fx = xforce;
		double fy = yforce;
		double dt = deltaT;
		double m = this.myMass;
		double ax = fx/m;
		double ay = fy/m;
		double nvx = myXVel + dt * ax;
		double nvy = myYVel + dt * ay;
		double nx = myXPos + dt * nvx;
		double ny = myYPos + dt * nvy;
		myXPos = nx;
		myYPos = ny;
		myXVel = nvx;
		myYVel = nvy;



	}

	/**
	 * Draws this planet's image at its current position
	 */
	public void draw() {
		StdDraw.picture(myXPos,myYPos,"C:/Users/sara_/Documents/p1/p1-nbody-fall20/images/"+myFileName);
	}
}
