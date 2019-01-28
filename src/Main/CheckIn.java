package Main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

//办理入住界面类
public class CheckIn {
	
	Font fontBold = Font.font("Times New Roman", FontWeight.BOLD,
            FontPosture.REGULAR, 20);
    Font fontNormal = Font.font("Times New Roman", FontWeight.NORMAL,
            FontPosture.REGULAR,20);
	
	Label label0 = new Label("入 住 办 理 ");
    Label label1 = new Label("学号");
    Label label2 = new Label("姓名");
    Label label3 = new Label("学院");
    Label label4 = new Label("系别");
    Label label5 = new Label("班级");
    Label label6 = new Label("床号");
    Label label7 = new Label("阳台");
    Label label8 = new Label("  1\n 号 \n 床 \n   ");
    Label label9 = new Label("  2\n 号 \n 床 \n   ");
    Label label10 = new Label("  3\n 号 \n 床 \n   ");
    Label label11 = new Label("  4\n 号 \n 床 \n   ");
    Label label12 = new Label("   ");
    Label label13 = new Label("未使用");
    Label label14 = new Label("   ");
    Label label15 = new Label("已使用");
    Label label16 = new Label("房\n间\n布\n局\n示\n意\n图");
    Label label17 = new Label();
    
    TextField textfield1 = new TextField();
    TextField textfield2 = new TextField();
    TextField textfield3 = new TextField();
    TextField textfield4 = new TextField();
    TextField textfield5 = new TextField();
    TextField textfield6 = new TextField();
    
    Button btn1 = new Button("确认入住");
    Button btn2 = new Button("返回主页");
    
    BorderPane bPane = new BorderPane();
    VBox vPane = new VBox(30);
    
    Scene scene;
	
	public CheckIn() {
		//界面初始化
		
        HBox hPane1 = new HBox(15);
        hPane1.getChildren().addAll(label1,textfield1);
        
        HBox hPane2 = new HBox(15);
        hPane2.getChildren().addAll(label2,textfield2);
        
        HBox hPane3 = new HBox(15);
        hPane3.getChildren().addAll(label3,textfield3);
        
        HBox hPane4 = new HBox(15);
        hPane4.getChildren().addAll(label4,textfield4);
        
        HBox hPane5 = new HBox(15);
        hPane5.getChildren().addAll(label5,textfield5);
        
        textfield6.setEditable(false);
        textfield6.setStyle("-fx-background-color:lightgray");
        
        HBox hPane6 = new HBox(15);
        hPane6.getChildren().addAll(label6,textfield6);
        
        HBox hPaneBtn = new HBox(15);
        hPaneBtn.getChildren().addAll(btn1,btn2);
        hPaneBtn.setAlignment(Pos.CENTER);
       
        VBox vPane1 = new VBox(20);
        vPane1.getChildren().addAll(hPane1,hPane3,hPane5);
        
        VBox vPane2 = new VBox(20);
        vPane2.getChildren().addAll(hPane2,hPane4,hPane6,hPaneBtn);
        
        label7.setAlignment(Pos.CENTER);
        
        HBox hPane7 = new HBox(20);
        hPane7.getChildren().addAll(label7);
        hPane7.setStyle("-fx-background-color:blue;-fx-border-color:blue; -fx-border-width:2");
        
        HBox hPane8 = new HBox(20);
        hPane8.getChildren().addAll(label8,label9);
        
        HBox hPane9 = new HBox(20);
        hPane9.getChildren().addAll(label10,label11);
        
        VBox vPane3 = new VBox(20);
        vPane3.getChildren().addAll(hPane7,hPane8,hPane9);
        vPane3.setAlignment(Pos.TOP_CENTER);
        
        label12.setStyle("-fx-background-color:yellow;-fx-border-color:yellow; -fx-border-width:2");
        
        HBox hPane10 = new HBox(5);
        hPane10.getChildren().addAll(label12,label13);
        
        label14.setStyle("-fx-background-color:red;-fx-border-color:red; -fx-border-width:2");
        
        HBox hPane11 = new HBox(5);
        hPane11.getChildren().addAll(label14,label15);
        
        VBox vPane4 = new VBox(20);
        vPane4.getChildren().addAll(hPane10,hPane11);

        HBox hPane12 = new HBox(20);
        hPane12.getChildren().addAll(vPane3,vPane4,label16);
        hPane12.setAlignment(Pos.TOP_CENTER);
        
        HBox hPane0 = new HBox(20);
        hPane0.getChildren().addAll(vPane1,vPane2,hPane12);
        hPane0.setAlignment(Pos.CENTER); 
        
        label0.setFont(fontBold);
        vPane.getChildren().addAll(label0,hPane0,label17);
        vPane.setAlignment(Pos.TOP_CENTER); 
        vPane.setPadding(new Insets(20, 30, 30, 30));
        
        bPane.setCenter(vPane);
        
        scene = new Scene(bPane);
        
	}
	
	//获取当前界面布局的方法
    public BorderPane getPane() {
    	return bPane;
    }
    
    //设置当前入住寝室信息的方法
    public void setRoomMassage(int roomNumber,int restBedNumber) {
    	label17.setText(String.format("您当前选择入住的寝室是：%d  寝室空余床位数：%d", roomNumber,restBedNumber));
    }
    
    //清空文本域
    public void clearTextfield() {
    	textfield1.clear();
    	textfield2.clear();
    	textfield3.clear();
    	textfield4.clear();
    	textfield5.clear();
    	textfield6.clear();
    }

}
