package Main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

//登录界面类
public class LogIn {
	
	public BackgroundImage myBI= new BackgroundImage(new Image("image/bg.jpg",800,360,false,true),
	        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
	          BackgroundSize.DEFAULT);
	
	Font fontBold = Font.font("Times New Roman", FontWeight.BOLD,
            FontPosture.REGULAR,30);
    Font fontNormal = Font.font("Times New Roman", FontWeight.NORMAL,
            FontPosture.REGULAR,20);
	
	Label label1 = new Label("寝 室 入 住 管 理 系 统");
	Label label2 = new Label("用户登录");
	Label label3 = new Label("用户名: ");
	Label label4 = new Label("密码:    ");
	Label label5 = new Label("验证码:");
	Label label6 = new Label("");
	
	TextField textfield1 = new TextField();
	TextField textfield2 = new TextField();
	
	PasswordField passwordfield = new PasswordField();
	
	Button btn1 = new Button("登录");	
	Button btn2 = new Button("修改密码");
	
	BorderPane bPane = new BorderPane();
	
	public LogIn() {
		//界面初始化
		
		HBox hPane1 = new HBox(10);
        hPane1.getChildren().addAll(label3,textfield1);
        hPane1.setAlignment(Pos.CENTER);
        
        HBox hPane2 = new HBox(10);
        hPane2.getChildren().addAll(label4,passwordfield);
        hPane2.setAlignment(Pos.CENTER);
        
        textfield2.setMaxWidth(150);
        label6.setFont(fontNormal);
		label6.setText(String.format("%d%d%d%d",(int)(Math.random()*10),(int)(Math.random()*10),(int)(Math.random()*10),(int)(Math.random()*10)));
        
        HBox hPane3 = new HBox(10);
        hPane3.getChildren().addAll(label5,textfield2,label6);
        hPane3.setAlignment(Pos.CENTER);
        
        HBox hPane4 = new HBox(20);
        hPane4.getChildren().addAll(btn1,btn2);
        hPane4.setAlignment(Pos.CENTER);
        
        label1.setFont(fontBold);
        label1.setPadding(new Insets(20,10,20,10));
        label1.setAlignment(Pos.CENTER);
        
        label2.setFont(fontNormal);
        label2.setPadding(new Insets(10,10,10,10));
        label2.setAlignment(Pos.CENTER);
        
        VBox vPane = new VBox(10);
        vPane.setPadding(new Insets(10,10,10,10));
        vPane.getChildren().addAll(label1,label2,hPane1,hPane2,hPane3,hPane4);
        vPane.setAlignment(Pos.TOP_CENTER);
        
        bPane.setCenter(vPane);
        bPane.setBackground(new Background(myBI));
	}
	
	//获取当前界面布局的方法
	public BorderPane getPane() {
		return bPane;
	}
	
}
