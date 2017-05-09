package com.creatoo.hn.actions.api.zx;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文章点赞接口
 * Created by wangxl on 2017/4/12.
 */
@RestController
@RequestMapping("/api/whzx")
public class APIWhzxAction {
    /**
     * 日志控制器
     */
    Logger log = Logger.getLogger(this.getClass().getName());
}
