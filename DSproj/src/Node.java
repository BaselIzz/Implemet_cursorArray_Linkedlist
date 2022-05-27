// COMP242
// Project No. # 1
// Done by : Basel Said Izz
// ID  : 1180336
// Supervisor : Dr. M. Nawahdah

// Class written By Dr. M. Nawahdah

public class Node<T extends Comparable<T>> {
	T data;
	int next;
	

	public Node(T data, int next) {
		this.data = data;
		this.next = next;
	}

	@Override
	public String toString() {
		return data+"";
	}

	public T getData() {
		return data;
	}

	public int getNext() {
		return next;
	}

	public void setData(T data) {
		this.data = data;
	}

	public void setNext(int next) {
		this.next = next;
	}

}
