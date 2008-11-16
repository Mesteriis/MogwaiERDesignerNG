/**
 * Mogwai ERDesigner. Copyright (C) 2002 The Mogwai Project.
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 */
package de.erdesignerng.model.serializer.repository;

import java.sql.Connection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import de.erdesignerng.model.Model;
import de.erdesignerng.model.ModelUtilities;
import de.erdesignerng.model.serializer.repository.entities.AttributeEntity;
import de.erdesignerng.model.serializer.repository.entities.ChangeEntity;
import de.erdesignerng.model.serializer.repository.entities.CommentEntity;
import de.erdesignerng.model.serializer.repository.entities.RepositoryEntity;
import de.erdesignerng.model.serializer.repository.entities.DomainEntity;
import de.erdesignerng.model.serializer.repository.entities.IndexEntity;
import de.erdesignerng.model.serializer.repository.entities.RelationEntity;
import de.erdesignerng.model.serializer.repository.entities.SubjectAreaEntity;
import de.erdesignerng.model.serializer.repository.entities.TableEntity;

/**
 * Serializer to store the model in a data dictionary.
 * 
 * @author msertic
 */
public class DictionaryModelSerializer extends DictionaryBaseSerializer {

    public static final DictionaryModelSerializer SERIALIZER = new DictionaryModelSerializer();

    protected Configuration createConfiguration(Class aHibernateDialectClass) {
        Configuration theConfiguration = new Configuration();
        theConfiguration.addClass(DomainEntity.class);
        theConfiguration.addClass(TableEntity.class);
        theConfiguration.addClass(AttributeEntity.class);
        theConfiguration.addClass(IndexEntity.class);
        theConfiguration.addClass(RelationEntity.class);
        theConfiguration.addClass(CommentEntity.class);
        theConfiguration.addClass(SubjectAreaEntity.class);
        theConfiguration.addClass(RepositoryEntity.class);
        theConfiguration.addClass(ChangeEntity.class);
        theConfiguration.setProperty(Environment.DIALECT, aHibernateDialectClass.getName());
        theConfiguration.setProperty(Environment.HBM2DDL_AUTO, "update");
        theConfiguration.setProperty(Environment.CONNECTION_PROVIDER, ThreadbasedConnectionProvider.class.getName());
        return theConfiguration;
    }

    protected Session createSession(Connection aConnection, Class aHibernateDialectClass) {

        Configuration theConfiguration = createConfiguration(aHibernateDialectClass);
        SessionFactory theSessionFactory = theConfiguration.buildSessionFactory();

        return theSessionFactory.openSession(aConnection, AuditInterceptor.INSTANCE);
    }

    public void serialize(Model aModel, Connection aConnection, Class aHibernateDialectClass) throws Exception {

        ThreadbasedConnectionProvider.initializeForThread(aConnection);
        Session theSession = null;
        Transaction theTx = null;
        try {

            theSession = createSession(aConnection, aHibernateDialectClass);    
            theTx = theSession.beginTransaction();

            RepositoryEntity theEntity = null;
            boolean existing = false;
            Criteria theCriteria = theSession.createCriteria(RepositoryEntity.class);
            List theEntities = theCriteria.list();
            if (theEntities.size() == 1) {
                theEntity = (RepositoryEntity) theEntities.get(0);
                existing = true;
            } else {
                theEntity = new RepositoryEntity();
                theEntity.setSystemId(ModelUtilities.createSystemIdFor(theEntity));
                theEntity.setName("MODEL");
            }

            
            DictionaryDomainSerializer.SERIALIZER.serialize(aModel, theSession, theEntity);
            
            DictionaryTableSerializer.SERIALIZER.serialize(aModel, theSession, theEntity);
            
            DictionaryRelationSerializer.SERIALIZER.serialize(aModel, theSession, theEntity);
            
            DictionaryCommentSerializer.SERIALIZER.serialize(aModel, theSession, theEntity);
            
            DictionarySubjectAreaSerializer.SERIALIZER.serialize(aModel, theSession, theEntity);
            
            if (existing) {
                theSession.update(theEntity);
            } else {
                theSession.save(theEntity);
            }
            
            theTx.commit();
            
        } catch (Exception e) {
            if (theTx != null) {
                theTx.rollback();
            }
            
            throw e;
        } finally {

            if (theSession != null) {
                theSession.close();
            }
            
            ThreadbasedConnectionProvider.cleanup();
        }

    }

    public Model deserialize(Connection aConnection, Class aHibernateDialectClass) throws Exception {

        ThreadbasedConnectionProvider.initializeForThread(aConnection);
        Session theSession = null;
        Transaction theTx = null;
        try {
            
            Model theNewModel = new Model();

            theSession = createSession(aConnection, aHibernateDialectClass);    
            theTx = theSession.beginTransaction();
            
            DictionaryDomainSerializer.SERIALIZER.deserialize(theNewModel, theSession);
            
            DictionaryTableSerializer.SERIALIZER.deserialize(theNewModel, theSession);
            
            DictionaryRelationSerializer.SERIALIZER.deserialize(theNewModel, theSession);
            
            DictionaryCommentSerializer.SERIALIZER.deserialize(theNewModel, theSession);
            
            DictionarySubjectAreaSerializer.SERIALIZER.deserialize(theNewModel, theSession);
            
            theTx.rollback();
            
            return theNewModel;
            
        } catch (Exception e) {
            if (theTx != null) {
                theTx.rollback();
            }
            
            throw e;
        } finally {

            if (theSession != null) {
                theSession.close();
            }
            
            ThreadbasedConnectionProvider.cleanup();
        }
    }
}