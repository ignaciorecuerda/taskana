package pro.taskana.rest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pro.taskana.Classification;
import pro.taskana.ClassificationQuery;
import pro.taskana.ClassificationService;
import pro.taskana.ClassificationSummary;
import pro.taskana.exceptions.ClassificationAlreadyExistException;
import pro.taskana.exceptions.ClassificationNotFoundException;
import pro.taskana.exceptions.ConcurrencyException;
import pro.taskana.exceptions.DomainNotFoundException;
import pro.taskana.exceptions.InvalidArgumentException;
import pro.taskana.exceptions.NotAuthorizedException;

/**
 * Controller for Importing / Exporting classifications.
 */
@RestController
@RequestMapping(path = "/v1/classificationdefinitions", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ClassificationDefinitionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassificationDefinitionController.class);

    @Autowired
    private ClassificationService classificationService;

    @GetMapping
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public ResponseEntity<List<ClassificationSummary>> exportClassifications(
        @RequestParam(required = false) String domain) {
        LOGGER.debug("Entry to exportClassifications(domain= {})", domain);
        ClassificationQuery query = classificationService.createClassificationQuery();
        List<ClassificationSummary> summaries = domain != null ? query.domainIn(domain).list() : query.list();

        LOGGER.debug("Exit from exportClassifications(), returning {}", new ResponseEntity<>(summaries, HttpStatus.OK));
        return new ResponseEntity<>(summaries, HttpStatus.OK);
    }

    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<String> importClassifications(
        @RequestBody List<Classification> classifications) throws InvalidArgumentException {
        LOGGER.debug("Entry to importClassifications(classifications= {})", classifications);
        Map<String, String> systemIds = classificationService.createClassificationQuery()
            .list()
            .stream()
            .collect(Collectors.toMap(i -> i.getKey() + "|" + i.getDomain(), ClassificationSummary::getId));
        try {
            for (Classification classification : classifications) {
                if (systemIds.containsKey(classification.getKey() + "|" + classification.getDomain())) {
                    classificationService.updateClassification(classification);
                } else {
                    classificationService.createClassification(classification);
                }
            }
        } catch (NotAuthorizedException e) {
            TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
            LOGGER.debug("Exit from createTask(), returning {}", new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (ClassificationNotFoundException | DomainNotFoundException e) {
            TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
            LOGGER.debug("Exit from createTask(), returning {}", new ResponseEntity<>(HttpStatus.NOT_FOUND));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ClassificationAlreadyExistException e) {
            TransactionInterceptor.currentTransactionStatus().setRollbackOnly();
            LOGGER.debug("Exit from createTask(), returning {}", new ResponseEntity<>(HttpStatus.CONFLICT));
            return new ResponseEntity<>(HttpStatus.CONFLICT);
            // TODO why is this occuring???
        } catch (ConcurrencyException e) {
        }

        LOGGER.debug("Exit from importClassifications(), returning {}", new ResponseEntity<>(HttpStatus.OK));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
