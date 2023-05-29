package com.marker.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.marker.entity.Course;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YSZ
 * @since 2023-05-15
 */
public interface CourseService extends IService<Course> {

    void setStudentCourse(Integer courseId, Integer studentId);

    void deleteStudentCourse(Integer courseId, Integer studentId);
}
