package com.how2java.tmall.controller;
 

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.util.ImageUtil;
import com.how2java.tmall.util.Page;
import com.how2java.tmall.util.UploadedImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
 
@Controller
@RequestMapping("")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
  
    @RequestMapping("admin_category_list")
    public String list(Model model, Page page){
        //page对象的创建：在springmvc 框架里，只要方法参数有它，就会自动创建对象。 如果浏览器没有传递任何参数过来，那么这个对象就都是默认值。
//        List<Category> cs= categoryService.list(page);
//        int total = categoryService.total();
//        page.setTotal(total);
//        model.addAttribute("cs", cs);
//        model.addAttribute("page", page);
//        return "admin/listCategory";

        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Category> cs= categoryService.list();
        int total = (int)new PageInfo<>(cs).getTotal();
        page.setTotal(total);
        model.addAttribute("cs", cs);
        model.addAttribute("page", page);
        return "admin/listCategory";
    }

    @RequestMapping("admin_category_add")
    public String add(Category c, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException{
        categoryService.add(c);
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));

        File file = new File(imageFolder, c.getId() + ".jpg");

        if ( !file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }

        uploadedImageFile.getImage().transferTo(file);

        BufferedImage img = ImageUtil.change2jpg(file);

        ImageIO.write(img, "jpg", file);

        return "redirect:/admin_category_list";

    }

    /**
     * 分类表（category）是主表，则属性表（property）和产品表(product)是从表。
     产品表(product)是主表，则产品图片表(productimage) 和 属性值表(propertyValue)是从表。

     要想删除分类，则需要先①删除产品图片，再②删除属性值。接着才能③删除产品。
     再④删除属性，最后才能⑤删除分类。

     * @param id
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("admin_category_delete")
    public String delete(int id, HttpSession session) throws Exception{
        categoryService.delete(id);

        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, id+".jpg");
        file.delete();

        return "redirect:/admin_category_list";
    }

    @RequestMapping("admin_category_edit")
    public String edit(int id, Model model) throws IOException{
        Category c = categoryService.get(id);
        model.addAttribute("c", c);
        return "admin/editCategory";
    }

    @RequestMapping("admin_category_update")
    public String update(Category c, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException{
        categoryService.update(c);
        MultipartFile image = uploadedImageFile.getImage();
        if(null!=image &&!image.isEmpty()){
            File  imageFolder= new File(session.getServletContext().getRealPath("img/category"));
            File file = new File(imageFolder,c.getId()+".jpg");

            file.delete();
            image.transferTo(file);

            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        }
        return "redirect:/admin_category_list";
    }

}