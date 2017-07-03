package movie.example.ls.vvmoviemanager.bean;

import java.io.Serializable;

/**
 * 里面里面的 评论的bean
 * Created by Administrator on 2016/12/22.
 */

public class CommentList implements Serializable{

    private Comment comment;

    private FromUser fromUser;

    private ReplyUser replyUser;

    public ReplyUser getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(ReplyUser replyUser) {
        this.replyUser = replyUser;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public FromUser getFromUser() {
        return fromUser;
    }

    public void setFromUser(FromUser fromUser) {
        this.fromUser = fromUser;
    }
}
