package adt.skipList;

public class SkipListImplAL<T> implements SkipList<T> {

	protected SkipListNode<T> root;
	protected SkipListNode<T> NIL;

	protected int height;
	protected int maxHeight;

	protected boolean USE_MAX_HEIGHT_AS_HEIGHT = true;
	protected double PROBABILITY = 0.5;

	public SkipListImplAL(int maxHeight) {
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
			for (int i = 0; i < maxHeight; i++) {
				root.forward[i] = NIL;
			}
		} else {
			root.forward[0] = NIL;
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
		if (newValue != null && height <= maxHeight) {
			SkipListNode<T>[] update = new SkipListNode[maxHeight];

			SkipListNode<T> aux = root;

			for (int i = height - 1; i >= 0; i--) {
				while (aux.forward[i] != null && aux.forward[i].key < key) {
					aux = aux.forward[i];
				}

				update[i] = aux; // guarda o caminho
			}

			aux = aux.forward[0];

			if (aux.key == key) {
				if (aux.height == height) {
					aux.value = newValue;
				} else {
					remove(key);
					insert(key, newValue, height);
				}
			} else {
				int newLevel = height;

				if (newLevel > this.height) {
					for (int i = this.height; i < newLevel; i++) {
						update[i] = root;
					}

					this.height = newLevel;
				}

				aux = new SkipListNode<T>(key, newLevel, newValue);
				
				for (int i = 0; i < newLevel; i++) {
					if (update[i].forward[i] == null) {
						aux.forward[i] = NIL;
					} else {
						aux.forward[i] = update[i].forward[i];
					}

					update[i].forward[i] = aux;
				}
			}
		}
	}

	@Override
	public void remove(int key) {
		SkipListNode<T>[] update = new SkipListNode[maxHeight];
		SkipListNode<T> aux = this.root;
		
		for (int i = this.height - 1; i >= 0; i--) {
			while (aux.forward[i].key < key) {
				aux = aux.forward[i];
			}

			update[i] = aux;
		}
		aux = aux.forward[0];

		if (aux.forward[0].key == key) {
			aux = aux.forward[0];
			
			for (int i = 0; i < height; i++) {
				if (!update[i].forward[i].equals(aux)) {
					break;
				}

				update[i].forward[i] = aux.forward[i];

				while (height > 1 && root.forward[height - 1].equals(NIL)) {
					if (USE_MAX_HEIGHT_AS_HEIGHT) {
						height--;
					} else {
						root.forward[height - 1] = null;
						height--;
					}
				}
			}
		}
	}

	@Override
	public int height() {
		int i;
		for (i = 0; i < height; i++) {
			if (root.forward[i] == NIL) {
				break;
			}
		}

		return i;
	}

	@Override
	public SkipListNode<T> search(int key) {
		SkipListNode<T> node = this.root;

		for (int i = (height - 1); i >= 0; i--) {
			while (node.forward[i].key < key) {
				node = node.forward[i];
			}
		}

		node = node.forward[0];

		if (node.key != key) {
			node = null;
		}

		return node;
	}

	@Override
	public int size() {
		int size = 0;

		SkipListNode<T> node = root.forward[0];
		while (node != NIL) {
			size++;
			node = node.forward[0];
		}

		return size;
	}

	@Override
	public SkipListNode<T>[] toArray() {
		SkipListNode<T>[] array = new SkipListNode[size() + 2];

		int i = 0;

		SkipListNode<T> node = root;
		while (node != null) {
			array[i++] = node;
			node = node.forward[0];
		}

		return array;
	}

}