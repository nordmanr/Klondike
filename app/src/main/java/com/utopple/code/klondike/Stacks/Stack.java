package com.utopple.code.klondike.Stacks;

import com.utopple.code.klondike.CardRegion;

public class Stack {
	Node head;

	public Stack(){
		head = null;
	}

	public void push(CardRegion cardRegion){
		Node node = new Node(cardRegion);

		node.next = head;
		head = node;
	}

	public CardRegion pop(){
		CardRegion cardRegion = head.data;

		head = head.next;

		return cardRegion;
	}

	public CardRegion peek(){
		return head.data;
	}

	public boolean isEmpty(){
		return (head==null);
	}

	public void pushAll(Stack stack){
		for(int i=stack.size()-1; i>=0; i--){
			this.push(stack.get(i));
		}
	}

	public int size(){
		int count = 0;
		Node traverse = head;

		while(traverse!=null){
			count++;
			traverse = traverse.next;
		}

		return count;
	}

	public int indexOf(CardRegion cardRegion){
		Node traverse = head;
		int count = 0;

		while(traverse!=null){
			if(traverse.data.equals(cardRegion)){
				return count;
			}else{
				traverse = traverse.next;
				count++;
			}
		}

		return -1;
	}

	public Stack sublist(int from, int to){
		Stack sub = new Stack();

		for(int i=to-1; i>=from; i--){
			sub.push(get(i));
		}

		return sub;
	}

	public Stack popSublist(int from, int to){
		Stack sub = sublist(from, to);

		if(from==0){
			head = getNode(to);
		}else {
			getNode(from - 1).next = getNode(to);
		}

		return sub;
	}

	public CardRegion get(int index){
		Node traverse = head;
		int count=0;

		while(traverse != null){
			if(count<index){
				traverse=traverse.next;
				count++;
			}else if(count==index){
				return traverse.data;
			}
		}

		throw new IndexOutOfBoundsException();
	}
	private Node getNode(int index){
		Node traverse = head;
		int count=0;

		while(traverse != null){
			if(count<index){
				traverse=traverse.next;
				count++;
			}else if(count==index){
				return traverse;
			}
		}

		if(count==index){
			return traverse;
		}

		throw new IndexOutOfBoundsException();
	}

	public CardRegion[] toArray(){
		CardRegion arr[] = new CardRegion[size()];

		for(int i=0; i<size(); i++){
			arr[i] = get(i);
		}

		return arr;
	}

	@Override
	public String toString() {
		Node traverse = head;
		StringBuilder stringBuilder = new StringBuilder();

		while(traverse!=null){
			stringBuilder.append(indexOf(traverse.data));
			stringBuilder.append("\t");
			stringBuilder.append(traverse.toString());
			stringBuilder.append("\n");

			traverse=traverse.next;
		}

		return stringBuilder.toString();
	}

	public void print(){
		System.out.print(toString());
	}
}