package authentification.base.service;

import authentification.base.exception.SenMarketException;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.sql.Timestamp;
import java.util.Map;
import java.util.HashMap;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.Validation;
import java.util.Set;

/**
 * Classe de service de base pour tous les services de l'application SenMarket
 * Fournit des fonctionnalités communes à tous les services métier
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public abstract class BaseService {
    
    /**
     * Logger pour cette classe de service
     */
    protected final Logger logger;
    
    /**
     * Validator pour la validation des objets
     */
    protected final Validator validator;
    
    /**
     * Timestamp de création du service
     */
    private final Timestamp serviceCreationTime;
    
    /**
     * Nom du service (nom de la classe concrète)
     */
    private final String serviceName;
    
    /**
     * Statistiques d'utilisation du service
     */
    private final Map<String, Long> serviceStats;
    
    /**
     * Indique si le service est en mode debug
     */
    protected boolean debugMode = false;
    
    /**
     * Contexte d'exécution actuel
     */
    private final ThreadLocal<String> currentContext = new ThreadLocal<>();
    
    /**
     * Informations de session courante
     */
    private final ThreadLocal<Map<String, Object>> sessionInfo = new ThreadLocal<>();
    
    /**
     * Constructeur
     */
    public BaseService() {
        this.serviceName = this.getClass().getSimpleName();
        this.logger = Logger.getLogger(this.serviceName);
        this.serviceCreationTime = new Timestamp(System.currentTimeMillis());
        this.serviceStats = new HashMap<>();
        
        // Initialiser le validator
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
        
        // Initialiser les statistiques
        initializeStats();
        
        logger.info("Service " + serviceName + " initialisé à " + serviceCreationTime);
    }
    
    /**
     * Initialise les statistiques du service
     */
    private void initializeStats() {
        serviceStats.put("totalRequests", 0L);
        serviceStats.put("successfulRequests", 0L);
        serviceStats.put("failedRequests", 0L);
        serviceStats.put("averageResponseTime", 0L);
    }
    
    /**
     * Définit le contexte d'exécution actuel
     */
    protected void setCurrentContext(String methodName) {
        currentContext.set(serviceName + "." + methodName);
        if (debugMode) {
            logger.info("Contexte défini: " + currentContext.get());
        }
    }
    
    /**
     * Récupère le contexte d'exécution actuel
     */
    protected String getCurrentContext() {
        return currentContext.get();
    }
    
    /**
     * Nettoie le contexte d'exécution
     */
    protected void clearCurrentContext() {
        currentContext.remove();
    }
    
    /**
     * Définit les informations de session
     */
    protected void setSessionInfo(String userId, String sessionId, String ipAddress) {
        Map<String, Object> session = new HashMap<>();
        session.put("userId", userId);
        session.put("sessionId", sessionId);
        session.put("ipAddress", ipAddress);
        session.put("timestamp", new Timestamp(System.currentTimeMillis()));
        sessionInfo.set(session);
    }
    
    /**
     * Récupère les informations de session
     */
    protected Map<String, Object> getSessionInfo() {
        return sessionInfo.get();
    }
    
    /**
     * Nettoie les informations de session
     */
    protected void clearSessionInfo() {
        sessionInfo.remove();
    }
    
    /**
     * Valide un objet en utilisant Bean Validation
     */
    protected <T> void validateObject(T object) throws SenMarketException {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder("Erreurs de validation: ");
            for (ConstraintViolation<T> violation : violations) {
                sb.append(violation.getPropertyPath())
                  .append(" - ")
                  .append(violation.getMessage())
                  .append("; ");
            }
            throw new SenMarketException(sb.toString(), "VALID001", "VALIDATION", 
                                       SenMarketException.NiveauGravite.MOYEN);
        }
    }
    
    /**
     * Valide un objet avec un message d'erreur personnalisé
     */
    protected <T> void validateObject(T object, String customMessage) throws SenMarketException {
        try {
            validateObject(object);
        } catch (SenMarketException e) {
            throw new SenMarketException(customMessage + " - " + e.getMessage(), 
                                       e.getCodeErreur(), e.getTypeErreur(), e.getNiveauGravite());
        }
    }
    
    /**
     * Log une opération avec timing
     */
    protected void logOperation(String operation, long startTime) {
        long duration = System.currentTimeMillis() - startTime;
        incrementStat("totalRequests");
        updateAverageResponseTime(duration);
        
        if (debugMode) {
            logger.info("Opération '" + operation + "' terminée en " + duration + "ms");
        }
    }
    
    /**
     * Log une opération réussie
     */
    protected void logSuccess(String operation, long startTime) {
        logOperation(operation, startTime);
        incrementStat("successfulRequests");
        
        if (debugMode) {
            logger.info("Succès: " + operation);
        }
    }
    
    /**
     * Log une opération échouée
     */
    protected void logFailure(String operation, long startTime, Throwable error) {
        logOperation(operation, startTime);
        incrementStat("failedRequests");
        
        logger.log(Level.WARNING, "Échec: " + operation, error);
    }
    
    /**
     * Enrichit une exception avec le contexte du service
     */
    protected void enrichException(SenMarketException exception) {
        exception.setContexteExecution(getCurrentContext());
        
        Map<String, Object> session = getSessionInfo();
        if (session != null) {
            exception.setIdentifiantUtilisateur((String) session.get("userId"));
            exception.setSessionId((String) session.get("sessionId"));
            exception.setAdresseIp((String) session.get("ipAddress"));
        }
        
        exception.addMetadonnee("serviceName", serviceName);
        exception.addMetadonnee("timestamp", new Timestamp(System.currentTimeMillis()));
    }
    
    /**
     * Crée une exception métier avec le contexte du service
     */
    protected SenMarketException createBusinessException(String message, String errorCode) {
        SenMarketException exception = new SenMarketException(message, errorCode, "BUSINESS",
                                                            SenMarketException.NiveauGravite.MOYEN);
        enrichException(exception);
        return exception;
    }
    
    /**
     * Crée une exception système avec le contexte du service
     */
    protected SenMarketException createSystemException(String message, Throwable cause) {
        SenMarketException exception = new SenMarketException(message, cause);
        exception.setCodeErreur("SYS001");
        exception.setTypeErreur("SYSTEM");
        exception.setNiveauGravite(SenMarketException.NiveauGravite.ELEVE);
        enrichException(exception);
        return exception;
    }
    
    /**
     * Vérifie qu'un paramètre n'est pas null
     */
    protected void checkNotNull(Object parameter, String parameterName) throws SenMarketException {
        if (parameter == null) {
            throw createBusinessException("Le paramètre '" + parameterName + "' ne peut pas être null", 
                                        "PARAM001");
        }
    }
    
    /**
     * Vérifie qu'une chaîne n'est pas null ou vide
     */
    protected void checkNotEmpty(String parameter, String parameterName) throws SenMarketException {
        checkNotNull(parameter, parameterName);
        if (parameter.trim().isEmpty()) {
            throw createBusinessException("Le paramètre '" + parameterName + "' ne peut pas être vide", 
                                        "PARAM002");
        }
    }
    
    /**
     * Vérifie qu'un ID est valide (positif)
     */
    protected void checkValidId(Long id, String parameterName) throws SenMarketException {
        checkNotNull(id, parameterName);
        if (id <= 0) {
            throw createBusinessException("Le paramètre '" + parameterName + "' doit être positif", 
                                        "PARAM003");
        }
    }
    
    /**
     * Exécute une opération avec gestion automatique du contexte et des statistiques
     */
    protected <T> T executeOperation(String operationName, ServiceOperation<T> operation) 
            throws SenMarketException {
        long startTime = System.currentTimeMillis();
        setCurrentContext(operationName);
        
        try {
            T result = operation.execute();
            logSuccess(operationName, startTime);
            return result;
        } catch (SenMarketException e) {
            enrichException(e);
            logFailure(operationName, startTime, e);
            throw e;
        } catch (Exception e) {
            SenMarketException systemException = createSystemException(
                "Erreur système lors de l'opération: " + operationName, e);
            logFailure(operationName, startTime, systemException);
            throw systemException;
        } finally {
            clearCurrentContext();
        }
    }
    
    /**
     * Interface fonctionnelle pour les opérations de service
     */
    @FunctionalInterface
    protected interface ServiceOperation<T> {
        T execute() throws Exception;
    }
    
    /**
     * Incrémente une statistique
     */
    private void incrementStat(String statName) {
        synchronized (serviceStats) {
            serviceStats.put(statName, serviceStats.get(statName) + 1);
        }
    }
    
    /**
     * Met à jour le temps de réponse moyen
     */
    private void updateAverageResponseTime(long newResponseTime) {
        synchronized (serviceStats) {
            long totalRequests = serviceStats.get("totalRequests");
            long currentAverage = serviceStats.get("averageResponseTime");
            long newAverage = ((currentAverage * (totalRequests - 1)) + newResponseTime) / totalRequests;
            serviceStats.put("averageResponseTime", newAverage);
        }
    }
    
    /**
     * Retourne les statistiques du service
     */
    public Map<String, Long> getServiceStats() {
        synchronized (serviceStats) {
            return new HashMap<>(serviceStats);
        }
    }
    
    /**
     * Remet à zéro les statistiques
     */
    public void resetStats() {
        synchronized (serviceStats) {
            initializeStats();
        }
        logger.info("Statistiques remises à zéro pour le service " + serviceName);
    }
    
    /**
     * Active ou désactive le mode debug
     */
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
        logger.info("Mode debug " + (debugMode ? "activé" : "désactivé") + " pour " + serviceName);
    }
    
    /**
     * Retourne le nom du service
     */
    public String getServiceName() {
        return serviceName;
    }
    
    /**
     * Retourne la date de création du service
     */
    public Timestamp getServiceCreationTime() {
        return serviceCreationTime;
    }
    
    /**
     * Retourne des informations de santé du service
     */
    public Map<String, Object> getHealthInfo() {
        Map<String, Object> health = new HashMap<>();
        health.put("serviceName", serviceName);
        health.put("creationTime", serviceCreationTime);
        health.put("uptime", System.currentTimeMillis() - serviceCreationTime.getTime());
        health.put("debugMode", debugMode);
        health.put("stats", getServiceStats());
        return health;
    }
    
    /**
     * Méthode appelée lors de la destruction du service
     */
    public void destroy() {
        logger.info("Destruction du service " + serviceName);
        clearCurrentContext();
        clearSessionInfo();
    }
}