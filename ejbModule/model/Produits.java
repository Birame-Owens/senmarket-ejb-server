package model;
// Generated 1 juil. 2025, 15:15:57 by Hibernate Tools 6.5.1.Final


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Produits generated by hbm2java
 */
public class Produits  implements java.io.Serializable {


     private int id;
     private Marques marques;
     private Produits produits;
     private Boutiques boutiques;
     private CategoriesProduits categoriesProduits;
     private Devises devises;
     private Serializable uuid;
     private String nom;
     private String description;
     private String descriptionCourte;
     private String sku;
     private String codeBarres;
     private String qrCode;
     private BigDecimal prixAchat;
     private BigDecimal prixVente;
     private BigDecimal prixPromo;
     private BigDecimal tauxTva;
     private Integer stockActuel;
     private Integer stockMinimum;
     private Integer stockMaximum;
     private String uniteMesure;
     private BigDecimal poids;
     private Serializable dimensions;
     private Serializable couleursDisponibles;
     private Serializable taillesDisponibles;
     private Serializable imagesUrls;
     private String imagePrincipaleUrl;
     private Serializable videosUrls;
     private Serializable documentsUrls;
     private Serializable motsCles;
     private Serializable tags;
     private Serializable contenuRecherche;
     private Boolean AVariations;
     private Serializable attributsVariation;
     private Date dateCreationProduit;
     private Date dateExpiration;
     private Date datePeremption;
     private BigDecimal noteMoyenne;
     private Integer nombreEvaluations;
     private Integer nombreVues;
     private Integer nombreVentes;
     private Boolean estNumerique;
     private Boolean necessiteLivraison;
     private Boolean estFragile;
     private String instructionsLivraison;
     private Serializable metadonnees;
     private Boolean estActif;
     private Boolean estEnStock;
     private Boolean estPublie;
     private Boolean estEnPromotion;
     private Timestamp creeLe;
     private Timestamp modifieLe;
     private Set historiqueRechercheses = new HashSet(0);
     private Set mouvementsStocks = new HashSet(0);
     private Set articlesCommandes = new HashSet(0);
     private Set evaluationsProduitses = new HashSet(0);
     private Set evenementsAnalyticses = new HashSet(0);
     private Set produitses = new HashSet(0);
     private Set articlesPaniers = new HashSet(0);
     private Set attributsProduitses = new HashSet(0);

