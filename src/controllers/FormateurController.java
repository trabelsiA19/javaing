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
import models.Formateurs;

public class FormateurController implements Initializable {

    @FXML
    private TextField txtsearch;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField email;
    @FXML
    private TextField tele;

    @FXML
    private TextField nbrh;

    @FXML
    private Button btnSave3, btnupf, btnsaveupf, delform, btnsearch, logout3, cours3, speci3, etud3, settingsf;
    @FXML
    Label lblsa;

    @FXML
    TableView tbl2;

    @FXML
    private ComboBox<String> specx;

    @FXML
    private ComboBox<String> gra1;

    @FXML
    private Label totals;

    /**
     * Initializes the controller class.
     */
    PreparedStatement preparedStatement;
    Connection connection;

    public FormateurController() {
        connection = (Connection) ConnectionUtil.conDB();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        (btnsaveupf).setVisible(false);
        gra1.getItems().add("1er ");
        gra1.getItems().add("2er ");
        gra1.getItems().add("3er ");

        specx.getItems().add(" choisir le cours");
        try {

            // Execute query and store result in a resultset
            String SQL1 = "SELECT id, nom   FROM cours ";
            ResultSet rs = connection.createStatement().executeQuery(SQL1);
            while (rs.next()) {

                //get string from db,whichever way 
                specx.getItems().add(rs.getString("id") + "-" + rs.getString("nom"));

            }

        } catch (SQLException ex) {
            System.err.println("Error" + ex);
        }

        fetColumnList();
        fetRowList();
        
     CountSalaire();
   
        

    }

    @FXML
    private void HandleEvents(MouseEvent event) throws SQLException, IOException {

        //check if not empty
        if (nom.getText().isEmpty() || prenom.getText().isEmpty() || email.getText().isEmpty() || tele.getText().isEmpty() || nbrh.getText().isEmpty()) {
            lblsa.setTextFill(Color.TOMATO);
            lblsa.setText("Entrer toutes les  detailes");
        } else {
            saveData();
        }

    }

    private void CountSalaire() {
         String sum = "";
        try {

            String sql = "Select Sum(salaire) as sumSAL from formateurs";
            ResultSet rs = connection.createStatement().executeQuery(sql);
            if (rs.next()) {
                 sum = rs.getString("sumSAL");
               
            }
        } 
        catch (SQLException ex) {
            System.err.println("Error" + ex);
        }
            totals.setText(sum);

    }

    private String saveData() {

        try {
            String st = "INSERT INTO formateurs ( nom,prenom,email,telephone,grade,nbHeure,salaire,id_c) VALUES (?,?,?,?,?,?,?,?)";
            preparedStatement = (PreparedStatement) connection.prepareStatement(st);
            preparedStatement.setString(1, nom.getText());
            preparedStatement.setString(2, prenom.getText());
            preparedStatement.setString(3, email.getText());
            preparedStatement.setString(4, tele.getText());
            String g = gra1.getValue().toString();
            preparedStatement.setString(5, g);
            preparedStatement.setInt(6, Integer.parseInt(nbrh.getText()));
            Formateurs c = new Formateurs();
            int nb = Integer.parseInt(nbrh.getText());
            double sal = c.calculeSalaire(g, nb);
            preparedStatement.setDouble(7, sal);
            String a = specx.getValue().toString();
            String spe = a.substring(0, 1);

            preparedStatement.setString(8, spe);
            System.out.println("++++++++++++++++++++++++++++++++");
            System.out.println(st);
            preparedStatement.executeUpdate();
            lblsa.setTextFill(Color.GREEN);
            lblsa.setText("Formateur ajouter avec succes");

            fetRowList();
            //clear fields
            clearFields();
            return "Success";

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            lblsa.setTextFill(Color.TOMATO);
            lblsa.setText(ex.getMessage());
            return "Exception";
        }
    }

