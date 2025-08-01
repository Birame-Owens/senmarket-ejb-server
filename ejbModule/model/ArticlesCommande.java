package model;
// Generated 1 juil. 2025, 15:15:57 by Hibernate Tools 6.5.1.Final


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * ArticlesCommande generated by hbm2java
 */
public class ArticlesCommande  implements java.io.Serializable {


     private int id;
     private Produits produits;
     private Commandes commandes;
     private String nomProduit;
     private String skuProduit;
     private int quantite;
     private BigDecimal prixUnitaire;
     private BigDecimal prixTotal;
     private BigDecimal tauxTva;
     private Serializable variationsCommandees;
     private Serializable personnalisations;
     private String statutArticle;
     private Integer quantiteLivree;
     private Timestamp creeLe;

    public ArticlesCommande() {
    }

	
    public ArticlesCommande(int id, Produits produits, Commandes commandes, String nomProduit, int quantite, BigDecimal prixUnitaire, BigDecimal prixTotal) {
        this.id = id;
        this.produits = produits;
        this.commandes = commandes;
        this.nomProduit = nomProduit;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.prixTotal = prixTotal;
    }
    public ArticlesCommande(int id, Produits produits, Commandes commandes, String nomProduit, String skuProduit, int quantite, BigDecimal prixUnitaire, BigDecimal prixTotal, BigDecimal tauxTva, Serializable variationsCommandees, Serializable personnalisations, String statutArticle, Integer quantiteLivree, Timestamp creeLe) {
       this.id = id;
       this.produits = produits;
       this.commandes = commandes;
       this.nomProduit = nomProduit;
       this.skuProduit = skuProduit;
       this.quantite = quantite;
       this.prixUnitaire = prixUnitaire;
       this.prixTotal = prixTotal;
       this.tauxTva = tauxTva;
       this.variationsCommandees = variationsCommandees;
       this.personnalisations = personnalisations;
       this.statutArticle = statutArticle;
       this.quantiteLivree = quantiteLivree;
       this.creeLe = creeLe;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public Produits getProduits() {
        return this.produits;
    }
    
    public void setProduits(Produits produits) {
        this.produits = produits;
    }
    public Commandes getCommandes() {
        return this.commandes;
    }
    
    public void setCommandes(Commandes commandes) {
        this.commandes = commandes;
    }
    public String getNomProduit() {
        return this.nomProduit;
    }
    
    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }
    public String getSkuProduit() {
        return this.skuProduit;
    }
    
    public void setSkuProduit(String skuProduit) {
        this.skuProduit = skuProduit;
    }
    public int getQuantite() {
        return this.quantite;
    }
    
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
    public BigDecimal getPrixUnitaire() {
        return this.prixUnitaire;
    }
    
    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
    public BigDecimal getPrixTotal() {
        return this.prixTotal;
    }
    
    public void setPrixTotal(BigDecimal prixTotal) {
        this.prixTotal = prixTotal;
    }
    public BigDecimal getTauxTva() {
        return this.tauxTva;
    }
    
    public void setTauxTva(BigDecimal tauxTva) {
        this.tauxTva = tauxTva;
    }
    public Serializable getVariationsCommandees() {
        return this.variationsCommandees;
    }
    
    public void setVariationsCommandees(Serializable variationsCommandees) {
        this.variationsCommandees = variationsCommandees;
    }
    public Serializable getPersonnalisations() {
        return this.personnalisations;
    }
    
    public void setPersonnalisations(Serializable personnalisations) {
        this.personnalisations = personnalisations;
    }
    public String getStatutArticle() {
        return this.statutArticle;
    }
    
    public void setStatutArticle(String statutArticle) {
        this.statutArticle = statutArticle;
    }
    public Integer getQuantiteLivree() {
        return this.quantiteLivree;
    }
    
    public void setQuantiteLivree(Integer quantiteLivree) {
        this.quantiteLivree = quantiteLivree;
    }
    public Timestamp getCreeLe() {
        return this.creeLe;
    }
    
    public void setCreeLe(Timestamp creeLe) {
        this.creeLe = creeLe;
    }




}


