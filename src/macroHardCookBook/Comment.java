package macroHardCookBook;
import java.util.*;

/**
 * @author LU Qin
 *
 */
public class Comment {
	private String content;
	private LoggedInUser uploadUser;
	
	public Comment(String content, LoggedInUser uploadUser) {
		this.content = content;
		this.uploadUser = uploadUser;
	}
}
