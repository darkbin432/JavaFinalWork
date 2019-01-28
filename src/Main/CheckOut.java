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

//办理退房界面类
public class CheckOut {
	
	Font fontBold = Font.font("Times New Roman", FontWeight.BOLD,
            FontPosture.REGULAR, 25);
    Font fontNormal = Font.font("Times New Roman", FontWeight.NORMAL,
            FontPosture.REGULAR,20);
	
	Label label1 = new Label(" 退 房 办 理 ");
	Label label2 = new Label("学号");
	Label label3 = new Label("姓名");
	Label label4 = new Label("学院");
	Label label5 = new Label("系别");
	Label label6 = new Label("班级");
	Label label7 = new Label();
	
	TextField textfield1 = new TextField();
    TextField textfield2 = new TextField();
    TextField textfield3 = new TextField();
    TextField textfield4 = new TextField();
    TextField textfield5 = new TextField();
	
	Button btn1 = new Button("确认退房");
	Button btn2 = new Button("返回主页");
	
	BorderPane bPane = new BorderPane();
	
	public CheckOut() {
		//界面初始化
		
		HBox hPane1 = new HBox(15);
		hPane1.getChildren().addAll(label2,textfield1,label3,textfield2);
		hPane1.setAlignment(Pos.CENTER);
		
		HBox hPane2 = new HBox(15);
		hPane2.getChildren().addAll(label4,textfield3,label5,textfield4);
		hPane2.setAlignment(Pos.CENTER);
		
		HBox hPane3 = new HBox(15);
		hPane3.setPadding(new Insets(0,0,0,72));
		hPane3.getChildren().addAll(btn1,btn2);
		hPane3.setAlignment(Pos.CENTER);
		
		HBox hPane4 = new HBox(15);
		hPane4.getChildren().addAll(label6,textfield5,hPane3);
		hPane4.setAlignment(Pos.CENTER);
		
		label1.setFont(fontBold);
		label1.setPadding(new Insets(40,10,20,10));
		
		VBox vPane = new VBox(25);
		vPane.getChildren().addAll(label1,hPane1,hPane2,hPane4,label7);
		vPane.setAlignment(Pos.TOP_CENTER);
		
		bPane.setCenter(vPane);
		
	}
	
	//获取当前界面布局的方法
	public BorderPane getPane() {
    	return bPane;
    }
	
	//设置当前退房寝室信息的方法
	public void setRoomMassage(int roomNumber) {
    	label7.setText(String.format("您当前选择退房的寝室是：  %d  ", roomNumber));
    }
	
	//清空文本域
	public void clearTextfield() {
    	textfield1.clear();
    	textfield2.clear();
    	textfield3.clear();
    	textfield4.clear();
    	textfield5.clear();
    }

}
