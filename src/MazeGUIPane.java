package test;

import java.awt.Color;
import java.beans.EventHandler;
import java.util.List;


import solver.Zombie;
import game.Coordinate;
import game.Maze;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class MazeGUIPane extends VBox{




	private GridPane grid;
	private Button btn;
	private HBox hbox;
	private BorderPane pane;
	private Label result;
	private Zombie solver;
	private Maze maze;
	private Label[][]labels;
	
	public MazeGUIPane(Zombie solvein){
		solver = solvein;
		this.pane = new BorderPane();
		this.labels = new Label[18][18];
		this.grid = new GridPane();
		this.btn = new Button("Solve");
		this.hbox = new HBox();
		this.result = new Label();








		this.getChildren().add(main());
	}


	private BorderPane main(){
		pane.setTop(text());
		pane.setCenter(createTable());
		pane.setBottom(getHbox());

		pane.getStylesheets().add("application/application.css");

		return pane;
	}




	private HBox text() {
		HBox hb = new HBox();
		Label txt = new Label("Zombie Attack");
		hb.getChildren().add(txt);
		txt.getStyleClass().add("TXT");
		hb.getStyleClass().add("HBOX");
		return hb;
		
		
	}


	private GridPane createFrame(){
		for(int i = 0;i<17;i++){
			Label lb = new Label();
			lb.getStyleClass().add("OUT");
			grid.addRow(i, lb);
		}

		for(int i = 17;i>=0;i--){
			Label lb = new Label();
			lb.getStyleClass().add("OUT");
			grid.add(lb,17,i);
		}

		for(int k = 1;k<17;k++){
			Label lb = new Label();
			lb.getStyleClass().add("OUT");
			grid.addColumn(k, lb);
		}
		for(int i = 17;i>=0;i--){
			Label lb = new Label();
			lb.getStyleClass().add("OUT");
			grid.add(lb,i,17);
		}
		return grid;
	}


	private GridPane createTable() {

		createFrame();

		for(int i = 1; i<17; i++){
			for (int k = 1; k<17;k++){
				Label lb = new Label ();
				grid.add(lb, i, k);
				labels[i][k] = lb;
				lb.getStyleClass().add("GRID");
			}
		}
		Label lb1 = new Label("S");
		Label lb2 = new Label ("F");
		lb1.getStyleClass().add("LB");
		lb2.getStyleClass().add("LB");
		grid.add(lb1, 2, 1);
		grid.add(lb2, 16, 16);

		
		
		btn.addEventFilter(MouseEvent.MOUSE_CLICKED, new javafx.event.EventHandler<Event>() {
			
			@Override
			public void handle(Event event) {
			Label cur = new Label();
			List<Coordinate>way = solver.solve(maze);
				for(int i = 1; i<17; i++){
					for (int k = 1; k<17;k++){
						
						for(Coordinate c: way){
							if(c.getCol()==16 && c.getRow()==16){
								grid.add(cur, i, k);
								cur.getStyleClass().add("SOLVE");
							}
						}
					}
					}
			}
		});
		btn.getStyleClass().add("btn");
		
		return grid;

	}
	private HBox getHbox(){

		hbox.getChildren().add(btn);
		hbox.getChildren().add(result);


		/*btn.addEventFilter(MouseEvent.MOUSE_CLICKED, new javafx.event.EventHandler<Event>() {

			@Override
			public void handle(Event event) {
			//	clear();
				List<Coordinate> way = solver.solve(maze);
				boolean solved;
				if(way.size() <2){
					solved = false;
				}
				else{
					solved = true;
				}
				Label cur;
				for(int i =0;i<17;i++){
					for(int k=0;k<17;k++){
						cur = labels[i][k];
						
						for(Coordinate c: way){
							cur = labels[c.getRow()][c.getCol()];
							if(c.getRow() == 16 && c.getCol() ==16 && solved){
								cur.getStyleClass().add("WRONG");
							}
							else{
								cur.getStyleClass().add("SOLVE");
							}
								
						}
					}
				}
			}
		});*/



		hbox.getStyleClass().add("HBOX");
		return hbox;


	}


	public void clear(){
		result.setText("");

		char currVal;
		for (int i = 0; i < 17; i++){
			for (int j = 0; j < 17; j++) {
				currVal = maze.getCoordinateValue(i, j);
				if (currVal == ' ')
					labels[i][j].getStyleClass().add("NOR");
		}
		}

	}

	private void setZombie(){
		maze = new Maze(18,18);
		maze.setCoordinateValue(1, 2, 'S');
		maze.setCoordinateValue(16, 16, 'F');

		for(int i = 0; i<17; i++){
			for (int k = 0; k<17;k++){
				if(i==0|| k == 16)
					maze.setCoordinateValue(i, k, 'W');
				else if(k ==0|| i ==16)
					maze.setCoordinateValue(i, k, 'W');
				else 
					maze.setCoordinateValue(i, k, ' ');
			}
		}
		


	}
	
	
	
	private void click(){
		
		
		grid.addEventFilter(MouseEvent.MOUSE_CLICKED, new javafx.event.EventHandler<Event>() {
			int row,col;
			@Override
			public void handle(Event event) {
				maze.getCoordinate(row, col);
				Label lb = new Label();
				grid.add(lb, col, row);
				lb.getStyleClass().add("BLK");
				
			}
			
		});
	}
	private boolean wallOnOff(Label lb, boolean wall, int row, int col) {
		lb.setText("");
		if (wall) {
			wall = false;
			maze.setCoordinateValue(row, col, ' ');
			lb.getStyleClass().add("BLK");
		} else {
			wall = true;
			maze.setCoordinateValue(row, col, 'W');
			lb.getStyleClass().add("BLK");
		}
		return wall;
	}
	
	
	
}
