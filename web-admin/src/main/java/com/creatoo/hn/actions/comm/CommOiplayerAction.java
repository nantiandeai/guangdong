package com.creatoo.hn.actions.comm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by rbg on 2017/4/21.
 */

@Controller
@RequestMapping("/oiplayer")
public class CommOiplayerAction {

    @RequestMapping("/show")
    public Object showOiplayer(ModelMap mmp, String imgUrl, String type, String url){
        mmp.addAttribute("imgUrl", imgUrl);
        mmp.addAttribute("type", type);
        mmp.addAttribute("url", url);
        return "comm/home/oiplayer";
    }
}