    @FXML
    private void deletef(MouseEvent event) throws SQLException, IOException {
        String p_nom;
        if (event.getSource() == delform) {
            p_nom = tbl2.getSelectionModel().getSelectedItem().toString().split(" ")[0];
            String id = p_nom.substring(1, p_nom.length() - 1);
            System.out.println(id);
            String state = "DELETE FROM formateurs WHERE id=" + (Integer.parseInt(id) + "");
            preparedStatement = (PreparedStatement) connection.prepareStatement(state);
            preparedStatement.executeUpdate();
            lblsa.setTextFill(Color.GREEN);
            lblsa.setText("formateur supprimer avec success");
            fetRowList();
            clearFields();

        }

    }

    @FXML
    private void update(MouseEvent event) throws SQLException, IOException {
        String p_nom, no, p_id, id, pre, p_prenom, ema, p_email, p_tel, tel, p_cou, cou, p_garde, gra, p_nbr, nb;

        if (event.getSource() == btnupf) {
            p_nom = tbl2.getSelectionModel().getSelectedItem().toString().split(" ")[1];
            no = p_nom.substring(0, p_nom.length() - 1);
            System.out.println(no);
            nom.setText(no);

            p_prenom = tbl2.getSelectionModel().getSelectedItem().toString().split(" ")[2];
            pre = p_prenom.substring(0, p_prenom.length() - 1);
            System.out.println(pre);
            prenom.setText(pre);

            p_email = tbl2.getSelectionModel().getSelectedItem().toString().split(" ")[3];
            ema = p_email.substring(0, p_email.length() - 1);
            System.out.println(ema);
            email.setText(ema);

            p_tel = tbl2.getSelectionModel().getSelectedItem().toString().split(" ")[4];
            tel = p_tel.substring(0, p_tel.length() - 1);
            System.out.println(tel);
            tele.setText(tel);

            p_cou = tbl2.getSelectionModel().getSelectedItem().toString().split(" ")[5];
            cou = p_cou.substring(0, p_cou.length() - 1);
            specx.setValue(cou);
            p_garde = tbl2.getSelectionModel().getSelectedItem().toString().split(" ")[6];
            gra = p_garde.substring(0, p_garde.length());
            gra1.setValue(gra);

            p_nbr = tbl2.getSelectionModel().getSelectedItem().toString().split(" ")[8];
            nb = p_nbr.substring(0, p_nbr.length() - 1);
            System.out.print(nb);
            nbrh.setText(nb);

            (btnSave3).setVisible(false);
            (btnSave3).setDisable(true);
            (btnsaveupf).setVisible(true);

        }

        if (event.getSource() == btnsaveupf) {
            p_id = tbl2.getSelectionModel().getSelectedItem().toString().split(" ")[0];
            id = p_id.substring(1, p_id.length() - 1);
            System.out.println(id);

            try {

                String st = "UPDATE formateurs SET nom = ?, prenom = ?, email = ?, telephone = ?, grade = ?, nbHeure = ?, salaire = ?,id_c=? WHERE id=" + (Integer.parseInt(id) + "");
                System.out.println("++++++++++++++++++++++++++++==");
                preparedStatement = (PreparedStatement) connection.prepareStatement(st);
                preparedStatement.setString(1, nom.getText());
                System.out.println(nom.getText());
                preparedStatement.setString(2, prenom.getText());
                System.out.println(prenom.getText());
                preparedStatement.setString(3, email.getText());
                System.out.println(email.getText());
                preparedStatement.setString(4, tele.getText());
                System.out.println(tele.getText());
                preparedStatement.setString(5, gra1.getValue().toString());
                System.out.println(gra1.getValue().toString());
                preparedStatement.setInt(6, Integer.parseInt(nbrh.getText()));
                System.out.println(Integer.parseInt(nbrh.getText()));
                Formateurs c = new Formateurs();
                double sal = c.calculeSalaire(gra1.getValue().toString(), Integer.parseInt(nbrh.getText()));
                preparedStatement.setDouble(7, sal);
                System.out.println(sal);

                String a = specx.getValue().toString();
                String spe = a.substring(0, 1);
                preparedStatement.setString(8, spe);
                System.out.println(spe);
                preparedStatement.executeUpdate();
                lblsa.setTextFill(Color.GREEN);
                lblsa.setText("formateurs modifier avec succes");
                fetRowList();
                //clear fields
                clearFields();
                CountSalaire();
                (btnsaveupf).setVisible(false);

                (btnSave3).setDisable(false);
                (btnSave3).setVisible(true);

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                lblsa.setTextFill(Color.TOMATO);
                lblsa.setText(ex.getMessage());

            }

        }
    }

