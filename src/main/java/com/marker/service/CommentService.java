package com.marker.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.marker.entity.Comment;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author YSZ
 * @since 2023-05-15
 */
public interface CommentService extends IService<Comment> {

    List<Comment> findCommentDetail(Integer articleId);
}
