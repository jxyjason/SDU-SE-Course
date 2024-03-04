package com.mj;

import java.util.ArrayList;
import java.util.List;

import com.mj.file.Files;
import com.mj.printer.BinaryTreeInfo;
import com.mj.printer.BinaryTrees;
import com.mj.tree.AVLTree;
import com.mj.tree.BST;
import com.mj.tree.BinaryTree;
import com.mj.tree.BinaryTree.Visitor;

@SuppressWarnings("unused")
public class Main {

	static void test1() {
		Integer data[] = new Integer[] {
				67, 52, 92, 96, 53, 95, 13, 63, 34, 82, 76, 54, 9, 68, 39
		};
		
		AVLTree<Integer> avl = new AVLTree<>();
		for (int i = 0; i < data.length; i++) {
			avl.add(data[i]);
//			System.out.println("【" + data[i] + "】");
//			BinaryTrees.println(avl);
//			System.out.println("---------------------------------------");
		}
		
//		for (int i = 0; i < data.length; i++) {
//			avl.remove(data[i]);
//			System.out.println("【" + data[i] + "】");
//			BinaryTrees.println(avl);
//			System.out.println("---------------------------------------");
//		}
		
		
		BinaryTrees.println(avl);
	}
	
	static void test2() {
		List<Integer> data = new ArrayList<>();
		for (int i = 0; i < 100_0000; i++) {
			data.add((int)(Math.random() * 100_0000));
		}
		
		BST<Integer> bst = new BST<>();
		for (int i = 0; i < data.size(); i++) {
			bst.add(data.get(i));
		}
		for (int i = 0; i < data.size(); i++) {
			bst.contains(data.get(i));
		}
		for (int i = 0; i < data.size(); i++) {
			bst.remove(data.get(i));
		}
		
		AVLTree<Integer> avl = new AVLTree<>();
		for (int i = 0; i < data.size(); i++) {
			avl.add(data.get(i));
		}
		for (int i = 0; i < data.size(); i++) {
			avl.contains(data.get(i));
		}
		for (int i = 0; i < data.size(); i++) {
			avl.remove(data.get(i));
		}
	}
	
	public static void main(String[] args) {
		test1();
		
		
	}
}
