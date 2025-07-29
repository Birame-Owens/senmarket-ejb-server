package authentification.base.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * DTO de base pour toutes les requêtes du module authentification
 * Contient les informations communes à toutes les requêtes
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public abstract class BaseRequest implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Identifiant unique de la requête (pour traçabilité)
     */
    private String requestId;
    
    /**
     * Timestamp de création de la requête
     */
    private Timestamp timestamp;
    
    /**
     * Langue de la requête (fr, en, wo, etc.)
     */
    private String langue = "fr";
    
    /**
     * Adresse IP du client
     */
    private String adresseIp;
    
    /**
     * User-Agent du navigateur/application
     */
    private String userAgent;
    
    /**
     * Empreinte unique de l'appareil
     */
    private String empreinteAppareil;
    
    /**
     * Type d'appareil (mobile, desktop, tablet)
     */
    private String typeAppareil;
    
    /**
     * Navigateur utilisé
     */
    private String navigateur;
    
    /**
     * Système d'exploitation
     */
    private String systemeExploitation;
    
    /**
     * Informations de géolocalisation
     */
    private String pays;
    private String ville;
    private String region;
    
    /**
     * Métadonnées additionnelles (extensible)
     */
    private Map<String, Object> metadonnees;
    
    /**
     * Canal d'origine de la requête (web, mobile, api, etc.)
     */
    private String canal = "web";
    
    /**
     * Version de l'application client
     */
    private String versionClient;
    
    // Constructeur
    public BaseRequest() {
        this.requestId = generateRequestId();
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.metadonnees = new HashMap<>();
    }
    
    /**
     * Génère un identifiant unique pour la requête
     */
    private String generateRequestId() {
        return "REQ_" + System.currentTimeMillis() + "_" + 
               Integer.toHexString((int) (Math.random() * 1000000));
    }
    
    /**
     * Ajoute une métadonnée
     */
    public void addMetadonnee(String cle, Object valeur) {
        if (this.metadonnees == null) {
            this.metadonnees = new HashMap<>();
        }
        this.metadonnees.put(cle, valeur);
    }
    
    /**
     * Récupère une métadonnée
     */
    public Object getMetadonnee(String cle) {
        return this.metadonnees != null ? this.metadonnees.get(cle) : null;
    }
    
    /**
     * Vérifie si la requête est valide (à surcharger dans les classes filles)
     */
    public boolean isValid() {
        return requestId != null && timestamp != null;
    }
    
    /**
     * Retourne la description de l'appareil
     */
    public String getDescriptionAppareil() {
        StringBuilder description = new StringBuilder();
        
        if (navigateur != null && !navigateur.trim().isEmpty()) {
            description.append(navigateur);
        }
        
        if (systemeExploitation != null && !systemeExploitation.trim().isEmpty()) {
            if (description.length() > 0) {
                description.append(" sur ");
            }
            description.append(systemeExploitation);
        }
        
        if (typeAppareil != null && !typeAppareil.trim().isEmpty()) {
            if (description.length() > 0) {
                description.append(" (").append(typeAppareil).append(")");
            } else {
                description.append(typeAppareil);
            }
        }
        
        return description.toString();
    }
    
    /**
     * Retourne la localisation complète
     */
    public String getLocalisationComplete() {
        StringBuilder localisation = new StringBuilder();
        
        if (ville != null && !ville.trim().isEmpty()) {
            localisation.append(ville);
        }
        
        if (region != null && !region.trim().isEmpty()) {
            if (localisation.length() > 0) {
                localisation.append(", ");
            }
            localisation.append(region);
        }
        
        if (pays != null && !pays.trim().isEmpty()) {
            if (localisation.length() > 0) {
                localisation.append(", ");
            }
            localisation.append(pays);
        }
        
        return localisation.toString();
    }
    
    // Getters et Setters
    public String getRequestId() {
        return requestId;
    }
    
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    
    public Timestamp getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getLangue() {
        return langue;
    }
    
    public void setLangue(String langue) {
        this.langue = langue;
    }
    
    public String getAdresseIp() {
        return adresseIp;
    }
    
    public void setAdresseIp(String adresseIp) {
        this.adresseIp = adresseIp;
    }
    
    public String getUserAgent() {
        return userAgent;
    }
    
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    
    public String getEmpreinteAppareil() {
        return empreinteAppareil;
    }
    
    public void setEmpreinteAppareil(String empreinteAppareil) {
        this.empreinteAppareil = empreinteAppareil;
    }
    
    public String getTypeAppareil() {
        return typeAppareil;
    }
    
    public void setTypeAppareil(String typeAppareil) {
        this.typeAppareil = typeAppareil;
    }
    
    public String getNavigateur() {
        return navigateur;
    }
    
    public void setNavigateur(String navigateur) {
        this.navigateur = navigateur;
    }
    
    public String getSystemeExploitation() {
        return systemeExploitation;
    }
    
    public void setSystemeExploitation(String systemeExploitation) {
        this.systemeExploitation = systemeExploitation;
    }
    
    public String getPays() {
        return pays;
    }
    
    public void setPays(String pays) {
        this.pays = pays;
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
    
    public Map<String, Object> getMetadonnees() {
        return metadonnees;
    }
    
    public void setMetadonnees(Map<String, Object> metadonnees) {
        this.metadonnees = metadonnees;
    }
    
    public String getCanal() {
        return canal;
    }
    
    public void setCanal(String canal) {
        this.canal = canal;
    }
    
    public String getVersionClient() {
        return versionClient;
    }
    
    public void setVersionClient(String versionClient) {
        this.versionClient = versionClient;
    }
    
    @Override
    public String toString() {
        return "BaseRequest{" +
                "requestId='" + requestId + '\'' +
                ", timestamp=" + timestamp +
                ", langue='" + langue + '\'' +
                ", canal='" + canal + '\'' +
                ", typeAppareil='" + typeAppareil + '\'' +
                ", pays='" + pays + '\'' +
                '}';
    }
}