package com.marker.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.marker.common.Result;
import com.marker.entity.Course;
import com.marker.mapper.CourseMapper;
import com.marker.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author YSZ
 * @since 2023-05-15
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseMapper courseMapper;


    @PostMapping
    public Result saveOrUpdate(@RequestBody Course course) {
        courseService.saveOrUpdate(course);
        return Result.success();
    }

    @PostMapping("/studentCourse/{courseId}/{studentId}")
    public Result studentCourse(@PathVariable Integer courseId, @PathVariable Integer studentId) {
        courseService.deleteStudentCourse(courseId, studentId);
        courseService.setStudentCourse(courseId, studentId);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        courseService.removeById(id);
        return Result.success();
    }


    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        courseService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        List<Course> sysMenus = courseService.list();
        return Result.success(sysMenus);
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(courseService.getById(id));
    }


    @GetMapping("/page")
    public Result page(@RequestParam String name,
                       @RequestParam Integer pageNum,
                       @RequestParam Integer pageSize) {
        return Result.success(courseMapper.findPage(new Page<Course>(pageNum, pageSize), name));
    }

}

