/**
 * @author Brooke MacQuarrie, University of Ottawa
 */
public interface List<E> {

	abstract void add(E elem);

	abstract E remove(int index);

	abstract boolean remove(E o);

	abstract E get(int index);

	abstract int size();

	abstract boolean isEmpty();
}
