package com.bookmarks.resources;

import com.bookmarks.core.Bookmark;
import com.bookmarks.core.User;
import com.bookmarks.db.BookmarkDAO;
import com.google.common.base.Optional;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

// https://localhost:8443/bookmarks
@Path("/bookmarks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookmarksResource {

    private BookmarkDAO dao;

    public BookmarksResource(BookmarkDAO dao) {
        this.dao = dao;
    }

    @GET
    @UnitOfWork
    public List<Bookmark> getBookmarks(@Auth User user) {
        return dao.findForUser(user.getId());
    }

    private Optional<Bookmark> findIfAuthorized(long bookmarkId, long userId) {
        Optional<Bookmark> result = dao.findById(bookmarkId);
        if (result.isPresent() && userId != result.get().getUser().getId()) {
            throw new ForbiddenException("Looks like this bookmark belongs to someone else.");
        }
        return result; // either empty optional, or a bookmark!
    }


    @GET
    @Path("/{id}") // https://localhost:8443/bookmarks/1
    @UnitOfWork
    public Optional<Bookmark> getBookmark(@PathParam("id")LongParam id, @Auth User user) {
        return findIfAuthorized(id.get(), user.getId());
    }

    @DELETE
    @Path("/{id}") // https://localhost:8443/bookmarks/1
    @UnitOfWork
    public Optional<Bookmark> delete(@PathParam("id")LongParam id, @Auth User user) {
        Optional<Bookmark> bookmark = findIfAuthorized(id.get(), user.getId());

        if (bookmark.isPresent()) {
            dao.delete(bookmark.get());
        }
        return bookmark;
    }

    @POST
    @UnitOfWork
    public Bookmark saveBookmark(Bookmark bookmark, @Auth User user) {
        bookmark.setUser(user);
        return dao.save(bookmark);
    }

    @PUT
    @Path("/{id}") // https://localhost:8443/bookmarks/1
    @UnitOfWork
    public Bookmark updateBookmark(@PathParam("id")LongParam id, Bookmark updatedBookmark, @Auth User user) {
        Optional<Bookmark> bookmark = findIfAuthorized(id.get(), user.getId());

        if (bookmark.isPresent()) {
            // this is an existing bookmark
            bookmark.get().setName(updatedBookmark.getName());
            bookmark.get().setDescription(updatedBookmark.getDescription());
            bookmark.get().setUrl(updatedBookmark.getUrl());
            return dao.save(bookmark.get());
        } else {
            // this is a new bookmark
            updatedBookmark.setUser(user);
            return dao.save(updatedBookmark);
        }
    }
}
