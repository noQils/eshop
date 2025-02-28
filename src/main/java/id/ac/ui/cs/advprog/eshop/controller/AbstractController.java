package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.service.ObjectPostService;
import id.ac.ui.cs.advprog.eshop.service.ObjectGetService;
import org.springframework.ui.Model;
import java.util.List;

public abstract class AbstractController<T> {
    protected final ObjectGetService<T> getService;
    protected final ObjectPostService<T> postService;
    protected final String createView;
    protected final String listView;
    protected final String editView;
    protected final String objectAttribute;
    protected final String objectsAttribute;
    protected final String redirectList;

    public AbstractController(ObjectGetService<T> service, ObjectPostService<T> postService, String createView, String listView, String editView, String objectAttribute, String objectsAttribute, String redirectList) {
        this.getService = service;
        this.postService = postService;
        this.createView = createView;
        this.listView = listView;
        this.editView = editView;
        this.objectAttribute = objectAttribute;
        this.objectsAttribute = objectsAttribute;
        this.redirectList = redirectList;
    }

    public String createPage(Model model) {
        model.addAttribute(objectAttribute, createNewInstance());
        return createView;
    }

    protected String createPost(T object) {
        postService.create(object);
        return redirectList;
    }

    public String listPage(Model model) {
        List<T> allObjects = getService.findAll();
        model.addAttribute(objectsAttribute, allObjects);
        return listView;
    }

    public String editPage(String id, Model model) {
        T object = getService.findById(id);
        if (object == null) {
            return listView;
        }
        model.addAttribute(objectAttribute, object);
        return editView;
    }

    protected String editPost(String id, T object) {
        postService.edit(id, object);
        return redirectList;
    }

    protected String delete(String id) {
        postService.delete(id);
        return redirectList;
    }

    protected abstract T createNewInstance();
}