    @FXML
    private void nav(MouseEvent event) throws SQLException, IOException {
        if (event.getSource() == logout3) {
            Stage stage = (Stage) logout3.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("DEVTECH Login");

            stage.show();

        } else if (event.getSource() == cours3) {
            Stage stage2 = (Stage) cours3.getScene().getWindow();
            stage2.close();
            Parent root2 = FXMLLoader.load(getClass().getResource("/fxml/Cours.fxml"));
            Scene scene2 = new Scene(root2);
            stage2.setScene(scene2);
            stage2.setTitle("DEVTECH Cours");

            stage2.show();

        } else if (event.getSource() == speci3) {
            Stage stage2 = (Stage) speci3.getScene().getWindow();
            stage2.close();
            Parent root2 = FXMLLoader.load(getClass().getResource("/fxml/specialite.fxml"));
            Scene scene2 = new Scene(root2);
            stage2.setScene(scene2);
            stage2.setTitle("DEVTECH SPECIALITE");

            stage2.show();

        } else if (event.getSource() == etud3) {
            Stage stage2 = (Stage) etud3.getScene().getWindow();
            stage2.close();
            Parent root2 = FXMLLoader.load(getClass().getResource("/fxml/OnBoard.fxml"));
            Scene scene2 = new Scene(root2);
            stage2.setScene(scene2);
            stage2.setTitle("DEVTECH ETUDIANT");

            stage2.show();

        } else if (event.getSource() == settingsf) {
            Stage stage2 = (Stage) settingsf.getScene().getWindow();
            stage2.close();
            Parent root2 = FXMLLoader.load(getClass().getResource("/fxml/Admin.fxml"));
            Scene scene2 = new Scene(root2);
            stage2.setScene(scene2);
            stage2.setTitle("DEVTECH ETUDIANT");

            stage2.show();

        }
    }

    private void clearFields() {
        nom.clear();
        prenom.clear();
        email.clear();
        tele.clear();

    }

    private ObservableList<ObservableList> data;
    String SQL = "SELECT f.id,f.nom,f.prenom,f.email,f.telephone,c.nom,f.grade,f.nbHeure,f.salaire from Formateurs f ,cours c where f.id_c=c.id";

    //only fetch columns
    public void fetColumnList() {

        TableColumn col = new TableColumn<Formateurs, String>("ID");

        TableColumn col2 = new TableColumn<Formateurs, String>("Nom ");

        TableColumn col3 = new TableColumn<Formateurs, String>("Prenom ");

        TableColumn col4 = new TableColumn<Formateurs, String>("Email");
        TableColumn col5 = new TableColumn<Formateurs, String>("Téléphone");

        TableColumn col6 = new TableColumn<Formateurs, String>("Formé");
        TableColumn col7 = new TableColumn<Formateurs, String>("Grade");
        TableColumn col8 = new TableColumn<Formateurs, String>("nombres dheurs");
        TableColumn col9 = new TableColumn<Formateurs, String>("Salaire");

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
                });
                col4.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(3).toString());
                    }
                });
                col5.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(4).toString());
                    }
                });

                col6.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(5).toString());
                    }
                });
                col7.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(6).toString());
                    }

                });

                col8.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(7).toString());
                    }
                });

                col9.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(8).toString());
                    }
                });

            }

            tbl2.getColumns().addAll(col, col2, col3, col4, col5, col6, col7, col8, col9);

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

            tbl2.setItems(data);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
