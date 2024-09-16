package ch.wiss.m294_doc_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{collectionName}/documents")
public class GenericDocumentController {

    @Autowired
    private GenericDocumentService genericDocumentService;

    @PostMapping
    public GenericDocument createDocument(@PathVariable String collectionName, @RequestBody GenericDocument document) {
        return genericDocumentService.save(collectionName, document);
    }

    @PutMapping("/{id}") // <- Stelle sicher, dass dieser Pfad vorhanden ist
    public GenericDocument updateDocument(@PathVariable String collectionName, @PathVariable String id, @RequestBody GenericDocument document) {
        if (document.getId() == null) {
            throw new IllegalArgumentException("Document ID must not be null");
        }
        document.setId(id); // Ensure the ID is set in the document
        return genericDocumentService.save(collectionName, document);
    }

    @GetMapping
    public List<GenericDocument> getAllDocuments(@PathVariable String collectionName) {
        return genericDocumentService.findAll(collectionName);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericDocument> getDocumentById(@PathVariable String collectionName, @PathVariable String id) {
        return genericDocumentService.findById(collectionName, id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable String collectionName, @PathVariable String id) {
        boolean isDeleted = genericDocumentService.deleteById(collectionName, id);
        if (isDeleted) {
            return ResponseEntity.noContent().build(); // 204 No Content, wenn erfolgreich gelöscht
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found, wenn kein Dokument gefunden wurde
        }
    }
}

