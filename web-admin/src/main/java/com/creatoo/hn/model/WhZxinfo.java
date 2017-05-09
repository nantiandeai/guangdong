package com.creatoo.hn.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "wh_zxinfo")
public class WhZxinfo {
    /**
     * 艺术标识
     */
    @Id
    private String zxid;

    /**
     * 长标题
     */
    private String zxtitle;

    /**
     * 短标题
     */
    private String zxshorttitle;

    /**
     * 艺术类型：个人作品/团队作品
     */
    private String zxtyp;

    /**
     * 艺术类型标识：艺术家/团队标识
     */
    private String zxtypid;

    /**
     * 开始时间
     */
    private Date zxstime;

    /**
     * 结束时间
     */
    private Date zxetime;

    /**
     * 标签
     */
    private String zxtags;

    /**
     * 关键字
     */
    private String zxkeys;

    /**
     * 地址
     */
    private String zxaddr;

    /**
     * 作者
     */
    private String zxauthor;

    /**
     * 小编
     */
    private String zxeditor;

    /**
     * 创建者
     */
    private String zxcreator;

    /**
     * 创建时间
     */
    private Date zxcrttime;

    /**
     * 备注
     */
    private String zxnote;

    /**
     * 来源
     */
    private String zxfrom;

    /**
     * 封面图
     */
    private String zxpic1;

    /**
     * 详情图片
     */
    private String zxpic2;

    /**
     * 流程状态：0初始，1送审，2已审，3已发
     */
    private String zxstate;

    /**
     * 流程标识
     */
    private String zxflowid;

    /**
     * 详细内容
     */
    private String zxcontent;

    /**
     * 获取艺术标识
     *
     * @return zxid - 艺术标识
     */
    public String getZxid() {
        return zxid;
    }

    /**
     * 设置艺术标识
     *
     * @param zxid 艺术标识
     */
    public void setZxid(String zxid) {
        this.zxid = zxid;
    }

    /**
     * 获取长标题
     *
     * @return zxtitle - 长标题
     */
    public String getZxtitle() {
        return zxtitle;
    }

    /**
     * 设置长标题
     *
     * @param zxtitle 长标题
     */
    public void setZxtitle(String zxtitle) {
        this.zxtitle = zxtitle;
    }

    /**
     * 获取短标题
     *
     * @return zxshorttitle - 短标题
     */
    public String getZxshorttitle() {
        return zxshorttitle;
    }

    /**
     * 设置短标题
     *
     * @param zxshorttitle 短标题
     */
    public void setZxshorttitle(String zxshorttitle) {
        this.zxshorttitle = zxshorttitle;
    }

    /**
     * 获取艺术类型：个人作品/团队作品
     *
     * @return zxtyp - 艺术类型：个人作品/团队作品
     */
    public String getZxtyp() {
        return zxtyp;
    }

    /**
     * 设置艺术类型：个人作品/团队作品
     *
     * @param zxtyp 艺术类型：个人作品/团队作品
     */
    public void setZxtyp(String zxtyp) {
        this.zxtyp = zxtyp;
    }

    /**
     * 获取艺术类型标识：艺术家/团队标识
     *
     * @return zxtypid - 艺术类型标识：艺术家/团队标识
     */
    public String getZxtypid() {
        return zxtypid;
    }

    /**
     * 设置艺术类型标识：艺术家/团队标识
     *
     * @param zxtypid 艺术类型标识：艺术家/团队标识
     */
    public void setZxtypid(String zxtypid) {
        this.zxtypid = zxtypid;
    }

    /**
     * 获取开始时间
     *
     * @return zxstime - 开始时间
     */
    public Date getZxstime() {
        return zxstime;
    }

    /**
     * 设置开始时间
     *
     * @param zxstime 开始时间
     */
    public void setZxstime(Date zxstime) {
        this.zxstime = zxstime;
    }

    /**
     * 获取结束时间
     *
     * @return zxetime - 结束时间
     */
    public Date getZxetime() {
        return zxetime;
    }

    /**
     * 设置结束时间
     *
     * @param zxetime 结束时间
     */
    public void setZxetime(Date zxetime) {
        this.zxetime = zxetime;
    }

    /**
     * 获取标签
     *
     * @return zxtags - 标签
     */
    public String getZxtags() {
        return zxtags;
    }

    /**
     * 设置标签
     *
     * @param zxtags 标签
     */
    public void setZxtags(String zxtags) {
        this.zxtags = zxtags;
    }

