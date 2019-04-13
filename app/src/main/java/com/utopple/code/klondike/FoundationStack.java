package com.utopple.code.klondike;

import java.util.Stack;

public class FoundationStack extends Stack<CardTappable> {
	public FoundationStack() {
		super();
	}

	@Override
	public CardTappable push(CardTappable item) {
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
