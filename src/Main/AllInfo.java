package Main;

import java.sql.Date;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

//信息查询界面类
public class AllInfo {
	
	Label label1 = new Label("按系别查询");
	Label label2 = new Label("按班级查询");
	Label label3 = new Label("    按学号查询");
	Label label4 = new Label("     按姓名查询");
	Label label5 = new Label("    按寝室查询");
	Label label6 = new Label("");
	
	TextField textfield1 = new TextField();
	TextField textfield2 = new TextField();
	TextField textfield3 = new TextField();
	
	ChoiceBox cb1 = new ChoiceBox();
	ChoiceBox cb2 = new ChoiceBox();

	Button btn1 = new Button("确定");
	Button btn2 = new Button("确定");
	Button btn3 = new Button("确定");
	Button btn4 = new Button("清空查询条件");
	Button btn5 = new Button("返回主页");
	
	ListView lv = new ListView();
	
	ArrayList<String> data = new ArrayList<String>();
	
	String sqlStr = "select * from studentinfo";
	
	BorderPane bPane = new BorderPane();
	ScrollPane scPane = new ScrollPane();
	ScrollBar sc = new ScrollBar();
	
	public AllInfo() {
		//界面初始化
		
		cb1.setItems(FXCollections.observableArrayList("","生物化学", "生物医学", "健康管理"));
		
		lv.setMaxHeight(200);
		lv.setMinSize(800, 200);
		
		HBox hPane1 = new HBox(10);
		hPane1.getChildren().addAll(label1,cb1); 
		
		HBox hPane2 = new HBox(10);
		hPane2.getChildren().addAll(label2,cb2); 
		
		HBox hPane3 = new HBox(20);
		hPane3.setPadding(new Insets(10,0,0,0));
		hPane3.getChildren().addAll(hPane1,hPane2);
		hPane3.setAlignment(Pos.CENTER);
		
		HBox hPane4 = new HBox(15);
		hPane4.getChildren().addAll(label3,textfield1,btn1,label4,textfield2,btn2);
		
		btn4.setOnAction((ActionEvent e)->{
			data.clear();
			lv.setItems(FXCollections.observableArrayList());
			clearData(-1);
		});
		
		HBox hPane5 = new HBox(60);
		hPane5.setPadding(new Insets(0,0,0,80));
		hPane5.getChildren().addAll(btn4,btn5);
		hPane5.setAlignment(Pos.CENTER_RIGHT);
		
		HBox hPane6 = new HBox(15);
		hPane6.getChildren().addAll(label5,textfield3,btn3,hPane5);
		
		VBox vPane1 = new VBox(10);
		vPane1.getChildren().addAll(hPane3,hPane4,hPane6);
		
		VBox vPane2 = new VBox(5);
		vPane2.getChildren().addAll(label6,lv);
		vPane2.setAlignment(Pos.TOP_LEFT);
		
		bPane.setTop(vPane1);
		bPane.setCenter(vPane2);
		
	}
	
	//获取当前界面布局的方法
	public BorderPane getPane() {
		return bPane;
	}
	
	//清空查询条件的方法
	public void clearData(int x) {
		if (x!=0) {
    		cb1.getSelectionModel().clearSelection();
        	cb2.getSelectionModel().clearSelection();
    	}
    	if (x!=1) textfield1.clear();
    	if (x!=2) textfield2.clear();
    	if (x!=3) textfield3.clear();
    	label6.setText("");
    	data.clear();
    }

}
