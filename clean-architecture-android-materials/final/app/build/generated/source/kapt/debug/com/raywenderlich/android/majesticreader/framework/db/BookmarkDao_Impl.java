package com.raywenderlich.android.majesticreader.framework.db;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class BookmarkDao_Impl implements BookmarkDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfBookmarkEntity;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfBookmarkEntity;

  public BookmarkDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBookmarkEntity = new EntityInsertionAdapter<BookmarkEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `bookmark`(`id`,`documentUri`,`page`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, BookmarkEntity value) {
        stmt.bindLong(1, value.id);
        if (value.getDocumentUri() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getDocumentUri());
        }
        stmt.bindLong(3, value.getPage());
      }
    };
    this.__deletionAdapterOfBookmarkEntity = new EntityDeletionOrUpdateAdapter<BookmarkEntity>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `bookmark` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, BookmarkEntity value) {
        stmt.bindLong(1, value.id);
      }
    };
  }

  @Override
  public Object addBookmark(final BookmarkEntity bookmark,
      final Continuation<? super Unit> continuation) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object removeBookmark(final BookmarkEntity bookmark,
      final Continuation<? super Unit> continuation) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object getBookmarks(final String documentUri,
      final Continuation<? super List<BookmarkEntity>> continuation) {
    final String _sql = "SELECT * FROM bookmark WHERE documentUri = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (documentUri == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, documentUri);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final Object _result;
      if(_cursor.moveToFirst()) {
        _result = new Object();
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
