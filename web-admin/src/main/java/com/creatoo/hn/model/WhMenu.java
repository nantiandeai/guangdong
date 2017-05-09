package com.creatoo.hn.model;

import javax.persistence.*;

@Table(name = "wh_menu")
public class WhMenu {
    @Id
    private String id;

    /**
     * 类型：0分类菜单，1操作菜单
     */
    private Integer type;

    /**
     * 上级菜单的id,一级菜单为空字符串
     */
    private String parent;

    /**
     * 菜单名称
     */
    private String text;

    /**
     * 操作菜单的访问URL相对路径
     */
    private String href;

    /**
     * 顶级菜单的图标样式
     */
    private String iconcls;

    /**
     * 菜单排序标识
     */
    private Integer idx;

    /**
     * 允许的操作,json格式:[{"id":"", "name":""},{"id":"", "name":""}]
     */
    private String opts;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取类型：0分类菜单，1操作菜单
     *
     * @return type - 类型：0分类菜单，1操作菜单
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型：0分类菜单，1操作菜单
     *
     * @param type 类型：0分类菜单，1操作菜单
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取上级菜单的id,一级菜单为空字符串
     *
     * @return parent - 上级菜单的id,一级菜单为空字符串
     */
    public String getParent() {
        return parent;
    }

    /**
     * 设置上级菜单的id,一级菜单为空字符串
     *
     * @param parent 上级菜单的id,一级菜单为空字符串
     */
    public void setParent(String parent) {
        this.parent = parent;
    }

    /**
     * 获取菜单名称
     *
     * @return text - 菜单名称
     */
    public String getText() {
        return text;
    }

    /**
     * 设置菜单名称
     *
     * @param text 菜单名称
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 获取操作菜单的访问URL相对路径
     *
     * @return href - 操作菜单的访问URL相对路径
     */
    public String getHref() {
        return href;
    }

    /**
     * 设置操作菜单的访问URL相对路径
     *
     * @param href 操作菜单的访问URL相对路径
     */
    public void setHref(String href) {
        this.href = href;
    }

    /**
     * 获取顶级菜单的图标样式
     *
     * @return iconcls - 顶级菜单的图标样式
     */
    public String getIconcls() {
        return iconcls;
    }

    /**
     * 设置顶级菜单的图标样式
     *
     * @param iconcls 顶级菜单的图标样式
     */
    public void setIconcls(String iconcls) {
        this.iconcls = iconcls;
    }

    /**
     * 获取菜单排序标识
     *
     * @return idx - 菜单排序标识
     */
    public Integer getIdx() {
        return idx;
    }

    /**
     * 设置菜单排序标识
     *
     * @param idx 菜单排序标识
     */
    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    /**
     * 获取允许的操作,json格式:[{"id":"", "name":""},{"id":"", "name":""}]
     *
     * @return opts - 允许的操作,json格式:[{"id":"", "name":""},{"id":"", "name":""}]
     */
    public String getOpts() {
        return opts;
    }

    /**
     * 设置允许的操作,json格式:[{"id":"", "name":""},{"id":"", "name":""}]
     *
     * @param opts 允许的操作,json格式:[{"id":"", "name":""},{"id":"", "name":""}]
     */
    public void setOpts(String opts) {
        this.opts = opts;
    }
}