    /**
     * 获取关键字
     *
     * @return zxkeys - 关键字
     */
    public String getZxkeys() {
        return zxkeys;
    }

    /**
     * 设置关键字
     *
     * @param zxkeys 关键字
     */
    public void setZxkeys(String zxkeys) {
        this.zxkeys = zxkeys;
    }

    /**
     * 获取地址
     *
     * @return zxaddr - 地址
     */
    public String getZxaddr() {
        return zxaddr;
    }

    /**
     * 设置地址
     *
     * @param zxaddr 地址
     */
    public void setZxaddr(String zxaddr) {
        this.zxaddr = zxaddr;
    }

    /**
     * 获取作者
     *
     * @return zxauthor - 作者
     */
    public String getZxauthor() {
        return zxauthor;
    }

    /**
     * 设置作者
     *
     * @param zxauthor 作者
     */
    public void setZxauthor(String zxauthor) {
        this.zxauthor = zxauthor;
    }

    /**
     * 获取小编
     *
     * @return zxeditor - 小编
     */
    public String getZxeditor() {
        return zxeditor;
    }

    /**
     * 设置小编
     *
     * @param zxeditor 小编
     */
    public void setZxeditor(String zxeditor) {
        this.zxeditor = zxeditor;
    }

    /**
     * 获取创建者
     *
     * @return zxcreator - 创建者
     */
    public String getZxcreator() {
        return zxcreator;
    }

    /**
     * 设置创建者
     *
     * @param zxcreator 创建者
     */
    public void setZxcreator(String zxcreator) {
        this.zxcreator = zxcreator;
    }

    /**
     * 获取创建时间
     *
     * @return zxcrttime - 创建时间
     */
    public Date getZxcrttime() {
        return zxcrttime;
    }

    /**
     * 设置创建时间
     *
     * @param zxcrttime 创建时间
     */
    public void setZxcrttime(Date zxcrttime) {
        this.zxcrttime = zxcrttime;
    }

    /**
     * 获取备注
     *
     * @return zxnote - 备注
     */
    public String getZxnote() {
        return zxnote;
    }

    /**
     * 设置备注
     *
     * @param zxnote 备注
     */
    public void setZxnote(String zxnote) {
        this.zxnote = zxnote;
    }

    /**
     * 获取来源
     *
     * @return zxfrom - 来源
     */
    public String getZxfrom() {
        return zxfrom;
    }

    /**
     * 设置来源
     *
     * @param zxfrom 来源
     */
    public void setZxfrom(String zxfrom) {
        this.zxfrom = zxfrom;
    }

    /**
     * 获取封面图
     *
     * @return zxpic1 - 封面图
     */
    public String getZxpic1() {
        return zxpic1;
    }

    /**
     * 设置封面图
     *
     * @param zxpic1 封面图
     */
    public void setZxpic1(String zxpic1) {
        this.zxpic1 = zxpic1;
    }

    /**
     * 获取详情图片
     *
     * @return zxpic2 - 详情图片
     */
    public String getZxpic2() {
        return zxpic2;
    }

    /**
     * 设置详情图片
     *
     * @param zxpic2 详情图片
     */
    public void setZxpic2(String zxpic2) {
        this.zxpic2 = zxpic2;
    }

    /**
     * 获取流程状态：0初始，1送审，2已审，3已发
     *
     * @return zxstate - 流程状态：0初始，1送审，2已审，3已发
     */
    public String getZxstate() {
        return zxstate;
    }

    /**
     * 设置流程状态：0初始，1送审，2已审，3已发
     *
     * @param zxstate 流程状态：0初始，1送审，2已审，3已发
     */
    public void setZxstate(String zxstate) {
        this.zxstate = zxstate;
    }

    /**
     * 获取流程标识
     *
     * @return zxflowid - 流程标识
     */
    public String getZxflowid() {
        return zxflowid;
    }

    /**
     * 设置流程标识
     *
     * @param zxflowid 流程标识
     */
    public void setZxflowid(String zxflowid) {
        this.zxflowid = zxflowid;
    }

    /**
     * 获取详细内容
     *
     * @return zxcontent - 详细内容
     */
    public String getZxcontent() {
        return zxcontent;
    }

    /**
     * 设置详细内容
     *
     * @param zxcontent 详细内容
     */
    public void setZxcontent(String zxcontent) {
        this.zxcontent = zxcontent;
    }
}