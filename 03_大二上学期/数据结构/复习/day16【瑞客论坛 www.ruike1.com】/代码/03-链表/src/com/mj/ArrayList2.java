package com.mj;

@SuppressWarnings("unchecked")

/**
 * 有动态缩容操作
 * @author MJ Lee
 *
 * @param <E>
 */
public class ArrayList2<E> extends AbstractList<E> {
	/**
	 * 所有的元素
	 */
	private E[] elements;
	private static final int DEFAULT_CAPACITY = 10;
	
	public ArrayList2(int capaticy) {
		capaticy = (capaticy < DEFAULT_CAPACITY) ? DEFAULT_CAPACITY : capaticy;
		elements = (E[]) new Object[capaticy];
	}
	
	public ArrayList2() {
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * 清除所有元素
	 */
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
		
		// 仅供参考
		if (elements != null && elements.length > DEFAULT_CAPACITY) {
			elements = (E[]) new Object[DEFAULT_CAPACITY];
		}
	}

	/**
	 * 获取index位置的元素
	 * @param index
	 * @return
	 */
	public E get(int index) { // O(1)
		rangeCheck(index);
		
		return elements[index]; 
	}

	/**
	 * 设置index位置的元素
	 * @param index
	 * @param element
	 * @return 原来的元素ֵ
	 */
	public E set(int index, E element) { // O(1)
		rangeCheck(index);
		
		E old = elements[index];
		elements[index] = element;
		return old;
	}

	/**
	 * 在index位置插入一个元素
	 * @param index
	 * @param element
	 */
	public void add(int index, E element) { 
		/*
		 * 最好：O(1)
		 * 最坏：O(n)
		 * 平均：O(n)
		 */
		rangeCheckForAdd(index);
		
		ensureCapacity(size + 1);
		
		for (int i = size; i > index; i--) {
			elements[i] = elements[i - 1];
		}
		elements[index] = element;
		size++;
	} // size是数据规模

	/**
	 * 删除index位置的元素
	 * @param index
	 * @return
	 */
	public E remove(int index) {
		/*
		 * 最好：O(1)
		 * 最坏：O(n)
		 * 平均：O(n)
		 */
		rangeCheck(index);
		
		E old = elements[index];
		for (int i = index + 1; i < size; i++) {
			elements[i - 1] = elements[i];
		}
		elements[--size] = null;
		
		trim();
		
		return old;
	}

	/**
	 * 查看元素的索引
	 * @param element
	 * @return
	 */
	public int indexOf(E element) {
		if (element == null) {
			for (int i = 0; i < size; i++) {
				if (elements[i] == null) return i;
			}
		} else {
			for (int i = 0; i < size; i++) {
				if (element.equals(elements[i])) return i;
			}
		}
		return ELEMENT_NOT_FOUND;
	}
	
	/**
	 * 保证要有capacity的容量
	 * @param capacity
	 */
	private void ensureCapacity(int capacity) {
		int oldCapacity = elements.length;
		if (oldCapacity >= capacity) return;
		
		// 新容量为旧容量的1.5倍
		int newCapacity = oldCapacity + (oldCapacity >> 1);
		
		// 新容量为旧容量的2倍
		// int newCapacity = oldCapacity << 1;
		E[] newElements = (E[]) new Object[newCapacity];
		for (int i = 0; i < size; i++) {
			newElements[i] = elements[i];
		}
		elements = newElements;
		
		System.out.println(oldCapacity + "扩容为" + newCapacity);
	}
	
	private void trim() {
		// 30
		int oldCapacity = elements.length;
		// 15
		int newCapacity = oldCapacity >> 1;
		if (size > (newCapacity) || oldCapacity <= DEFAULT_CAPACITY) return;
		
		// 剩余空间还很多
		E[] newElements = (E[]) new Object[newCapacity];
		for (int i = 0; i < size; i++) {
			newElements[i] = elements[i];
		}
		elements = newElements;
		
		System.out.println(oldCapacity + "缩容为" + newCapacity);
	}
	
	@Override
	public String toString() {
		// size=3, [99, 88, 77]
		StringBuilder string = new StringBuilder();
		string.append("size=").append(size).append(", [");
		for (int i = 0; i < size; i++) {
			if (i != 0) {
				string.append(", ");
			}
			
			string.append(elements[i]);
			
//			if (i != size - 1) {
//				string.append(", ");
//			}
		}
		string.append("]");
		return string.toString();
	}
}
