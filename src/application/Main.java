package application;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.stage.*;
import javafx.util.Duration;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.*;
import javafx.collections.*;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Desktop;
import java.io.*;
import javafx.event.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.image.*;
import javafx.scene.image.*;
import javafx.embed.swing.*;
import java.awt.geom.*;
import java.text.*;
import javafx.scene.media.*;
import javax.imageio.ImageIO;
import javafx.concurrent.*;
public class Main extends Application {
	String user;
	int gridWidth;
	
	int count;
	boolean timecheck;
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Match the Pokemon!");
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/images/longico-23-512.png")));
			new MediaPlayer(new Media((getClass().getResource("/resources/sounds/Application opens.mp3")).toString())).play();
			init(primaryStage);
		}catch(Exception e) {e.printStackTrace();}
					
					
	}
	
	
	private boolean check(ToggleButton[][] arr) {
		boolean flag=true;
		for(int i=0;i<arr.length;i++) {
			for(int j=0;j<arr[i].length;j++) {
		if(!arr[i][j].isDisabled()) {
			flag=false;
			break;
		}
			}}
			return flag;
		
				}
					
		public void init(Stage primaryStage) {
			
			
			FlowPane root = new FlowPane(10,50);
			Scene scene = new Scene(root,300,200);
			root.setStyle("-fx-background-color: BEIGE");
			root.setAlignment(Pos.CENTER);
			TextField inputuser=new TextField("Enter username");
			Button OK=new Button("OK");
			CheckBox timer=new CheckBox("Countdown mode");
			ComboBox<String> grid=new ComboBox<String>(FXCollections.observableArrayList("4x4","6x6","8x8","10x10","12x12","14x14","16x16"));
			
			inputuser.setPrefWidth(160);
			grid.setPrefWidth(80);
			
			OK.setPrefWidth(50);
			grid.setValue("4x4");
			
			
			OK.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					if(timer.isSelected())
						timecheck=true;
					else
						timecheck=false;
					user=inputuser.getText();
					gridWidth=grid.getValue().charAt(0)-'0';
					puzzle(primaryStage);
				}
			});
