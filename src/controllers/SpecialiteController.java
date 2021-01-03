package controllers;

import com.mysql.jdbc.PreparedStatement;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javafx.scene.paint.Color;

import javafx.stage.Stage;
import javafx.util.Callback;

import models.Specialitee;

/**
 * FXML Controller class
 *
 * @author ahmed
 */

public class SpecialiteController implements Initializable {

        @FXML
    private TextField txtspec,txtsearch3;
            @FXML
    Label lblStatus1;

    @FXML
    TableView tbl;
    
        @FXML
    private Button btnsave,btndel,logout1,forma1,etud,cours1,btnup1,btnsaveup,btnsearch,settingss;

        
    PreparedStatement preparedStatement;
    Connection connection;
    
        public SpecialiteController() {
        connection = (com.mysql.jdbc.Connection) ConnectionUtil.conDB();
    }

        
        /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
             
    (btnsaveup).setVisible(false);
        fetColumnList();
        
       fetRowList();
    
 
          
          
    }  
    
        @FXML
    private void HandleEvents(MouseEvent event) throws SQLException, IOException {
   
        //check if not empty
        if (  txtspec.getText().isEmpty()) {
            lblStatus1.setTextFill(Color.TOMATO);
            lblStatus1.setText("Entrer toutes les  detailes");
        } 
        else {
              
            saveData();
        }
        
  
               
              
         
          }
        
        
        


    
    
    
              @FXML
    private void nav(MouseEvent event) throws SQLException, IOException {  
     if (event.getSource() ==logout1) {
       Stage stage = (Stage) logout1.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
       Scene scene = new Scene(root);
       stage.setScene(scene);
       stage.setTitle("DEVTECH LOGIN");

        stage.show();   
        
     }
        else if (event.getSource() ==forma1) {
       Stage stage2 = (Stage) forma1.getScene().getWindow();
        stage2.close();
        Parent root2 = FXMLLoader.load(getClass().getResource("/fxml/Formateur.fxml"));
       Scene scene2 = new Scene(root2);
       stage2.setScene(scene2);
       stage2.setTitle("DEVTECH FORMATEURS ");

        stage2.show();   
        
    }
        else if (event.getSource() ==etud) {
       Stage stage2 = (Stage) etud.getScene().getWindow();
        stage2.close();
        Parent root2 = FXMLLoader.load(getClass().getResource("/fxml/OnBoard.fxml"));
       Scene scene2 = new Scene(root2);
       stage2.setScene(scene2);
       stage2.setTitle("DEVTECH ETUDIANT");

        stage2.show();   
        
    }
             else if (event.getSource() ==cours1) {
       Stage stage2 = (Stage) cours1.getScene().getWindow();
        stage2.close();
        Parent root2 = FXMLLoader.load(getClass().getResource("/fxml/Cours.fxml"));
       Scene scene2 = new Scene(root2);
       stage2.setScene(scene2);
       stage2.setTitle("DEVTECH COURS ");

        stage2.show();   
        
    }
     
                  else if (event.getSource() ==settingss) {
       Stage stage2 = (Stage) settingss.getScene().getWindow();
        stage2.close();
        Parent root2 = FXMLLoader.load(getClass().getResource("/fxml/Admin.fxml"));
       Scene scene2 = new Scene(root2);
       stage2.setScene(scene2);
       stage2.setTitle("DEVTECH COURS ");

        stage2.show();   
        
    }
    }
     
    
          @FXML
    private void delete(MouseEvent event) throws SQLException, IOException {  
        String p_nom;
                        if (event.getSource() ==btndel) {
                           
                     p_nom=tbl.getSelectionModel().getSelectedItem().toString().split(" ")[0];
             String id= p_nom.substring(1,p_nom.length()-1);
             System.out.println(id);
     		String state ="DELETE FROM specialitee WHERE id="+(Integer.parseInt(id)+"");  
                 preparedStatement = (PreparedStatement) connection.prepareStatement(state);
                preparedStatement.executeUpdate(); 
              lblStatus1.setTextFill(Color.GREEN);
            lblStatus1.setText("specialité supprimer avec success");
            fetRowList();
            clearFields();
          
            
         
             
         }
      
    }
    

    
    
    

    
        private void clearFields() {
        txtspec.clear();
   
  
    }
        
                    private String saveData() {

        try {
            

            String st = "INSERT INTO specialitee (nom_s) VALUES (?)";
            
            preparedStatement = (PreparedStatement) connection.prepareStatement(st);
            preparedStatement.setString(1,  txtspec.getText());
            System.out.println(st);
            preparedStatement.executeUpdate();
            lblStatus1.setTextFill(Color.GREEN);
            lblStatus1.setText("spécialité ajouter avec succes");

           fetRowList();
            //clear fields
            clearFields();
            return "Success";

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            lblStatus1.setTextFill(Color.TOMATO);
            lblStatus1.setText(ex.getMessage());
            return "Exception";
        }
    }
        
                      @FXML
    private void update(MouseEvent event) throws SQLException, IOException {  
       String p_nom,nom,p_id,id; 
       
     if (event.getSource() ==btnup1) {
               
    
      p_nom=tbl.getSelectionModel().getSelectedItem().toString().split(" ")[1];
      nom=p_nom.substring(0,p_nom.length()-1);
      System.out.println(nom);
      txtspec.setText(nom);
      
     (btnsave).setVisible(false);
     (btnsave).setDisable(true);
         (btnsaveup).setVisible(true);
     }
                 if (event.getSource() ==btnsaveup) {
                       p_id=tbl.getSelectionModel().getSelectedItem().toString().split(" ")[0];
        id=p_id.substring(1,p_id.length()-1);
      System.out.println(id);
                  
                     
                  
                try {
            
   String st="UPDATE specialitee set nom_s=? WHERE id="+(Integer.parseInt(id)+""); 
               String a=txtspec.getText();
                  System.out.println(a); 
            preparedStatement = (PreparedStatement) connection.prepareStatement(st);
            preparedStatement.setString(1,txtspec.getText());
            System.out.println(st);
            preparedStatement.executeUpdate();
            lblStatus1.setTextFill(Color.GREEN);
            lblStatus1.setText("specialité modifier avec succes");

           fetRowList();
            //clear fields
            clearFields();
            (btnsaveup).setVisible(false);
          
            (btnsave).setDisable(false);
           (btnsave).setVisible(true);
                

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            lblStatus1.setTextFill(Color.TOMATO);
            lblStatus1.setText(ex.getMessage());
            
                 }
         
         
      
    }
    }
    
  
       

       
        
    private ObservableList<ObservableList> data;
    String SQL = "SELECT id ,nom_s As  nom FROM specialitee";
        //fetches rows and data from the list
    
    
    public void fetColumnList() {
              
                          TableColumn col = new TableColumn<Specialitee,String>("ID");
              
                  TableColumn col2 = new TableColumn<Specialitee,String>("Nom de Specialité");

        try {
            ResultSet rs = connection.createStatement().executeQuery(SQL);


            //SQL FOR SELECTING ALL OF CUSTOMER
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;

                
                
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(0).toString());
                    }
                });
                
                      col2.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(1).toString());
                    }
                });
  }
           
                tbl.getColumns().add(col);
             
                 tbl.getColumns().add(col2);    

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());

        }
    }


    private void fetRowList() {
        data = FXCollections.observableArrayList();
        ResultSet rs;
        try {
            rs = connection.createStatement().executeQuery(SQL);

            while (rs.next()) {
                //Iterate Row
                ObservableList row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added " + row);
                data.add(row);

            }

            tbl.setItems(data);
        } catch (SQLException ex) {
          
            System.err.println(ex.getMessage());
        }
        



        
        
 
		
    }
    
    
    
   
     

}
