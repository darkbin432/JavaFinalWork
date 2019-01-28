package Main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class FirstLogin {
	
	Font fontBold = Font.font("Times New Roman", FontWeight.BOLD,
            FontPosture.REGULAR,30);
    Font fontNormal = Font.font("Times New Roman", FontWeight.NORMAL,
            FontPosture.REGULAR,20);
	
	Label label1 = new Label("您是第一次登陆，请进行邮箱绑定！");
	Label label2 = new Label("邮箱：    ");
	Label label3 = new Label("验证码：");
	
	TextField textfield1 = new TextField();
	TextField textfield2 = new TextField();
	
	Button btn1 = new Button("发送");
	Button btn2 = new Button("验证");
	
	BorderPane bPane = new BorderPane();
	
	public FirstLogin() {
		
		HBox hPane1 = new HBox(10);
        hPane1.getChildren().addAll(label2,textfield1,btn1);
        hPane1.setAlignment(Pos.CENTER);
        
        HBox hPane2 = new HBox(10);
        hPane2.getChildren().addAll(label3,textfield2,btn2);
        hPane2.setAlignment(Pos.CENTER);
        
        label1.setFont(fontNormal);
        label1.setPadding(new Insets(10,10,10,10));
        label1.setAlignment(Pos.CENTER);
        
        VBox vPane = new VBox(15);
        vPane.setPadding(new Insets(10,10,10,10));
        vPane.getChildren().addAll(label1,hPane1,hPane2);
        vPane.setAlignment(Pos.CENTER);
        
        bPane.setCenter(vPane);
	}
	
	public BorderPane getPane() {
		return bPane;
	}
	
	public void clearFirstLoginPane() {
		textfield1.clear();
		textfield2.clear();
	}

}