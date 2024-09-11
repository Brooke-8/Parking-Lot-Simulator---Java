/**
 * @author Brooke MacQuarrie, University of Ottawa
 */

 /*
  * Class for optimizing the number of parking spots required for a given number of cars entering per hour.
  */
public class CapacityOptimizer {
	private static final int NUM_RUNS = 10;

	private static final double THRESHOLD = 5.0d;

	public static int getOptimalNumberOfSpots(int hourlyRate) {
		int n = 1;
		boolean largeEnough = false;
		ParkingLot lot = new ParkingLot(n);
		
		

		while (!largeEnough){
			System.out.println("\n==== Setting lot capacity to: "+n+"====");
			int sum = 0;
			for (int i=0;i<NUM_RUNS;i++){
				lot = new ParkingLot(n);
				Simulator sim = new Simulator(lot, hourlyRate, Simulator.SIMULATION_DURATION);
				long start = System.currentTimeMillis();
				sim.simulate();
				long end = System.currentTimeMillis();
				sum += sim.getIncomingQueueSize();
				System.out.println("Simulation run "+i+" ("+(end-start)+"ms); Queue length at the end of simulation run: "+sim.getIncomingQueueSize());
			}
			if (sum/NUM_RUNS <= THRESHOLD){
				break;
			}
			else{
				n++;
			}
		}
		return (n);
	
	}

	public static void main(String args[]) {

		long mainStart = System.currentTimeMillis();

		if (args.length < 1) {
			System.out.println("Usage: java CapacityOptimizer <hourly rate of arrival>");
			System.out.println("Example: java CapacityOptimizer 11");
			return;
		}

		if (!args[0].matches("\\d+")) {
			System.out.println("The hourly rate of arrival should be a positive integer!");
			return;
		}

		int hourlyRate = Integer.parseInt(args[0]);

		int lotSize = getOptimalNumberOfSpots(hourlyRate);

		System.out.println();
		System.out.println("SIMULATION IS COMPLETE!");
		System.out.println("The smallest number of parking spots required: " + lotSize);

		long mainEnd = System.currentTimeMillis();

		System.out.println("Total execution time: " + ((mainEnd - mainStart) / 1000f) + " seconds");

	}
}