package movie.example.ls.vvmoviemanager.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 返回发布的瞬间 的详细信息
 * Created by Administrator on 2016/11/23.
 */
public class CommontBack4C implements Serializable {

    private ReleaseCommontBack moment;
    //点赞 信息
    private List<CommentList> likeComments = new ArrayList<CommentList>();
    //评论信息
    private List<CommentList> textComments = new ArrayList<CommentList>();
    private long likeCount = 0;
    private int commentCount = 0;
    private Boolean liked = false;


    public void setLikeComments(List<CommentList> likeComments) {
        this.likeComments = likeComments;
    }

    public List<CommentList> getTextComments() {
        return textComments;
    }

    public void setTextComments(List<CommentList> textComments) {
        this.textComments = textComments;
    }

    //zan 本地表情记录
    private int goodID = 0;

    private PublisherProfile publisherProfile;
    private PublisherProfile originalPublisherProfile;

    public ReleaseCommontBack getMoment() {
        return moment;
    }

    public void setMoment(ReleaseCommontBack moment) {
        this.moment = moment;
    }

    public PublisherProfile getPublisherProfile() {
        return publisherProfile;
    }

    public PublisherProfile getOriginalPublisherProfile() {
        return originalPublisherProfile;
    }

    public void setOriginalPublisherProfile(PublisherProfile originalPublisherProfile) {
        this.originalPublisherProfile = originalPublisherProfile;
    }

    public void setPublisherProfile(PublisherProfile publisherProfile) {
        this.publisherProfile = publisherProfile;
    }

    public List<CommentList> getLikeComments() {
        return likeComments;
    }

    public int getGoodID() {
        return goodID;
    }

    public void setGoodID(int goodID) {
        this.goodID = goodID;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }


}
