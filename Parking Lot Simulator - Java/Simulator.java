/**
 * @author Brooke MacQuarrie and Mehrdad Sabetzadeh, University of Ottawa
 */
public class Simulator {

	public static final int PLATE_NUM_LENGTH = 3; //Length of car plate numbers

	public static final int NUM_SECONDS_IN_1H = 3600; //Number of seconds in one hour

	public static final int MAX_PARKING_DURATION = 8 * NUM_SECONDS_IN_1H; //Maximum duration a car can be parked in the lot

	public static final int SIMULATION_DURATION = 24 * NUM_SECONDS_IN_1H; //Total duration of the simulation in (simulated) seconds

	//The probability distribution for a car leaving the lot based on the duration
	//that the car has been parked in the lot
	public static final TriangularDistribution departurePDF = new TriangularDistribution(0, MAX_PARKING_DURATION / 2, MAX_PARKING_DURATION);

	private Rational probabilityOfArrivalPerSec; //The probability that a car would arrive at any given (simulated) second

	//The simulation clock. Initially the clock should be set to zero; the clock
	//should then be incremented by one unit after each (simulated) second
	private int clock;

	//Total number of steps (simulated seconds) that the simulation should run for.
	//This value is fixed at the start of the simulation. The simulation loop
	//should be executed for as long as clock < steps. When clock == steps, the
	//simulation is finished.
	private int steps;

	private ParkingLot lot; //Instance of the parking lot being simulated.

	private Queue<Spot> incomingQueue; //Queue for the cars wanting to enter the parking lot

	private Queue<Spot> outgoingQueue; //Queue for the cars wanting to leave the parking lot

	/**
	 * @param lot   is the parking lot to be simulated
	 * @param steps is the total number of steps for simulation
	 */
	public Simulator(ParkingLot lot, int perHourArrivalRate, int steps) throws NullPointerException, IllegalArgumentException{
		if (lot == null){
			throw new NullPointerException("Lot cannot be null");
		}
		if (perHourArrivalRate < 0 || steps < 0){
			throw new IllegalArgumentException("perHourArrivalRate or Steps is invalid");
		}
		this.lot = lot;
		this.steps = steps;
		this.clock = 0;
		this.probabilityOfArrivalPerSec = new Rational(perHourArrivalRate, 3600);
		incomingQueue = new LinkedQueue<Spot>();
		outgoingQueue = new LinkedQueue<Spot>();
	}


	/**
	 * Simulate the parking lot for the number of steps specified by the steps
	 * instance variable
	 */
	public void simulate() {
		this.clock = 0;
		while (clock < steps){
			if(RandomGenerator.eventOccurred(probabilityOfArrivalPerSec)){
				Car newCar = new Car(RandomGenerator.generateRandomString(PLATE_NUM_LENGTH));
				Spot newSpot = new Spot(newCar,clock);
				incomingQueue.enqueue(newSpot);
			}
			for (int i=0; i<lot.getOccupancy();i++){
				Spot viewing = lot.getSpotAt(i);
				if (clock - viewing.getTimestamp() >=MAX_PARKING_DURATION){
					outgoingQueue.enqueue(lot.remove(i));
				}
				else if (RandomGenerator.eventOccurred(departurePDF.pdf(clock - viewing.getTimestamp()))){
					outgoingQueue.enqueue(lot.remove(i));
				}
			}
			if (!incomingQueue.isEmpty()){
				if (lot.attemptParking(incomingQueue.peek().getCar(), clock)){
					incomingQueue.dequeue();
				}
			}
			if(!outgoingQueue.isEmpty()){
				outgoingQueue.dequeue();
		
			}
			clock++;
		}
	}

	public int getIncomingQueueSize() {
		return incomingQueue.size();
	}
}