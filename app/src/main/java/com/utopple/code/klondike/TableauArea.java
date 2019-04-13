package com.utopple.code.klondike;

import android.content.Context;

import java.util.Iterator;
import java.util.List;

public class TableauArea extends DrawableArea {
	private TableauStack cardTapLayouts;

	public TableauArea(Context context) {
		super(context);
		cardTapLayouts = new TableauStack();
	}

	public CardTapLayout forcePush(CardTapLayout item){
		this.addView(item);
		alignView(item);
		return cardTapLayouts.forcePush(item);
	}

	public List<CardTapLayout> popTo(CardTapLayout selected){
		int index = cardTapLayouts.indexOf(selected);
		List<CardTapLayout> list = cardTapLayouts.subList(0, index);

		while(index < cardTapLayouts.size()){
			this.removeView(cardTapLayouts.pop());
		}

		return list;
	}

	public void pushAll(List<CardTapLayout> pushList){
		Iterator<CardTapLayout> iter = pushList.iterator();

		while(iter.hasNext()){
			push(iter.next());
		}
	}

	private void alignView(CardTapLayout toAlign){
		LayoutParams layoutParams = new LayoutParams(GLOBAL_VARS.widthOfCard, GLOBAL_VARS.heightOfCard);
		layoutParams.addRule(ALIGN_PARENT_TOP);
		layoutParams.topMargin = (cardTapLayouts.size()*(GLOBAL_VARS.heightOfCard/5));

		toAlign.setLayoutParams(layoutParams);
	}
}