package model;
// Generated 1 juil. 2025, 15:15:57 by Hibernate Tools 6.5.1.Final


import java.io.Serializable;
import java.sql.Timestamp;

/**
 * SessionsActives generated by hbm2java
 */
public class SessionsActives  implements java.io.Serializable {


     private int id;
     private ComptesUtilisateur comptesUtilisateur;
     private String tokenSession;
     private String tokenRefresh;
     private Timestamp expireLe;
     private Timestamp derniereActivite;
     private Serializable adresseIp;
     private String userAgent;
     private String empreinteAppareil;
     private String nomAppareil;
     private String pays;
     private String ville;
     private Serializable coordonneesGps;
     private Serializable metadonnees;
     private Boolean estActif;
     private Timestamp creeLe;

    public SessionsActives() {
    }

	
    public SessionsActives(int id, ComptesUtilisateur comptesUtilisateur, String tokenSession, Timestamp expireLe) {
        this.id = id;
        this.comptesUtilisateur = comptesUtilisateur;
        this.tokenSession = tokenSession;
        this.expireLe = expireLe;
    }
    public SessionsActives(int id, ComptesUtilisateur comptesUtilisateur, String tokenSession, String tokenRefresh, Timestamp expireLe, Timestamp derniereActivite, Serializable adresseIp, String userAgent, String empreinteAppareil, String nomAppareil, String pays, String ville, Serializable coordonneesGps, Serializable metadonnees, Boolean estActif, Timestamp creeLe) {
       this.id = id;
       this.comptesUtilisateur = comptesUtilisateur;
       this.tokenSession = tokenSession;
       this.tokenRefresh = tokenRefresh;
       this.expireLe = expireLe;
       this.derniereActivite = derniereActivite;
       this.adresseIp = adresseIp;
       this.userAgent = userAgent;
       this.empreinteAppareil = empreinteAppareil;
       this.nomAppareil = nomAppareil;
       this.pays = pays;
       this.ville = ville;
       this.coordonneesGps = coordonneesGps;
       this.metadonnees = metadonnees;
       this.estActif = estActif;
       this.creeLe = creeLe;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public ComptesUtilisateur getComptesUtilisateur() {
        return this.comptesUtilisateur;
    }
    
    public void setComptesUtilisateur(ComptesUtilisateur comptesUtilisateur) {
        this.comptesUtilisateur = comptesUtilisateur;
    }
    public String getTokenSession() {
        return this.tokenSession;
    }
    
    public void setTokenSession(String tokenSession) {
        this.tokenSession = tokenSession;
    }
    public String getTokenRefresh() {
        return this.tokenRefresh;
    }
    
    public void setTokenRefresh(String tokenRefresh) {
        this.tokenRefresh = tokenRefresh;
    }
    public Timestamp getExpireLe() {
        return this.expireLe;
    }
    
    public void setExpireLe(Timestamp expireLe) {
        this.expireLe = expireLe;
    }
    public Timestamp getDerniereActivite() {
        return this.derniereActivite;
    }
    
    public void setDerniereActivite(Timestamp derniereActivite) {
        this.derniereActivite = derniereActivite;
    }
    public Serializable getAdresseIp() {
        return this.adresseIp;
    }
    
    public void setAdresseIp(Serializable adresseIp) {
        this.adresseIp = adresseIp;
    }
    public String getUserAgent() {
        return this.userAgent;
    }
    
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    public String getEmpreinteAppareil() {
        return this.empreinteAppareil;
    }
    
    public void setEmpreinteAppareil(String empreinteAppareil) {
        this.empreinteAppareil = empreinteAppareil;
    }
    public String getNomAppareil() {
        return this.nomAppareil;
    }
    
    public void setNomAppareil(String nomAppareil) {
        this.nomAppareil = nomAppareil;
    }
    public String getPays() {
        return this.pays;
    }
    
    public void setPays(String pays) {
        this.pays = pays;
    }
    public String getVille() {
        return this.ville;
    }
    
    public void setVille(String ville) {
        this.ville = ville;
    }
    public Serializable getCoordonneesGps() {
        return this.coordonneesGps;
    }
    
    public void setCoordonneesGps(Serializable coordonneesGps) {
        this.coordonneesGps = coordonneesGps;
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




}


