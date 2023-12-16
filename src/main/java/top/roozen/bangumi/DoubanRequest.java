package top.roozen.bangumi;

import lombok.Builder;
import lombok.Data;

/**
 * @author <a href="https://roozen.top">Roozen</a>
 * @version 1.0
 */
@Data
@Builder
public class DoubanRequest {
    private String userId;
    private Integer count;
    private Integer start;
    private DoubanType type;
    private DoubanStatus status;

    public enum DoubanType{
        movie,music,book,qame,drama;
    }
    public enum DoubanStatus{
        mark,doing,done;
    }

}
