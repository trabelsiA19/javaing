package models;
// Generated Aug 23, 2019 9:15:04 PM by Hibernate Tools 4.3.1



/**
 * Etudiant generated by hbm2java
 */
public class Etudiant extends Personne  implements java.io.Serializable {


     private Specialitee specialitee;
     private String dateins;
     private String payment;
     private int cin;

    public Etudiant(Integer id, Specialitee specialitee, String dateins, String payment, int cin, String nom, String prenom, String email, String telephone, String genere) {
        super(id, nom, prenom, email, telephone, genere);
   
        this.specialitee = specialitee;
        this.dateins = dateins;
        this.payment = payment;
        this.cin = cin;
    }

    public Etudiant(Integer id, String nom, String prenom, String email, String telephone, String genere) {
        super(id, nom, prenom, email, telephone, genere);
    }


     
     

    public Specialitee getSpecialitee() {
        return this.specialitee;
    }
    
    public void setSpecialitee(Specialitee specialitee) {
        this.specialitee = specialitee;
    }


    public String getDateins() {
        return this.dateins;
    }
    
    public void setDateins(String dateins) {
        this.dateins = dateins;
    }
    public String getPayment() {
        return this.payment;
    }
    
    public void setPayment(String payment) {
        this.payment = payment;
    }
    public int getCin() {
        return this.cin;
    }
    
    public void setCin(int cin) {
        this.cin = cin;
    }

    @Override
    public double calculeSalaire(String grade, int nbh) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 



}

