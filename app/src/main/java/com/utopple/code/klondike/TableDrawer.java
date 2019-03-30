package com.utopple.code.klondike;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Iterator;


public class TableDrawer {
	private static int viewWidth;		// Width of screen (px)
	private static int viewHeight;		// Height of screen (px)
	public static int widthOfCard = 50;		// Width of each card when drawn (in px)
	public static int heightOfCard = 75;	// Height of each card when drawn (in px)
	private static int margin;				// Space between card columns (in px)

	public static CardLayout moveCard;	// which card we are trying to move (null when none is selected)

	private Table table;
	private MainActivity context;
	private TalonArea talonArea;
	private WasteArea wasteArea;
	private TableauArea[] tableauAreas;
	private FoundationArea[] foundationAreas;

	TableDrawer(Table table, MainActivity context){
		// Sizing	--------------------------------------------------------------------------------
		viewWidth = context.getResources().getDisplayMetrics().widthPixels;
		viewHeight = context.getResources().getDisplayMetrics().heightPixels;

		widthOfCard = viewWidth/8;
		heightOfCard = ((int) (widthOfCard * 1.5));

		margin = ((viewWidth-7*widthOfCard)-dpToPx(context,20))/(7);	// Space between cards

		// Initializing	----------------------------------------------------------------------------
		this.table = table;
		this.context = context;
		talonArea = new TalonArea(context);
		wasteArea = new WasteArea(context);
		tableauAreas = new TableauArea[7];
		for(int i=0; i<tableauAreas.length; i++){
			tableauAreas[i] = new TableauArea(context);
		}
		foundationAreas = new FoundationArea[7];
		for(int i=0; i<foundationAreas.length; i++){
			foundationAreas[i] = new FoundationArea(context);
		}


		// Create CardLayouts from table	--------------------------------------------------------
		Iterator<Card> iter;
		Card currentCard;
		CardLayout currentCardLayout;

		iter = table.getTalon().iterator();
		while(iter.hasNext()){
			currentCard = iter.next();
			currentCardLayout = new CardLayout(context);
			currentCardLayout.setCard(currentCard);
			talonArea.addCardLayout(currentCardLayout);
		}

		for(int i=0; i<7; i++){
			iter = table.getTableaus()[i].iterator();
			while(iter.hasNext()){
				currentCard = iter.next();
				currentCardLayout = new CardLayout(context);
				currentCardLayout.setCard(currentCard);
				tableauAreas[i].addCardLayout(currentCardLayout);
			}
		}

		// Positioning on screen	----------------------------------------------------------------

		RelativeLayout.LayoutParams layoutParams;

		((RelativeLayout) context.findViewById(R.id.loc_talon)).addView(talonArea);
		((RelativeLayout) context.findViewById(R.id.loc_waste)).addView(wasteArea);

		((RelativeLayout) context.findViewById(R.id.loc_tableaus)).addView(tableauAreas[0]);

		for(int i=1; i<7; i++){
			((RelativeLayout) context.findViewById(R.id.loc_tableaus)).addView(tableauAreas[i]);


			layoutParams = (RelativeLayout.LayoutParams) tableauAreas[i].getLayoutParams();
			layoutParams.addRule(RelativeLayout.RIGHT_OF, tableauAreas[i-1].getId());
			layoutParams.leftMargin = margin;
			tableauAreas[i].setLayoutParams(layoutParams);
		}

		((RelativeLayout) context.findViewById(R.id.loc_foundations)).addView(foundationAreas[0]);

		for(int i=1; i<4; i++){
			((RelativeLayout) context.findViewById(R.id.loc_foundations)).addView(foundationAreas[i]);


			layoutParams = (RelativeLayout.LayoutParams) foundationAreas[i].getLayoutParams();
			layoutParams.addRule(RelativeLayout.RIGHT_OF, foundationAreas[i-1].getId());
			layoutParams.leftMargin = margin;
			foundationAreas[i].setLayoutParams(layoutParams);
		}


		// Draw cards	----------------------------------------------------------------------------
		talonArea.updateDraw();
		wasteArea.updateDraw();
		for(int i=0; i<7; i++){
			tableauAreas[i].updateDraw();
		}
		for(int i=0; i<4; i++){
			foundationAreas[i].updateDraw();
		}


		// Clickable Areas added onto screen	----------------------------------------------------
		TalonClickableArea talonClickableArea;
		talonClickableArea = new TalonClickableArea(context);

		talonClickableArea.initializeClickable(this);

		layoutParams = new RelativeLayout.LayoutParams(widthOfCard, heightOfCard);
		layoutParams.addRule(RelativeLayout.ALIGN_START, talonArea.getId());
		layoutParams.addRule(RelativeLayout.ALIGN_TOP, talonArea.getId());

		talonClickableArea.setLayoutParams(layoutParams);

		((RelativeLayout)context.findViewById(R.id.loc_talon)).addView(talonClickableArea);


	}


	public void draw(){




		if(table.checkWin()){
			Toast.makeText(context, "You win!", Toast.LENGTH_SHORT).show();
		}
	}

	public Table getTable(){
		return table;
	}
	public TalonArea getTalonArea() {
		return talonArea;
	}
	public WasteArea getWasteArea() {
		return wasteArea;
	}
	public TableauArea[] getTableauAreas() {
		return tableauAreas;
	}
	public FoundationArea[] getFoundationAreas() {
		return foundationAreas;
	}

	private int dpToPx(Context context, int dp) {
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
	}
}
