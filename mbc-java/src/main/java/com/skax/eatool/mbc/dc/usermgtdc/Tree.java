
package com.skax.eatool.mbc.dc.usermgtdc;

import com.skax.eatool.ksa.infra.po.NewAbstractDTO;

/**
 * Tree class
 */
public class Tree extends NewAbstractDTO {

    private String userId;
    private String userName;
    private String treeId;
    private String treeName;
    private String parentId;

    public Tree() {
        // Default constructor
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
