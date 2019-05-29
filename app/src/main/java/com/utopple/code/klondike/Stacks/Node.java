package com.utopple.code.klondike.Stacks;

import com.utopple.code.klondike.CardRegion;

public class Node {
	public Node next;
	public CardRegion data;

	public Node(){
		data = null;
		next = null;
	}

	public Node(CardRegion cardRegion){
		this.data = cardRegion;
		next = null;
	}
}
