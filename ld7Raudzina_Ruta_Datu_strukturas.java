import java.util.*;
import java.io.*;

public class ld7Raudzina_Ruta_Datu_strukturas {
	static class Node {
		public int data;
		public Node right, left;

		Node(int Data) {
			data = Data;
			right = null;
			left = null;
		}
	}
	
	static void showLNR(Node root) {
		if (root == null)
			return;
		showLNR(root.left); // L
		System.out.format("%4d", root.data); // N
		showLNR(root.right); // R
	}
	
	static void showLRN(Node root) {
		if (root==null) return;
		showLRN(root.left);
		showLRN(root.right);
		System.out.format("%4d", root.data);
	}
	
	static void showNLR(Node root) {
		if (root==null) return;
		System.out.printf("%4d", root.data);
		showNLR(root.left);
		showNLR(root.right);
	}
	
	static int count(Node root) {
		if (root == null)
			return 0;
		return 1 + count(root.left) + count(root.right); // N + L + R
	}

	static Node addSorted(Node root, int data) {
		if (root == null)
			return new Node(data);
		if (root.data > data)
			root.left = addSorted(root.left, data);
		else
			root.right = addSorted(root.right, data);
		return root;
	}
	
	static Node addBalanced(Node root, int data) {
		if (root == null)
			return new Node(data);
		if (count(root.left) < count(root.right))
			root.left = addBalanced(root.left, data);
		else
			root.right = addBalanced(root.right, data);
		return root;
	}
	
	static int leafsCount(Node root) {
		int leafs=0;
		if (root==null)return leafs;
		if (root.left==null && root.right==null) {
			leafs = 1 + leafsCount(root.left) + leafsCount(root.right);
		}
		else leafs = leafs + leafsCount(root.left) + leafsCount(root.right);
		return leafs;
	}
	
	static int height(Node root) {
		int height = 0;
		if (root==null) return height;
		int left = 1 + height(root.left);
		int right = 1 + height(root.right);
		if (left > right) height = left;
		else height = right;
		return height;
	}
	
	static int min(Node root) {
		if (root == null) return Integer.MAX_VALUE;
		int min1 = root.data;
		int min2 = min(root.left);
		int min3 = min(root.right);
		return Math.min(Math.min(min2, min3),min1);
	}

	static int max(Node root) {
		if (root== null) return Integer.MIN_VALUE;
		int max1 = root.data;
		int max2 = max(root.left);
		int max3 = max(root.right);
		return Math.max(Math.max(max2, max3), max1);
	}
	
	static boolean isSorted(Node root) {
		boolean sorted = false;
		if (root==null) return true;
		if (root.data>=max(root.left) && root.data<=min(root.right) && isSorted(root.left)==true && isSorted(root.right)==true) sorted = true;
		else sorted = false;
		return sorted;
	}
	
	static boolean isBalanced(Node root) {
		boolean balanced = false;
		if (root == null) return true;
		int leftSide = 1 + count(root.left);
		int rightSide = 1 + count(root.right);
		if (Math.abs(rightSide-leftSide)<=1 && isBalanced(root.left) && isBalanced(root.right)) balanced = true;
		else return false;
		return balanced;
	}
	
	static Node readSorted(Node root, String filename) {
		try {
			Scanner file = new Scanner(new File(filename));
			while (file.hasNextInt()) {
				root = addSorted(root, file.nextInt());
			}
			file.close();
		}
		catch(FileNotFoundException e){
			System.out.println("Error. File not found.");
		}
		return root;
	}
	
	static Node readBalanced(Node root, String filename) {
		try {
			Scanner file = new Scanner(new File(filename));
			while (file.hasNextInt()) {
				root = addBalanced(root, file.nextInt());
			}
			file.close();
		}
		catch(FileNotFoundException e){
			System.out.println("Error. File not found.");
		}
		return root;
	}
	
	static void visualize(String format, Node root) {
		if (root!=null) {
			visualize(format + "      ", root.right);
			System.out.println(format + "  /");
			System.out.println(format + root.data);
			System.out.println(format + "  \\");
			visualize(format + "      ", root.left);
		}
	}

	public static void main(String[] args) {
		Node tree = null;
		Scanner sc = new Scanner(System.in);
		int num=-1;
			while (num!=0) {
				System.out.println("\r\nPlease, choose an action:"
				+ "\r\n 1 - Add sorted;"
				+ "\r\n 2 - Add balanced;"
				+ "\r\n 3 - Clear all;"
				+ "\r\n 4 - Information about the tree;"
				+ "\r\n 5 - Add sorted from file;"
				+ "\r\n 6 - Add balanced from file;"
				+ "\r\n 7 - Visualize the tree;"
				+ "\r\n 0 - End the program.");
				if (sc.hasNextInt()) {
				num=sc.nextInt();
				switch (num) {
				case 1:{
					System.out.print("Please, enter a number to add:");
					if (sc.hasNextInt()) {
						int data = sc.nextInt();
						tree = addSorted(tree, data);
						break;
					}
					else {
						System.out.println("Error. Invalid data.\r\nProgram end.");
						return;
					}
				}
				case 2:{
					System.out.print("Please, enter a number to add:");
					if (sc.hasNextInt()) {
						int data = sc.nextInt();
						tree = addBalanced(tree, data);
						break;
					}
					else {
						System.out.println("Error. Invalid data.\r\nProgram end.");
						return;
					}
				}
				case 3:{
					tree = null;
					break;
				}
				case 4:{
					if (tree == null) {
						System.out.println("The tree is empty.");
					}
					else {
						System.out.println("\r\n---------------------------------------------------------------------------------------------");
						System.out.print("\r\nNumbers of the current tree in LNR order: ");
						showLNR(tree);
						System.out.print("\r\nNumbers of the current tree in LRN order: ");
						showLRN(tree);
						System.out.print("\r\nNumbers of the current tree in NLR order: ");
						showNLR(tree);
						System.out.println("\r\n");
						int leafs = leafsCount(tree);
						System.out.println("Current tree has " + leafs + " leafs");
						int height = height(tree);
						System.out.println("Tree's height is: " + height);
						int min = min(tree);
						System.out.println("The minimum value is: " + min);
						min = Integer.MAX_VALUE;
						int max = max(tree);
						System.out.println("The maximum value is: " + max);
						max = Integer.MIN_VALUE;
						boolean sorted = isSorted(tree);
						System.out.println("The tree is sorted: " + sorted);
						boolean balanced = isBalanced(tree);
						System.out.println("The tree is balanced: " + balanced);
						balanced = false;
						System.out.println("\r\n---------------------------------------------------------------------------------------------\r\n");
					}
					break;
				}
				case 5:{
					System.out.print("Please enter file name: ");
					if (sc.hasNext()) {
						String filename = sc.next();
						tree = readSorted(tree, filename);
					}
					break;
				}
				case 6:{
					System.out.print("Please enter file name: ");
					if (sc.hasNext()) {
						String filename = sc.next();
						tree = readBalanced(tree, filename);
					}
					break;
				}
				case 7:{
					if (tree==null) System.out.println("\r\nThe tree is emplty.");
					else {
						System.out.println("\r\nThe tree currently looks like this:\r\n");
						visualize("  ", tree);
					}
					
					break;
				}
				case 0:{
					System.out.println("Program end.");
					return;
				}
				default:{
					System.out.println("Program end.");
					return;
				}
				}
				num = -1;
			}
				else {
					System.out.println("Error. Invalid data.\r\nProgram end.");
					sc.close();
					return;
				}
			
		}
		sc.close();
	}
	
}

