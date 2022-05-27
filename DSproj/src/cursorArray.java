// COMP242
// Project No. # 1
// Done by : Basel Said Izz
// ID  : 1180336
// Supervisor : Dr. M. Nawahdah


public class cursorArray<T extends Comparable<T>> {
	public cursorArray() {
		initalization();
	}

	Node<T>[] cArray = new Node[1500000];

	public int initalization() {

		for (int i = 0; i < cArray.length - 1; i++)
			cArray[i] = new Node(null, i + 1);
		cArray[cArray.length - 1] = new Node(null, 0);
		return 0;
	}

	public int createList() {
		int l = malloc();
		if (l == 0)
			System.out.println("Error: Out of space!!!");
		else
			cArray[l] = new Node("-", 0);
		return l;
	}

	public int malloc() {
		int p = cArray[0].next;
		cArray[0].next = cArray[p].next;
		return p;
	}

	public void free(int p) {
		cArray[p] = new Node(null, cArray[0].next);
		cArray[0].next = p;
	}

	public boolean isNull(int l) {
		return cArray[l] == null;
	}

	public boolean isEmpty(int l) {
		return cArray[l].next == 0;
	}

	public boolean isLast(int p) {
		return cArray[p].next == 0;
	}

	public void insertAtHead(T data, int l) {
		if (isNull(l)) // list not created
			return;
		int p = malloc();
		if (p != 0) {
			cArray[p] = new Node<>(data, cArray[l].next);
			cArray[l].next = p;
		} else
			System.out.println("Error: Out of space!!!");
	}

	public String traversList(int l) {
		
		System.out.print("list_" + l + "-->");
		String s = "";
		while (!isNull(l) && !isEmpty(l)) {

			l = cArray[l].next;

			s += cArray[l].data.toString() + "@";

			
			System.out.print(cArray[l] + "-->");
		}

		System.out.println("null");
		return s;
	}

	public int find(T data, int l) {
		while (!isNull(l) && !isEmpty(l)) {
			l = cArray[l].next;
			if (cArray[l].data.equals(data))
				return l;
		}
		return -1; // not found
	}

	public int findPrevious(T data, int l) {
		while (!isNull(l) && !isEmpty(l)) {
			if (cArray[cArray[l].next].data.equals(data))
				return l;
			l = cArray[l].next;
		}
		return -1; // not found
	}

	public Node delete(T data, int l) {
		int p = findPrevious(data, l);
		if (p != -1) {
			int c = cArray[p].next;
			Node temp = cArray[c];
			cArray[p].next = temp.next;
			free(c);
		}
		return null;

	}

// The Code below written by Basel Izz
	
	public void insertAtFinal(T data, int l) {
		if (isNull(l)) // list not created
			return;
		if (!isEmpty(l)) {

			int p = malloc();
			int next = cArray[l].next;
			while (!isEmpty(next)) {
				next = cArray[next].next;
			}
			cArray[p] = new Node<>(data, 0);
			cArray[next].next = p;
		} else
			insertAtHead(data, l);

	}


	// Method to sort a list of string (Words) based on LSD Radix Sort
	public void sortRadixLSD(int l, int maxlen) {
		// Position of the character which start at the least significant digit
		int charpos = maxlen - 1; 
		// point to next node of list 
		int next; 
		// loop for from Largest length -1  to zero ( last Char in word)
		// for all words
		while (0 <= charpos) { 
			next = cArray[l].next; 
			 // Check if reached the end  of list
			while (!(cArray[next].data == null)) {
				// Check the length of the word is less than to reached index charPos 
				if (cArray[next].data.toString().length() - 1 < charpos) {
					// add to bucket of space
					insertAtFinal(cArray[next].data, 1);
					int p = next;
					next = cArray[next].next;
					cArray[l].next = next;
					free(p);
				}		
					
				 else 
				 { 
				 // check the char if = space then word --- > Bucket of space
					if (cArray[next].data.toString().charAt(charpos) == 32) 
						insertAtFinal(cArray[next].data, 1); 
					else { 
						// else this char on this position is letter between a - z
						// Add the word to the bucket of the specific char case insensitive
						char charHelp = cArray[next].data.toString().toLowerCase().charAt(charpos); 																			
						int CH = 2;
						CH += (int) charHelp; // convert to ASCII 
						insertAtFinal(cArray[next].data, CH - 97);
					}
					int p = next; // free the node checked 
					next = cArray[next].next;
					cArray[l].next = next;
					free(p);
				 }
			}
			
			// loop to collect the words from Buckets in order and copy them
			// again to the main list to process the next char from the right side
			for (int i = 1; i <= 27; i++) { 
			// check for not empty buckets 
				if (!isEmpty(i)) {
					int indexTail = cArray[i].next;
					while (cArray[indexTail].data != null) {
						insertAtFinal(cArray[indexTail].data, l);
						int p = indexTail;
						indexTail = cArray[indexTail].next;
						cArray[i].next = indexTail;
						free(p);
					}
				}
			}
			//decrement the position of the length 	
			charpos--;
		}
	}

	// create a 27 bucket the first one for space and the other 27 buckets for letters [a -z]
	public void createBuckets() {
		
		for (int i = 0; i < 27; i++)
			createList();

	}

	// Clear a list
	public void clear(int l) { 
		int indexTail = cArray[l].next;
		while (cArray[indexTail].data != null) {
			int p = indexTail;
			indexTail = cArray[indexTail].next;
			cArray[l].next = indexTail;
			free(p);
		}
	}

}

