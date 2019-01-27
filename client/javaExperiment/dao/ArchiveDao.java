package javaExperiment.dao;

import java.util.List;

import javaExperiment.domain.Archive;
import javaExperiment.exception.*;


public interface ArchiveDao extends BaseDao<Archive> {
	
	public List<Archive> findByTitle(String title) throws BaseException;
	
}