    public Produits() {
    }

	
    public Produits(int id, Boutiques boutiques, CategoriesProduits categoriesProduits, String nom, BigDecimal prixVente) {
        this.id = id;
        this.boutiques = boutiques;
        this.categoriesProduits = categoriesProduits;
        this.nom = nom;
        this.prixVente = prixVente;
    }
    public Produits(int id, Marques marques, Produits produits, Boutiques boutiques, CategoriesProduits categoriesProduits, Devises devises, Serializable uuid, String nom, String description, String descriptionCourte, String sku, String codeBarres, String qrCode, BigDecimal prixAchat, BigDecimal prixVente, BigDecimal prixPromo, BigDecimal tauxTva, Integer stockActuel, Integer stockMinimum, Integer stockMaximum, String uniteMesure, BigDecimal poids, Serializable dimensions, Serializable couleursDisponibles, Serializable taillesDisponibles, Serializable imagesUrls, String imagePrincipaleUrl, Serializable videosUrls, Serializable documentsUrls, Serializable motsCles, Serializable tags, Serializable contenuRecherche, Boolean AVariations, Serializable attributsVariation, Date dateCreationProduit, Date dateExpiration, Date datePeremption, BigDecimal noteMoyenne, Integer nombreEvaluations, Integer nombreVues, Integer nombreVentes, Boolean estNumerique, Boolean necessiteLivraison, Boolean estFragile, String instructionsLivraison, Serializable metadonnees, Boolean estActif, Boolean estEnStock, Boolean estPublie, Boolean estEnPromotion, Timestamp creeLe, Timestamp modifieLe, Set historiqueRechercheses, Set mouvementsStocks, Set articlesCommandes, Set evaluationsProduitses, Set evenementsAnalyticses, Set produitses, Set articlesPaniers, Set attributsProduitses) {
       this.id = id;
       this.marques = marques;
       this.produits = produits;
       this.boutiques = boutiques;
       this.categoriesProduits = categoriesProduits;
       this.devises = devises;
       this.uuid = uuid;
       this.nom = nom;
       this.description = description;
       this.descriptionCourte = descriptionCourte;
       this.sku = sku;
       this.codeBarres = codeBarres;
       this.qrCode = qrCode;
       this.prixAchat = prixAchat;
       this.prixVente = prixVente;
       this.prixPromo = prixPromo;
       this.tauxTva = tauxTva;
       this.stockActuel = stockActuel;
       this.stockMinimum = stockMinimum;
       this.stockMaximum = stockMaximum;
       this.uniteMesure = uniteMesure;
       this.poids = poids;
       this.dimensions = dimensions;
       this.couleursDisponibles = couleursDisponibles;
       this.taillesDisponibles = taillesDisponibles;
       this.imagesUrls = imagesUrls;
       this.imagePrincipaleUrl = imagePrincipaleUrl;
       this.videosUrls = videosUrls;
       this.documentsUrls = documentsUrls;
       this.motsCles = motsCles;
       this.tags = tags;
       this.contenuRecherche = contenuRecherche;
       this.AVariations = AVariations;
       this.attributsVariation = attributsVariation;
       this.dateCreationProduit = dateCreationProduit;
       this.dateExpiration = dateExpiration;
       this.datePeremption = datePeremption;
       this.noteMoyenne = noteMoyenne;
       this.nombreEvaluations = nombreEvaluations;
       this.nombreVues = nombreVues;
       this.nombreVentes = nombreVentes;
       this.estNumerique = estNumerique;
       this.necessiteLivraison = necessiteLivraison;
       this.estFragile = estFragile;
       this.instructionsLivraison = instructionsLivraison;
       this.metadonnees = metadonnees;
       this.estActif = estActif;
       this.estEnStock = estEnStock;
       this.estPublie = estPublie;
       this.estEnPromotion = estEnPromotion;
       this.creeLe = creeLe;
       this.modifieLe = modifieLe;
       this.historiqueRechercheses = historiqueRechercheses;
       this.mouvementsStocks = mouvementsStocks;
       this.articlesCommandes = articlesCommandes;
       this.evaluationsProduitses = evaluationsProduitses;
       this.evenementsAnalyticses = evenementsAnalyticses;
       this.produitses = produitses;
       this.articlesPaniers = articlesPaniers;
       this.attributsProduitses = attributsProduitses;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public Marques getMarques() {
        return this.marques;
    }
    
    public void setMarques(Marques marques) {
        this.marques = marques;
    }
    public Produits getProduits() {
        return this.produits;
    }
    
    public void setProduits(Produits produits) {
        this.produits = produits;
    }
    public Boutiques getBoutiques() {
        return this.boutiques;
    }
    
    public void setBoutiques(Boutiques boutiques) {
        this.boutiques = boutiques;
    }
    public CategoriesProduits getCategoriesProduits() {
        return this.categoriesProduits;
    }
    
    public void setCategoriesProduits(CategoriesProduits categoriesProduits) {
        this.categoriesProduits = categoriesProduits;
    }
    public Devises getDevises() {
        return this.devises;
    }
    
    public void setDevises(Devises devises) {
        this.devises = devises;
    }
    public Serializable getUuid() {
        return this.uuid;
    }
    
    public void setUuid(Serializable uuid) {
        this.uuid = uuid;
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
    public String getDescriptionCourte() {
        return this.descriptionCourte;
    }
    
    public void setDescriptionCourte(String descriptionCourte) {
        this.descriptionCourte = descriptionCourte;
    }
    public String getSku() {
        return this.sku;
    }
    
    public void setSku(String sku) {
        this.sku = sku;
    }
    public String getCodeBarres() {
        return this.codeBarres;
    }
    
    public void setCodeBarres(String codeBarres) {
        this.codeBarres = codeBarres;
    }
    public String getQrCode() {
        return this.qrCode;
    }
    
    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
    public BigDecimal getPrixAchat() {
        return this.prixAchat;
    }
    
    public void setPrixAchat(BigDecimal prixAchat) {
        this.prixAchat = prixAchat;
    }
    public BigDecimal getPrixVente() {
        return this.prixVente;
    }
    
    public void setPrixVente(BigDecimal prixVente) {
        this.prixVente = prixVente;
    }
    public BigDecimal getPrixPromo() {
        return this.prixPromo;
    }
    
    public void setPrixPromo(BigDecimal prixPromo) {
        this.prixPromo = prixPromo;
    }
    public BigDecimal getTauxTva() {
        return this.tauxTva;
    }
    
    public void setTauxTva(BigDecimal tauxTva) {
        this.tauxTva = tauxTva;
    }
    public Integer getStockActuel() {
        return this.stockActuel;
    }
    
    public void setStockActuel(Integer stockActuel) {
        this.stockActuel = stockActuel;
    }
    public Integer getStockMinimum() {
        return this.stockMinimum;
    }
    
    public void setStockMinimum(Integer stockMinimum) {
        this.stockMinimum = stockMinimum;
    }
    public Integer getStockMaximum() {
        return this.stockMaximum;
    }
    
    public void setStockMaximum(Integer stockMaximum) {
        this.stockMaximum = stockMaximum;
    }
    public String getUniteMesure() {
        return this.uniteMesure;
    }
    
    public void setUniteMesure(String uniteMesure) {
        this.uniteMesure = uniteMesure;
    }
    public BigDecimal getPoids() {
        return this.poids;
    }
    
    public void setPoids(BigDecimal poids) {
        this.poids = poids;
    }
    public Serializable getDimensions() {
        return this.dimensions;
    }
    
    public void setDimensions(Serializable dimensions) {
        this.dimensions = dimensions;
    }
    public Serializable getCouleursDisponibles() {
        return this.couleursDisponibles;
    }
    
    public void setCouleursDisponibles(Serializable couleursDisponibles) {
        this.couleursDisponibles = couleursDisponibles;
    }
    public Serializable getTaillesDisponibles() {
        return this.taillesDisponibles;
    }
    
    public void setTaillesDisponibles(Serializable taillesDisponibles) {
        this.taillesDisponibles = taillesDisponibles;
    }
    public Serializable getImagesUrls() {
        return this.imagesUrls;
    }
    
    public void setImagesUrls(Serializable imagesUrls) {
        this.imagesUrls = imagesUrls;
    }
    public String getImagePrincipaleUrl() {
        return this.imagePrincipaleUrl;
    }
    
    public void setImagePrincipaleUrl(String imagePrincipaleUrl) {
        this.imagePrincipaleUrl = imagePrincipaleUrl;
    }
    public Serializable getVideosUrls() {
        return this.videosUrls;
    }
    
    public void setVideosUrls(Serializable videosUrls) {
        this.videosUrls = videosUrls;
    }
    public Serializable getDocumentsUrls() {
        return this.documentsUrls;
    }
    
    public void setDocumentsUrls(Serializable documentsUrls) {
        this.documentsUrls = documentsUrls;
    }
    public Serializable getMotsCles() {
        return this.motsCles;
    }
    
    public void setMotsCles(Serializable motsCles) {
        this.motsCles = motsCles;
    }
    public Serializable getTags() {
        return this.tags;
    }
    
    public void setTags(Serializable tags) {
        this.tags = tags;
    }
    public Serializable getContenuRecherche() {
        return this.contenuRecherche;
    }
    
    public void setContenuRecherche(Serializable contenuRecherche) {
        this.contenuRecherche = contenuRecherche;
    }
    public Boolean getAVariations() {
        return this.AVariations;
    }
    
    public void setAVariations(Boolean AVariations) {
        this.AVariations = AVariations;
    }
    public Serializable getAttributsVariation() {
        return this.attributsVariation;
    }
    
    public void setAttributsVariation(Serializable attributsVariation) {
        this.attributsVariation = attributsVariation;
    }
    public Date getDateCreationProduit() {
        return this.dateCreationProduit;
    }
    
    public void setDateCreationProduit(Date dateCreationProduit) {
        this.dateCreationProduit = dateCreationProduit;
    }
    public Date getDateExpiration() {
        return this.dateExpiration;
    }
    
    public void setDateExpiration(Date dateExpiration) {
        this.dateExpiration = dateExpiration;
    }
    public Date getDatePeremption() {
        return this.datePeremption;
    }
    
    public void setDatePeremption(Date datePeremption) {
        this.datePeremption = datePeremption;
    }
    public BigDecimal getNoteMoyenne() {
        return this.noteMoyenne;
    }
    
    public void setNoteMoyenne(BigDecimal noteMoyenne) {
        this.noteMoyenne = noteMoyenne;
    }
    public Integer getNombreEvaluations() {
        return this.nombreEvaluations;
    }
    
    public void setNombreEvaluations(Integer nombreEvaluations) {
        this.nombreEvaluations = nombreEvaluations;
    }
    public Integer getNombreVues() {
        return this.nombreVues;
    }
    
    public void setNombreVues(Integer nombreVues) {
        this.nombreVues = nombreVues;
    }
    public Integer getNombreVentes() {
        return this.nombreVentes;
    }
    
    public void setNombreVentes(Integer nombreVentes) {
        this.nombreVentes = nombreVentes;
    }
    public Boolean getEstNumerique() {
        return this.estNumerique;
    }
    
    public void setEstNumerique(Boolean estNumerique) {
        this.estNumerique = estNumerique;
    }
    public Boolean getNecessiteLivraison() {
        return this.necessiteLivraison;
    }
    
    public void setNecessiteLivraison(Boolean necessiteLivraison) {
        this.necessiteLivraison = necessiteLivraison;
    }
    public Boolean getEstFragile() {
        return this.estFragile;
    }
    
    public void setEstFragile(Boolean estFragile) {
        this.estFragile = estFragile;
    }
    public String getInstructionsLivraison() {
        return this.instructionsLivraison;
    }
    
    public void setInstructionsLivraison(String instructionsLivraison) {
        this.instructionsLivraison = instructionsLivraison;
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
    public Boolean getEstEnStock() {
        return this.estEnStock;
    }
    
    public void setEstEnStock(Boolean estEnStock) {
        this.estEnStock = estEnStock;
    }
    public Boolean getEstPublie() {
        return this.estPublie;
    }
    
    public void setEstPublie(Boolean estPublie) {
        this.estPublie = estPublie;
    }
    public Boolean getEstEnPromotion() {
        return this.estEnPromotion;
    }
    
    public void setEstEnPromotion(Boolean estEnPromotion) {
        this.estEnPromotion = estEnPromotion;
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
    public Set getHistoriqueRechercheses() {
        return this.historiqueRechercheses;
    }
    
    public void setHistoriqueRechercheses(Set historiqueRechercheses) {
        this.historiqueRechercheses = historiqueRechercheses;
    }
    public Set getMouvementsStocks() {
        return this.mouvementsStocks;
    }
    
    public void setMouvementsStocks(Set mouvementsStocks) {
        this.mouvementsStocks = mouvementsStocks;
    }
    public Set getArticlesCommandes() {
        return this.articlesCommandes;
    }
    
    public void setArticlesCommandes(Set articlesCommandes) {
        this.articlesCommandes = articlesCommandes;
    }
    public Set getEvaluationsProduitses() {
        return this.evaluationsProduitses;
    }
    
    public void setEvaluationsProduitses(Set evaluationsProduitses) {
        this.evaluationsProduitses = evaluationsProduitses;
    }
    public Set getEvenementsAnalyticses() {
        return this.evenementsAnalyticses;
    }
    
    public void setEvenementsAnalyticses(Set evenementsAnalyticses) {
        this.evenementsAnalyticses = evenementsAnalyticses;
    }
    public Set getProduitses() {
        return this.produitses;
    }
    
    public void setProduitses(Set produitses) {
        this.produitses = produitses;
    }
    public Set getArticlesPaniers() {
        return this.articlesPaniers;
    }
    
    public void setArticlesPaniers(Set articlesPaniers) {
        this.articlesPaniers = articlesPaniers;
    }
    public Set getAttributsProduitses() {
        return this.attributsProduitses;
    }
    
    public void setAttributsProduitses(Set attributsProduitses) {
        this.attributsProduitses = attributsProduitses;
    }




}


