package com.creatoo.hn.services.comm;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.creatoo.hn.ext.emun.*;
import com.creatoo.hn.mapper.*;
import com.creatoo.hn.model.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 生成主键值的服务类
 * @author wangxl
 * @version 20160928
 */
@SuppressWarnings("ALL")
@Service
public class CommService {
	/**
	 * 日志
	 */
	Logger log = Logger.getLogger(this.getClass());

	/**
	 * 分类表Mapper对象
	 */
	@Autowired
	private WhgYwiTypeMapper whgYwiTypeMapper;

	/**
	 * 标签表Mapper对象
	 */
	@Autowired
	private WhgYwiTagMapper whgYwiTagMapper;

	/**
	 * 关键字表Mapper对象
	 */
	@Autowired
	private WhgYwiKeyMapper whgYwiKeyMapper;

	/**
	 * 序号表的Mapper对象
	 */
	@Autowired
    private WhSerialnoMapper whSerialnoMapper;

	/**
	 * commonMapper对象
	 */
	@Autowired
	private CommonMapper commonMapper;
	
	/**
	 * 分类表的Mapper对象
	 */
	@Autowired
    private WhTypMapper whTypMapper;
	
	/**
	 * 标签表的Mapper对象
	 */
	@Autowired
    private WhTagMapper whTagMapper;
	
	/**
	 * 关键字表的Mapper对象
	 */
	@Autowired
    private WhKeyMapper whKeyMapper;
	
	/**
	 * 资讯栏目
	 */
	@Autowired
	private WhZxColumnMapper columnMapper;

	/**
	 * 文化馆Dao
	 */
	@Autowired
	private WhgSysCultMapper whgSysCultMapper;

	/**
	 * 文化品牌
	 */
	@Autowired
	private WhgYwiWhppMapper whgYwiWhppMapper;

	/**
	 * 资源管理mapper
	 */
	@Autowired
	private WhgComResourceMapper whgComResourceMapper;

	/**
	 * 系统操作日志DAO
	 */
	@Autowired
	private WhgYwiNoteMapper whgYwiNoteMapper;

	/**
	 * 资讯信息关联mapper
	 */
	@Autowired
	private WhgPubInfoMapper whgPubInfoMapper;

	/**
	 * 资讯信息mapper
	 */
	@Autowired
	private WhZxColinfoMapper whZxColinfoMapper;

	/**
	 * 保存系统的操作日志
	 * @param note 操作日志对象
	 * @throws Exception
	 */
	public void saveOptNote(WhgYwiNote note)throws Exception{
		if(note != null){
			note.setId(this.getKey("whg_ywi_note"));
			this.whgYwiNoteMapper.insertSelective(note);//.insert(note);
		}
	}
	
	/**
	 * 根据表名获取需要的主键值
	 * @param tableName 不区分大小写表名
	 * @return 主键值
	 * @throws Exception
	 */
	public String getKey(String tableName)throws Exception{
		//表名转小写，日期字符串yyyyMMdd
		String tname = tableName.toLowerCase();
		String cdate = new java.text.SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis());
		
		//根据表名和日期查询当前值
		WhSerialno whSerialno = null;
		Example example = new Example(WhSerialno.class);
		example.createCriteria().andEqualTo("tname", tname).andEqualTo("cdate", cdate);
		List<WhSerialno> lst = this.whSerialnoMapper.selectByExample(example);
		if(lst != null && lst.size() > 0){
			whSerialno = lst.get(0);
		}
		
		//获取最新序号
		int curval = 1;
		if(whSerialno == null){
			whSerialno = new WhSerialno();
			whSerialno.setTname(tname);
			whSerialno.setCdate(cdate);
			whSerialno.setCurval(curval);
			int rows = this.whSerialnoMapper.insert(whSerialno);
			if(rows != 1){
				throw new Exception("生成表"+tname+"的主键时发生错误,无法插入数据");
			}
		}else{
			curval = whSerialno.getCurval()+1;
			whSerialno.setCurval(curval);
			int rows = this.whSerialnoMapper.updateByPrimaryKey(whSerialno);
			if(rows != 1){
				throw new Exception("生成表"+tname+"的主键时发生错误,无法插入数据");
			}
		}
		
		//删除非当日的数据
		Example example2 = new Example(WhSerialno.class);
		example2.createCriteria().andEqualTo("tname", tname).andNotEqualTo("cdate", cdate);
		this.whSerialnoMapper.deleteByExample(example2);
		
