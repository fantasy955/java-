package javaExperiment.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javaExperiment.domain.Document;
import javaExperiment.exception.BaseException;

public interface DocumentService {
	Document uploadDocument(Document document) throws BaseException, SQLException, IOException, ClassNotFoundException;
		
	Document deleteDocument(int id) throws BaseException, IOException, SQLException;
	
	Document getDocument(int id) throws BaseException, SQLException;
	
	List<Document> getAllDocuments() throws BaseException, SQLException;
	
	void clear() throws BaseException;
	
    default String getExtensionName(String filename) {   
        if ((filename != null) && (filename.length() > 0)) {   
            int dot = filename.lastIndexOf('.');   
            if ((dot >-1) && (dot < (filename.length() - 1))) {   
                return filename.substring(dot + 1);   
            }   
        }   
        return filename;   
    } 
    
    default String getFileName(String filename) {   
        if ((filename != null) && (filename.length() > 0)) {   
            int split = filename.lastIndexOf('\\');  
            if (split == -1) {
            	split = filename.lastIndexOf('/'); 
            }
            if ((split >-1) && (split < (filename.length() - 1))) {   
                return filename.substring(split + 1);   
            }   
        }   
        return filename;   
    }

	void downloadDocument(int id, String targetPath) throws BaseException, IOException, SQLException;
}
