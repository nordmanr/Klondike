package com.utopple.code.klondike.Stacks;

import com.utopple.code.klondike.CardRegion;

import java.util.ConcurrentModificationException;
import java.util.Stack;

public class TableauStack extends Stack<CardRegion> {
	public TableauStack() {
		super();
	}

	@Override
	public CardRegion push(CardRegion item) {
		if(this.isEmpty()){		// add when empty
			return super.push(item);
		}

		if(this.peek().getCard().getValue()-1 != item.getCard().getValue()){	// invalid: not next card
			return null;
		}

		if(this.peek().getCard().isRed()==item.getCard().isRed()){	// invalid: same color
			return null;
		}

		return super.push(item);	// valid: add card
	}

	public CardRegion forcePush(CardRegion item){
		return super.push(item);
	}

	public boolean canPush(CardRegion item){
		if(push(item) != null){
			pop();
			return true;
		}else{
			return false;
		}
	}
}
