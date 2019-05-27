package com.bookmarks.db;

import com.bookmarks.core.Bookmark;
import com.bookmarks.core.User;
import com.google.common.base.Optional;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class BookmarkDAO extends AbstractDAO<Bookmark> {

    public BookmarkDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Bookmark> findForUser(long id) {
        return list(
                namedQuery("com.bookmarks.core.Bookmark.findForUser")
                .setParameter("id", id)
        );
    }

    public Optional<Bookmark> findById(long id) {
        return Optional.fromNullable(get(id));
    }

    public Bookmark save(Bookmark bookmark) {
        return persist(bookmark);
    }

    public void delete(Bookmark bookmark) {
        namedQuery("com.bookmarks.core.Bookmark.remove")
                .setParameter("id", bookmark.getId())
                .executeUpdate();
    }

    public void deleteAllForUser(User user) {
        namedQuery("com.bookmarks.core.Bookmark.removeAllBookmarksForUser")
                .setParameter("id", user.getId())
                .executeUpdate();
    }
}
