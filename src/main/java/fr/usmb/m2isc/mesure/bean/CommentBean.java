package fr.usmb.m2isc.mesure.bean;

import fr.usmb.m2isc.mesure.ejb.BacklogItemEJB;
import fr.usmb.m2isc.mesure.ejb.CommentEJB;
import fr.usmb.m2isc.mesure.jpa.BacklogItem;
import fr.usmb.m2isc.mesure.jpa.Comment;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ManagedBean(name="commentaryBean")
@ViewScoped
public class CommentBean {

    private FacesContext context = FacesContext.getCurrentInstance();

    private BacklogItem backlogItem;

    private String content;

    private List<Comment> comments;

    @EJB
    BacklogItemEJB backlogItemEJB;
    @EJB
    CommentEJB commentEJB;

    public CommentBean() {}

    @PostConstruct
    public void init() {
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        Object o = request.getAttribute("backlogItemId");
        int backlogItemId = 2;  // Default first id
        if (o != null)
            backlogItemId = (int) o;
        System.out.println("backlogItemId: " + backlogItemId);
        backlogItem = backlogItemEJB.findBacklogItem(backlogItemId); // FIXME in case no backlog is found

        reloadComments();
    }

    private void reloadComments() {
        comments = commentEJB.findAllCommentaryOfBacklogItem(backlogItem);
    }


    public void addCommentary() {
        Comment c = new Comment("Default username", content, backlogItem);
        c = commentEJB.addCommentary(c);
        System.out.println(c);
        reloadComments();
    }

    public BacklogItem getBacklogItem() {
        return backlogItem;
    }

    public void setBacklogItem(BacklogItem backlogItem) {
        this.backlogItem = backlogItem;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
