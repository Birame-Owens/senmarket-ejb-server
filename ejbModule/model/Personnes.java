package model;
// Generated 1 juil. 2025, 15:15:57 by Hibernate Tools 6.5.1.Final


import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Personnes generated by hbm2java
 */
public class Personnes  implements java.io.Serializable {


     private int id;
     private ProfilsUtilisateur profilsUtilisateur;
     private Pays pays;
     private Serializable uuid;
     private String prenom;
     private String nom;
     private String nomComplet;
     private String email;
     private String telephone;
     private Date dateNaissance;
     private String genre;
     private String photoProfilUrl;
     private String adresseLigne1;
     private String adresseLigne2;
     private String ville;
     private String region;
     private String codePostal;
     private Serializable coordonneesGps;
     private String languePreferee;
     private String fuseauHoraire;
     private Serializable preferences;
     private Serializable metadonnees;
     private Boolean estActif;
     private Timestamp creeLe;
     private Timestamp modifieLe;
     private Set comptesUtilisateurs = new HashSet(0);
     private Set clientses = new HashSet(0);
     private Set boutiqueses = new HashSet(0);
     private Set employeses = new HashSet(0);

    public Personnes() {
    }

	
    public Personnes(int id, ProfilsUtilisateur profilsUtilisateur, String prenom, String nom) {
        this.id = id;
        this.profilsUtilisateur = profilsUtilisateur;
        this.prenom = prenom;
        this.nom = nom;
    }
    public Personnes(int id, ProfilsUtilisateur profilsUtilisateur, Pays pays, Serializable uuid, String prenom, String nom, String nomComplet, String email, String telephone, Date dateNaissance, String genre, String photoProfilUrl, String adresseLigne1, String adresseLigne2, String ville, String region, String codePostal, Serializable coordonneesGps, String languePreferee, String fuseauHoraire, Serializable preferences, Serializable metadonnees, Boolean estActif, Timestamp creeLe, Timestamp modifieLe, Set comptesUtilisateurs, Set clientses, Set boutiqueses, Set employeses) {
       this.id = id;
       this.profilsUtilisateur = profilsUtilisateur;
       this.pays = pays;
       this.uuid = uuid;
       this.prenom = prenom;
       this.nom = nom;
       this.nomComplet = nomComplet;
       this.email = email;
       this.telephone = telephone;
       this.dateNaissance = dateNaissance;
       this.genre = genre;
       this.photoProfilUrl = photoProfilUrl;
       this.adresseLigne1 = adresseLigne1;
       this.adresseLigne2 = adresseLigne2;
       this.ville = ville;
       this.region = region;
       this.codePostal = codePostal;
       this.coordonneesGps = coordonneesGps;
       this.languePreferee = languePreferee;
       this.fuseauHoraire = fuseauHoraire;
       this.preferences = preferences;
       this.metadonnees = metadonnees;
       this.estActif = estActif;
       this.creeLe = creeLe;
       this.modifieLe = modifieLe;
       this.comptesUtilisateurs = comptesUtilisateurs;
       this.clientses = clientses;
       this.boutiqueses = boutiqueses;
       this.employeses = employeses;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public ProfilsUtilisateur getProfilsUtilisateur() {
        return this.profilsUtilisateur;
    }
    
    public void setProfilsUtilisateur(ProfilsUtilisateur profilsUtilisateur) {
        this.profilsUtilisateur = profilsUtilisateur;
    }
    public Pays getPays() {
        return this.pays;
    }
    
    public void setPays(Pays pays) {
        this.pays = pays;
    }
    public Serializable getUuid() {
        return this.uuid;
    }
    
    public void setUuid(Serializable uuid) {
        this.uuid = uuid;
    }
    public String getPrenom() {
        return this.prenom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getNom() {
        return this.nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getNomComplet() {
        return this.nomComplet;
    }
    
    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelephone() {
        return this.telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public Date getDateNaissance() {
        return this.dateNaissance;
    }
    
    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
    public String getGenre() {
        return this.genre;
    }
    
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public String getPhotoProfilUrl() {
        return this.photoProfilUrl;
    }
    
    public void setPhotoProfilUrl(String photoProfilUrl) {
        this.photoProfilUrl = photoProfilUrl;
    }
    public String getAdresseLigne1() {
        return this.adresseLigne1;
    }
    
    public void setAdresseLigne1(String adresseLigne1) {
        this.adresseLigne1 = adresseLigne1;
    }
    public String getAdresseLigne2() {
        return this.adresseLigne2;
    }
    
    public void setAdresseLigne2(String adresseLigne2) {
        this.adresseLigne2 = adresseLigne2;
    }
    public String getVille() {
        return this.ville;
    }
    
    public void setVille(String ville) {
        this.ville = ville;
    }
    public String getRegion() {
        return this.region;
    }
    
    public void setRegion(String region) {
        this.region = region;
    }
    public String getCodePostal() {
        return this.codePostal;
    }
    
    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }
    public Serializable getCoordonneesGps() {
        return this.coordonneesGps;
    }
    
    public void setCoordonneesGps(Serializable coordonneesGps) {
        this.coordonneesGps = coordonneesGps;
    }
    public String getLanguePreferee() {
        return this.languePreferee;
    }
    
    public void setLanguePreferee(String languePreferee) {
        this.languePreferee = languePreferee;
    }
    public String getFuseauHoraire() {
        return this.fuseauHoraire;
    }
    
    public void setFuseauHoraire(String fuseauHoraire) {
        this.fuseauHoraire = fuseauHoraire;
    }
    public Serializable getPreferences() {
        return this.preferences;
    }
    
    public void setPreferences(Serializable preferences) {
        this.preferences = preferences;
    }
    public Serializable getMetadonnees() {
        return this.metadonnees;
    }
    
    public void setMetadonnees(Serializable metadonnees) {
        this.metadonnees = metadonnees;
    }
    public Boolean getEstActif() {
        return this.estActif;
    }
    
    public void setEstActif(Boolean estActif) {
        this.estActif = estActif;
    }
    public Timestamp getCreeLe() {
        return this.creeLe;
    }
    
    public void setCreeLe(Timestamp creeLe) {
        this.creeLe = creeLe;
    }
    public Timestamp getModifieLe() {
        return this.modifieLe;
    }
    
    public void setModifieLe(Timestamp modifieLe) {
        this.modifieLe = modifieLe;
    }
    public Set getComptesUtilisateurs() {
        return this.comptesUtilisateurs;
    }
    
    public void setComptesUtilisateurs(Set comptesUtilisateurs) {
        this.comptesUtilisateurs = comptesUtilisateurs;
    }
    public Set getClientses() {
        return this.clientses;
    }
    
    public void setClientses(Set clientses) {
        this.clientses = clientses;
    }
    public Set getBoutiqueses() {
        return this.boutiqueses;
    }
    
    public void setBoutiqueses(Set boutiqueses) {
        this.boutiqueses = boutiqueses;
    }
    public Set getEmployeses() {
        return this.employeses;
    }
    
    public void setEmployeses(Set employeses) {
        this.employeses = employeses;
    }




}


