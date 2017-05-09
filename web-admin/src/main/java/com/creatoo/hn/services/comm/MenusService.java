package com.creatoo.hn.services.comm;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.subject.Subject;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.InputStream;
import java.util.*;
import java.util.Map.Entry;

/**
 * Created by rbg on 2017/3/15.
 */
public class MenusService {
    Logger log = Logger.getLogger(this.getClass());

    private List<Map> meunsTreeList = new ArrayList<Map>();

    private List<Map> meunsList = new ArrayList<Map>();

    private Map<String, String> optsList = new HashMap<String,String>();

    /**
     * 按登录者权限返回可操作的树
     * @param currentUser
     * @return
     */
    public List<Map> getMeunsTree4Auth(Subject currentUser){
        List<Map> restMap = new ArrayList<>();
        for(Map meun : this.meunsTreeList){
            Map authMap = getAuthMeunMap(meun, currentUser);
            if (authMap!=null){
                restMap.add(authMap);
            }
        }

        return restMap;
    }

    private Map getAuthMeunMap(Map meunMap ,Subject currentUser) {
        String type = (String) meunMap.get("type");

        //菜单项时，查验是否有权限
        if (type != null && "1".equals(type)){
            String permission = meunMap.get("id")+":view";
            if (currentUser.isPermitted(permission) ){
                return copyMap(meunMap);
            } else {
                return null;
            }
        }

        //菜单组时
        Map mmp = copyMap(meunMap);
        if (type != null && "0".equals(type)){
            //无子级不用添加
            List children = (List) meunMap.get("children");
            if (children != null) {
                //装入 权限相子列表
                List clist = (List) mmp.get("children");
                if (clist == null){
                    clist = new ArrayList();
                    mmp.put("children", clist);
                }
                for(Object obj : children){
                    Map mMap = (Map) obj;
                    Map cmmp = getAuthMeunMap(mMap, currentUser);
                    if (cmmp != null){
                        clist.add(cmmp);
                    }
                }
            }
        }

        if (mmp.get("children") == null ){
            return null;
        }
        List ls = (List) mmp.get("children");
        if (ls == null || ls.size() == 0){
            return null;
        }
        return mmp;
    }

    private Map copyMap(Map mp){
        Map res = new HashMap();
        if (mp == null) return res;
        for(Object key : mp.keySet() ){
            String k = (String) key;
            if ("children".equalsIgnoreCase(k)){
                continue;
            }
            res.put(k, mp.get(k));
        }
        return res;
    }

    /**
     * 获取菜单树
     * @return
     */
    public List<Map> getMeunsTreeList(){
        return this.meunsTreeList;
    }

    /**
     * 获取菜单项列表
     * @return
     */
    public List<Map> getMeunsList(){
        return this.meunsList;
    }

    /**
     * 获取菜单操作定义项
     * @return
     */
    public Map<String, String> getOptsList(){
        return this.optsList;
    }


    /**
     * 初始化此Bean时，调用解析菜单
     */
    @PostConstruct
    private void autoloadMenuXml(){
        try {
            File menuxml = this.getMenuXmlFile();
            this.parseMenu(menuxml);
        } catch (Exception e) {
            log.error("加载系统菜单配置文件出错", e);
        }
    }

    /**
     * 获取menuxml配置文件
     * @return
     * @throws Exception
     */
    private File getMenuXmlFile() throws Exception{
        Resource source = new ClassPathResource("cfgdata/sys_menus.xml");
        return source.getFile();
    }

    /*public static void main(String[] args) {
        String filepath = "D:\\IdeaProjects\\szwhg-dg\\src\\main\\resources\\cfgdata\\sys_menus.xml";
        MenusService ms = new MenusService();
        try {
            ms.parseMenu(new File(filepath));

            List tes = ms.getMeunsList();

            List tts = ms.getMeunsTreeList();

            List pts = ms.getOptsList();

            System.out.println(" ================================="+ tes.size());
            System.out.println(Arrays.deepToString(tes.toArray()));
            System.out.println(" ================================="+ tts.size());
            System.out.println(Arrays.deepToString(tts.toArray()));
            System.out.println(" ================================="+ pts.size());
            System.out.println(Arrays.deepToString(pts.toArray()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /**
     * 解析 menuxml 到菜单载体
     * @param menuxml
     */
    public void parseMenu(File menuxml) throws Exception{
        SAXReader saxReader = new SAXReader();
        Document doc = saxReader.read(menuxml);
        //xml root
        Element root = doc.getRootElement();

        this.parseTreeListMeun(root);

        this.parseOptsList(root);
    }

    /**
     * 以传入的根 取到相关菜单配置的 list-map 树
     * @param root
     * @throws Exception
     */
    private void parseTreeListMeun(Element root) throws Exception{
        //xml menus
        List<Element> menus_list = root.elements("menus");
        for (Element menues : menus_list){
            Iterator<Element> it = menues.elementIterator();
            while (it.hasNext()){
                this._parseTreeMenu(it.next(), this.meunsTreeList, null);
            }
        }
    }

    /**
     * 回调取子菜单树
     * @param el
     * @param list
     */
    private void _parseTreeMenu(Element el, List<Map> list, String parent) throws Exception{
        String ename = el.getName();
        if (ename == null || !"menu".equalsIgnoreCase(ename)){
            return;
        }

        Map menuMap = this.elementMenu2Map(el);
        if (parent!=null){
            menuMap.put("parent", parent);
        }

        String state = (String) menuMap.get("state");
        String type = (String) menuMap.get("type");
        if (state != null && "1".equals(state)){
            list.add(menuMap);
            if("1".equals(type)) {
                this.meunsList.add(menuMap);
            }
        }

        if (type!= null && "0".equals(type)){
            List children = (List) menuMap.get("children");
            if (children == null){
                children = new ArrayList();
                menuMap.put("children", children);
            }

            Iterator<Element> it = el.elementIterator();
            while (it.hasNext()){
                this._parseTreeMenu(it.next(), children, (String) menuMap.get("id"));
            }
        }
    }

    /**
     * 以传入的根 取到相关菜单配置的 list-map 仅菜单项列表
     * @param root
     * @throws Exception
     */
    /*private void parseListMenu(Element root) throws Exception{
        List<Element> menuList = root.elements("menu");
        for(Element el : menuList){
            Map menuMap = this.elementMenu2Map(el);
            if (menuMap.get("type") == null || "1".equals( menuMap.get("type") ) ){
                continue;
            }
            if (menuMap.get("state") == null || "0".equals( menuMap.get("state") ) ){
                continue;
            }

            this.meunsList.add(menuMap);
        }
    }*/

    private void parseOptsList(Element root) throws Exception{
        List<Element> optList = root.elements("opts");
        for (Element els : optList){
            List<Element> _el = els.elements("opt");
            if(_el != null){
                for(Element el : _el){
                    String id = el.attributeValue("id");
                    String text = el.getText();
                    if (id!=null && !id.isEmpty()){
                        id = id.trim();
                        this.optsList.put(id, text);
                    }
                }
            }
        }
    }

    /**
     * menu转成Map
     * @param el
     * @return
     */
    private Map elementMenu2Map(Element el) throws Exception{
        Map map = new HashMap();
        if (el == null){
            return map;
        }

        Iterator<Attribute> it = el.attributeIterator();
        while (it.hasNext()){
            Attribute att = it.next();
            map.put(att.getName(), att.getValue().trim());
        }
        return map;
    }



}
