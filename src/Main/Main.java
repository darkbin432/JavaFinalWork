package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

//主控制类Main
public class Main extends Application{
	
	private static Connection con;
	private static Statement sta;
	private static ResultSet rs;
	private static Statement sta1;
	private static ResultSet rs1;
	private static PreparedStatement pSta;
	private static String driverName = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://127.0.0.1:3306/dormitory?useUnicode=true&characterEncoding=UTF-8";
	private static String user = "root";
	private static String password = "";
	private static String firstLoginYZM;
	
	public LogIn logIn = new LogIn();
	public ChangePassword changePassword = new ChangePassword();
	public RoomInfo roomInfo = new RoomInfo();
	public CheckIn checkIn = new CheckIn();
	public CheckOut checkOut = new CheckOut();
	public AllInfo allInfo = new AllInfo();
	public FirstLogin firstLogin = new FirstLogin();
	public Scene mainScene;
	public BorderPane mainPane = new BorderPane();
	public BorderPane firstLoginPane = new BorderPane();
	public Stage firstLoginStage = new Stage();
	public Scene firstLoginScene = new Scene(firstLoginPane,600,250);
	
	Button login = logIn.btn1;
	Button changePass = logIn.btn2;
	Button confirmChangePasswordBtn = changePassword.btn1;
	Button backToLogin = changePassword.btn2;
	Button toCheckInBtn = roomInfo.btn1;
	Button toCheckOutBtn = roomInfo.btn2;
	Button toSearchInfoBtn = roomInfo.btn3;
	Button logOutBtn = roomInfo.btn4;
	Button confirmCheckInBtn = checkIn.btn1;
	Button backToHomepage1 = checkIn.btn2;
	Button backToHomepage2 = checkOut.btn2;
	Button backToHomepage3 = allInfo.btn5;
	Button confirmCheckOutBtn = checkOut.btn1;
	Button searchById = allInfo.btn1;
	Button searchByName = allInfo.btn2;
	Button searchByRoom = allInfo.btn3;
	Button firstLoginSendMailBtn = firstLogin.btn1;
	Button firstLoginCheckBtn = firstLogin.btn2;
	Button[][] number = roomInfo.number;
	
	Label yzm = logIn.label6;
	Label[] bed = {checkIn.label8,checkIn.label9,checkIn.label10,checkIn.label11};
	
	ChoiceBox cb1 = allInfo.cb1;
	ChoiceBox cb2 = allInfo.cb2;
	
	
	public static void connect() {
		try {
			Class.forName(driverName);

			con = DriverManager.getConnection(url, user, password);

			System.out.println("数据库连接成功");

		} catch (ClassNotFoundException e) {
			System.out.println("加载驱动出错：" + e.getMessage());
		} catch (SQLException e) {
			System.out.println("连接数据库出错：" + e.getMessage());
		}
	}

