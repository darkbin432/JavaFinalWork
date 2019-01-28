package Main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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

//修改密码界面类
public class ChangePassword {
	
	public BackgroundImage myBI= new BackgroundImage(new Image("image/bg.jpg",800,360,false,true),
	        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
	          BackgroundSize.DEFAULT);
	
	Label label1 = new Label(" 修 改 密 码 ");
	Label label2 = new Label("   原密码:");
	Label label3 = new Label("   新密码:");
	Label label4 = new Label("确认密码:");
	
	PasswordField passwordfield1 = new PasswordField();
	PasswordField passwordfield2 = new PasswordField();
	PasswordField passwordfield3 = new PasswordField();
	
	Button btn1 = new Button("确认修改");
	Button btn2 = new Button("返回登陆");
	
	BorderPane bPane = new BorderPane();

	public ChangePassword() {
		//界面初始化
		
		HBox hPane1 = new HBox(15);
		hPane1.getChildren().addAll(label2,passwordfield1);
		hPane1.setAlignment(Pos.CENTER);
		
		HBox hPane2 = new HBox(15);
		hPane2.getChildren().addAll(label3,passwordfield2);
		hPane2.setAlignment(Pos.CENTER);
		
		HBox hPane3 = new HBox(15);
		hPane3.getChildren().addAll(label4,passwordfield3);
		hPane3.setAlignment(Pos.CENTER);
		
		HBox hPane4 = new HBox(30);
		hPane4.getChildren().addAll(btn1,btn2);
		hPane4.setAlignment(Pos.CENTER);
		
		label1.setFont(Font.font("Times New Roman", FontWeight.NORMAL,
            FontPosture.REGULAR,25));
		
		VBox vPane = new VBox(30);
		vPane.setPadding(new Insets(30,10,20,10));
		vPane.getChildren().addAll(label1,hPane1,hPane2,hPane3,hPane4);
		vPane.setAlignment(Pos.TOP_CENTER);
		
		bPane.setCenter(vPane);
		bPane.setBackground(new Background(myBI));
		
	}
	
	//获取当前界面布局的方法
	public BorderPane getPane() {
		return bPane;
	}

	//清空文本域
	public void clearChangePasswordPane() {
		passwordfield1.clear();
		passwordfield2.clear();
		passwordfield3.clear();
	}
	
}
