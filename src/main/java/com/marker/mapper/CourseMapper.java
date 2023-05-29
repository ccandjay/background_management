package com.marker.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.marker.entity.Course;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author YSZ
 * @since 2023-05-15
 */
public interface CourseMapper extends BaseMapper<Course> {
    Page<Course> findPage(Page<Course> page, @Param("name") String name);


    void setStudentCourse(@Param("courseId")Integer courseId,Integer studentId);

    void deleteStudentCourse(Integer courseId, Integer studentId);
}
