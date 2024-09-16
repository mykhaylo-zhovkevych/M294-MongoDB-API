package ch.wiss.m294_doc_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenericDocumentService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public GenericDocument save(String collectionName, GenericDocument document) {
        if (document.getId() != null) {
            Optional<GenericDocument> existingDocument = findById(collectionName, document.getId());
            if (existingDocument.isPresent()) {
                // Update das existierende Dokument
                mongoTemplate.save(document, collectionName);
            } else {
                throw new IllegalArgumentException("Document with ID not found for update.");
            }
        } else {
            // Neues Dokument erstellen
            mongoTemplate.insert(document, collectionName);
        }
        return document;
    }

    public List<GenericDocument> findAll(String collectionName) {
        return mongoTemplate.findAll(GenericDocument.class, collectionName);
    }

    public Optional<GenericDocument> findById(String collectionName, String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, GenericDocument.class, collectionName));
    }

    public boolean deleteById(String collectionName, String id) {
 
        GenericDocument document = mongoTemplate.findById(id, GenericDocument.class, collectionName);
        if (document != null) {
            mongoTemplate.remove(document, collectionName);
            return true;  // Return true if document is found and deleted
        }
        return false;  // Return false if document is not found
    }
}