	public static void closeConnection() {
		if (con != null) {
			try {
				con.close();
				System.out.println("数据库连接关闭");
			} catch (SQLException e) {
				System.out.println("关闭数据库出错：" + e.getMessage());
			}
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//初始化和事件注册
		primaryStage.setTitle("寝室入住管理系统");
		
		mainPane.setCenter(logIn.getPane());
		mainScene = new Scene(mainPane,800,350);

		updateBtn();
		createChoiceBoxEvent();
		yzm.setOnMouseClicked(new getYZM());
		login.setOnAction(new Login());
		changePass.setOnAction(new toChangePassword());
		confirmChangePasswordBtn.setOnAction(new confirmChangePassword());
		firstLoginSendMailBtn.setOnAction(new toSendMail());
		firstLoginCheckBtn.setOnAction(new CheckYZM());
		backToLogin.setOnAction(new BackToLogin());
		toCheckInBtn.setOnAction(new toCheckIn());
		toCheckOutBtn.setOnAction(new toCheckOut());
		toSearchInfoBtn.setOnAction(new toSearchInfo());
		logOutBtn.setOnAction(new BackToLogin());
		confirmCheckInBtn.setOnAction(new confirmCheckIn());
		backToHomepage1.setOnAction(new BackToHomepage());
		backToHomepage2.setOnAction(new BackToHomepage());
		backToHomepage3.setOnAction(new BackToHomepage());
		confirmCheckOutBtn.setOnAction(new confirmCheckOut());
		searchById.setOnAction(new SearchById());
		searchByName.setOnAction(new SearchByName());
		searchByRoom.setOnAction(new SearchByRoom()); 
		for (int i=0;i<3;++i) {
			for (int j=0;j<10;++j) {
				number[i][j].setOnAction(new ChooseRoom());
			}
		}
		for (int i=0;i<4;++i) {
			bed[i].setOnMouseClicked(new ChooseBed());
		}
		
		primaryStage.getIcons().add(new Image("image/img.jpg"));
        primaryStage.setScene(mainScene);
        
        primaryStage.show();
        
        //窗口关闭监听
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
            	//关闭数据库连接
            	closeConnection();
            	//结束进程
            	System.exit(0);
            }
          });
        
	}
	
	public static void main(String[] args) throws SQLException {
		connect();	
		launch(args);
	}
	
	//界面更新函数，通过是更换布局实现
	public void updateMainScene(BorderPane pane) {
		mainPane.setCenter(pane);
	}
	
	//更新房间信息按钮的状态
	public void updateBtn() {
		
		String sqlStr = "select * from roominfo";
		int cnt = 0;
		
		try {
    		sta = con.createStatement();

    		rs = sta.executeQuery(sqlStr);

    		while(rs.next()){
    			cnt = 0;
    			for (int i=2;i<=5;++i) {
    				if (!rs.getString(i).equals("无")) {
    					cnt++;
    				}
    			}
    			if (cnt == 4) {
        			number[rs.getInt(1)/100-1][rs.getInt(1)%100-1].setStyle("-fx-background-color:red;-fx-border-color:red; -fx-border-width:3");
        		}else{
        			number[rs.getInt(1)/100-1][rs.getInt(1)%100-1].setStyle("-fx-background-color:yellow;-fx-border-color:yellow; -fx-border-width:3");
        		}
    		}
    		
    	} catch (SQLException e) {
    		System.out.println("查询出错：" + e.getMessage());
    	}
	}
	
	//通过两个ChoiceBox的事件注册实现系别和班级的查询
	public void createChoiceBoxEvent() {
		cb1.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> ov1,
				Number old_val1,Number new_val1)->{
					if (new_val1.intValue() == 0) {
						cb2.setItems(FXCollections.observableArrayList(""));
						cb2.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> ov2,
								Number old_val2,Number new_val2)->{
									if (new_val2.intValue() == 0) {
										//全部学生信息
										updateData(0,"ALL");
									}
								});	
					}else if (new_val1.intValue() == 1) {
						cb2.setItems(FXCollections.observableArrayList("","生化181", "生化182"));
						cb2.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> ov2,
								Number old_val2,Number new_val2)->{
									if (new_val2.intValue() == 0) {
										//生物化学系全部学生信息
										updateData(4,"生物化学");
									}else if (new_val2.intValue() == 1) {
										//生化181班学生信息
										updateData(5,"生化181");
									}else if (new_val2.intValue() == 2) {
										//生化182班学生信息
										updateData(5,"生化182");
									}
								});	
					}else if (new_val1.intValue() == 2){
						cb2.setItems(FXCollections.observableArrayList("","生医181", "生医182"));
						cb2.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> ov2,
								Number old_val2,Number new_val2)->{
									if (new_val2.intValue() == 0) {
										//生物医学系全部学生信息
										updateData(4,"生物医学");
									}else if (new_val2.intValue() == 1) {
										//生医181班学生信息
										updateData(5,"生医181");
									}else if (new_val2.intValue() == 2) {
										//生医182班学生信息
										updateData(5,"生医182");
									}
								});	
					}else if (new_val1.intValue() == 3) {
						cb2.setItems(FXCollections.observableArrayList("","健管181"));
						cb2.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> ov2,
								Number old_val2,Number new_val2)->{
									if (new_val2.intValue() == 0) {
										//健康管理系全部学生信息
										updateData(4,"健康管理");
									}else if (new_val2.intValue() == 1) {
										//健管181班学生信息
										updateData(5,"健管181");
									}
								});	
					}
					//allInfo.clearData(0);
				});
	}
	
	//通过姓名查询该学生的入住信息
	public String getRoomInfoByName(String name) {
		String roomNumber = "";
		int bedNumber = 0;
		try {
    		
    		String sqlStr = "select * from roominfo";
    		
    		sta1 = con.createStatement();

    		rs1 = sta1.executeQuery(sqlStr);

    		while(rs1.next()){
    			for (int i=2;i<=5;++i) {
    				if (rs1.getString(i).equals(name)) {
    					roomNumber = rs1.getString(1);
    					bedNumber = i-1;
    					break;
    				}
    			}
    		}
		} catch (SQLException e) {
    		System.out.println("查询出错：" + e.getMessage());
    	}
		return "入住寝室："+roomNumber+"  床号："+bedNumber;
	}
	
	//字符串模糊匹配
	public boolean strMatches(String s1, String s2) {
		for (int i=0 ;i<s1.length()-s2.length()+1;++i) {
			if (s1.substring(i, i+s2.length()).equals(s2)){
				return true;
			}
		}
		return false;
	}
    
	//更新查询到的学生信息
    public void updateData(int sqlIndex,String massage) {
		
    	try {
    		
    		String sqlStr = "select * from studentinfo";
    		
    		sta = con.createStatement();

    		rs = sta.executeQuery(sqlStr);
    		
    		String checkInformation = "";
    		
    		boolean flag = false;
    		
    		int num = 0;

    		while(rs.next()){
    			if (sqlIndex == 0) {
    				if (!flag) {
    					allInfo.data.add(String.format("         %s               %s             %s                 %s                  %s             %s\n", "学号","姓名","学院","系别","班级","入住情况"));
    					flag = true;
    				}
    				if (rs.getString(6).equals("0")) {
    					checkInformation = "未入住";
    				}else {
    					checkInformation = getRoomInfoByName(rs.getString(2));
    				}
    				allInfo.data.add(String.format("      %s           %s           %s            %s            %s           %s\n", rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),checkInformation));
					num++;
    			}else if (strMatches(rs.getString(sqlIndex),massage)) {
    				if (!flag) {
    					allInfo.data.add(String.format("         %s               %s             %s                 %s                  %s             %s\n", "学号","姓名","学院","系别","班级","入住情况"));
    					flag = true;
    				}
    				if (rs.getString(6).equals("0")) {
    					checkInformation = "未入住";
    				}else {	
    					checkInformation = getRoomInfoByName(rs.getString(2));
    				}
    				allInfo.data.add(String.format("      %s           %s           %s            %s            %s           %s\n", rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),checkInformation));
    				num++;
    			}
    		}
    		
    		allInfo.label6.setText("   共查询到"+num+"条信息：");
    		allInfo.lv.setItems(FXCollections.observableArrayList(allInfo.data));
    		allInfo.data.clear();
    		
    	} catch (SQLException e) {
    		System.out.println("查询出错：" + e.getMessage());
    	}
	}
    
    //更新查询到的寝室信息
    public void updateRoomData(String roomNumber) {
    	
    	try {
    		
    		String sqlStr = "select * from roominfo";
    		
    		sta = con.createStatement();

    		rs = sta.executeQuery(sqlStr);
    		
    		String data = "";
    		
    		boolean flag = false;

    		while(rs.next()){
    			if (rs.getString(1).equals(roomNumber)) {
    				if (!flag) {
    					allInfo.data.add(String.format("      %s          %s          %s          %s          %s\n", "寝室号","1号床","2号床","3号床","4号床"));
    					flag = true;
    				}
    				String s1 = rs.getString(2);
    				if (s1.length() == 1) s1 = "   "+s1;
    				String s2 = rs.getString(3);
    				if (s2.length() == 1) s2 = "   "+s2;
    				String s3 = rs.getString(4);
    				if (s3.length() == 1) s3 = "   "+s3;
    				String s4 = rs.getString(5);
    				if (s4.length() == 1) s4 = "   "+s4;
    				allInfo.data.add(String.format("        %s             %s             %s            %s           %s\n", roomNumber,s1,s2,s3,s4));
    			}
    		}
    		if (flag) {
    			allInfo.label6.setText("   以下为"+roomNumber+"寝室的入住情况");
    		}else {
    			Alert error = new Alert(Alert.AlertType.ERROR,"该寝室号不存在！");
				error.show();
    		}
    		allInfo.lv.setItems(FXCollections.observableArrayList(allInfo.data));
    		allInfo.data.clear();
    		
    	} catch (SQLException e) {
    		System.out.println("查询出错：" + e.getMessage());
    	}
    }
	
    //获取随机的验证码
	class getYZM implements EventHandler<MouseEvent> {
		private String num;

		public void handle(MouseEvent event) {
			num = String.format("%d%d%d%d",(int)(Math.random()*10),(int)(Math.random()*10),(int)(Math.random()*10),(int)(Math.random()*10));
			yzm.setText(num);
		}
	}
	
	//修改密码按钮事件，进入修改密码界面
	class toChangePassword implements EventHandler<ActionEvent> {
		
		public void handle(ActionEvent arg0) {
			if (logIn.textfield1.getText().equals("")) {
				Alert warning = new Alert(Alert.AlertType.WARNING,"请输入要修改的用户名");
    			warning.show();
			}else if (!logIn.textfield1.getText().equals("admin")) {
				Alert warning = new Alert(Alert.AlertType.WARNING,"系统中没有找到该用户");
    			warning.show();
			}else {
				changePassword.clearChangePasswordPane();
				updateMainScene(changePassword.getPane());
			}
			
		}
	}
	
	//确认修改密码事件，确认密码修改
	class confirmChangePassword implements EventHandler<ActionEvent> {
		
		public void handle(ActionEvent arg0) {
			
			if (!changePassword.passwordfield2.getText().equals(changePassword.passwordfield3.getText())) {
				Alert error = new Alert(Alert.AlertType.ERROR,"确认密码与新密码不一致");
				error.show();
			}else {
				String sqlStr = "select * from admininfo";
	    		
	        	try {
	        		sta = con.createStatement();

	        		rs = sta.executeQuery(sqlStr);

	        		while(rs.next()){
	        			if (!rs.getString(2).equals(changePassword.passwordfield1.getText())) {
	        				Alert error = new Alert(Alert.AlertType.ERROR,"原密码错误");
	        				error.show();
	        			}else {
	        				try {
	        					pSta = con.prepareStatement("UPDATE admininfo SET password = ? WHERE name = ?");

    							pSta.setString(1, changePassword.passwordfield3.getText());
    							pSta.setString(2, "admin");
    							pSta.executeUpdate();
    							
    							Alert information = new Alert(Alert.AlertType.INFORMATION,"密码修改成功");
    							Optional<ButtonType> result = information.showAndWait();
								if(result.isPresent() && result.get() == ButtonType.OK) {
									if (changePassword.passwordfield1.getText().equals("123")) {
										firstLogin.clearFirstLoginPane();
			    						firstLoginPane.setCenter(firstLogin.getPane());
			    						firstLoginStage.setScene(firstLoginScene);
			    						firstLoginStage.show();
									}else
										updateMainScene(logIn.getPane());
								}
    							
	        				} catch (SQLException e) {
	        					e.printStackTrace();
	        				}
	        			}
	        		}
	        		
	        	} catch (SQLException e) {
	        		System.out.println("查询出错：" + e.getMessage());
	        	}
			}
			
		}
	}
	
	//发送邮箱绑定验证码按钮事件
	class toSendMail implements EventHandler<ActionEvent> {
		private String MailAddress;
		
		public void handle(ActionEvent arg0) {
			firstLoginYZM = String.format("%d%d%d%d",(int)(Math.random()*10),(int)(Math.random()*10),(int)(Math.random()*10),(int)(Math.random()*10));
			MailAddress =firstLogin.textfield1.getText();
			if (MailAddress.equals("")) {
				Alert error = new Alert(Alert.AlertType.ERROR,"请输入正确的邮箱！");
				error.show();
			}else {
				try {
					SendMail mail = new SendMail(MailAddress,firstLoginYZM);
					mail.send();
					Alert information = new Alert(Alert.AlertType.INFORMATION,"验证码已发送到邮箱");
					information.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//检查邮箱绑定验证码按钮事件
	class CheckYZM implements EventHandler<ActionEvent> {
		
		public void handle(ActionEvent arg0) {
			if (firstLogin.textfield2.getText().equals(firstLoginYZM)) {
				try {
					pSta = con.prepareStatement("UPDATE admininfo SET mailaddress = ? WHERE name = ?");

					pSta.setString(1, firstLogin.textfield1.getText());
					pSta.setString(2, "admin");
					pSta.executeUpdate();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				Alert information = new Alert(Alert.AlertType.INFORMATION,"绑定邮箱成功");
				Optional<ButtonType> result = information.showAndWait();
				if(result.isPresent() && result.get() == ButtonType.OK) {
					firstLoginStage.hide();
					updateMainScene(roomInfo.getPane());
				}
			}else{
				Alert error = new Alert(Alert.AlertType.ERROR,"验证码错误！");
				error.show();
			}
			
		}
	}
	
	//返回登录界面按钮事件，返回登录界面
	class BackToLogin implements EventHandler<ActionEvent> {
		
		public void handle(ActionEvent arg0) {
			logIn.passwordfield.clear();
        	logIn.textfield2.clear();
        	logIn.label6.setText(String.format("%d%d%d%d",(int)(Math.random()*10),(int)(Math.random()*10),(int)(Math.random()*10),(int)(Math.random()*10)));
			updateMainScene(logIn.getPane());
		}
	}
	
	//登录按钮事件，进行登录操作
	class Login implements EventHandler<ActionEvent> {
		private String name;
		private String password;
		private String yanzm;
		private boolean isFirst;
		
        public void handle(ActionEvent arg0) {
        	name = logIn.textfield1.getText();
        	password = logIn.passwordfield.getText();
        	yanzm = logIn.textfield2.getText();
        	isFirst = false;
        	
        	if (!logIn.label6.getText().equals(yanzm)) {
        		Alert error = new Alert(Alert.AlertType.ERROR,"验证码错误");
				error.show();
        	}else {
        		String sqlStr = "select * from admininfo";
        		
        		boolean flag = false;
        		
            	try {
            		sta = con.createStatement();

            		rs = sta.executeQuery(sqlStr);

            		while(rs.next()){
            			if (rs.getString(1).equals(name)&&rs.getString(2).equals(password)) {
            				flag = true;
            				if (rs.getString(2).equals("123")) {
            					isFirst = true;
            				}
            			}
            			break;
            		}
            		
            		if (!flag) {
            			Alert error = new Alert(Alert.AlertType.ERROR,"用户名或密码错误");
        				error.show();
            		}else {
            			Alert information = new Alert(Alert.AlertType.INFORMATION,"登陆成功");
						Optional<ButtonType> result = information.showAndWait();
	    				if(result.isPresent() && result.get() == ButtonType.OK) {
	    					if (isFirst) {
	    						changePassword.clearChangePasswordPane();
	            				Alert information1 = new Alert(Alert.AlertType.INFORMATION,"您是第一次登陆，请修改密码");
								Optional<ButtonType> result1 = information1.showAndWait();
								if(result1.isPresent() && result1.get() == ButtonType.OK) {
									updateMainScene(changePassword.getPane());
								}
	            			}else {
	            				updateMainScene(roomInfo.getPane());
	            			}
	    				}
            		}
            		
            	} catch (SQLException e) {
            		System.out.println("查询出错：" + e.getMessage());
            	}
        	}
        	logIn.passwordfield.clear();
        	logIn.textfield2.clear();
        	logIn.label6.setText(String.format("%d%d%d%d",(int)(Math.random()*10),(int)(Math.random()*10),(int)(Math.random()*10),(int)(Math.random()*10)));
		}
	}
	
	//选择房间按钮事件，点击对应房间按钮选中和取消选中
	class ChooseRoom implements EventHandler<ActionEvent> {
		private int roomNum;
		
        public void handle(ActionEvent arg0) {
			roomNum = Integer.valueOf(((Button)arg0.getSource()).getText());

			for (int a=0;a<3;++a){
				for (int b=0;b<10;++b){
					if (a == roomNum/100-1 && b == roomNum%100-1) {
						if (number[a][b].getStyle().equals("-fx-background-color:red;-fx-border-color:red; -fx-border-width:3")) {
							number[a][b].setStyle("-fx-background-color:red;-fx-border-color:black; -fx-border-width:3");
							roomInfo.textfield1.setText(""+roomNum);
						}else if (number[a][b].getStyle().equals("-fx-background-color:yellow;-fx-border-color:yellow; -fx-border-width:3")){
							number[a][b].setStyle("-fx-background-color:yellow;-fx-border-color:black; -fx-border-width:3");
							roomInfo.textfield1.setText(""+roomNum);
						}else if (number[a][b].getStyle().equals("-fx-background-color:red;-fx-border-color:black; -fx-border-width:3")) {
							number[a][b].setStyle("-fx-background-color:red;-fx-border-color:red; -fx-border-width:3");
							roomInfo.textfield1.clear();
						}else if (number[a][b].getStyle().equals("-fx-background-color:yellow;-fx-border-color:black; -fx-border-width:3")) {
							number[a][b].setStyle("-fx-background-color:yellow;-fx-border-color:yellow; -fx-border-width:3");
							roomInfo.textfield1.clear();
						}
					}
					else {
						if (number[a][b].getStyle().equals("-fx-background-color:yellow;-fx-border-color:black; -fx-border-width:3"))
							number[a][b].setStyle("-fx-background-color:yellow;-fx-border-color:yellow; -fx-border-width:3");
						else if (number[a][b].getStyle().equals("-fx-background-color:red;-fx-border-color:black; -fx-border-width:3"))
							number[a][b].setStyle("-fx-background-color:red;-fx-border-color:red; -fx-border-width:3");
					}
				}
			}
		}
	}
	
	//办理入住按钮事件，进入办理入住界面操作
	class toCheckIn implements EventHandler<ActionEvent> {
    	private int roomNumber = 0;
    	private int restBedNumber = 0;

        public void handle(ActionEvent arg0) {
        	if (roomInfo.isCheckIn()&&roomInfo.canCheckIn()) {
        		
        		roomNumber = roomInfo.getRoomNumber();
        		
        		String sqlStr = "select * from roominfo";
            		
            	try {
            		sta = con.createStatement();

            		rs = sta.executeQuery(sqlStr);

            		while(rs.next()){
            			if (rs.getInt(1)==roomNumber) {
            				restBedNumber = 0;
            				for (int i=2;i<=5;++i) {
            					if (rs.getString(i).equals("无")) {
            						if (i==2) checkIn.label8.setStyle("-fx-background-color:yellow;-fx-border-color:yellow; -fx-border-width:2");
            						else if (i==3) checkIn.label9.setStyle("-fx-background-color:yellow;-fx-border-color:yellow; -fx-border-width:2");
            						else if (i==4) checkIn.label10.setStyle("-fx-background-color:yellow;-fx-border-color:yellow; -fx-border-width:2");
            						else if (i==5) checkIn.label11.setStyle("-fx-background-color:yellow;-fx-border-color:yellow; -fx-border-width:2");
            						restBedNumber++;
            					}else {
            						if (i==2) checkIn.label8.setStyle("-fx-background-color:red;-fx-border-color:red; -fx-border-width:2");
            						else if (i==3) checkIn.label9.setStyle("-fx-background-color:red;-fx-border-color:red; -fx-border-width:2");
            						else if (i==4) checkIn.label10.setStyle("-fx-background-color:red;-fx-border-color:red; -fx-border-width:2");
            						else if (i==5) checkIn.label11.setStyle("-fx-background-color:red;-fx-border-color:red; -fx-border-width:2");
            					}
            				}
            				break;
            			}
            		}
            	} catch (SQLException e) {
            		System.out.println("查询出错：" + e.getMessage());
            	}
            		
            	checkIn.setRoomMassage(roomNumber, restBedNumber);
            		
            	updateMainScene(checkIn.getPane());
        	}else if (!roomInfo.isCheckIn()){
        		Alert warning = new Alert(Alert.AlertType.WARNING,"请选择想要入住的寝室");
    			warning.show();
        	}else if (!roomInfo.canCheckIn()) {
        		Alert warning = new Alert(Alert.AlertType.WARNING,"该寝室已住满，请选择未住满的寝室");
    			warning.show();
        	}

        }
    }

	//办理退房按钮事件，进入办理退房界面操作
    class toCheckOut implements EventHandler<ActionEvent> {
    	private int roomNumber=0;
    	
        public void handle(ActionEvent arg0) {
        	if (roomInfo.isCheckOut()) {
        		roomNumber = roomInfo.getRoomNumber();
        		checkOut.clearTextfield();
        		checkOut.setRoomMassage(roomNumber);
        		updateMainScene(checkOut.getPane());
        	}else {
        		Alert warning = new Alert(Alert.AlertType.WARNING,"请选择想要退房的寝室");
    			warning.show();
        	}
        }

    }
    
    //信息查询按钮事件，进入查询信息界面
    class toSearchInfo implements EventHandler<ActionEvent> {
    	
        public void handle(ActionEvent arg0) {
        	allInfo.clearData(-1);
        	allInfo.lv.setItems(FXCollections.observableArrayList());
        	updateMainScene(allInfo.getPane());
        }

    }
    
    //确认入住按钮事件，确认办理入住操作
    class confirmCheckIn implements EventHandler<ActionEvent> {
    	private String stuId;
    	private String stuName;
    	private String stuColleget;
    	private String stuDepartment;
    	private String stuClass;
    	private String bedNumber;
    	private int roomNumber;
    	
        public void handle(ActionEvent arg0) {
        	stuId = checkIn.textfield1.getText();
        	stuName = checkIn.textfield2.getText();
        	stuColleget = checkIn.textfield3.getText();
        	stuDepartment = checkIn.textfield4.getText();
        	stuClass = checkIn.textfield5.getText();
        	bedNumber = checkIn.textfield6.getText();
        	roomNumber = roomInfo.getRoomNumber();
        	
        	String[] s = {stuId,stuName,stuColleget,stuDepartment,stuClass};
        	
        	String sqlStr = "select * from studentinfo";
        	String massage = " ";
        	
    		if (bedNumber.equals("")) {
    			massage = "请选择入住床位";
    		}
        	
        	boolean check = false;
    		
        	try {
        		sta = con.createStatement();

        		rs = sta.executeQuery(sqlStr);

        		while(rs.next()){
        			if (rs.getString(1).equals(stuId)) {
        				for (int i=2;i<=5;++i) {
        					if (!rs.getString(i).equals(s[i-1])) {
        						massage = "您输入的信息有误或不全，请确认后重新输入！";
        					}
        					if (massage.equals(" ")) {
        						if (rs.getString(6).equals("1")) {
        							massage = "该同学已经办理过入住，请先退房后再办理入住！";
        						}
        					}
        				}
        				check= true;
        				break;
        			}
        		}
        		if (!check) {
        			massage = "没有检索到该学号学生的信息！";
        		}
        		if (massage.equals(" ")) {
        			massage = "学号:"+stuId
        					+"\n姓名:"+stuName
        					+"\n学院:"+stuColleget
        					+"\n专业:"+stuDepartment
        					+"\n班级:"+stuClass
        					+"\n入住寝室:"+roomNumber
        					+"\n入住床号:"+bedNumber;
    				Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,massage);
    				confirmation.setTitle("入住信息确认");
    				confirmation.setHeaderText("请检查你您的入住信息是否正确");
    				Optional<ButtonType> result = confirmation.showAndWait();
    				if(result.isPresent() && result.get() == ButtonType.OK) {
    					int flag = 0;
    					
    					try {
    						pSta = con.prepareStatement("UPDATE studentinfo SET isCheckIn = ? WHERE stuId = ?");

    						pSta.setString(1, "1");
    						pSta.setString(2, stuId);
    						flag += pSta.executeUpdate();
    						
    						String bedStr = "bed"+bedNumber;
    						pSta = con.prepareStatement("UPDATE roominfo SET "+bedStr+" = ? WHERE roomId = ?");
    						
    						pSta.setString(1, stuName);
    						pSta.setString(2, ""+roomNumber);
    						flag += pSta.executeUpdate();
    						
    						if (flag == 2) {
    							Alert information = new Alert(Alert.AlertType.INFORMATION,"入住成功");
    							Optional<ButtonType> result1 = information.showAndWait();
								if(result1.isPresent() && result1.get() == ButtonType.OK) {
									updateBtn();
						        	checkIn.clearTextfield();
						        	roomInfo.textfield1.setText(" ");
									updateMainScene(roomInfo.getPane());
								}
    							
    						}else {
    							Alert information = new Alert(Alert.AlertType.INFORMATION,"入住失败");
    							information.show();
    						}
    					} catch (SQLException e) {
    						e.printStackTrace();
    					}
    				}
    			}else {
    				Alert error = new Alert(Alert.AlertType.ERROR,massage);
    				error.show();
    			}
        	} catch (SQLException e) {
        		System.out.println("查询出错：" + e.getMessage());
        	}
        }

    }
    
    //选择入住床位标签事件，选择入住床位操作
    class ChooseBed implements EventHandler<MouseEvent> {
		private String bedNum;

		public void handle(MouseEvent event) {
			bedNum = ((Label)event.getSource()).getText();
			
			for (int i=0;i<4;++i) {
				if (bed[i].getText().equals(bedNum)) {
					if (bed[i].getStyle().equals("-fx-background-color:yellow;-fx-border-color:yellow; -fx-border-width:2")) {
						bed[i].setStyle("-fx-background-color:yellow;-fx-border-color:black; -fx-border-width:2");
						checkIn.textfield6.setText(bedNum.substring(2, 3));
					}else if (bed[i].getStyle().equals("-fx-background-color:yellow;-fx-border-color:black; -fx-border-width:2")){
						bed[i].setStyle("-fx-background-color:yellow;-fx-border-color:yellow; -fx-border-width:2");
						checkIn.textfield6.clear();
					}
						
				}else {
					if (bed[i].getStyle().equals("-fx-background-color:yellow;-fx-border-color:black; -fx-border-width:2"))
						bed[i].setStyle("-fx-background-color:yellow;-fx-border-color:yellow; -fx-border-width:2");
				}
			}
			
		}
	}
    
    //确认退房按钮事件，确认退房操作
    class confirmCheckOut implements EventHandler<ActionEvent> {
    	private String stuId;
    	private String stuName;
    	private String stuColleget;
    	private String stuDepartment;
    	private String stuClass;
    	private String roomNumber;
    	private int bedNumber;
    	
        public void handle(ActionEvent arg0) {
        	stuId = checkOut.textfield1.getText();
        	stuName = checkOut.textfield2.getText();
        	stuColleget = checkOut.textfield3.getText();
        	stuDepartment = checkOut.textfield4.getText();
        	stuClass = checkOut.textfield5.getText();
        	roomNumber = roomInfo.textfield1.getText();
        	
        	String[] s = {stuId,stuName,stuColleget,stuDepartment,stuClass};
        	
        	String sqlStr = "select * from studentinfo";
        	String massage = " ";
        	
        	boolean check = false;
    		
        	try {
        		sta = con.createStatement();

        		rs = sta.executeQuery(sqlStr);

        		while(rs.next()){
        			if (rs.getString(1).equals(stuId)) {
        				for (int i=2;i<=5;++i) {
        					if (!rs.getString(i).equals(s[i-1])) {
        						massage = "您输入的信息有误或不全，请确认后重新输入！";
        					}
        					if (massage.equals(" ")) {
        						if (rs.getString(6).equals("0")) {
        							massage = "该同学尚未入住寝室，不能办理退房！";
        						}
        					}
        				}
        				check= true;
        				break;
        			}
        		}
        		if (!check) {
        			massage = "没有检索到该学号学生的信息！";
        		}
        		if (massage.equals(" ")) {
        			sqlStr = "select * from roominfo";
        			
        			check = false;
        			
        			try {
                		sta = con.createStatement();

                		rs = sta.executeQuery(sqlStr);

                		while(rs.next()){
                			if (rs.getString(1).equals(roomNumber)) {
                				for (int i=2;i<=5;++i) {
                					if (rs.getString(i).equals(stuName)) {
                						bedNumber = i-1;
                						check= true;
                					}
                				}
                				break;
                			}
                		}
        			} catch (SQLException e) {
        				System.out.println("查询出错：" + e.getMessage());
        			}
        			
        			if (!check) {
        				Alert error = new Alert(Alert.AlertType.ERROR,"该学生未入住此寝室，请确认后重试！");
        				error.show();
        			}else {
        				massage = "学号:"+stuId
            					+"\n姓名:"+stuName
            					+"\n学院:"+stuColleget
            					+"\n专业:"+stuDepartment
            					+"\n班级:"+stuClass
            					+"\n入住寝室:"+roomNumber
            					+"\n入住床号:"+bedNumber;
        				Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,massage);
        				confirmation.setTitle("退房信息确认");
        				confirmation.setHeaderText("请检查你您的信息是否正确");
        				Optional<ButtonType> result = confirmation.showAndWait();
        				if(result.isPresent() && result.get() == ButtonType.OK) {
        					int flag = 0;
        					
        					try {
        						pSta = con.prepareStatement("UPDATE studentinfo SET isCheckIn = ? WHERE stuId = ?");

        						pSta.setString(1, "0");
        						pSta.setString(2, stuId);
        						flag += pSta.executeUpdate();
        						
        						String bedStr = "bed"+bedNumber;
        						pSta = con.prepareStatement("UPDATE roominfo SET "+bedStr+" = ? WHERE roomId = ?");
        						
        						pSta.setString(1, "无");
        						pSta.setString(2, roomNumber);
        						flag += pSta.executeUpdate();
        						
        						if (flag == 2) {
        							Alert information = new Alert(Alert.AlertType.INFORMATION,"退房成功");
        							Optional<ButtonType> result1 = information.showAndWait();
    								if(result1.isPresent() && result1.get() == ButtonType.OK) {
    									updateBtn();
    						        	checkIn.clearTextfield();
    						        	roomInfo.textfield1.setText(" ");
    									updateMainScene(roomInfo.getPane());
    								}
        							
        						}else {
        							Alert information = new Alert(Alert.AlertType.INFORMATION,"退房失败");
        							information.show();
        						}
        					} catch (SQLException e) {
        						e.printStackTrace();
        					}
        				}
        				
        			}
        			
    			}else {
    				Alert error = new Alert(Alert.AlertType.ERROR,massage);
    				error.show();
    			}
        	} catch (SQLException e) {
        		System.out.println("查询出错：" + e.getMessage());
        	}
        	
        }

    }
    
    //返回主页按钮事件，返回主页操作
    class BackToHomepage implements EventHandler<ActionEvent> {
    	
        public void handle(ActionEvent arg0) {
        	updateBtn();
        	checkIn.clearTextfield();
        	roomInfo.textfield1.clear();
        	updateMainScene(roomInfo.getPane());
        }

    }
    
    //按学号查询按钮事件，按照学号查询信息操作
    class SearchById implements EventHandler<ActionEvent> {
    	
    		public void handle(ActionEvent arg0) {
    			allInfo.clearData(1);
    			if (allInfo.textfield1.getText().equals("")) {
    				Alert warning = new Alert(Alert.AlertType.WARNING,"请输入想要查询的内容");
        			warning.show();
    			}else updateData(1, allInfo.textfield1.getText());
    		}

    }

    //按姓名查询按钮事件，按照姓名查询信息操作
	class SearchByName implements EventHandler<ActionEvent> {
	
		public void handle(ActionEvent arg0) {
			allInfo.clearData(2);
			if (allInfo.textfield2.getText().equals("")) {
				Alert warning = new Alert(Alert.AlertType.WARNING,"请输入想要查询的内容");
    			warning.show();
			}else updateData(2, allInfo.textfield2.getText());
    	}

	}

    //按寝室查询按钮事件，按照寝室查询信息操作
	class SearchByRoom implements EventHandler<ActionEvent> {
	
		public void handle(ActionEvent arg0) {
			allInfo.clearData(3);
			updateRoomData(allInfo.textfield3.getText());
		}

	}

}
