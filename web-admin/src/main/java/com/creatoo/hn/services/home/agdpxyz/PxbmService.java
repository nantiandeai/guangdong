package com.creatoo.hn.services.home.agdpxyz;

import com.creatoo.hn.ext.emun.EnumBMState;
import com.creatoo.hn.ext.emun.EnumDelState;
import com.creatoo.hn.ext.emun.EnumState;
import com.creatoo.hn.ext.emun.EnumTypeClazz;
import com.creatoo.hn.mapper.*;
import com.creatoo.hn.model.*;
import com.creatoo.hn.services.comm.CommService;
import com.creatoo.hn.services.comm.SMSService;
import com.creatoo.hn.utils.IdUtils;
import com.creatoo.hn.utils.WhConstance;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 培训报名服务类
 * Created by tangwei on 2017/4/6.
 */
@Service
public class PxbmService {
    Logger log = Logger.getLogger(this.getClass());

    @Autowired
    private CommService commService;

    @Autowired
    public WhgTraMapper whTrainMapper;

    @Autowired
    public WhgTraEnrolMapper WhgTraEnrolMapper;

    /**
     * 培训课程DAO
     */
    @Autowired
    public WhgTraCourseMapper whgTraCourseMapper;

    /**
     * 培训报名签到DAO
     */
    @Autowired
    public WhgTraEnrolCourseMapper whgTraEnrolCourseMapper;

    @Autowired
    private WhUserMapper userMapper;

    @Autowired
    private SMSService smsService;

