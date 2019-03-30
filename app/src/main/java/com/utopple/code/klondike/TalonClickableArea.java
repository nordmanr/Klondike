package com.utopple.code.klondike;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

public class TalonClickableArea extends ClickableArea {
	public TalonClickableArea(Context context){
		super(context);
	}
	public TalonClickableArea(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public void initializeClickable(TableDrawer tableDrawer){
		this.setOnClickListener(new TalonHandler(tableDrawer));
	}

	public class TalonHandler implements View.OnClickListener {
	/*
		Handler for the Talon
	 */

		private TableDrawer tableDrawer;

		TalonHandler(TableDrawer tableDrawer) {
			this.tableDrawer = tableDrawer;
		}

		@Override
		public void onClick(View v) {
			if(! tableDrawer.getTable().getTalon().isEmpty()){
				tableDrawer.getTable().moveToWaste(tableDrawer.getTable().getMoveToWasteAmt());
				for(int i=0; i<tableDrawer.getTable().getMoveToWasteAmt(); i++){
					tableDrawer.getWasteArea().addCardLayout(tableDrawer.getTalonArea().pop());
				}
			}else{
				tableDrawer.getTable().refillTalon();

				while(! tableDrawer.getWasteArea().cardLayouts.isEmpty()){
					tableDrawer.getTalonArea().addCardLayout(tableDrawer.getWasteArea().pop());
				}
			}

			tableDrawer.getTalonArea().updateDraw();
			tableDrawer.getWasteArea().updateDraw();

			if(MainActivity.DEBUG_FLAG){
				Toast.makeText(getContext(), "Talon press", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
