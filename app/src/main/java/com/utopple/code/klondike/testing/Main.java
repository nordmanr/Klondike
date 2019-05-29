package com.utopple.code.klondike.testing;

import java.util.Arrays;

public class Main {
	public static void main(String args[]){
		Stack stack = new Stack();
		Stack stack1 = new Stack();

		stack.push(5);
		stack.push(4);
		stack.push(3);
		stack.push(2);

		stack1.push(15);
		stack1.push(14);

		stack.print();

		System.out.println("get(0)\t"+stack.get(0));
		System.out.println("get(1)\t"+stack.get(1));
		System.out.println("get(2)\t"+stack.get(2));

		stack.print();

		System.out.println("toArray()\t"+Arrays.toString(stack.toArray()));

		System.out.println("pop()\t"+stack.pop());

		stack.print();

		System.out.println("peek()\t"+stack.peek());

		stack.print();

		System.out.println("isEmpty()\t"+stack.isEmpty());

		System.out.println("pushAll(stack1)");
		stack.pushAll(stack1);
		stack.print();

		System.out.println("indexOf(3)\t"+stack.indexOf(3));


		System.out.println("sublist(1,3)");

		stack.sublist(1,3).print();
		System.out.println("stack.print()");
		stack.print();


		System.out.println("popSublist(1,3)");

		stack.popSublist(1,3).print();
		System.out.println("stack.print()");
		stack.print();
	}
}
