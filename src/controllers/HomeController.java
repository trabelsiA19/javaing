
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;


import java.time.LocalDate;

import models.Etudiant;


/**
 * FXML Controller class
 *
 * @author oXCToo
 */
public class HomeController implements Initializable {

    @FXML
    private TextField txtFirstname;
    @FXML
    private TextField txtLastname;
       @FXML
    private TextField txtsearch;
    @FXML
    private TextField txtCin;
    @FXML
    private TextField txtEmail;
    @FXML
    private DatePicker txtDOB;
    @FXML
    private Button btnSave,btnsaveup1,btndel,btnup,btnsearch,logout,forma,speci,cours,settings;
    @FXML
    private ComboBox<String> txtGender;
    
        @FXML
    private ComboBox<String>  spec;
    @FXML
    private ComboBox<String> txtPay;
    @FXML
    Label lblStatus;

    @FXML
    TableView tblData;

    /**
     * Initializes the controller class.
     */
    PreparedStatement preparedStatement;
    Connection connection;
   

    public HomeController() {
        connection = (Connection) ConnectionUtil.conDB();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         
        txtGender.getItems().addAll("Homme", "Femme");
        txtGender.getSelectionModel().select("Homme");
        txtPay.getItems().addAll("payment par","espece", "chéque");
        txtPay.getSelectionModel().select("payment par");
          (btnsaveup1).setVisible(false);
        spec.getItems().add(" choisir le specialité");
         try {
            
             
           
             
            
            // Execute query and store result in a resultset
            String SQL1 ="SELECT id, nom_s   FROM specialitee ";
                     ResultSet rs = connection.createStatement().executeQuery(SQL1);
            while (rs.next()) {
                
                //get string from db,whichever way 
                  spec.getItems().add(rs.getString("id")+"-"+rs.getString("nom_s"));
                
            }

        } catch (SQLException ex) {
            System.err.println("Error"+ex);
        }

    
            
 
        
        fetColumnList();
        fetRowList();

    }
    
    



    
    
    
    
                      @FXML
    private void update(MouseEvent event) throws SQLException, IOException, ParseException {  
       String p_nom,nom,p_id,id,p_prenom,prenom,email,p_email,cin,p_cin,p_date,datein,p_pay,pay,p_spec,specc; 
       
     if (event.getSource() ==btnup) {
  
                  p_nom=tblData.getSelectionModel().getSelectedItem().toString().split(" ")[1];
      nom=p_nom.substring(0,p_nom.length()-1);
      System.out.println(nom);
      txtFirstname.setText(nom);
      
      p_prenom=tblData.getSelectionModel().getSelectedItem().toString().split(" ")[2];
      prenom=p_prenom.substring(0,p_prenom.length()-1);
      System.out.println(prenom);
      txtLastname.setText(prenom);
      
      p_email=tblData.getSelectionModel().getSelectedItem().toString().split(" ")[4];
      email=p_email.substring(0,p_email.length()-1);
      System.out.println(email);
      txtEmail.setText(email);
            
       p_cin=tblData.getSelectionModel().getSelectedItem().toString().split(" ")[5];
      cin=p_cin.substring(0,p_cin.length()-1);
      System.out.println(cin);
      txtCin.setText(cin);
            
      
             p_date=tblData.getSelectionModel().getSelectedItem().toString().split(" ")[6];
      datein=p_date.substring(0,p_date.length()-1);
    System.out.println(datein); 
    String yy=p_date.substring(0,4);
     String mm=p_date.substring(5,7);
     String jj=p_date.substring(8,10);
      System.out.println(yy);
       System.out.println(mm);
         System.out.println(jj);
    txtDOB.setValue(LocalDate.of(Integer.parseInt(yy), Integer.parseInt(mm),Integer.parseInt(jj) ));
          
             p_pay=tblData.getSelectionModel().getSelectedItem().toString().split(" ")[7];
      pay=p_pay.substring(0,p_pay.length()-1);
    System.out.println(pay);
    txtPay.setValue(pay);
          
             p_spec=tblData.getSelectionModel().getSelectedItem().toString().split(" ")[8];
      specc=p_spec.substring(0,p_spec.length()-1);
    System.out.println(specc); 
          
spec.setValue(specc);
    
     (btnSave).setVisible(false);
     (btnSave).setDisable(true);
         (btnsaveup1).setVisible(true);
     }
     if (event.getSource() ==btnsaveup1) {
                      
                     
                  
               try {
                      p_id=tblData.getSelectionModel().getSelectedItem().toString().split(" ")[0];
        id=p_id.substring(1,p_id.length()-1);
      System.out.println(id);
   String st="UPDATE etudiant set nom=? ,prenom=?,genere=?,email=?,dateins=?,payment=?,cin=?,id_s=? WHERE id="+(Integer.parseInt(id)+""); 

            preparedStatement = (PreparedStatement) connection.prepareStatement(st);
            preparedStatement.setString(1, txtFirstname.getText());
            preparedStatement.setString(2, txtLastname.getText());
            preparedStatement.setString(4, txtEmail.getText());
            preparedStatement.setString(3, txtGender.getValue().toString());
            preparedStatement.setString(5, txtDOB.getValue().toString());
            preparedStatement.setString(6, txtPay.getValue().toString());
            preparedStatement.setString(7, txtCin.getText());
              String a=spec.getValue().toString();
            String spe=a.substring(0,1);
            System.out.println(spe);
            preparedStatement.setString(8,spe);
           
            preparedStatement.executeUpdate();
            lblStatus.setTextFill(Color.GREEN);
            lblStatus.setText("etudiant modifier avec succes");

           fetRowList();
            //clear fields
            clearFields();
            (btnsaveup1).setVisible(false);
            (btnSave).setDisable(false);
           (btnSave).setVisible(true);
                

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText(ex.getMessage());
            
                 }
         
         
      
    }
    
    }
    

