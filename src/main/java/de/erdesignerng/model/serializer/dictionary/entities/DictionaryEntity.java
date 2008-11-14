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
package de.erdesignerng.model.serializer.dictionary.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * @author msertic
 */
public class DictionaryEntity extends ModelEntity {

    private List<DomainEntity> domains = new ArrayList<DomainEntity>();
    
    private List<TableEntity> tables = new ArrayList<TableEntity>();
    
    private List<RelationEntity> relations = new ArrayList<RelationEntity>();

    private List<CommentEntity> comments = new ArrayList<CommentEntity>();
    
    private List<SubjectAreaEntity> subjectareas = new ArrayList<SubjectAreaEntity>();
    
    private List<ChangeEntity> changes = new ArrayList<ChangeEntity>();

    /**
     * @return the domains
     */
    public List<DomainEntity> getDomains() {
        return domains;
    }

    /**
     * @param domains the domains to set
     */
    public void setDomains(List<DomainEntity> domains) {
        this.domains = domains;
    }

    /**
     * @return the tables
     */
    public List<TableEntity> getTables() {
        return tables;
    }

    /**
     * @param tables the tables to set
     */
    public void setTables(List<TableEntity> tables) {
        this.tables = tables;
    }

    /**
     * @return the relations
     */
    public List<RelationEntity> getRelations() {
        return relations;
    }

    /**
     * @param relations the relations to set
     */
    public void setRelations(List<RelationEntity> relations) {
        this.relations = relations;
    }

    /**
     * @return the comments
     */
    public List<CommentEntity> getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }

    /**
     * @return the subjectareas
     */
    public List<SubjectAreaEntity> getSubjectareas() {
        return subjectareas;
    }

    /**
     * @param subjectareas the subjectareas to set
     */
    public void setSubjectareas(List<SubjectAreaEntity> subjectareas) {
        this.subjectareas = subjectareas;
    }

    /**
     * @return the changes
     */
    public List<ChangeEntity> getChanges() {
        return changes;
    }

    /**
     * @param changes the changes to set
     */
    public void setChanges(List<ChangeEntity> changes) {
        this.changes = changes;
    }
}