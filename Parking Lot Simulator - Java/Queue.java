/**
 * @author Marcel Turcotte, Guy-Vincent Jourdan and Mehrdad Sabetzadeh
 *         (University of Ottawa)
 */

public interface Queue<E> {
	boolean isEmpty();

	void enqueue(E newElement);

	E dequeue();

	E peek();

	int size();
}
