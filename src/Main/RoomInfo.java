package Main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

//房间信息界面类
public class RoomInfo {
		
    Font fontBold = Font.font("Times New Roman", FontWeight.BOLD,
            FontPosture.REGULAR,20);
    Font fontNormal = Font.font("Times New Roman", FontWeight.NORMAL,
            FontPosture.REGULAR,20);
    
    Label label1 = new Label("                                                               寝 室 列 表");
    Label label2 = new Label("    ");
    Label label3 = new Label("已满");
    Label label4 = new Label("    ");
    Label label5 = new Label("未满");
    Label label6 = new Label("选择的寝室是：");
    Label label7 = new Label("");

    TextField textfield1 = new TextField();
    
    Button btn1 = new Button("办理入住");
    Button btn2 = new Button("办理退房");
    Button btn3 = new Button("查询信息");
    Button btn4 = new Button("注销");
    Button[][] number = new Button[3][10];
    
    BorderPane bPane = new BorderPane();
    VBox vPane1 = new VBox(10);
    
    Scene scene;

    public RoomInfo() {
    	//界面初始化
    	
    	textfield1.setStyle("-fx-background-color:lightgray");
    	textfield1.setEditable(false);
    	
        for (int i=0;i<3;++i){
        	for (int j=0;j<10;++j){
        		number[i][j] = new Button(String.valueOf((100*(i+1)+j+1)));
        	}
        }
        
        HBox hPane1 = new HBox(10);
        hPane1.setPadding(new Insets(30, 10, 10, 10));
        label1.setFont(fontBold);
        label7.setFont(fontBold);
        hPane1.getChildren().addAll(label1,label7);
        hPane1.setAlignment(Pos.CENTER);        
        
        label2.setStyle("-fx-background-color:red;-fx-border-color:red; -fx-border-width:3");
        label4.setStyle("-fx-background-color:yellow;-fx-border-color:yellow; -fx-border-width:3");
        
        HBox hPane2 = new HBox(10);
        hPane2.setPadding(new Insets(10, 10, 10, 10));
        hPane2.getChildren().addAll(label2,label3,label4,label5);
        hPane2.setAlignment(Pos.CENTER);
        
        HBox hPane3 = new HBox(10);
        hPane3.setPadding(new Insets(10, 10, 10, 10));
        hPane3.getChildren().addAll(label6,textfield1,btn1,btn2,btn3,btn4);
        hPane3.setAlignment(Pos.CENTER);
        
        GridPane gPane = new GridPane();
        gPane.setPadding(new Insets(10, 10, 10, 10));
        gPane.setHgap(10);
        gPane.setVgap(10);
        gPane.setAlignment(Pos.TOP_CENTER);
        
        for (int i=0;i<3;++i){
        	for (int j=0;j<10;++j){
        		gPane.add(number[i][j], j, i);
        	}
        }
        
        vPane1.getChildren().addAll(hPane1,gPane,hPane2,hPane3);
        
      //动态时间显示
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss ");
    	Timer timer = new Timer();
    	timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				Platform.runLater(()->{
					label7.setText("                          "+sdf.format(new Date(System.currentTimeMillis())));
				});
			}
    		
    	}, 0, 1000);
        
        bPane.setCenter(vPane1);
        
    }
    
    //获取当前界面布局的方法
    public BorderPane getPane() {
    	return bPane;
    }
    
    //判断是否选中入住寝室的方法
    public boolean isCheckIn() {
    	return textfield1.getText().equals("") ? false : true;
    }
    
    //判断选中退房寝室的方法
    public boolean isCheckOut() {
    	return textfield1.getText().equals("") ? false : true;
    }
    
    //判断选中寝室是否可以入住的方法
    public boolean canCheckIn() {
		return number[Integer.valueOf(textfield1.getText())/100-1][Integer.valueOf(textfield1.getText())%100-1].getStyle().equals("-fx-background-color:yellow;-fx-border-color:black; -fx-border-width:3");
	}
    
    //获取选中的寝室号
    public int getRoomNumber() {
    	return Integer.valueOf(textfield1.getText());
    }

}