    @FXML
    private void HandleEvents(MouseEvent event) throws SQLException, IOException {
   
        //check if not empty
        if (txtEmail.getText().isEmpty() || txtFirstname.getText().isEmpty() || txtLastname.getText().isEmpty() || txtDOB.getValue().equals(null)) {
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText("Entrer toutes les  detailes");
        } 
        else {
            saveData();
        }
        


    }
    
    
    
      @FXML
    private void clickdelete(MouseEvent event) throws SQLException, IOException {  
        String p_nom;
                        if (event.getSource() ==btndel) {
                     p_nom=tblData.getSelectionModel().getSelectedItem().toString().split(" ")[0];
             String id= p_nom.substring(1,p_nom.length()-1);
     		String state ="DELETE FROM etudiant WHERE id="+(Integer.parseInt(id)+"");  
                 preparedStatement = (PreparedStatement) connection.prepareStatement(state);
                 
                 
                preparedStatement.executeUpdate(); 
              lblStatus.setTextFill(Color.GREEN);
            lblStatus.setText("etudiant supprimer avec success");
            fetRowList();
            clearFields();
             
         }
      
    }
    
    
    
    
    
    
    
    
          @FXML
    private void nav(MouseEvent event) throws SQLException, IOException {  
     if (event.getSource() ==logout) {
       Stage stage = (Stage) logout.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
       Scene scene = new Scene(root);
       stage.setScene(scene);
       stage.setTitle("DEVTECH LOGIN");

        stage.show();   
        
     }
        else if (event.getSource() ==forma) {
       Stage stage2 = (Stage) forma.getScene().getWindow();
        stage2.close();
        Parent root2 = FXMLLoader.load(getClass().getResource("/fxml/Formateur.fxml"));
       Scene scene2 = new Scene(root2);
       stage2.setScene(scene2);
       stage2.setTitle("DEVTECH FORMATEURS ");

        stage2.show();   
        
    }
     
             else if (event.getSource() ==speci) {
       Stage stage2 = (Stage) speci.getScene().getWindow();
        stage2.close();
        Parent root2 = FXMLLoader.load(getClass().getResource("/fxml/specialite.fxml"));
       Scene scene2 = new Scene(root2);
       stage2.setScene(scene2);
       stage2.setTitle("DEVTECH SPECIALITE");

        stage2.show();   
        
    }
                 else if (event.getSource() ==cours) {
       Stage stage2 = (Stage) cours.getScene().getWindow();
        stage2.close();
        Parent root2 = FXMLLoader.load(getClass().getResource("/fxml/Cours.fxml"));
       Scene scene2 = new Scene(root2);
       stage2.setScene(scene2);
       stage2.setTitle("DEVTECH COURS ");

        stage2.show();   
        
    }
     else if (event.getSource() ==settings) {
       Stage stage2 = (Stage) settings.getScene().getWindow();
        stage2.close();
        Parent root2 = FXMLLoader.load(getClass().getResource("/fxml/Admin.fxml"));
       Scene scene2 = new Scene(root2);
       stage2.setScene(scene2);
       stage2.setTitle("DEVTECH Admin ");

        stage2.show();   
        
    }
     

     
     
     
    }
     
    
     
