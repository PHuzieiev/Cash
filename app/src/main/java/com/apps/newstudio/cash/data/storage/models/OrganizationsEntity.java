package com.apps.newstudio.cash.data.storage.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity(active = true, nameInDb = "ORGANIZATIONS")
public class OrganizationsEntity {

    @Id
    private String id;

    private String titleUkr;

    private String titleRus;

    private String titleEng;

    private String orgType;

    private String phone;

    private String link;

    private String date;

    @ToMany(joinProperties = {
            @JoinProperty(name = "id",referencedName = "organizationId")
    })
    private List<CurrenciesEntity> currencies;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 2061151580)
    private transient OrganizationsEntityDao myDao;

    @Generated(hash = 356145485)
    public OrganizationsEntity(String id, String titleUkr, String titleRus,
            String titleEng, String orgType, String phone, String link,
            String date) {
        this.id = id;
        this.titleUkr = titleUkr;
        this.titleRus = titleRus;
        this.titleEng = titleEng;
        this.orgType = orgType;
        this.phone = phone;
        this.link = link;
        this.date = date;
    }

    @Generated(hash = 990439400)
    public OrganizationsEntity() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitleUkr() {
        return this.titleUkr;
    }

    public void setTitleUkr(String titleUkr) {
        this.titleUkr = titleUkr;
    }

    public String getTitleRus() {
        return this.titleRus;
    }

    public void setTitleRus(String titleRus) {
        this.titleRus = titleRus;
    }

    public String getTitleEng() {
        return this.titleEng;
    }

    public void setTitleEng(String titleEng) {
        this.titleEng = titleEng;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1549319281)
    public List<CurrenciesEntity> getCurrencies() {
        if (currencies == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CurrenciesEntityDao targetDao = daoSession.getCurrenciesEntityDao();
            List<CurrenciesEntity> currenciesNew = targetDao
                    ._queryOrganizationsEntity_Currencies(id);
            synchronized (this) {
                if (currencies == null) {
                    currencies = currenciesNew;
                }
            }
        }
        return currencies;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1567151309)
    public synchronized void resetCurrencies() {
        currencies = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 516363739)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getOrganizationsEntityDao() : null;
    }

   
}
