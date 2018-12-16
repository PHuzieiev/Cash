package com.apps.newstudio.cash.data.storage.models;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TEMPLATES".
*/
public class TemplateEntityDao extends AbstractDao<TemplateEntity, String> {

    public static final String TABLENAME = "TEMPLATES";

    /**
     * Properties of entity TemplateEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, String.class, "id", true, "ID");
        public final static Property OrganizationId = new Property(1, String.class, "organizationId", false, "ORGANIZATION_ID");
        public final static Property ShortCurrencyTitle = new Property(2, String.class, "shortCurrencyTitle", false, "SHORT_CURRENCY_TITLE");
        public final static Property Value = new Property(3, String.class, "value", false, "VALUE");
        public final static Property Direction = new Property(4, String.class, "direction", false, "DIRECTION");
        public final static Property Action = new Property(5, String.class, "action", false, "ACTION");
        public final static Property Date = new Property(6, String.class, "date", false, "DATE");
    }

    private DaoSession daoSession;


    public TemplateEntityDao(DaoConfig config) {
        super(config);
    }
    
    public TemplateEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TEMPLATES\" (" + //
                "\"ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: id
                "\"ORGANIZATION_ID\" TEXT," + // 1: organizationId
                "\"SHORT_CURRENCY_TITLE\" TEXT," + // 2: shortCurrencyTitle
                "\"VALUE\" TEXT," + // 3: value
                "\"DIRECTION\" TEXT," + // 4: direction
                "\"ACTION\" TEXT," + // 5: action
                "\"DATE\" TEXT);"); // 6: date
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TEMPLATES\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, TemplateEntity entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String organizationId = entity.getOrganizationId();
        if (organizationId != null) {
            stmt.bindString(2, organizationId);
        }
 
        String shortCurrencyTitle = entity.getShortCurrencyTitle();
        if (shortCurrencyTitle != null) {
            stmt.bindString(3, shortCurrencyTitle);
        }
 
        String value = entity.getValue();
        if (value != null) {
            stmt.bindString(4, value);
        }
 
        String direction = entity.getDirection();
        if (direction != null) {
            stmt.bindString(5, direction);
        }
 
        String action = entity.getAction();
        if (action != null) {
            stmt.bindString(6, action);
        }
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(7, date);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, TemplateEntity entity) {
        stmt.clearBindings();
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(1, id);
        }
 
        String organizationId = entity.getOrganizationId();
        if (organizationId != null) {
            stmt.bindString(2, organizationId);
        }
 
        String shortCurrencyTitle = entity.getShortCurrencyTitle();
        if (shortCurrencyTitle != null) {
            stmt.bindString(3, shortCurrencyTitle);
        }
 
        String value = entity.getValue();
        if (value != null) {
            stmt.bindString(4, value);
        }
 
        String direction = entity.getDirection();
        if (direction != null) {
            stmt.bindString(5, direction);
        }
 
        String action = entity.getAction();
        if (action != null) {
            stmt.bindString(6, action);
        }
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(7, date);
        }
    }

    @Override
    protected final void attachEntity(TemplateEntity entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public TemplateEntity readEntity(Cursor cursor, int offset) {
        TemplateEntity entity = new TemplateEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // organizationId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // shortCurrencyTitle
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // value
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // direction
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // action
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // date
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, TemplateEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setOrganizationId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setShortCurrencyTitle(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setValue(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDirection(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setAction(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setDate(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    @Override
    protected final String updateKeyAfterInsert(TemplateEntity entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public String getKey(TemplateEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(TemplateEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
