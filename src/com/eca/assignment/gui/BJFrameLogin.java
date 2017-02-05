package com.eca.assignment.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.Timer;

import com.eca.assignment.entity.BJPlayer;
import com.eca.assignment.game.BJDatabaseConn;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class BJFrameLogin extends JFrame {

	// Container components
	private BJPanelLogin panelLogin;
	private MigLayout layout_jframe;

	// Game Objects
	private BJPlayer player;
	private BJDatabaseConn conn;
	private Timer timer;
	

	public BJFrameLogin() {
		
		this.setSize(600, 460);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.layout_jframe = new MigLayout("", "0[600]0", "0[460]0");
		this.setLayout(layout_jframe);		
		this.setLocationRelativeTo(null);		
		this.pack();
		
		createJPanelLogin();	
		
		//It gives the initial focus to the Login button
		this.getRootPane().setDefaultButton(panelLogin.getJb_login());
		panelLogin.getJb_login().requestFocus();
		
		this.setVisible(true);
	}
	
	

	private void createJPanelLogin() {

		// Creates de Login panel using the BJPanelLogin class
		panelLogin = new BJPanelLogin();
		
		//Initialize the timer
		timer = new Timer(2000, null);
		timer.setRepeats(false);		
		timer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				createJPanelGame();
				
			}
		});
		
		// Adds the login action to the button		
		panelLogin.getJb_login().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(panelLogin.getJc_createUser().isSelected()){
					conn = new BJDatabaseConn();
					
					conn.insertNewUser(panelLogin.getJt_login().getText(), panelLogin.getJt_password().getPassword(), panelLogin.getJt_login().getText());
					
					player = new BJPlayer(panelLogin.getJt_login().getText());
					player.setName(panelLogin.getJt_login().getText());					
					panelLogin.getJl_login_error().setText("<html><font color=\"green\">OK, let's play!</font></html>");
					
					//It executes the actionPerformed method from the ActionListener previously defined
					timer.start();
					panelLogin.removeCreationPanel();
					
				}else if ((panelLogin.getJt_login().getText().trim().length() != 0) && panelLogin.getJt_password().getPassword().length != 0) {
					try {
						
						conn = new BJDatabaseConn();
						player = conn.authenticate(panelLogin.getJt_login().getText(),panelLogin.getJt_password().getPassword());
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					if (player != null) {
						
						panelLogin.getJl_login_error().setText("<html><b><font color=\"green\">OK, let's play!</font></b></html>");						
						
						//It executes the ActionListener previously defined
						timer.start();						
						
					} else {
						panelLogin.getJl_login_error().setText("<html><b>User and/or Pass incorrect!</b></html>");
					}

				}
			}
		});

		this.add(panelLogin, "growx, growy");

	}
	
	
	private void createJPanelGame() {
		new BJFrameGame(player);
		this.setVisible(false);		

	}

	
	
}
