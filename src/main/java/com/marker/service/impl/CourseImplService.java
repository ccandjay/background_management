package com.marker.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.marker.entity.Course;
import com.marker.mapper.CourseMapper;
import com.marker.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YSZ
 * @since 2023-05-15
 */
@Service
public class CourseImplService extends ServiceImpl<CourseMapper, Course> implements CourseService {


    @Resource
    private CourseMapper courseMapper;

    @Override
    public void setStudentCourse(Integer courseId, Integer studentId) {
        courseMapper.setStudentCourse(courseId,studentId);
    }

    @Override
    public void deleteStudentCourse(Integer courseId, Integer studentId) {
        courseMapper.deleteStudentCourse(courseId,studentId);
    }
}
