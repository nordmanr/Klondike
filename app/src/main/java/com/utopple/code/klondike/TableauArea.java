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

	public CardRegion pop(){
		return super.pop();
	}

	public void push(CardRegion pushing){
		if(cardTapLayouts.canPush(pushing)){
			this.addView(pushing);
			alignView(pushing);
			cardTapLayouts.push(pushing);

			pushing.resizeTapRegion();
		}
	}
	public CardRegion forcePush(CardRegion item){
		this.addView(item);
		alignView(item);
		return cardTapLayouts.forcePush(item);
	}

	@Override
	public CardRegion peek() {
		return cardTapLayouts.peek();
	}

	public List<CardRegion> popTo(CardRegion selected){
		int index = cardTapLayouts.indexOf(selected);
		List<CardRegion> list = cardTapLayouts.subList(0, index);

		while(index < cardTapLayouts.size()){
			this.removeView(cardTapLayouts.pop());
		}

		return list;
	}
	public void pushAll(List<CardRegion> pushList){
		Iterator<CardRegion> iter = pushList.iterator();

		while(iter.hasNext()){
			push(iter.next());
		}
	}

	private void alignView(CardRegion toAlign){
		LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(ALIGN_PARENT_TOP);
		layoutParams.topMargin = (cardTapLayouts.size()*(GLOBAL_VARS.heightOfCard/5));

		toAlign.setLayoutParams(layoutParams);
		toAlign.resizeTapRegion();
	}
}