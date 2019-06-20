package soft.demo.controller;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import soft.demo.exception.MessageException;
import soft.demo.pojo.Items;
import soft.demo.pojo.QueryVo;
import soft.demo.service.ItemService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;
import java.util.UUID;

/*
   商品管理
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    //入门程序 第一 包类+类包+方法名
    /*
        1.ModelAndView 无敌的 带着数据 返回视图路径 不建议使用
        2.String 返回视图路径 model带数据 官方推荐此种方法 解耦 数据 视图 分离 MVC 建议使用
        3.void Ajax 请求 合适 json格式数据（response 异步请求使用
     */
    /*@RequestMapping(value = "/soft/itemlist.action")
    public ModelAndView itemlist(){
        //从mysql中查询
        List<Items> list = itemService.selectIteList();

        //数据
        ModelAndView mav = new ModelAndView();
        mav.addObject("itemList",list);
        mav.setViewName("itemList");
        return mav;
    }*/
    @RequestMapping(value = "/soft/itemlist.action")
    public String itemlist(Model model) throws MessageException {
        //Integer i = 1/0;
        //从mysql中查询
        List<Items> list = itemService.selectIteList();

        /*if (null == null){
            throw new MessageException("商品信息不能为空");
        }*/

        //数据
        model.addAttribute("itemList",list);
        return "itemList";
    }
    //去修改界面 入参id
    @RequestMapping(value = "/itemEdit.action")
    public ModelAndView toEdit(Integer id,HttpServletRequest request, HttpServletResponse response,
                               HttpSession session, Model model){
        //Servlet时开发
//        String id = request.getParameter("id");
//        Items items = itemService.selectItemById(Integer.parseInt(id));

        Items items = itemService.selectItemById(id);
        //数据
        ModelAndView mav = new ModelAndView();
        mav.addObject("item",items);
        mav.setViewName("editItem");
        return mav;
    }
    //提交修改界面 入参为 Items对象
    @RequestMapping(value = "/updateitem.action")
//    public ModelAndView updateitem(Items items){
    public String updateitem(QueryVo vo, MultipartFile pictureFile) throws Exception {  //pictureFile必须与name一致

        //保存图片到文件夹
        //重新命名图片名称 防止图片名字重复
        String name = UUID.randomUUID().toString().replaceAll("-","");
        //获取图片名称扩展名 getOriginalFilename()-->原始名
        String extension = FilenameUtils.getExtension(pictureFile.getOriginalFilename());
        //保存
        pictureFile.transferTo(new File("D:\\IDEA-workspace\\upload\\"+name+"."+extension));
        //保存到数据库
        vo.getItems().setPic(name+"."+extension);

        //修改
        itemService.updateItemById(vo.getItems());

        /*ModelAndView mav = new ModelAndView();
        mav.setViewName("success");*/
        return "redirect:/soft/itemlist.action"; //重定向 跳转到商品列表界面
//        return "forward:/soft/itemlist.action"; //内部转发
        //return "redirect:/itemEdit.action?id="+vo.getItems().getId();
    }
    //删除多个
    @RequestMapping(value = "/deletes.action")
    public ModelAndView deletes(Integer[] ids){

        ModelAndView mav = new ModelAndView();
        mav.setViewName("success");
        return mav;
    }
    //修改多个
    @RequestMapping(value = "/updates.action")
    public ModelAndView updates(QueryVo vo){

        ModelAndView mav = new ModelAndView();
        mav.setViewName("success");
        return mav;
    }

    //json数据交互
    @RequestMapping(value = "/json.action")
    //@ResponseBody 发出去  @RequestBody 接收
    public @ResponseBody
    Items json(@RequestBody Items items){

        return items;
    }

    //去登录界面
    @RequestMapping(value = "/login.action",method = RequestMethod.GET)
    public String login(){
        return "login";
    }
    @RequestMapping(value = "/login.action",method = RequestMethod.POST)
    public String login(String username,HttpSession httpSession){
        System.out.println(username);
        if (username != "" || !username.equals("")){
            System.out.println("111");
            httpSession.setAttribute("USER_SESSION",username);
        }
        return "redirect:/soft/itemlist.action";
    }

}