root.getChildren().addAll(inputuser,grid,OK,timer);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		public void puzzle(Stage primaryStage) {
			try {
			Media tileslide=new Media(getClass().getResource("/resources/sounds/Tile slides sound.mp3").toString());	
			Media lose=new Media(getClass().getResource("/resources/sounds/Lose Give up.mp3").toString());
			Media hinthaha=new Media(getClass().getResource("/resources/sounds/Hint Haha.mp3").toString());
			Media timelow=new Media(getClass().getResource("/resources/sounds/Time low Ding.mp3").toString());
			Media winner=new Media(getClass().getResource("/resources/sounds/Win Victory.mp3").toString());
			Image unseen = new Image(getClass().getResource("/resources/images/s-l1600.jpg").toExternalForm(),100,100,false,false);
			ImageView loserduck = new ImageView(new Image(getClass().getResource("/resources/images/slowpoke.jpg").toExternalForm(),100,100,false,false));
			Image[] imageList= {new Image(getClass().getResource("/resources/images/altaria-mega.gif").toExternalForm(),100,100,false,false),new Image(getClass().getResource("/resources/images/arbok.gif").toExternalForm(),100,100,false,false),new Image(getClass().getResource("/resources/images/audino-mega.gif").toExternalForm(),100,100,false,false),new Image(getClass().getResource("/resources/images/beedrill-mega.gif").toExternalForm(),100,100,false,false),new Image(getClass().getResource("/resources/images/brionne.gif").toExternalForm(),100,100,false,false),new Image(getClass().getResource("/resources/images/mewtwo.gif").toExternalForm(),100,100,false,false),new Image(getClass().getResource("/resources/images/mr._mime.gif").toExternalForm(),100,100,false,false),new Image(getClass().getResource("/resources/images/pikachu-kantocap.gif").toExternalForm(),100,100,false,false),new Image(getClass().getResource("/resources/images/rayquaza-mega.gif").toExternalForm(),100,100,false,false)};
			Image imgs[]=new Image[gridWidth*gridWidth/2];
			Integer arr[][]=Generator.generate(gridWidth);
			for(int i=0;i<gridWidth*gridWidth/2;i++)
				imgs[i]=imageList[(int)(Math.random()*8)];
			for(int i=0;i<gridWidth;i++) {
				for(int j=0;j<gridWidth;j++)
					System.out.print(arr[i][j]+"\t");
					System.out.println();
			}
			BorderPane root1=new BorderPane();
			root1.setStyle("-fx-background-color :honeydew");
			Label playername=new Label(user);
			playername.setFont(new Font(20));
			playername.setStyle("-fx-text-fill: black");
			root1.setTop(playername);
			BorderPane.setAlignment(playername, Pos.CENTER);
			FlowPane field=new FlowPane();
		
			
			field.setAlignment(Pos.CENTER);
			ToggleButton[][] tile=new ToggleButton[gridWidth][gridWidth];
	        for(int i=0;i<gridWidth;i++) {
	        	for(int j=0;j<gridWidth;j++) {
	        		tile[i][j]=new ToggleButton(arr[i][j].toString(),new ImageView(unseen));
	        		tile[i][j].setMinSize(100, 100);
	        		tile[i][j].setMaxSize(100, 100);
	        		tile[i][j].setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	        	}
	        }
			
			count=0;
			Label counter=new Label("Score: 0");
			long timeNow;
			if(timecheck)
			timeNow=gridWidth*60*1000+System.currentTimeMillis();
			else
				timeNow=System.currentTimeMillis();
			Label timeLabel=new Label();
			DateFormat timeFormat=new SimpleDateFormat("HH:mm:ss");
			timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			
			final Timeline timeline;
			timeline=new Timeline(
					new KeyFrame(
							Duration.millis(500),event->{
								final long time=Math.abs(timeNow-System.currentTimeMillis());
								
								
								
								timeLabel.setText(timeFormat.format(time));
								
								
								try {
								if(timecheck) {
									
									if((timeFormat.parse(timeLabel.getText()).getTime())==(gridWidth*60*1000)/2) {
										new MediaPlayer(timelow).play();
										timeLabel.setFont(new Font(15));
										timeLabel.setStyle("-fx-font-weight:bold;-fx-text-fill: red;");
									}
									if(timeFormat.parse(timeLabel.getText()).getTime()==0) {
										new MediaPlayer(lose).play();
										Alert alert = new Alert(AlertType.INFORMATION);
										alert.setTitle("Information Dialog");
										alert.setHeaderText(null);
										alert.setContentText("You LOSE!");
					                    alert.setGraphic(loserduck);
										alert.show();
										init(primaryStage);
										
									}
								}
								}catch(Exception e1) {System.err.println(e1);}
								
								
							}
							));
			timeline.setCycleCount(Animation.INDEFINITE);
			timeline.play();
		
			
			EventHandler<ActionEvent> MEHandler=new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e1) {
					new MediaPlayer(tileslide).play();
					((ToggleButton)e1.getSource()).setSelected(false);
					Integer num=Integer.valueOf(((ToggleButton)e1.getSource()).getText());
					 Task<Void> sleeper = new Task<Void>() {
				            @Override
				            protected Void call() throws Exception {
				                try {
				                    Thread.sleep(500);
				                } catch (InterruptedException e) {
				                }
				                return null;
				            }
				        };
					int i=0,j=0;
					
					  
					boolean onesel=false;
					int num1=-1;
					outer:for(;i<gridWidth;i++) {
						for(j=0;j<gridWidth;j++) {
							if(tile[i][j].isSelected()) {
								onesel=true;
								num1=arr[i][j];
								break outer;
							}
						}
							
					}
					
					
					
					if(onesel) {
						if(num==num1) {
							((ToggleButton)e1.getSource()).setGraphic(new ImageView(imgs[num]));
							((ToggleButton)e1.getSource()).setDisable(true);
							tile[i][j].setSelected(false);
							tile[i][j].setDisable(true);
							count+=10;
						}
						else {
							((ToggleButton)e1.getSource()).setGraphic(new ImageView(imgs[num]));
							 sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
						            @Override
						            public void handle(WorkerStateEvent event) {
						            	((ToggleButton)e1.getSource()).setGraphic(new ImageView(unseen));
						            	
						            }
						        });
							 tile[i][j].setSelected(false);
								tile[i][j].setGraphic(new ImageView(unseen));
							new Thread(sleeper).start();
						}
							
					}
					else {
						((ToggleButton)e1.getSource()).setGraphic(new ImageView(imgs[num]));
						((ToggleButton)e1.getSource()).setSelected(true);
					}
					
					counter.setText("Score: "+count);
					if(check(tile)) {
						playername.setText(user+" wins!!!");
						new MediaPlayer(winner).play();
						
						
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Information Dialog");
						alert.setHeaderText(null);
						alert.setContentText(user+" WINS!");
	                    
						alert.showAndWait();
						timeline.stop();
						try{init(primaryStage);}catch(Exception e2) {}
						
					}
				}
			};
			for(int i=0;i<gridWidth;i++) {
				for(int j=0;j<gridWidth;j++) {
					tile[i][j].setOnAction(MEHandler);
					field.getChildren().add(tile[i][j]);
				}
			}
			FlowPane footbar=new FlowPane(50,10);
			footbar.getChildren().addAll(counter,timeLabel);
			Button gup=new Button("Give up :(");
			gup.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					new MediaPlayer(lose).play();
					timeline.stop();
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText(null);
					alert.setContentText("You LOSE!");
                    alert.setGraphic(loserduck);
					alert.showAndWait();
					try{init(primaryStage);}catch(Exception e2) {System.err.println(e2);}
					
				}
			});
			
		
		
			root1.setRight(gup);
			BorderPane.setAlignment(gup, Pos.CENTER);
			
			field.setMaxSize(gridWidth*100, gridWidth*100);
			root1.setCenter(field);
			timeLabel.setStyle("-fx-text-fill: black");
			counter .setStyle("-fx-text-fill: magenta");
			root1.setBottom(footbar);
			Scene scene1=new Scene(root1,gridWidth*100+200,gridWidth*100+200);
			primaryStage.setScene(scene1);
			primaryStage.show();
			
		
	
	
	
} catch(Exception e) {
	e.printStackTrace();
}
		}
			
	
	public static void main(String[] args) {
		launch(args);
	}
}
