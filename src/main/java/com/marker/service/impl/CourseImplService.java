package com.marker.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.marker.entity.Course;
import com.marker.mapper.CourseMapper;
import com.marker.service.CourseService;
import org.springframework.stereotype.Service;

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

}
