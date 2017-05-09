package com.creatoo.hn.services.admin.system;

import com.creatoo.hn.mapper.WhTrainMapper;
import com.creatoo.hn.mapper.WhUserMapper;
import com.creatoo.hn.mapper.WhUserTroupeuserMapper;
import com.creatoo.hn.model.WhUser;
import com.creatoo.hn.model.WhgSysUser;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.utils.ReqParamsUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 个人用户中心业务类
 *
 * @author dzl
 */
@Service
public class UserService {
    @Autowired
    private WhUserMapper userMapper;
    @Autowired
    public CommService commService;
    @Autowired
    public WhUserTroupeuserMapper troupeUser;
    @Autowired
    public WhTrainMapper whTrainMapper;

    /**
     * 分页查询用户信息
     *
     * @param request
     * @param whuser
     * @return
     * @throws Exception
     */
    public PageInfo<WhUser> t_srchList4p(HttpServletRequest request, WhUser whuser) throws Exception {
        Map<String, Object> paramMap = ReqParamsUtil.parseRequest(request);

        //分页信息
        int page = Integer.parseInt((String) paramMap.get("page"));
        int rows = Integer.parseInt((String) paramMap.get("rows"));

        //搜索条件
        Example example = new Example(WhUser.class);
        Example.Criteria c = example.createCriteria();

        //名称条件
        if (whuser != null && whuser.getName() != null) {
            c.andLike("name", "%" + whuser.getName() + "%");
            whuser.setName(null);
        }
        if (whuser != null && whuser.getNickname() != null) {
            c.andLike("nickname", "%" + whuser.getNickname() + "%");
            whuser.setNickname(null);
        }
        if (whuser != null && whuser.getPhone() != null) {
            c.andLike("phone", "%" + whuser.getPhone() + "%");
            whuser.setPhone(null);
        }
        if (whuser != null && whuser.getEmail() != null) {
            c.andLike("email", "%" + whuser.getEmail() + "%");
            whuser.setEmail(null);
        }

        //其它条件
        c.andEqualTo(whuser);

        //排序
        if (paramMap.containsKey("sort") && paramMap.get("sort") != null) {
            StringBuffer sb = new StringBuffer((String) paramMap.get("sort"));
            if (paramMap.containsKey("order") && paramMap.get("order") != null) {
                sb.append(" ").append(paramMap.get("order"));
            }
            example.setOrderByClause(sb.toString());
        } else {
            example.setOrderByClause("id desc");
        }

        //分页查询
        PageHelper.startPage(page, rows);
        List<WhUser> list = this.userMapper.selectByExample(example);
        return new PageInfo<WhUser>(list);
    }

    /**
     * 查询用户信息
     *
     * @param id 用户ID
     * @return
     * @throws Exception
     */
    public WhUser t_srchOne(String id) throws Exception {
        WhUser record = new WhUser();
        record.setId(id);
        return this.userMapper.selectOne(record);
    }

    /**
     * 编辑文化馆
     *
     * @param whuser
     * @return
     * @throws Exception
     */
    public WhUser t_edit(WhUser whuser, WhgSysUser sysUser) throws Exception {
        int rows = this.userMapper.updateByPrimaryKeySelective(whuser);
        if (rows != 1) {
            throw new Exception("编辑会员信息失败");
        }
        return whuser;
    }

    /**
     * 删除会员
     *
     * @param ids 文化馆ID
     * @throws Exception
     */
    public void t_del(String ids, WhgSysUser sysUser) throws Exception {
        if (ids != null) {
            String[] idArr = ids.split(",");
            Example example = new Example(WhUser.class);
            Example.Criteria c = example.createCriteria();
            c.andIn("id", Arrays.asList(idArr));
            this.userMapper.deleteByExample(example);
        }
    }

    /**
     * 显示用户列表
     *
     * @return
     */
    public List<WhUser> getList() {

        return this.userMapper.selectAll();
    }

