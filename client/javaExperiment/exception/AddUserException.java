package javaExperiment.exception;

public class AddUserException extends BaseException{
	public AddUserException(){
		super.message =ADD_USER_ERROR;
	}
	
	public static String ADD_USER_ERROR = "用户已存在";
}
