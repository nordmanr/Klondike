package com.utopple.code.klondike;

import android.content.Context;

import java.util.Iterator;
import java.util.List;

public class TableauArea extends DrawableArea {
	public TableauStack cardTapLayouts;

	public TableauArea(Context context) {
		super(context);
		cardTapLayouts = new TableauStack();
	}

	public CardTappable pop(){
		CardTappable popped = cardTapLayouts.pop();
		this.removeView(popped);

		return popped;
	}

	public void push(CardTappable pushing){
		if(cardTapLayouts.canPush(pushing)){
			this.addView(pushing);
			alignView(pushing);
			cardTapLayouts.push(pushing);

			pushing.resizeTapRegion();
		}
	}
	public CardTappable forcePush(CardTappable item){
		this.addView(item);
		alignView(item);
		return cardTapLayouts.forcePush(item);
	}

	public List<CardTappable> popTo(CardTappable selected){
		int index = cardTapLayouts.indexOf(selected);
		List<CardTappable> list = cardTapLayouts.subList(0, index);

		while(index < cardTapLayouts.size()){
			this.removeView(cardTapLayouts.pop());
		}

		return list;
	}
	public void pushAll(List<CardTappable> pushList){
		Iterator<CardTappable> iter = pushList.iterator();

		while(iter.hasNext()){
			push(iter.next());
		}
	}

	private void alignView(CardTappable toAlign){
		LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(ALIGN_PARENT_TOP);
		layoutParams.topMargin = (cardTapLayouts.size()*(GLOBAL_VARS.heightOfCard/5));

		toAlign.setLayoutParams(layoutParams);
		toAlign.resizeTapRegion();
	}
}