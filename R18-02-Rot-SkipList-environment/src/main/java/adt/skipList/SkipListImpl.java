package adt.skipList;

public class SkipListImpl<T> implements SkipList<T> {

	protected SkipListNode<T> root;
	protected SkipListNode<T> NIL;

	protected int height;
	protected int maxHeight;

	protected boolean USE_MAX_HEIGHT_AS_HEIGHT;
	protected double PROBABILITY = 0.5;

	public SkipListImpl(int maxHeight) {
		if (USE_MAX_HEIGHT_AS_HEIGHT) {
			this.height = maxHeight;
		} else {
			this.height = 1;
		}
		this.maxHeight = maxHeight;
		root = new SkipListNode(Integer.MIN_VALUE, maxHeight, null);
		NIL = new SkipListNode(Integer.MAX_VALUE, maxHeight, null);
		connectRootToNil();
	}

	/**
	 * Faz a ligacao inicial entre os apontadores forward do ROOT e o NIL Caso
	 * esteja-se usando o level do ROOT igual ao maxLevel esse metodo deve
	 * conectar todos os forward. Senao o ROOT eh inicializado com level=1 e o
	 * metodo deve conectar apenas o forward[0].
	 */
	private void connectRootToNil() {
		if (USE_MAX_HEIGHT_AS_HEIGHT) {
			for (int i = 0; i < this.maxHeight; i++) {
				root.forward[i] = NIL;
			}
		} else {
			for (int i = 0; i < this.height; i++) {
				root.forward[i] = NIL;
			}
		}
	}

	/**
	 * Metodo que gera uma altura aleatoria para ser atribuida a um novo no no
	 * metodo insert(int,V)
	 */
	private int randomLevel() {
		int randomLevel = 1;
		double random = Math.random();
		while (Math.random() <= PROBABILITY && randomLevel < maxHeight) {
			randomLevel = randomLevel + 1;
		}
		return randomLevel;
	}

	@Override
	public void insert(int key, T newValue, int height) {
		if (height > maxHeight || height < 0 || newValue == null) {
			return;
		}

		fixRoot(height);

		SkipListNode<T>[] update = new SkipListNode[this.height];

		SkipListNode<T> aux = root;

		for (int i = this.height - 1; i >= 0; i--) {
			while (aux.forward[i] != null && aux.forward[i].key < key) {
				aux = aux.forward[i];
			}

			update[i] = aux; // guarda o caminho
		}

		aux = aux.forward[0];

		if (aux.key == key) {
			aux.value = newValue;
		} else {
			aux = new SkipListNode<T>(key, height, newValue);

			for (int i = 0; i < height; i++) {
				aux.forward[i] = update[i].forward[i];
				update[i].forward[i] = aux;
			}
		}
	}

	public void fixRoot(int height) {
		if (this.USE_MAX_HEIGHT_AS_HEIGHT) {
			height = this.maxHeight;
		}

		if (this.height < height) {
			for (int i = this.height; i < height; i++) {
				this.root.forward[i] = NIL;
			}
			this.height = height;
		}

	}

	@Override
	public void remove(int key) {
		SkipListNode<T>[] update = new SkipListNode[this.height];
		SkipListNode<T> aux = this.root;

		for (int i = this.height - 1; i >= 0; i--) {
			while (aux.forward[i] != null && aux.forward[i].key < key) {
				aux = aux.forward[i];
			}
			update[i] = aux;
		}

		if (aux.forward[0].key == key) {
			aux = aux.forward[0];
			for (int i = aux.height - 1; i >= 0; i--) {
				update[i].forward[i] = aux.forward[i];
				if (!this.USE_MAX_HEIGHT_AS_HEIGHT && (update[i] == this.root) && (update[i].forward[i] == this.NIL)
						&& i != 0)
					update[i].forward[i] = null;
			}

		}
	}

	@Override
	public int height() {
		return height;
	}

	@Override
	public SkipListNode<T> search(int key) {
		SkipListNode<T> aux = this.root;

		for (int i = height - 1; i >= 0; i--) {
			while (aux.forward[i] != null && aux.forward[i].key < key) {
				aux = aux.forward[i]; // anda ate o ultimo no com mesma altura
			}
		}

		aux = aux.forward[0];

		if (aux.key == key) {
			return aux;
		} else {
			return null;
		}
	}

	@Override
	public int size() {
		SkipListNode<T> aux = this.root.forward[0];
		int size = 0;

		while (aux != NIL) {
			size++;
			aux = aux.forward[0];
		}

		return size;
	}

	@Override
	public SkipListNode<T>[] toArray() {

		SkipListNode<T> aux = root;
		SkipListNode<T>[] array = new SkipListNode[this.size() + 2];

		int i = 0;
		while (aux != null) {
			array[i] = aux;
			i++;
			aux = aux.forward[0];
		}

		return array;
	}

}
