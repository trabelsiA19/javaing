/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.net.URL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

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
import javafx.scene.control.ComboBox;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Cours;


/**
 * FXML Controller class
 *
 * @author oXCToo
 */
public class CoursController implements Initializable {

       @FXML
    private TextField txtsearch;
    @FXML
    private TextField Ncours;
    @FXML
    private TextField Coeff;

    @FXML
    private Button btnSave2,btndelco,btnupC,btnSaveupco,btnsearch,logout2,forma2,speci2,etud2,settingsc;
    @FXML
    Label lblStatusc;
    
      @FXML
    private ComboBox<String>  specc;
      

    @FXML
    TableView tblc;

    /**
     * Initializes the controller class.
     */
    PreparedStatement preparedStatement;
    Connection connection;
   

    public CoursController() {
        connection = (Connection) ConnectionUtil.conDB();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {



              (btnSaveupco).setVisible(false);
              
              specc.getItems().add(" choisir le specialité");
         try {
            
             
           
             
            
            // Execute query and store result in a resultset
            String SQL1 ="SELECT id, nom_s   FROM specialitee ";
                     ResultSet rs = connection.createStatement().executeQuery(SQL1);
            while (rs.next()) {
                
                //get string from db,whichever way 
                  specc.getItems().add(rs.getString("id")+"-"+rs.getString("nom_s"));
                
            }

        } catch (SQLException ex) {
            System.err.println("Error"+ex);
        }

            
fetColumnList();
        fetRowList() ;
    

    }
    

    @FXML
    private void HandleEvents(MouseEvent event) throws SQLException, IOException {
   
        //check if not empty
        if (Ncours.getText().isEmpty() || Coeff.getText().isEmpty()) {
            lblStatusc.setTextFill(Color.TOMATO);
            lblStatusc.setText("Entrer toutes les  detailes");
        } 
        else {
            saveData();
         
        }
        


    }
    
    
    
        private String saveData() {

        try {
            String st = "INSERT INTO cours (nom,coeff,id_s) VALUES (?,?,?)";
            preparedStatement = (PreparedStatement) connection.prepareStatement(st);
            preparedStatement.setString(1,Ncours.getText());
            preparedStatement.setString(2,Coeff.getText());
            String a=specc.getValue().toString();
            
            String spe=a.substring(0,1);
            System.out.println(spe);
            preparedStatement.setString(3,spe);
            preparedStatement.executeUpdate();
            lblStatusc.setTextFill(Color.GREEN);
            lblStatusc.setText("cours ajouter avec succes");

            fetRowList();
            //clear fields
            clearFields();
            return "Success";

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            lblStatusc.setTextFill(Color.TOMATO);
            lblStatusc.setText(ex.getMessage());
            return "Exception";
        }
    }
    
    
    
      @FXML
    private void clickdelete(MouseEvent event) throws SQLException, IOException {  
        String p_nom;
                        if (event.getSource() ==btndelco) {
                     p_nom=tblc.getSelectionModel().getSelectedItem().toString().split(" ")[0];
             String id= p_nom.substring(1,p_nom.length()-1);
     		String state ="DELETE FROM cours WHERE id="+(Integer.parseInt(id)+"");  
                 preparedStatement = (PreparedStatement) connection.prepareStatement(state);
                preparedStatement.executeUpdate(); 
              lblStatusc.setTextFill(Color.GREEN);
            lblStatusc.setText("cours supprimer avec success");
            fetRowList();
            clearFields();
             
         }
      
    }
    
    
    
    
    
                     @FXML
    private void update(MouseEvent event) throws SQLException, IOException, ParseException {  
       String p_nom,nom,p_id,id,p_coeff,coeff; 
       
     if (event.getSource() ==btnupC) {
  
                  p_nom=tblc.getSelectionModel().getSelectedItem().toString().split(" ")[1];
      nom=p_nom.substring(0,p_nom.length()-1);
      System.out.println(nom);
      Ncours.setText(nom);
      
      p_coeff=tblc.getSelectionModel().getSelectedItem().toString().split(" ")[2];
      coeff=p_coeff.substring(0,p_coeff.length()-1);
      System.out.println(coeff);
      Coeff.setText(coeff);
      
            
      
 

    
     (btnSave2).setVisible(false);
     (btnSave2).setDisable(true);
         (btnSaveupco).setVisible(true);
     }
    
     if (event.getSource() ==btnSaveupco) {
                      
                     
                  
               try {
                      p_id=tblc.getSelectionModel().getSelectedItem().toString().split(" ")[0];
        id=p_id.substring(1,p_id.length()-1);
      System.out.println(id);
   String st="UPDATE cours set nom=? ,coeff=? WHERE id="+(Integer.parseInt(id)+""); 

            preparedStatement = (PreparedStatement) connection.prepareStatement(st);
            preparedStatement.setString(1, Ncours.getText());
            preparedStatement.setString(2, Coeff.getText());
 
            preparedStatement.executeUpdate();
            lblStatusc.setTextFill(Color.GREEN);
            lblStatusc.setText("etudiant modifier avec succes");

           fetRowList();
            //clear fields
            clearFields();
            (btnSaveupco).setVisible(false);
            (btnSave2).setDisable(false);
           (btnSave2).setVisible(true);
                

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            lblStatusc.setTextFill(Color.TOMATO);
            lblStatusc.setText(ex.getMessage());
            
                 }
         
         
      
    }
    
    }

    
    
    
    
    
    
    
          @FXML
    private void nav(MouseEvent event) throws SQLException, IOException {  
     if (event.getSource() ==logout2) {
       Stage stage = (Stage) logout2.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
       Scene scene = new Scene(root);
       stage.setScene(scene);
       stage.setTitle("DEVTECH Login");

        stage.show();   
        
     }
        else if (event.getSource() ==forma2) {
       Stage stage2 = (Stage) forma2.getScene().getWindow();
        stage2.close();
        Parent root2 = FXMLLoader.load(getClass().getResource("/fxml/Formateur.fxml"));
       Scene scene2 = new Scene(root2);
       stage2.setScene(scene2);
       stage2.setTitle("DEVTECH FORMATEURS");

        stage2.show();   
        
    }
     
             else if (event.getSource() ==speci2) {
       Stage stage2 = (Stage) speci2.getScene().getWindow();
        stage2.close();
        Parent root2 = FXMLLoader.load(getClass().getResource("/fxml/specialite.fxml"));
       Scene scene2 = new Scene(root2);
       stage2.setScene(scene2);
       stage2.setTitle("DEVTECH SPECIALITE");

        stage2.show();   
        
    }
                 else if (event.getSource() ==etud2) {
       Stage stage2 = (Stage) etud2.getScene().getWindow();
        stage2.close();
        Parent root2 = FXMLLoader.load(getClass().getResource("/fxml/OnBoard.fxml"));
       Scene scene2 = new Scene(root2);
       stage2.setScene(scene2);
       stage2.setTitle("DEVTECH ETUDIANT");

        stage2.show();   
        
    }
                           else if (event.getSource() ==settingsc) {
       Stage stage2 = (Stage) settingsc.getScene().getWindow();
        stage2.close();
        Parent root2 = FXMLLoader.load(getClass().getResource("/fxml/Admin.fxml"));
       Scene scene2 = new Scene(root2);
       stage2.setScene(scene2);
       stage2.setTitle("DEVTECH ETUDIANT");

        stage2.show();   
        
    }
    }
     
    
     
    private void clearFields() {
        Ncours.clear();
        Coeff.clear();
 
  
    }
    

 

    private ObservableList<ObservableList> data;
    String SQL = "SELECT c.id,c.nom,c.coeff, s.nom_s from cours c ,specialitee s where c.id_s=s.id";

    public void fetColumnList() {
              
                  TableColumn col = new TableColumn<Cours,String>("ID");
              
                  TableColumn col2 = new TableColumn<Cours,String>("Nom du cours ");
                  
                  TableColumn col3 = new TableColumn<Cours,String>("Coefficient ");
          
                  TableColumn col4 = new TableColumn<Cours,String>("Spécialité");
                

                  

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
                                            col3.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(2).toString());
                    }
                });                      col4.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(3).toString());
                    }
                });                     
  }
           
                tblc.getColumns().addAll(col,col2,col3,col4);
             
               

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());

        }
    }

    //fetches rows and data from the list
    public void fetRowList() {
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

            tblc.setItems(data);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }



}
