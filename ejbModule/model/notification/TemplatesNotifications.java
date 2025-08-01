package model.notification;
// Generated 1 juil. 2025, 15:15:57 by Hibernate Tools 6.5.1.Final


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import model.utilisateur.ComptesUtilisateur;

/**
 * TemplatesNotifications generated by hbm2java
 */
public class TemplatesNotifications  implements java.io.Serializable {


     private int id;
     private Integer version;
     private ComptesUtilisateur comptesUtilisateur;
     private String code;
     private String nom;
     private String description;
     private String typeNotification;
     private String evenementDeclencheur;
     private String sujet;
     private String contenuHtml;
     private String contenuTexte;
     private Serializable contenuPush;
     private Serializable variablesDisponibles;
     private String langue;
     private Boolean estActif;
     private Timestamp creeLe;
     private Timestamp modifieLe;
     private Set notificationsEnvoyeeses = new HashSet(0);

    public TemplatesNotifications() {
    }

	
    public TemplatesNotifications(int id, String code, String nom, String typeNotification, String evenementDeclencheur) {
        this.id = id;
        this.code = code;
        this.nom = nom;
        this.typeNotification = typeNotification;
        this.evenementDeclencheur = evenementDeclencheur;
    }
    public TemplatesNotifications(int id, ComptesUtilisateur comptesUtilisateur, String code, String nom, String description, String typeNotification, String evenementDeclencheur, String sujet, String contenuHtml, String contenuTexte, Serializable contenuPush, Serializable variablesDisponibles, String langue, Boolean estActif, Timestamp creeLe, Timestamp modifieLe, Set notificationsEnvoyeeses) {
       this.id = id;
       this.comptesUtilisateur = comptesUtilisateur;
       this.code = code;
       this.nom = nom;
       this.description = description;
       this.typeNotification = typeNotification;
       this.evenementDeclencheur = evenementDeclencheur;
       this.sujet = sujet;
       this.contenuHtml = contenuHtml;
       this.contenuTexte = contenuTexte;
       this.contenuPush = contenuPush;
       this.variablesDisponibles = variablesDisponibles;
       this.langue = langue;
       this.estActif = estActif;
       this.creeLe = creeLe;
       this.modifieLe = modifieLe;
       this.notificationsEnvoyeeses = notificationsEnvoyeeses;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public Integer getVersion() {
        return this.version;
    }
    
    public void setVersion(Integer version) {
        this.version = version;
    }
    public ComptesUtilisateur getComptesUtilisateur() {
        return this.comptesUtilisateur;
    }
    
    public void setComptesUtilisateur(ComptesUtilisateur comptesUtilisateur) {
        this.comptesUtilisateur = comptesUtilisateur;
    }
    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    public String getNom() {
        return this.nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    public String getTypeNotification() {
        return this.typeNotification;
    }
    
    public void setTypeNotification(String typeNotification) {
        this.typeNotification = typeNotification;
    }
    public String getEvenementDeclencheur() {
        return this.evenementDeclencheur;
    }
    
    public void setEvenementDeclencheur(String evenementDeclencheur) {
        this.evenementDeclencheur = evenementDeclencheur;
    }
    public String getSujet() {
        return this.sujet;
    }
    
    public void setSujet(String sujet) {
        this.sujet = sujet;
    }
    public String getContenuHtml() {
        return this.contenuHtml;
    }
    
    public void setContenuHtml(String contenuHtml) {
        this.contenuHtml = contenuHtml;
    }
    public String getContenuTexte() {
        return this.contenuTexte;
    }
    
    public void setContenuTexte(String contenuTexte) {
        this.contenuTexte = contenuTexte;
    }
    public Serializable getContenuPush() {
        return this.contenuPush;
    }
    
    public void setContenuPush(Serializable contenuPush) {
        this.contenuPush = contenuPush;
    }
    public Serializable getVariablesDisponibles() {
        return this.variablesDisponibles;
    }
    
    public void setVariablesDisponibles(Serializable variablesDisponibles) {
        this.variablesDisponibles = variablesDisponibles;
    }
    public String getLangue() {
        return this.langue;
    }
    
    public void setLangue(String langue) {
        this.langue = langue;
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
    public Set getNotificationsEnvoyeeses() {
        return this.notificationsEnvoyeeses;
    }
    
    public void setNotificationsEnvoyeeses(Set notificationsEnvoyeeses) {
        this.notificationsEnvoyeeses = notificationsEnvoyeeses;
    }




}