    private final  String SUCCESS="0";//成功
    /**
     * 检查培训报名
     * @param trainId
     * @return
     */
    public String checkApplyTrain(String trainId,String userId)throws Exception{
        WhgTra train = whTrainMapper.selectByPrimaryKey(trainId);
        //培训不存在,或者状态不是已发布
        if(train == null || train.getState() != 6){
            return "100";
        }
        //培训报名已结束
        if(train.getEnrollendtime().before(Calendar.getInstance().getTime())){
            return "101";
        }
        //培训报名额已满
        if(!checkTrainMaxNumber(train)){
            return "102";
        }
        //培训报名重复
        if(!checkExistEnrol(train.getId(),userId)){
            return "103";
        }
        //实名制验证
        if(train.getIsrealname() ==1){
            WhUser user = userMapper.selectByPrimaryKey(userId);
            if(user.getIsrealname() == null || user.getIsrealname().intValue() != 1){
                return "104";
            }
        }
        //获取普及班分类ID
        List<WhgTraEnrol> list = new ArrayList();
        /*String etype = "";
        List<WhgYwiType> wyts = this.commService.findYwiType(EnumTypeClazz.TYPE_TRAIN.getValue());
        if(wyts != null){
            for(WhgYwiType wyt : wyts){
                if(WhConstance.getSysProperty("whg.sys.type.tra.pjb", "普及班").equals(wyt.getName())){
                    etype = wyt.getId();
                    break;
                }
            }
        }*/
        /*if(train.getEtype().equals(etype)){
            //普及班验证
            if(!"".equals(etype)) {*/
                //String uid = enrol.getUserid();
                Example example = new Example(WhgTraEnrol.class);
                example.createCriteria().andEqualTo("userid", userId).andIn("state", Arrays.asList(1, 6, 4));
                list = this.WhgTraEnrolMapper.selectByExample(example);
                int listSize = 0;
                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        String traid = list.get(i).getTraid();
                        Example example1 = new Example(WhgTra.class);
                        example1.createCriteria().andEqualTo("id", traid).andEqualTo("state", 6);
                        int count = this.whTrainMapper.selectCountByExample(example1);
                        listSize += count;
                        if (listSize >= 2) {
                            return "106";  //已经报了两场普及班
                        }

                    }
                }
      /*      }
        }*/
        return SUCCESS;
    }

    /**
     * 根据ID获取培训信息
     * @param trainId
     * @return
     */
    public WhgTra getTrainById(String trainId) throws Exception{
        return whTrainMapper.selectByPrimaryKey(trainId);
    }

    /**
     * 检查是否超出报名名额
     * @param train
     */
    private Boolean checkTrainMaxNumber (WhgTra train){
        Example example  = new Example(WhgTraEnrol.class);
        Example.Criteria c = example.createCriteria();
        c.andEqualTo("traid", train.getId());
        c.andIn("state", Arrays.asList(1,4,6));
        int count = WhgTraEnrolMapper.selectCountByExample(example);
        if(count >= train.getMaxnumber()){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 检查报名是否重复
     * @param trainId
     * @param userId
     * @return
     */
    private Boolean checkExistEnrol (String trainId,String userId){
        Example example  = new Example(WhgTraEnrol.class);
        Example.Criteria c = example.createCriteria();
        c.andEqualTo("traid",trainId);
        c.andEqualTo("userid",userId);
        c.andIn("state", Arrays.asList(1,4,6));
        int count =  WhgTraEnrolMapper.selectCountByExample(example);
        return count == 0 ? Boolean.TRUE:Boolean.FALSE;
    }

    /**
     * 添加培训报名
     * @param enrol
     * @param enrolBirthdayStr
     * @param userId
     * @return
     * @throws Exception
     */
    public int addTranEnrol(WhgTraEnrol enrol,String enrolBirthdayStr,String userId)throws Exception{
        WhgTra train = getTrainById(enrol.getTraid());
        String id = commService.getKey("whg_tra_enrol");
        //订单号和id保持一致
        enrol.setId(id);
        enrol.setOrderid(id);
        int state = EnumBMState.BM_SQ.getValue();
        Date now = Calendar.getInstance().getTime();
        //先报先得培训,直接报名通过
        if(train.getIsbasicclass() == 2){
            state = EnumBMState.BM_CG.getValue();
        }
        if(StringUtils.isNotEmpty(enrolBirthdayStr)){
            String []parsePatterns ={"yyyy-MM-dd"};
            enrol.setBirthday(DateUtils.parseDate(enrolBirthdayStr,parsePatterns));
        }

        enrol.setState(state);
        enrol.setStatemdfdate(now);
        enrol.setStatemdfuser(userId);
        enrol.setUserid(userId);
        enrol.setCrttime(now);
        int result = WhgTraEnrolMapper.insertSelective(enrol);
        //发送短信
        if(result > 0 && state == EnumBMState.BM_CG.getValue()){
            Map<String,String> _map = new HashMap<>();
            _map.put("name",enrol.getRealname());
            _map.put("title",train.getTitle());
            _map.put("starttime",DateFormatUtils.format(train.getStarttime(),"yyyy-MM-dd HH:mm:ss"));
            _map.put("endtime",DateFormatUtils.format(train.getStarttime(),"yyyy-MM-dd HH:mm:ss"));
            String tempCode = "TRA_VIEW_PASS";
            this.smsService.t_sendSMS(enrol.getContactphone(),tempCode,_map);
        }
        return result;
    }

    /**
     * 验证年龄段
     */
    public String validAge(WhgTraEnrol enrol,String enrolBirthdayStr,String userid)throws Exception{

        //年龄段验证
        String[] age = new String[2];
        Date nowDate = new Date();
        WhgTra train = getTrainById(enrol.getTraid());
        String _age = train.getAge();
        //适合年龄
        if(!"".equals(_age) && _age != null){
            age = _age.split(",");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int bir = sdf.parse(enrolBirthdayStr).getYear();
        int _now = sdf.parse(sdf.format(nowDate)).getYear();
        int a = _now - bir;
//        Long sec = a/(1000*60);
//        Long year = sec/(60*24*365);
        if(!"".equals(age[0]) && age[0] != null && !"".equals(age[1]) && age[1] != null){
            if(!(a >= Integer.parseInt(age[0]) && (Integer.parseInt(age[1])>= a))){
                return "107"; //年龄段不符合培训要求
            };
        }



        return SUCCESS;
    }

    /**
     * 培训签到
     * @param courseId 课程ID
     * @param userId 会员ID
     * @return 100-签到成功; 101-已经签到; 102-已签到课程已取消; 109-签到异常
     * @throws Exception
     */
    public String signup(String traId, String enrolId, String courseId, String userId)throws Exception{
        String rtnCode = "100";//签到成功

        try {
            //查询签到信息
            WhgTraEnrolCourse record = new WhgTraEnrolCourse();
            record.setTraid(traId);
            record.setEnrolid(enrolId);
            record.setCourseid(courseId);
            record.setUserid(userId);
            record = this.whgTraEnrolCourseMapper.selectOne(record);

            if (record != null) {//有签到信息
                if(record.getSign().intValue() != 1){
                    record.setSign(1);//
                    record.setSigntime(new Date());
                }else{
                    rtnCode = "101";//已签到
                }
            } else {//没有签到信息
                //查询课程信息
                WhgTraCourse course = new WhgTraCourse();
                course.setId(courseId);
                course.setDelstate(EnumDelState.STATE_DEL_NO.getValue());
                course.setState(EnumState.STATE_YES.getValue());
                course = this.whgTraCourseMapper.selectOne(course);
                if(course != null && course.getId() != null){
                    record = new WhgTraEnrolCourse();
                    record.setId(commService.getKey("whg_tra_enrol_course"));
                    record.setSign(1);//
                    record.setSigntime(new Date());
                    record.setCoursestime(course.getStarttime());
                    record.setCoursestime(course.getEndtime());
                }else{
                    rtnCode = "102";//已签到课程已取消
                }
            }
        }catch (Exception e){
            rtnCode = "109";//签到失败
        }

        return rtnCode;
    }

    /**
     * 根据报名订单号查询所有课程
     * @param orderId 培训报名列表
     * @return
     */
    public List<Map<String, String>> queryEnrolCourseList(String orderId)throws Exception{
        List<Map<String, String>> list = new ArrayList<>();


        //查询报名记录得到培训ID
        WhgTraEnrol record = new WhgTraEnrol();
        record.setOrderid(orderId);
        record = this.WhgTraEnrolMapper.selectOne(record);//.selectByExample(example);

        //根据报名ID取已签到信息
        Example example2 = new Example(WhgTraEnrolCourse.class);
        example2.createCriteria().andEqualTo("enrolid", record.getId());
        List<WhgTraEnrolCourse> clist = this.whgTraEnrolCourseMapper.selectByExample(example2);
        Map<String, WhgTraEnrolCourse> courseMap = new HashMap<String, WhgTraEnrolCourse>();
        if(clist != null){
            for(WhgTraEnrolCourse ec : clist){
                courseMap.put(ec.getCourseid(), ec);
            }
        }

        //根据培训ID查询所有课程
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String traid = record.getTraid();
        Example example = new Example(WhgTraCourse.class);
        example.createCriteria().andEqualTo("traid", traid)
                .andEqualTo("state", EnumState.STATE_YES.getValue())
        .andEqualTo("delstate", EnumDelState.STATE_DEL_NO.getValue());
        example.setOrderByClause("starttime");
        List<WhgTraCourse> listCourse = this.whgTraCourseMapper.selectByExample(example);
        if(listCourse != null){
            for(WhgTraCourse course : listCourse){
                Map<String, String> cmap = new HashMap<String, String>();
                cmap.put("traid", traid);
                cmap.put("enrolid", record.getId());
                cmap.put("courseid", course.getId());
                cmap.put("starttime", sdf.format(course.getStarttime()));
                cmap.put("endtime", sdf.format(course.getEndtime()));

                String sign = "0";//签到状态 0-未签到, 1-已签到
                String signtime = "";//签到时间
                String userid = ""; //签到会员ID
                if(courseMap != null && courseMap.containsKey(course.getId())){
                    WhgTraEnrolCourse wec = courseMap.get(course.getId());
                    sign = wec.getSign()+"";
                    signtime = sdf.format(wec.getSigntime());
                    userid = wec.getUserid();
                }
                cmap.put("sign", sign);
                cmap.put("signtime", signtime);
                cmap.put("userid", userid);


                list.add(cmap);
            }
        }

        return list;
    }

    private String checkEmpty(String str){
        if(StringUtils.isEmpty(str) || str.trim().length() == 0){
            return null;
        }
        return str;
    }
}
