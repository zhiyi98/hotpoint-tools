package com.ruoyi.system.controller;

import com.alibaba.nacos.shaded.com.google.gson.Gson;
import com.ruoyi.common.core.utils.ShellCommandExecutor;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.domain.HotPoint;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * zhiyi98
 */
@RestController
@RequestMapping("/shell")
public class ShellController extends BaseController {

    @PostMapping("/execute")
    public AjaxResult execute(@RequestParam("command") String command) {
        return success(ShellCommandExecutor.execute(command));
    }

    @PostMapping("/executeAndParse")
    public AjaxResult execute(@RequestParam("command") String command,
                          @RequestParam("title") String title,
                          @RequestParam("link") String link) {
        List<HotPoint> result;
        try {
            String response = ShellCommandExecutor.execute(command);
            Pattern titlePattern = Pattern.compile(title);
            result = new ArrayList<>();
            // parse title
            Matcher titleMatcher = titlePattern.matcher(response);
            List<String> titles = new ArrayList<>();
            while (titleMatcher.find()) {
                titles.add(titleMatcher.group(1));
            }
            // parse link
            Pattern linkPattern = Pattern.compile(link);
            Matcher linkMatcher = linkPattern.matcher(response);
            List<String> links = new ArrayList<>();
            while (linkMatcher.find()) {
                links.add(linkMatcher.group(1));
            }
            if (titles.size() == links.size()) {
                for (int i = 0; i < titles.size(); i++) {
                    HotPoint hotPoint = new HotPoint(titles.get(i), links.get(i));
                    result.add(hotPoint);
                }
            }
        } catch (Exception e) {
            return error(e.getMessage());
        }
        Gson gson = new Gson();
        return success(gson.toJson(result));
    }

}
