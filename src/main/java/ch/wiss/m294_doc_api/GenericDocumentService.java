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
        return mongoTemplate.save(document, collectionName);
    }

    public List<GenericDocument> findAll(String collectionName) {
        return mongoTemplate.findAll(GenericDocument.class, collectionName);
    }

    public Optional<GenericDocument> findById(String collectionName, String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, GenericDocument.class, collectionName));
    }

    public void deleteById(String collectionName, String id) {
        mongoTemplate.remove(mongoTemplate.findById(id, GenericDocument.class, collectionName));
    }
}
