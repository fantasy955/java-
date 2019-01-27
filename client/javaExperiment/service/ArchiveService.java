package javaExperiment.service;

import java.io.IOException;
import java.util.List;

import javaExperiment.dao.ArchiveDao;
import javaExperiment.domain.Archive;
import javaExperiment.exception.BaseException;

public interface ArchiveService {
	
	/**
	 * 注入DAO对象
	 * @param archiveDao
	 */
	void setArchiveDao(ArchiveDao archiveDao);
	
	/**
	 * 上传案宗
	 * @param Archive
	 * @return
	 * @throws BaseException 
	 * @throws IOException
	 */
	Archive uploadArchive(Archive Archive) throws IOException, BaseException;
	
	/**
	 * 下载案宗
	 * @param id
	 * @param targetPath
	 * @return
	 * @throws BaseException
	 * @throws IOException
	 */
	void downloadArchive(int id, String targetPath) throws BaseException, IOException;
	
	/**
	 * 删除案宗
	 * @param id
	 * @return
	 * @throws BaseException
	 */
	void deleteArchive(int id) throws BaseException,IOException;
	
	/**
	 * 得到案宗
	 * @param id
	 * @return
	 * @throws BaseException
	 */
	Archive getArchive(int id) throws BaseException;
	
	/**
	 * 得到所有案宗
	 * @return
	 * @throws BaseException
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	List<Archive> getAllArchives() throws BaseException, IOException, ClassNotFoundException;
	
	/**
	 * 通过标题查找案宗
	 * @param title
	 * @return
	 * @throws BaseException
	 */
	List<Archive> findByTitle(String title) throws BaseException;
	
	/**
	 * 清空所有案宗
	 * @throws BaseException
	 */
	void clear() throws BaseException;

	/**
	 * 修改案宗基本信息
	 * @param archive
	 * @return
	 * @throws BaseException
	 */
	Archive updateArchive(Archive archive) throws BaseException;
}
