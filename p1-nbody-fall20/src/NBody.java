/**
 * @author YOUR NAME THE STUDENT IN 201
 * 
 * Simulation program for the NBody assignment
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class NBody {
	
	/**
	 * Read the specified file and return the radius
	 * @param fname is name of file that can be open
	 * @return the radius stored in the file
	 * @throws FileNotFoundException if fname cannot be open
	 */
	public static double readRadius(String fname) throws FileNotFoundException  {
		/**
		 * uses the information from file fname
		 * reads the first int and double to locate the radius and stores it in variable t
		 * returns the radius
		 */
		Scanner s = new Scanner(new File(fname));
		int x = s.nextInt();
		double t = s.nextDouble();
		double rad = 0.0;
		rad += t;
		s.close();
		return rad;
	}
	
	/**
	 * Read all data in file, return array of Celestial Bodies
	 * read by creating an array of Body objects from data read.
	 * @param fname is name of file that can be open
	 * @return array of Body objects read
	 * @throws FileNotFoundException if fname cannot be open
	 */
	public static CelestialBody[] readBodies(String fname) throws FileNotFoundException {
		/**
		 * scans through the bodies in the file
		 * stores the number of bodies in nb
		 * stores the xposition, ypos, x/y vels, mass, and file name by scanning the file
		 * creates a new celestial body with these properties
		 * passes it to an array containing all of the bodies
		 */

		Scanner s = new Scanner(new File(fname));
		int nb = 0;
		nb = s.nextInt();
		// TODO: read # bodies, store in nb
		CelestialBody[] nbbodies = new CelestialBody[nb];
		// TODO: Create array that can store nb CelestialBodies
		// TODO: read and ignore radius
		s.nextDouble();
		for(int k=0; k < nb; k++) {
			double xpos = s.nextDouble();
			double ypos = s.nextDouble();
			double xvel = s.nextDouble();
			double yvel = s.nextDouble();
			double mass = s.nextDouble();
			String filename = s.next();
			CelestialBody a = new CelestialBody(xpos, ypos, xvel, yvel, mass, filename);
			nbbodies[k] = a;
			// TODO: read data for each body
			// TODO: construct new body object and add to array

		}

		s.close();

		// TODO: return array of body objects read
		return nbbodies;
	}
	public static void main(String[] args) throws FileNotFoundException{
		double totalTime = 39447000.0;
		double dt = 25000.0;

		String fname= "./data/planets.txt";

		if (args.length > 2) {
			totalTime = Double.parseDouble(args[0]);
			dt = Double.parseDouble(args[1]);
			fname = args[2];
		}	
		
		CelestialBody[] bodies = readBodies(fname);
		double radius = readRadius(fname);

		StdDraw.enableDoubleBuffering();
		StdDraw.setScale(-radius, radius);
		StdDraw.picture(0,0,"images/starfield.jpg");

		// TODO: for music/sound, uncomment next line

		StdAudio.play("images/2001.wav");

		// run simulation until over

		for(double t = 0.0; t < totalTime; t += dt) {
			
			// TODO: create double arrays xforces and yforces
			//       to hold forces on each body
			/**
			 * creates arrays that can store the forces on each object
			 */
			int nb = bodies.length;
			double[] xforces = new double[nb];
			double[] yforces = new double[nb];

			// TODO: loop over all bodies
			// TODO: calculates netForcesX and netForcesY and store in
			//       arrays xforces and yforces

			for(int k=0; k < bodies.length; k++) {
				/**
				 * calculates the net forces on the body by calling a previously-written method and adds
				 * it to the correct array
				 */
				CelestialBody b = bodies[k];
				double h = b.calcNetForceExertedByX(bodies);
				xforces[k] = h;
				double g;
				g = b.calcNetForceExertedByY(bodies);
				yforces[k] = g;
  			}

			// TODO: loop over all bodies and call update
			//       with dt and corresponding xforces and yforces values
			for(int k=0; k < bodies.length; k++){
				/**
				 * updates the position and forces on every object
				 */
				bodies[k].update(dt, xforces[k], yforces[k]);
			}

			StdDraw.clear();
			StdDraw.picture(0,0,"images/starfield.jpg");
			
			// TODO: loop over all bodies and call draw on each one

			for(CelestialBody b : bodies){
				/**
				 * calls draw on every body in bodies
				 */
				b.draw();
			}

			StdDraw.show();
			StdDraw.pause(10);

		}
		
		// prints final values after simulation
		
		System.out.printf("%d\n", bodies.length);
		System.out.printf("%.2e\n", radius);
		for (int i = 0; i < bodies.length; i++) {
		    System.out.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
		   		              bodies[i].getX(), bodies[i].getY(), 
		                      bodies[i].getXVel(), bodies[i].getYVel(), 
		                      bodies[i].getMass(), bodies[i].getName());	
		}
	}
}
