package com.utopple.code.klondike.testing;

import com.utopple.code.klondike.CardRegion;

public class Node {
	public Node next;
	public Integer data;

	public Node(){
		data = null;
		next = null;
	}

	public Node(Integer integer){
		this.data = integer;
		next = null;
	}

	public String toString(){
		return this.data.toString();
	}
}