    private void clearFields() {
        txtFirstname.clear();
        txtLastname.clear();
        txtEmail.clear();   
         txtCin.clear();  
  
    }
    

    private String saveData() {

        try {
            String st = "INSERT INTO etudiant ( nom,prenom,genere,email,dateins,payment,cin,id_s) VALUES (?,?,?,?,?,?,?,?)";
            preparedStatement = (PreparedStatement) connection.prepareStatement(st);
            preparedStatement.setString(1, txtFirstname.getText());
            preparedStatement.setString(2, txtLastname.getText());
            preparedStatement.setString(4, txtEmail.getText());
            preparedStatement.setString(3, txtGender.getValue().toString());
            preparedStatement.setString(5, txtDOB.getValue().toString());
            preparedStatement.setString(6, txtPay.getValue().toString());
            preparedStatement.setString(7, txtCin.getText());
            String a=spec.getValue().toString();
            
            String spe=a.substring(0,1);
            System.out.println(spe);
            preparedStatement.setString(8,spe);
            preparedStatement.executeUpdate();
            lblStatus.setTextFill(Color.GREEN);
            lblStatus.setText("etudiant ajouter avec succes");

            fetRowList();
            //clear fields
            clearFields();
            return "Success";

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            lblStatus.setTextFill(Color.TOMATO);
            lblStatus.setText(ex.getMessage());
            return "Exception";
        }
    }

    private ObservableList<ObservableList> data;
    String SQL = "SELECT e.id,e.nom,e.prenom,e.genere,e.email,e.cin,e.dateins ,e.payment,s.nom_s from etudiant e ,specialitee s where e.id_s=s.id";

    //only fetch columns
    
    public void fetColumnList() {
              
                  TableColumn col = new TableColumn<Etudiant,String>("ID");
              
                  TableColumn col2 = new TableColumn<Etudiant,String>("Nom ");
                  
                  TableColumn col3 = new TableColumn<Etudiant,String>("Prenom ");
                  TableColumn col4 = new TableColumn<Etudiant,String>("Genre");
                  TableColumn col5 = new TableColumn<Etudiant,String>("Email");
                  TableColumn col6 = new TableColumn<Etudiant,String>("Cin");
                  TableColumn col7 = new TableColumn<Etudiant,String>("Date d'inscription");
                  TableColumn col8 = new TableColumn<Etudiant,String>("Payment");
                  TableColumn col9 = new TableColumn<Etudiant,String>("Specialité");
                  

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
                });                      col5.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(4).toString());
                    }
                });                      col6.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(5).toString());
                    }
                });                      col7.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(6).toString());
                    }
                });                      col8.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(7).toString());
                    }
                });                      col9.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(8).toString());
                    }
                });
                                            
  }
           
                tblData.getColumns().addAll(col,col2,col3,col4,col5,col6,col7,col8,col9);
             
               

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

            tblData.setItems(data);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }



}
