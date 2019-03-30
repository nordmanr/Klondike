package com.utopple.code.klondike;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import java.util.EmptyStackException;

public class WasteClickableArea extends ClickableArea {
	public WasteClickableArea(Context context){
		super(context);
	}
	public WasteClickableArea(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public void initializeClickable(TableDrawer tableDrawer){
		this.setOnClickListener(new WasteClickableArea.WasteHandler(tableDrawer));
	}

	public class WasteHandler implements View.OnClickListener {
	/*
		Handler for the Talon
	 */

		private TableDrawer tableDrawer;

		WasteHandler(TableDrawer tableDrawer) {
			this.tableDrawer = tableDrawer;
		}

		@Override
		public void onClick(View v) {
			try {
				// might be empty
				TableDrawer.moveCard = tableDrawer.getWasteArea().peek();

				if(MainActivity.DEBUG_FLAG){
					Toast.makeText(getContext(), "Waste press: "+tableDrawer.getWasteArea().peek().getCard().toString(), Toast.LENGTH_SHORT).show();
				}
			}catch (EmptyStackException e){
				if(MainActivity.DEBUG_FLAG){
					Toast.makeText(getContext(), "Waste press: "+e.toString(), Toast.LENGTH_SHORT).show();
				}
			}

		}
	}
}
