package com.marker.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.marker.entity.TComment;
import com.marker.mapper.TCommentMapper;
import com.marker.service.TCommentService;
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
public class TCommentImplService extends ServiceImpl<TCommentMapper, TComment> implements TCommentService {

}
