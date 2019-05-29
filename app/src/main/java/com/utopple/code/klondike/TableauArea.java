package com.utopple.code.klondike;

import android.content.Context;
import android.widget.Toast;

import com.utopple.code.klondike.Stacks.TableauStack;

import java.util.Iterator;
import java.util.List;

public class TableauArea extends DrawableArea {
	public TableauStack cardRegions;

	public TableauArea(Context context) {
		super(context);
		cardRegions = new TableauStack();
	}

	public CardRegion pop(){
		CardRegion popped = cardRegions.pop();
		this.removeView(popped);

		return popped;
	}

	public void push(CardRegion pushing){
		if(cardRegions.canPush(pushing)){
			this.addView(pushing);
			alignView(pushing);
			cardRegions.push(pushing);

			pushing.resizeTapRegionMax();
		}
	}
	public CardRegion forcePush(CardRegion item){
		this.addView(item);
		alignView(item);
		return cardRegions.forcePush(item);
	}

	@Override
	public CardRegion peek() {
		return cardRegions.peek();
	}

	public List<CardRegion> popTo(CardRegion selected){
		int index = cardRegions.indexOf(selected);
		List<CardRegion> list = cardRegions.subList(index, cardRegions.size());

		while(index < cardRegions.size()){
			this.pop();
		}

		return list;
	}
	public void pushAll(List<CardRegion> pushList){
		Iterator<CardRegion> iterator = pushList.iterator();
		Toast.makeText(getContext(), "pushlist size: "+pushList.size(), Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean contains(CardRegion cardRegion) {
		return cardRegions.contains(cardRegion);
	}

	private void alignView(CardRegion toAlign){
		LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(ALIGN_PARENT_TOP);
		layoutParams.topMargin = (cardRegions.size()*(GLOBAL_VARS.heightOfCard/5));

		toAlign.setLayoutParams(layoutParams);
		toAlign.resizeTapRegionMax();
	}
}