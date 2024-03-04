package com.mj.set;

import java.util.Comparator;

import com.mj.tree.BinaryTree;
import com.mj.tree.RBTree;

public class TreeSet<E> implements Set<E> {
	private RBTree<E> tree;
	
	public TreeSet() {
		this(null);
	}
	
	public TreeSet(Comparator<E> comparator) {
		tree = new RBTree<>(comparator);
	}
	
	@Override
	public int size() {
		return tree.size();
	}

	@Override
	public boolean isEmpty() {
		return tree.isEmpty();
	}

	@Override
	public void clear() {
		tree.clear();
	}

	@Override
	public boolean contains(E element) {
		return tree.contains(element);
	}

	@Override
	public void add(E element) {
		tree.add(element);
	}

	@Override
	public void remove(E element) {
		tree.remove(element);
	}

	@Override
	public void traversal(Visitor<E> visitor) {
		tree.inorder(new BinaryTree.Visitor<E>() {
			@Override
			public boolean visit(E element) {
				return visitor.visit(element);
			}
		});
	}

}
