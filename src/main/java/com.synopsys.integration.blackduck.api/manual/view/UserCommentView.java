package com.synopsys.integration.blackduck.api.manual.view;

import com.synopsys.integration.blackduck.api.core.BlackDuckView;
import com.synopsys.integration.blackduck.api.manual.component.CommentUserData;

//this file should not be edited - if changes are necessary, the generator should be updated, then this file should be re-created
public class UserCommentView extends BlackDuckView {
    private String comment;
    private String commentType;
    private java.util.Date createdAt;
    private java.util.Date updatedAt;
    private CommentUserData user;

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public String getCommentType() {
        return commentType;
    }

    public void setCommentType(final String commentType) {
        this.commentType = commentType;
    }

    public java.util.Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final java.util.Date createdAt) {
        this.createdAt = createdAt;
    }

    public java.util.Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final java.util.Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public CommentUserData getUser() {
        return user;
    }

    public void setUser(final CommentUserData user) {
        this.user = user;
    }

}
