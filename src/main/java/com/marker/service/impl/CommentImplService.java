package com.marker.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.marker.entity.Comment;
import com.marker.mapper.CommentMapper;
import com.marker.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author YSZ
 * @since 2023-05-15
 */
@Service
public class CommentImplService extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Override
    public List<Comment> findCommentDetail(Integer articleId) {
        return commentMapper.findCommentDetail(articleId);
    }
}
