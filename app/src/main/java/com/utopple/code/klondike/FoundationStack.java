package com.utopple.code.klondike;

import java.util.Stack;

public class FoundationStack extends Stack<CardRegion> {
	public FoundationStack() {
		super();
	}

	@Override
	public CardRegion push(CardRegion item) {
		if(this.isEmpty()){		// add when empty
			return super.push(item);
		}

		if(this.peek().getCard().getValue()+1 != item.getCard().getValue()){	// invalid: not next card
			return item;
		}

		if(this.peek().getCard().getSuit()!=item.getCard().getSuit()){	// invalid: different suits
			return item;
		}

		return super.push(item);	// valid: add card
	}
}
