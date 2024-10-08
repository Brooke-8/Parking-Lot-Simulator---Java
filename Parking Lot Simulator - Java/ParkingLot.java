/**
 * @author Mehrdad Sabetzadeh and Brooke MacQuarrie, University of Ottawa
 */
public class ParkingLot {

	//List for storing occupancy information in the lot
	private List<Spot> occupancy;

	//The maximum number of cars that the lot can accommodate
	private int capacity;

	/**
	 * Constructs a parking lot with a given (maximum) capacity
	 * @param capacity is the (maximum) capacity of the lot
	 */
	public ParkingLot(int capacity) throws IllegalArgumentException{

		if (capacity <= 0) {
			throw new IllegalArgumentException();
		}

		this.capacity = capacity;
		this.occupancy = new SinglyLinkedList<Spot>();
	}

	/**
	 * Parks a car (c) in the parking lot.
	 * 
	 * @param c         is the car to be parked
	 * @param timestamp is the (simulated) time when the car gets parked in the lot
	 */
	public void park(Car c, int timestamp) throws NullPointerException, IllegalArgumentException{
		if (c == null){
			throw new NullPointerException("Car cannot be Null");
		}
		if (timestamp <0){
			throw new IllegalArgumentException("Timestamp must be positive");
		}
		Spot entering = new Spot(c, timestamp);
		if (occupancy.size() > capacity){
			//System.out.println("Car "+c+" cannot be parked. Parking lot full.");
		}
		else{
			occupancy.add(entering);
		}
	}

	/**
	 * Removes the car (spot) parked at list index i in the parking lot
	 * 
	 * @param i is the index of the car to be removed
	 * @return the car (spot) that has been removed
	 */
	public Spot remove(int i) throws ArrayIndexOutOfBoundsException{
		if (i>occupancy.size() || i<0){
			throw new ArrayIndexOutOfBoundsException();
		}
		Spot removed = occupancy.remove(i);
		return removed;
	
	}

	public boolean attemptParking(Car c, int timestamp) throws NullPointerException, IllegalArgumentException {
		if (c == null){
			throw new NullPointerException("Car Cannot be Null");
		}
		if (timestamp < 0){
			throw new IllegalArgumentException("Timestamp cannot be negative");
		}
		if (occupancy.size()+1 <= capacity){
			park(c, timestamp);
			return true;
		}
		else{return false;}
	
	}

	/**
	 * @return the capacity of the parking lot
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Returns the spot instance at a given position (i)
	 * 
	 * @param i is the parking row index
	 * @return the spot instance at a given position (i)
	 */
	public Spot getSpotAt(int i) throws ArrayIndexOutOfBoundsException{
		if (i>occupancy.size() || i<0){
			throw new ArrayIndexOutOfBoundsException();
		}
		return occupancy.get(i);

	}

	/**
	 * @return the total number of cars parked in the lot
	 */
	public int getOccupancy() {
		return occupancy.size();
	}

	/**
	 * @return String representation of the parking lot
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("Total capacity: " + this.capacity + System.lineSeparator());
		buffer.append("Total occupancy: " + this.occupancy.size() + System.lineSeparator());
		buffer.append("Cars parked in the lot: " + this.occupancy + System.lineSeparator());

		return buffer.toString();
	}
}