    /**
     * 添加用户信息
     *
     * @throws Exception
     */
    public Object addUser(WhUser whuser) {
        try {
            whuser.setId(this.commService.getKey("whuser"));
            this.userMapper.insert(whuser);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * 根据id获得用户信息
     *
     * @param id
     * @return
     */
    public Object getUserId(Object id) {
        return this.userMapper.selectByPrimaryKey(id);
    }

    /**
     * 删除用户信息
     */
    public int removeUser(String id) {
        return this.userMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改用户信息
     */
    public Object modifyUser(WhUser whuser) {
        return this.userMapper.updateByPrimaryKeySelective(whuser);
    }

    /**
     * 根据条件查询用户
     */
    public Object findPage(int page, int rows) throws Exception {
        // 带条件的分页查询
        Example example = new Example(WhUser.class);
        PageHelper.startPage(page, rows);
        List<WhUser> list = this.userMapper.selectByExample(example);

        // 取分页信息
        PageInfo<WhUser> pageInfo = new PageInfo<WhUser>(list);

        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
        return rtnMap;
    }

    /**
     * 工具栏加载 数据
     *
     * @param page
     * @param rows
     * @param request
     * @return
     */
    public Object loadUser(int page, int rows, WebRequest request) throws Exception {
        Map<String, Object> param = ReqParamsUtil.parseRequest(request);
        PageHelper.startPage(page, rows);

        Example example = new Example(WhUser.class);
        Criteria criteria = example.createCriteria();

        //根据姓名查找用户信息
        if (param.containsKey("name") && param.get("name") != null) {
            String name = (String) param.get("name");
            if (!"".equals(name.trim())) {
                criteria.andLike("name", "%" + name.trim() + "%");
            }
        }

        //根据昵称查找用户信息
        if (param.containsKey("nickname") && param.get("nickname") != null) {
            String nickname = (String) param.get("nickname");
            if (!"".equals(nickname.trim())) {
                criteria.andLike("nickname", "%" + nickname.trim() + "%");
            }
        }

        //根据籍贯查找用户信息
        if (param.containsKey("origo") && param.get("origo") != null) {
            String origo = (String) param.get("origo");
            if (!"".equals(origo.trim())) {
                criteria.andLike("origo", "%" + origo.trim() + "%");
            }
        }

        //根据手机号码查找用户信息
        if (param.containsKey("phone") && param.get("phone") != null) {
            String phone = (String) param.get("phone");
            if (!"".equals(phone.trim())) {
                criteria.andLike("phone", "%" + phone.trim() + "%");
            }
        }
        //根据邮箱地址查找用户信息
        if (param.containsKey("email") && param.get("email") != null) {
            String email = (String) param.get("email");
            if (!"".equals(email.trim())) {
                criteria.andLike("email", "%" + email.trim() + "%");
            }
        }
        //根据实名状态查找用户信息
        if (param.containsKey("isrealname") && param.get("isrealname") != null) {
            String isrealname = (String) param.get("isrealname");
            if (!"".equals(isrealname.trim())) {
                criteria.andEqualTo("isrealname", isrealname);
            }
        }
        List<WhUser> list = this.userMapper.selectByExample(example);
        //List<Map> list = this.troupeUser.selectUser(param);
        PageInfo<WhUser> pinfo = new PageInfo<WhUser>(list);

        Map<String, Object> res = new HashMap<String, Object>();
        res.put("total", pinfo.getTotal());
        res.put("rows", pinfo.getList());

        return res;
    }

    /**
     * 实名审核
     *
     * @param user
     */
    public WhUser checkUserReal(WhUser user) throws Exception {
        this.userMapper.updateByPrimaryKeySelective(user);
        return this.userMapper.selectByPrimaryKey(user.getId());
    }

    /**
     * 查找历史培训
     *
     * @return
     */
    public Map<String, Object> selOldTra(Map<String, Object> param) throws Exception {
        //分页信息
        int page = Integer.parseInt((String) param.get("page"));
        int rows = Integer.parseInt((String) param.get("rows"));

        //带条件的分页查询
        PageHelper.startPage(page, rows);
        List<Map> list = this.whTrainMapper.selOldTra(param);
        ;

        // 取分页信息
        PageInfo<Map> pageInfo = new PageInfo<Map>(list);

        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
        return rtnMap;
    }

    /**
     * 查找历史场馆
     *
     * @param uid
     * @return
     */
    public Map<String, Object> selOldVen(Map<String, Object> param) throws Exception {
        //分页信息
        int page = Integer.parseInt((String) param.get("page"));
        int rows = Integer.parseInt((String) param.get("rows"));

        //带条件的分页查询
        PageHelper.startPage(page, rows);
        List<Map> list = this.whTrainMapper.selOldVen(param);

        // 取分页信息
        PageInfo<Map> pageInfo = new PageInfo<Map>(list);

        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
        return rtnMap;
        //return
    }

    /**
     * 查找历史活动
     *
     * @param paramMap
     * @return
     */
    public Map<String, Object> selOldAct(Map<String, Object> param) {
        //分页信息
        int page = Integer.parseInt((String) param.get("page"));
        int rows = Integer.parseInt((String) param.get("rows"));

        //带条件的分页查询
        PageHelper.startPage(page, rows);
        List<Map> list = this.whTrainMapper.selOldAct(param);

        // 取分页信息
        PageInfo<Map> pageInfo = new PageInfo<Map>(list);

        Map<String, Object> rtnMap = new HashMap<String, Object>();
        rtnMap.put("total", pageInfo.getTotal());
        rtnMap.put("rows", pageInfo.getList());
        return rtnMap;
    }

}
