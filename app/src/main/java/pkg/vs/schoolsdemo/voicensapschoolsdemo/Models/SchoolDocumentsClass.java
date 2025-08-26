package pkg.vs.schoolsdemo.voicensapschoolsdemo.Models;

import java.io.Serializable;

public class SchoolDocumentsClass implements Serializable {
    String id,DocumentName,DocumentDescription,DocumentURL,DocumentType;
    public SchoolDocumentsClass(String id, String DocumentName, String DocumentDescription, String DocumentURL, String DocumentType){
        this.id=id;
        this.DocumentName=DocumentName;
        this.DocumentDescription=DocumentDescription;
        this.DocumentURL=DocumentURL;
        this.DocumentType=DocumentType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocumentName() {
        return DocumentName;
    }

    public void setDocumentName(String documentName) {
        DocumentName = documentName;
    }

    public String getDocumentDescription() {
        return DocumentDescription;
    }

    public void setDocumentDescription(String documentDescription) {
        DocumentDescription = documentDescription;
    }

    public String getDocumentURL() {
        return DocumentURL;
    }

    public void setDocumentURL(String documentURL) {
        DocumentURL = documentURL;
    }

    public String getDocumentType() {
        return DocumentType;
    }

    public void setDocumentType(String documentType) {
        DocumentType = documentType;
    }
}
