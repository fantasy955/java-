package javaExperiment.dao;

import java.util.List;
import javaExperiment.exception.BaseException;

public interface BaseDao<T> {
	T insert(T object) throws BaseException;

	T update(T object) throws BaseException;

	T delete(T object) throws BaseException;

	T findById(int id) throws BaseException;

	List<T> findAllOnes() throws BaseException;
}