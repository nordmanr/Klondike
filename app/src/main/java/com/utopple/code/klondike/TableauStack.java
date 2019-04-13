package com.utopple.code.klondike;

import java.util.Stack;

public class TableauStack extends Stack<CardTapLayout> {
	public TableauStack() {
		super();
	}

	@Override
	public CardTapLayout push(CardTapLayout item) {
		if(this.isEmpty()){		// add when empty
			return super.push(item);
		}

		if(this.peek().getCard().getValue()-1 != item.getCard().getValue()){	// invalid: not next card
			return item;
		}

		if(this.peek().getCard().isRed()==item.getCard().isRed()){	// invalid: same color
			return item;
		}

		return super.push(item);	// valid: add card
	}

	public CardTapLayout forcePush(CardTapLayout item){
		return super.push(item);
	}


}
