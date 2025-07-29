package authentification.dto.common;



import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DTO contenant les informations essentielles d'un utilisateur
 * Utilisé pour transférer les données utilisateur sans exposer les détails sensibles
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public class UserInfo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String uuid;
    private String prenom;
    private String nom;
    private String nomComplet;
    private String email;
    private String telephone;
    private String genre;
    private String photoProfilUrl;
    private String ville;
    private String region;
    private String languePreferee;
    
    // Informations du profil
    private String profilCode;
    private String profilNom;
    private Integer niveauAcces;
    
    // Statut du compte
    private Boolean estActif;
    private Boolean estVerifie;
    private Timestamp derniereConnexion;
    private Timestamp dateInscription;
    
    // Constructeurs
    public UserInfo() {
    }
    
    public UserInfo(Integer id, String prenom, String nom, String email) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.nomComplet = prenom + " " + nom;
    }
    
    /**
     * Constructeur à partir d'une entité Personnes
     */
    public static UserInfo fromPersonnes(model.utilisateur.Personnes personne) {
        if (personne == null) return null;
        
        UserInfo userInfo = new UserInfo();
        userInfo.setId(personne.getId());
        userInfo.setUuid(personne.getUuid() != null ? personne.getUuid().toString() : null);
        userInfo.setPrenom(personne.getPrenom());
        userInfo.setNom(personne.getNom());
        userInfo.setNomComplet(personne.getNomComplet());
        userInfo.setEmail(personne.getEmail());
        userInfo.setTelephone(personne.getTelephone());
        userInfo.setGenre(personne.getGenre());
        userInfo.setPhotoProfilUrl(personne.getPhotoProfilUrl());
        userInfo.setVille(personne.getVille());
        userInfo.setRegion(personne.getRegion());
        userInfo.setLanguePreferee(personne.getLanguePreferee());
        userInfo.setEstActif(personne.getEstActif());
        userInfo.setDateInscription(personne.getCreeLe());
        
        // Informations du profil
        if (personne.getProfilsUtilisateur() != null) {
            userInfo.setProfilCode(personne.getProfilsUtilisateur().getCode());
            userInfo.setProfilNom(personne.getProfilsUtilisateur().getNom());
            userInfo.setNiveauAcces(personne.getProfilsUtilisateur().getNiveauAcces());
        }
        
        return userInfo;
    }
    
    // Méthodes utilitaires
    public boolean isAdmin() {
        return niveauAcces != null && niveauAcces >= 4;
    }
    
    public boolean canAccess(int requiredLevel) {
        return niveauAcces != null && niveauAcces >= requiredLevel;
    }
    
    public String getNomCompletCalcule() {
        if (nomComplet != null && !nomComplet.trim().isEmpty()) {
            return nomComplet;
        }
        if (prenom != null && nom != null) {
            return prenom + " " + nom;
        }
        return email; // Fallback
    }
    
    public String getInitiales() {
        StringBuilder initiales = new StringBuilder();
        if (prenom != null && !prenom.isEmpty()) {
            initiales.append(prenom.charAt(0));
        }
        if (nom != null && !nom.isEmpty()) {
            initiales.append(nom.charAt(0));
        }
        return initiales.toString().toUpperCase();
    }
    
    // Getters et Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getUuid() {
        return uuid;
    }
    
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getNomComplet() {
        return nomComplet;
    }
    
    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public String getPhotoProfilUrl() {
        return photoProfilUrl;
    }
    
    public void setPhotoProfilUrl(String photoProfilUrl) {
        this.photoProfilUrl = photoProfilUrl;
    }
    
    public String getVille() {
        return ville;
    }
    
    public void setVille(String ville) {
        this.ville = ville;
    }
    
    public String getRegion() {
        return region;
    }
    
    public void setRegion(String region) {
        this.region = region;
    }
    
    public String getLanguePreferee() {
        return languePreferee;
    }
    
    public void setLanguePreferee(String languePreferee) {
        this.languePreferee = languePreferee;
    }
    
    public String getProfilCode() {
        return profilCode;
    }
    
    public void setProfilCode(String profilCode) {
        this.profilCode = profilCode;
    }
    
    public String getProfilNom() {
        return profilNom;
    }
    
    public void setProfilNom(String profilNom) {
        this.profilNom = profilNom;
    }
    
    public Integer getNiveauAcces() {
        return niveauAcces;
    }
    
    public void setNiveauAcces(Integer niveauAcces) {
        this.niveauAcces = niveauAcces;
    }
    
    public Boolean getEstActif() {
        return estActif;
    }
    
    public void setEstActif(Boolean estActif) {
        this.estActif = estActif;
    }
    
    public Boolean getEstVerifie() {
        return estVerifie;
    }
    
    public void setEstVerifie(Boolean estVerifie) {
        this.estVerifie = estVerifie;
    }
    
    public Timestamp getDerniereConnexion() {
        return derniereConnexion;
    }
    
    public void setDerniereConnexion(Timestamp derniereConnexion) {
        this.derniereConnexion = derniereConnexion;
    }
    
    public Timestamp getDateInscription() {
        return dateInscription;
    }
    
    public void setDateInscription(Timestamp dateInscription) {
        this.dateInscription = dateInscription;
    }
    
    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", nomComplet='" + nomComplet + '\'' +
                ", email='" + email + '\'' +
                ", profilCode='" + profilCode + '\'' +
                ", niveauAcces=" + niveauAcces +
                '}';
    }
}