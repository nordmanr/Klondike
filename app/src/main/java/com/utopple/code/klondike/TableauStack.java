package com.utopple.code.klondike;

import java.util.Stack;

public class TableauStack extends Stack<CardTappable> {
	public TableauStack() {
		super();
	}

	@Override
	public CardTappable push(CardTappable item) {
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

	public CardTappable forcePush(CardTappable item){
		return super.push(item);
	}

	public boolean canPush(CardTappable item){
		if(push(item) != null){
			pop();
			return true;
		}else{
			return false;
		}
	}
}
