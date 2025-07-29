package authentification.base.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DTO de base pour toutes les réponses du module authentification
 * Contient les informations communes à toutes les réponses
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public abstract class BaseResponse implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Indique si l'opération a réussi
     */
    private boolean success;
    
    /**
     * Message principal de la réponse
     */
    private String message;
    
    /**
     * Code de statut de la réponse
     */
    private String statusCode;
    
    /**
     * Timestamp de génération de la réponse
     */
    private Timestamp timestamp;
    
    /**
     * Identifiant de la requête correspondante (traçabilité)
     */
    private String requestId;
    
    /**
     * Identifiant unique de la réponse
     */
    private String responseId;
    
    /**
     * Durée de traitement en millisecondes
     */
    private Long dureeTraitement;
    
    /**
     * Liste des erreurs (si applicable)
     */
    private List<String> erreurs;
    
    /**
     * Liste des avertissements
     */
    private List<String> avertissements;
    
    /**
     * Informations de débogage (en mode développement)
     */
    private Map<String, Object> debugInfo;
    
    /**
     * Métadonnées additionnelles
     */
    private Map<String, Object> metadonnees;
    
    /**
     * Version de l'API
     */
    private String versionApi = "1.0";
    
    /**
     * Serveur qui a traité la requête
     */
    private String serveurId;
    
    /**
     * Langue de la réponse
     */
    private String langue = "fr";
    
    // Constructeur par défaut
    public BaseResponse() {
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.responseId = generateResponseId();
        this.erreurs = new ArrayList<>();
        this.avertissements = new ArrayList<>();
        this.debugInfo = new HashMap<>();
        this.metadonnees = new HashMap<>();
    }
    
    /**
     * Constructeur avec statut de succès et message
     */
    public BaseResponse(boolean success, String message) {
        this();
        this.success = success;
        this.message = message;
        this.statusCode = success ? "SUCCESS" : "ERROR";
    }
    
    /**
     * Constructeur complet
     */
    public BaseResponse(boolean success, String message, String statusCode) {
        this(success, message);
        this.statusCode = statusCode;
    }
    
    /**
     * Génère un identifiant unique pour la réponse
     */
    private String generateResponseId() {
        return "RESP_" + System.currentTimeMillis() + "_" + 
               Integer.toHexString((int) (Math.random() * 1000000));
    }
    
    /**
     * Méthodes utilitaires pour la gestion des erreurs
     */
    public void addErreur(String erreur) {
        if (this.erreurs == null) {
            this.erreurs = new ArrayList<>();
        }
        this.erreurs.add(erreur);
        this.success = false; // Une erreur rend automatiquement la réponse en échec
    }
    
    public void addErreurs(List<String> erreurs) {
        if (erreurs != null && !erreurs.isEmpty()) {
            if (this.erreurs == null) {
                this.erreurs = new ArrayList<>();
            }
            this.erreurs.addAll(erreurs);
            this.success = false;
        }
    }
    
    /**
     * Méthodes utilitaires pour la gestion des avertissements
     */
    public void addAvertissement(String avertissement) {
        if (this.avertissements == null) {
            this.avertissements = new ArrayList<>();
        }
        this.avertissements.add(avertissement);
    }
    
    public void addAvertissements(List<String> avertissements) {
        if (avertissements != null && !avertissements.isEmpty()) {
            if (this.avertissements == null) {
                this.avertissements = new ArrayList<>();
            }
            this.avertissements.addAll(avertissements);
        }
    }
    
    /**
     * Méthodes utilitaires pour les métadonnées
     */
    public void addMetadonnee(String cle, Object valeur) {
        if (this.metadonnees == null) {
            this.metadonnees = new HashMap<>();
        }
        this.metadonnees.put(cle, valeur);
    }
    
    public Object getMetadonnee(String cle) {
        return this.metadonnees != null ? this.metadonnees.get(cle) : null;
    }
    
    /**
     * Méthodes utilitaires pour les informations de débogage
     */
    public void addDebugInfo(String cle, Object valeur) {
        if (this.debugInfo == null) {
            this.debugInfo = new HashMap<>();
        }
        this.debugInfo.put(cle, valeur);
    }
    
    /**
     * Calcule et définit la durée de traitement
     */
    public void calculateDureeTraitement(Timestamp timestampDebut) {
        if (timestampDebut != null && this.timestamp != null) {
            this.dureeTraitement = this.timestamp.getTime() - timestampDebut.getTime();
        }
    }
    
    /**
     * Méthodes de vérification
     */
    public boolean hasErreurs() {
        return this.erreurs != null && !this.erreurs.isEmpty();
    }
    
    public boolean hasAvertissements() {
        return this.avertissements != null && !this.avertissements.isEmpty();
    }
    
    public boolean isSuccess() {
        return this.success;
    }
    
    public boolean isFailure() {
        return !this.success;
    }
    
    /**
     * Créateurs statiques pour des réponses communes
     */
    public static <T extends BaseResponse> T createSuccess(T response, String message) {
        response.setSuccess(true);
        response.setMessage(message);
        response.setStatusCode("SUCCESS");
        return response;
    }
    
    public static <T extends BaseResponse> T createError(T response, String message) {
        response.setSuccess(false);
        response.setMessage(message);
        response.setStatusCode("ERROR");
        return response;
    }
    
    public static <T extends BaseResponse> T createError(T response, String message, String statusCode) {
        response.setSuccess(false);
        response.setMessage(message);
        response.setStatusCode(statusCode);
        return response;
    }
    
    /**
     * Copie les informations de base d'une requête
     */
    public void copyFromRequest(BaseRequest request) {
        if (request != null) {
            this.requestId = request.getRequestId();
            this.langue = request.getLangue();
            if (request.getTimestamp() != null) {
                calculateDureeTraitement(request.getTimestamp());
            }
        }
    }
    
    // Getters et Setters
    public boolean getSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getStatusCode() {
        return statusCode;
    }
    
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
    
    public Timestamp getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getRequestId() {
        return requestId;
    }
    
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    
    public String getResponseId() {
        return responseId;
    }
    
    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }
    
    public Long getDureeTraitement() {
        return dureeTraitement;
    }
    
    public void setDureeTraitement(Long dureeTraitement) {
        this.dureeTraitement = dureeTraitement;
    }
    
    public List<String> getErreurs() {
        return erreurs;
    }
    
    public void setErreurs(List<String> erreurs) {
        this.erreurs = erreurs;
    }
    
    public List<String> getAvertissements() {
        return avertissements;
    }
    
    public void setAvertissements(List<String> avertissements) {
        this.avertissements = avertissements;
    }
    
    public Map<String, Object> getDebugInfo() {
        return debugInfo;
    }
    
    public void setDebugInfo(Map<String, Object> debugInfo) {
        this.debugInfo = debugInfo;
    }
    
    public Map<String, Object> getMetadonnees() {
        return metadonnees;
    }
    
    public void setMetadonnees(Map<String, Object> metadonnees) {
        this.metadonnees = metadonnees;
    }
    
    public String getVersionApi() {
        return versionApi;
    }
    
    public void setVersionApi(String versionApi) {
        this.versionApi = versionApi;
    }
    
    public String getServeurId() {
        return serveurId;
    }
    
    public void setServeurId(String serveurId) {
        this.serveurId = serveurId;
    }
    
    public String getLangue() {
        return langue;
    }
    
    public void setLangue(String langue) {
        this.langue = langue;
    }
    
    @Override
    public String toString() {
        return "BaseResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", statusCode='" + statusCode + '\'' +
                ", responseId='" + responseId + '\'' +
                ", dureeTraitement=" + dureeTraitement +
                ", erreurs=" + (erreurs != null ? erreurs.size() : 0) +
                ", avertissements=" + (avertissements != null ? avertissements.size() : 0) +
                '}';
    }
}