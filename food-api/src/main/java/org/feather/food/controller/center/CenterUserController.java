package org.feather.food.controller.center;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.feather.food.bo.center.CenterUserBO;
import org.feather.food.common.utils.CookieUtils;
import org.feather.food.common.utils.DateUtil;
import org.feather.food.common.utils.JSONResult;
import org.feather.food.common.utils.JsonUtils;
import org.feather.food.controller.BaseController;
import org.feather.food.pojo.Users;
import org.feather.food.resource.FileUpload;
import org.feather.food.service.center.CenterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @projectName: food
 * @package: org.feather.food.controller.center
 * @className: CenterUserController
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2022-10-29 7:40
 * @version: 1.0
 */
@Api(value = "用户信息接口", tags = {"用户信息接口"})
@RequestMapping("/userInfo")
@RestController
public class CenterUserController extends BaseController {


    @Autowired
    private CenterUserService centerUserService;

    @Autowired
    private FileUpload fileUpload;


    @ApiOperation(value = "修改用户头像", notes = "修改用户头像", httpMethod = "POST")
    @PostMapping("/uploadFace")
    public JSONResult uploadFace (
            @ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
            @ApiParam(name = "file", value = "用户头像", required = true) MultipartFile file,
            HttpServletRequest request, HttpServletResponse response) {
        //头像保存的地址
      //  String fileSpace = IMAGE_USER_FACE_LOCATION;
        String fileSpace= fileUpload.getImageUserFaceLocation();
        //在路径下为每一个用户id用于区分不用用户上传
        String uploadPathPrefix = File.separator + userId;
        FileOutputStream fileOutputStream=null;
        if (file != null) {
            try {
                //获得文件上穿的名称
                String originalFilename = file.getOriginalFilename();
                if (StringUtils.isNotBlank(originalFilename)) {
                    //文件重命名   face.png---->["face",""png]
                    String[] fileNameArr = originalFilename.split("\\.");
                    //获取文件的后缀名
                    String suffix = fileNameArr[fileNameArr.length - 1];
                    if (!suffix.equalsIgnoreCase("png")&&
                    !suffix.equalsIgnoreCase("jpg")&&
                    !suffix.equalsIgnoreCase("jpeg")){
                        JSONResult.errorMsg("请上传正确的图片格式!");
                    }
                    //face-[userId].png
                    //文件名称重组  覆盖式
                    String newFileName = "face-" + userId + suffix;
                    //上传的头像最终保存位置
                    String fileFacePath=   fileSpace+uploadPathPrefix+File.separator+newFileName;
                    //用于提供给web服务器访问的地址
                    uploadPathPrefix+=("/"+newFileName);
                    File outFile= new File(fileFacePath);
                    if (outFile.getParentFile()!=null){
                        //创建文件夹
                        outFile.getParentFile().mkdir();
                    }
                    //文件输出保存到目录
                     fileOutputStream=new FileOutputStream(outFile);
                    InputStream inputStream=file.getInputStream();
                    IOUtils.copy(inputStream,fileOutputStream);

                }
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                if (fileOutputStream !=null){
                    try {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }

        } else {
            return JSONResult.errorMsg("文件不能为空!");
        }

        //获取图片服务地址
        String imageServerUrl = fileUpload.getImageServerUrl();
        //由于浏览器可能存在缓存，所以需要加上时间戳来保证更新后的图片可以及时刷新
        String finalUserFaceUrl = imageServerUrl + uploadPathPrefix
                + "?t=" + DateUtil.getCurrentDateString(DateUtil.DATE_PATTERN);
        //更新用户头像到数据库
        Users users = centerUserService.updateUserFace(userId, finalUserFaceUrl);
        users = setNullProperty(users);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(users), true);
        //TODO  后续要改，增加token令牌，会整合redis，分布式会话
        return JSONResult.ok(users);
    }

    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "POST")
    @PostMapping("/update")
    public JSONResult update(
            @ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
            @RequestBody @Valid CenterUserBO centerUserBO, BindingResult bindingResult,
            HttpServletRequest request, HttpServletResponse response) {
        // 判断BindingResult是否保存错误的验证信息，如果有，则直接return
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = getErrors(bindingResult);
            return JSONResult.errorMap(errorMap);
        }
        Users users = centerUserService.updateUserInfo(userId, centerUserBO);
        users = setNullProperty(users);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(users), true);
        //TODO  后续要改，增加token令牌，会整合redis，分布式会话
        return JSONResult.ok(users);
    }

    private Map<String, String> getErrors(BindingResult result) {
        Map<String, String> map = new HashMap<>();
        List<FieldError> errorList = result.getFieldErrors();
        for (FieldError error : errorList) {
            // 发生验证错误所对应的某一个属性
            String errorField = error.getField();
            // 验证错误的信息
            String errorMsg = error.getDefaultMessage();

            map.put(errorField, errorMsg);
        }
        return map;
    }

    private Users setNullProperty(Users users) {
        users.setPassword(null);
        users.setMobile(null);
        users.setEmail(null);
        users.setCreatedTime(null);
        users.setUpdatedTime(null);
        users.setBirthday(null);
        return users;
    }
}
