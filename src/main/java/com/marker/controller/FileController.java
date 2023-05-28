package com.marker.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.marker.common.Result;
import com.marker.entity.SysFile;
import com.marker.mapper.SysFileMapper;
import com.marker.service.SysFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 文件上传
 */
@RestController
@RequestMapping("/files")
@Slf4j
public class FileController {

    @Value("${files.upload.path}")
    private String path;

    @Autowired
    private SysFileMapper fileMapper;

    @Autowired
    private SysFileService fileService;

    /**
     * @param file 前端传过来的文件对象
     * @return
     */
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        // 文件原始名称
        String fileName = file.getOriginalFilename();
        // 获取文件类型
        String type = FileUtil.extName(fileName);
        // 获取文件大小
        long size = file.getSize();
        File uploadFile = new File(path);
        // 判断配置文件目录是否存在,不存在就创建一个新的文件目录
        if (!uploadFile.exists()) {
            uploadFile.mkdirs();
        }

        // 定义一个唯一文件码
        String uuid = IdUtil.fastSimpleUUID();
        String fileUUID = uuid + "." + type;
        File newFile = new File(path + fileUUID);
        // 上传文件到磁盘

        // 获取文件的md5
        String md5 = SecureUtil.md5(file.getInputStream());
        SysFile sysFile1 = getFileByMd5(md5);
        if (sysFile1 != null) {
            fileUUID = sysFile1.getUrl();
            // 删除已存在的文件
            newFile.delete();
        } else {
            try {
                file.transferTo(newFile);
            } catch (IOException e) {
                log.error("文件存储失败");
                throw new RuntimeException(e);
            }
        }

        // 将路径存储到数据库
        SysFile sysFile = new SysFile();
        sysFile.setName(fileName);
        sysFile.setType(type);
        sysFile.setSize(size / 1024); // 从 b  转到 kb
        sysFile.setMd5(md5);
        sysFile.setUrl(fileUUID);
        fileMapper.insert(sysFile);
        return fileUUID;
    }

    /**
     * 文件下载接口
     *
     * @param url      文件的url
     * @param response 响应
     * @throws IOException 文件异常
     */
    @GetMapping("/{url}")
    public void download(@PathVariable String url, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码获取文件
        File file = new File(path + url);
        ServletOutputStream outputStream = response.getOutputStream();
        // 设置响应头、以附件形式打开文件
        response.setHeader("content-disposition", "attachment; fileName=" + URLEncoder.encode(url, "UTF-8"));
        response.setContentType("application/octet-stream");
        // 读取上传文件的字节流
        outputStream.write(FileUtil.readBytes(file));
        outputStream.flush();
        outputStream.close();
    }


    /**
     * 根据MD5查询文件
     *
     * @param md5 文件的md5
     * @return
     */
    public SysFile getFileByMd5(String md5) {
        // 查询该md5的文件是否存在
        LambdaQueryWrapper<SysFile> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SysFile::getMd5, md5);
        return fileMapper.selectOne(lqw);
    }

    @PostMapping("/update")
    public Result saveOrUpdate(@RequestBody SysFile file) {
        return Result.success(fileService.updateById(file));
    }


    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name
    ) {
        LambdaQueryWrapper<SysFile> lqw = new LambdaQueryWrapper<>();
        lqw.orderByDesc(SysFile::getId);
        lqw.eq(SysFile::getIsDelete, false);
        if (!"".equals(name)) {
            lqw.like(SysFile::getName, name);
        }
        IPage<SysFile> page = new Page<>(pageNum, pageSize);
        return Result.success(fileService.page(page, lqw));
    }


    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        SysFile byId = fileService.getById(id);
        byId.setIsDelete(true);
        return Result.success(fileService.updateById(byId));
    }

    @PostMapping("/dels")
    public Result deletes(@RequestBody List<Integer> ids) {
        LambdaQueryWrapper<SysFile> lqw = new LambdaQueryWrapper<>();
        lqw.in(SysFile::getId, ids);
        List<SysFile> list = fileService.list(lqw);
        for (SysFile f : list) {
            f.setIsDelete(true);
        }
        return Result.success(fileService.saveOrUpdateBatch(list));
    }

    // 彻底删除,数据库数据和磁盘文件都删除
    @PostMapping("/complete/del")
    public Result completeDel(@RequestBody SysFile sfile) {
        Integer id = sfile.getId();
        String md5 = sfile.getMd5();
        LambdaQueryWrapper<SysFile> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SysFile::getMd5, md5);
        List<SysFile> list = fileService.list(lqw);
        if (list.size() > 1) {
            boolean b = fileService.removeById(id);
            return b ? Result.success("数据库信息删除成功") : Result.success("数据库信息删除失败");
        } else {
            SysFile sysFile = list.get(0);
            fileService.removeById(id);
            File file = new File(path + sysFile.getUrl());
            boolean delete = file.delete();
            return delete ? Result.success("磁盘文件删除成功") : Result.success("磁盘文件删除失败");
        }

    }

}
