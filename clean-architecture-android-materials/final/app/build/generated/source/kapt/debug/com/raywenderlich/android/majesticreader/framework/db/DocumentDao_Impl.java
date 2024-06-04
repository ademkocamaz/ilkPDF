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
public final class DocumentDao_Impl implements DocumentDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfDocumentEntity;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfDocumentEntity;

  public DocumentDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDocumentEntity = new EntityInsertionAdapter<DocumentEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `document`(`uri`,`title`,`size`,`thumbnail_uri`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DocumentEntity value) {
        if (value.getUri() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getUri());
        }
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        stmt.bindLong(3, value.getSize());
        if (value.getThumbnailUri() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getThumbnailUri());
        }
      }
    };
    this.__deletionAdapterOfDocumentEntity = new EntityDeletionOrUpdateAdapter<DocumentEntity>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `document` WHERE `uri` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, DocumentEntity value) {
        if (value.getUri() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getUri());
        }
      }
    };
  }

  @Override
  public Object addDocument(final DocumentEntity document,
      final Continuation<? super Unit> continuation) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object removeDocument(final DocumentEntity document,
      final Continuation<? super Unit> continuation) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object getDocuments(final Continuation<? super List<DocumentEntity>> continuation) {
    final String _sql = "SELECT * FROM document";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    int _argIndex = 1;
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
