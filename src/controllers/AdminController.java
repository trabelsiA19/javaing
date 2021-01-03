package controllers;

import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;

import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ahmed
 */

public class AdminController implements Initializable {

        @FXML
    private TextField Nemail;
        
        @FXML
    private TextField Npasse;

                @FXML
    Label lblaa; 
                
        @FXML
    private Button btnsave,btndel,logout1,forma1,etud,cours1,btnup2,btnsaveup,Home;

        
    PreparedStatement preparedStatement;
    Connection connection;
    
        public AdminController() {
        connection = (com.mysql.jdbc.Connection) ConnectionUtil.conDB();
    }

        
        /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
            
    
 
          
          
    }  
    
        @FXML
    private void HandleEvents(MouseEvent event) throws SQLException, IOException {
   
        //check if not empty
        if (  Nemail.getText().isEmpty()|| Npasse.getText().isEmpty()) {
            lblaa.setTextFill(Color.TOMATO);
            lblaa.setText("Entrer toutes les  detailes");
        } 
   
        
  
               
              
         
          }
    
                  @FXML
    private void nav(MouseEvent event) throws SQLException, IOException {  
     if (event.getSource() ==Home) {
       Stage stage = (Stage) Home.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/OnBoard.fxml"));
       Scene scene = new Scene(root);
       stage.setScene(scene);
       stage.setTitle("DEVTECH LOGIN");

        stage.show();   
        
     }
    }
        
        
        


    
    

    

    
    
    

    
        private void clearFields() {
        Nemail.clear();
           Npasse.clear();
  
    }
        
        
        
        
                      @FXML
    private void update(MouseEvent event) throws SQLException, IOException {  
       String p_nom,nom,p_id,id; 
       
     if (event.getSource() ==btnup2) {
               
    
   
                  
                     
                  
                try {
            
   String st="UPDATE admins set email=?,password=? WHERE id=1"; 
            preparedStatement = (PreparedStatement) connection.prepareStatement(st);
            preparedStatement.setString(1,Nemail.getText());
              preparedStatement.setString(2,Npasse.getText());
            System.out.println(st);
            preparedStatement.executeUpdate();
            lblaa.setTextFill(Color.GREEN);
            lblaa.setText("Admin modifier avec succes");
      if (event.getSource() ==Home) {
               Stage stage2 = (Stage) Home.getScene().getWindow();
        stage2.close();
        Parent root2 = FXMLLoader.load(getClass().getResource("/fxml/OnBoard.fxml"));
       Scene scene2 = new Scene(root2);
       stage2.setScene(scene2);
       stage2.setTitle("DEVTECH ETUDIANT");

        stage2.show();  
      }
                

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            lblaa.setTextFill(Color.TOMATO);
            lblaa.setText(ex.getMessage());
            
                 }
         
         
      
    }
    }
    
  
       

       
        

    

    
    
    
    
    


}