		//主键格式 yyyyMMdd00000000
		String keyVal = cdate + String.format("%08d", curval);
		
		return keyVal;
	}

	/**
	 * 生成订单号: getOrderId(EnumOrderType.ORDER_ACT.getValue()),取活动订单号
	 * @param type 订单类型, 详见EnumOrderType
	 * @return 新的订单号
	 * @throws Exception
	 */
	public String getOrderId(int type)throws Exception{
		//表名转小写，日期字符串yyyyMMdd
		String tname = "order_id_"+type;
		String cdate = new java.text.SimpleDateFormat("yyMMdd").format(System.currentTimeMillis());

		//根据表名和日期查询当前值
		WhSerialno whSerialno = null;
		Example example = new Example(WhSerialno.class);
		example.createCriteria().andEqualTo("tname", tname).andEqualTo("cdate", cdate);
		List<WhSerialno> lst = this.whSerialnoMapper.selectByExample(example);
		if(lst != null && lst.size() > 0){
			whSerialno = lst.get(0);
		}

		//获取最新序号
		int curval = 1;
		if(whSerialno == null){
			whSerialno = new WhSerialno();
			whSerialno.setTname(tname);
			whSerialno.setCdate(cdate);
			whSerialno.setCurval(curval);
			int rows = this.whSerialnoMapper.insert(whSerialno);
			if(rows != 1){
				throw new Exception("生成表"+tname+"的主键时发生错误,无法插入数据");
			}
		}else{
			curval = whSerialno.getCurval()+1;
			whSerialno.setCurval(curval);
			int rows = this.whSerialnoMapper.updateByPrimaryKey(whSerialno);
			if(rows != 1){
				throw new Exception("生成表"+tname+"的主键时发生错误,无法插入数据");
			}
		}

		//删除非当日的数据
		Example example2 = new Example(WhSerialno.class);
		example2.createCriteria().andEqualTo("tname", tname).andNotEqualTo("cdate", cdate);
		this.whSerialnoMapper.deleteByExample(example2);

		//主键格式   [type]yyMMdd00000 总共12位
		String keyVal = type + cdate + String.format("%05d", curval);

		return keyVal;
	}
	
	/**
	 * 获取配置在whtyp中的各种类型数据
	 * @param type 指定类型
	 * @return 指定类型的数据
	 * @throws Exception
	 */
	public List<WhTyp> findWhtyp(String type)throws Exception{
		List<WhTyp> typLst = new ArrayList<WhTyp>();
		if(type == null || "".equals(type.trim()) || "zxcolumn".equals(type) ){
			Example example_col = new Example(WhZxColumn.class);
			example_col.createCriteria().andEqualTo("colstate", 1);
			example_col.setOrderByClause("colidx");
			List<WhZxColumn> colLst = this.columnMapper.selectByExample(example_col);
			if(colLst != null){
				for(WhZxColumn column : colLst){
					WhTyp _typ = new WhTyp();
					_typ.setType("zxcolumn");
					_typ.setTypid( column.getColid() );
					_typ.setTypidx( column.getColidx());
					_typ.setTypmemo( column.getColtitle() );
					_typ.setTypname( column.getColtitle() );
					_typ.setTyppid( column.getColpid() );
					
					_typ.setTypstate( column.getColstate()+"" );
					typLst.add(_typ);
				}
			}
		}
		
		Example example = new Example(WhTyp.class);
		Criteria criteria = example.createCriteria();
		if(type != null && !"".equals(type.trim())){
			criteria.andEqualTo("type", type);
		}
		criteria.andEqualTo("typstate", "1");
		example.setOrderByClause("typidx");
		typLst.addAll( this.whTypMapper.selectByExample(example) );
		
		return typLst;
	}
	
	/**
	 * 获取配置在whtag中的各种类型数据
	 * @param type 指定类型
	 * @return 指定类型的数据
	 * @throws Exception
	 */
	public List<WhTag> findWhTag(String type)throws Exception{
		Example example = new Example(WhTag.class);
		example.createCriteria().andEqualTo("type", type);
		example.setOrderByClause("idx");
		return this.whTagMapper.selectByExample(example);
	}
	
	/**
	 * 获取配置在whtag中的各种类型数据
	 * @param type 指定类型
	 * @return 指定类型的数据
	 * @throws Exception
	 */
	public List<WhKey> findWhKey(String type)throws Exception{
		Example example = new Example(WhKey.class);
		example.createCriteria().andEqualTo("type", type);
		example.setOrderByClause("idx");
		return this.whKeyMapper.selectByExample(example);
	}

	/**
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> globalsrchcontent(Map<String, Object> param)throws Exception{
		//分页信息
		int page = Integer.parseInt((String)param.get("page"));
		int rows = Integer.parseInt((String)param.get("rows"));

        //查找所有相关的 关键字 id
        Example example = new Example(WhgYwiKey.class);
        example.createCriteria().andLike("name", param.get("srchkey").toString());
        List<WhgYwiKey> keys = this.whgYwiKeyMapper.selectByExample(example);
        if (keys!=null && keys.size()>0){
            List<String> ekeys = new ArrayList();
            for(WhgYwiKey key : keys){
                ekeys.add(key.getId());
            }
            param.put("ekeys", ekeys);
        }

		//带条件的分页查询
		PageHelper.startPage(page, rows);
		List<Map<String, Object>> list = this.commonMapper.searchGlobalContent(param);

		// 取分页信息
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String, Object>>(list);

		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("total", pageInfo.getTotal());
		rtnMap.put("rows", pageInfo.getList());
		return rtnMap;
	};

	/**
	 * 获取指定 ext/emun包下面的枚举变量.
	 * type规则: 枚举类名
	 * 如要获取EnumBizState中的枚举变量，type参数为EnumBizState
	 * 如要获取EnumBMState中的枚举变量，type参数为EnumBMState
	 * @param type 枚举类名字符串
	 * @return List<Map<String, String>>格式数据
	 * @throws Exception
	 */
	public List<Map<String, String>> findEnumState(String type)throws Exception{
		List<Map<String, String>> statelist = new ArrayList<Map<String, String>>();
		if(type != null && !type.isEmpty()){
			Class<?> class1 = Class.forName("com.creatoo.hn.ext.emun."+type);
			Object[] objs = class1.getEnumConstants();
			for(Object obj : objs){
				Method valueMethod = obj.getClass().getMethod("getValue");
				Method nameMethod = obj.getClass().getMethod("getName");
				String _val = valueMethod.invoke(obj).toString();
				String _nam = nameMethod.invoke(obj).toString();
				Map<String, String> map = new HashMap<String, String>();
				map.put("name", _nam);
				map.put("value", _val);
				statelist.add(map);
			}
		}
		return statelist;
	}

	/**
	 * 获取关键字
	 * @return
	 * @throws Exception
	 */
	public List<WhgYwiKey> findYwiKey()throws Exception{
		Example example = new Example(WhgYwiKey.class);
		example.createCriteria().andEqualTo("state", EnumState.STATE_YES.getValue())
				.andEqualTo("delstate", EnumDelState.STATE_DEL_NO.getValue());
		example.setOrderByClause("idx");
		return this.whgYwiKeyMapper.selectByExample(example);
	}

	/**
	 * 根据关键字类别获取关键字列表
	 * @param type 关键字分类
	 * @return 关键字列表
	 * @throws Exception
	 */
	public List<WhgYwiKey> findYwiKey(String type)throws Exception{
		Example example = new Example(WhgYwiKey.class);
		example.createCriteria().andEqualTo("state", EnumState.STATE_YES.getValue())
				.andEqualTo("delstate", EnumDelState.STATE_DEL_NO.getValue())
				.andEqualTo("type", type);
		example.setOrderByClause("idx");
		return this.whgYwiKeyMapper.selectByExample(example);
	}

	/**
	 * 获取标签
	 * @return
	 * @throws Exception
	 */
	public List<WhgYwiTag> findYwiTag()throws Exception{
		Example example = new Example(WhgYwiTag.class);
		example.createCriteria().andEqualTo("state", EnumState.STATE_YES.getValue())
				.andEqualTo("delstate", EnumDelState.STATE_DEL_NO.getValue());
		example.setOrderByClause("idx");
		return this.whgYwiTagMapper.selectByExample(example);
	}

	/**
	 * 根据标签分类取标签列表
	 * @param type 标签分类
	 * @return 标签列表
	 * @throws Exception
	 */
	public List<WhgYwiTag> findYwiTag(String type)throws Exception{
		Example example = new Example(WhgYwiTag.class);
		example.createCriteria().andEqualTo("state", EnumState.STATE_YES.getValue())
				.andEqualTo("delstate", EnumDelState.STATE_DEL_NO.getValue())
				.andEqualTo("type", type);
		example.setOrderByClause("idx");
		return this.whgYwiTagMapper.selectByExample(example);
	}

	/**
	 * 获取分类
	 * @return
	 * @throws Exception
	 */
	public List<WhgYwiType> findYwiType()throws Exception{
		Example example = new Example(WhgYwiType.class);
		example.createCriteria().andEqualTo("state", EnumState.STATE_YES.getValue())
				.andEqualTo("delstate", EnumDelState.STATE_DEL_NO.getValue());
		example.setOrderByClause("idx");
		return this.whgYwiTypeMapper.selectByExample(example);
	}

	/**
	 * 根据分类类别取分类列表
	 * @param type 分类类别
	 * @return 分类列表
	 * @throws Exception
	 */
	public List<WhgYwiType> findYwiType(String type)throws Exception{
		Example example = new Example(WhgYwiType.class);
		example.createCriteria().andEqualTo("state", EnumState.STATE_YES.getValue())
				.andEqualTo("delstate", EnumDelState.STATE_DEL_NO.getValue())
				.andEqualTo("type", type);
		example.setOrderByClause("idx");
		return this.whgYwiTypeMapper.selectByExample(example);
	}

	/**
	 * 查询文化品牌
	 * @return
	 * @throws Exception
	 */
	public List<WhgYwiWhpp> findBrand()throws Exception{
		Example example = new Example(WhgYwiWhpp.class);
		example.createCriteria().andEqualTo("state", EnumState.STATE_YES.getValue())
				.andEqualTo("delstate", EnumDelState.STATE_DEL_NO.getValue());
		example.setOrderByClause("crtdate desc");
		return this.whgYwiWhppMapper.selectByExample(example);
	}

	/**
	 * 查询文化馆
	 * @return 所有可用的文化改名字列表
	 * @throws Exception
	 */
	public List<WhgSysCult> findCult()throws Exception{
		Example example = new Example(WhgSysCult.class);
		example.createCriteria().andEqualTo("state", EnumState.STATE_YES.getValue())
				.andEqualTo("delstate", EnumDelState.STATE_DEL_NO.getValue());
		example.setOrderByClause("crtdate desc");
		return whgSysCultMapper.selectByExample(example);
	}

	/**
	 * 查询资源
	 * @param enttype 资源类型（1图片/2视频/3音频）
	 * @param reftype 实体类型（1、培训 2、活动 3、场馆）
	 * @param refid	实体id(培训/活动/场馆0.的ID)
	 * @return
     * @throws Exception
     */
	public List<WhgComResource> findRescource(String enttype, String reftype, String refid)throws Exception{
		Example example = new Example(WhgComResource.class);
		example.createCriteria().andEqualTo("enttype", enttype).andEqualTo("reftype", reftype).andEqualTo("refid",refid);
		example.setOrderByClause("redate desc");
		return whgComResourceMapper.selectByExample(example);
	}

	/**
	 * 查询实体关联的资讯信息
	 * @param entityid 实体ID
	 * @param clnftype 栏目ID
     * @return
     */
	public List<WhZxColinfo> findColinfo(String entityid, String clnftype){
		WhZxColinfo colinfo = new WhZxColinfo();
		List<WhZxColinfo> list = new ArrayList<>();
		Example example = new Example(WhgPubInfo.class);
		example.createCriteria().andEqualTo("entityid",entityid).andEqualTo("clnftype",clnftype);
		List<WhgPubInfo> Info = whgPubInfoMapper.selectByExample(example);
		if(Info.size() > 0){
			for(int i = 0; i < Info.size(); i++){
				String clnfid = Info.get(i).getClnfid();
				Example example1 = new Example(WhZxColinfo.class);
				Criteria c1 = example1.createCriteria();
				c1.andEqualTo("clnfid",clnfid);
				example1.setOrderByClause("clnfcrttime desc");
				List<WhZxColinfo> colinfoList = whZxColinfoMapper.selectByExample(example1);
				if(colinfoList.size() > 0){
					list.add(colinfoList.get(0));
				}
			}
		}
		return list;
	}
}
