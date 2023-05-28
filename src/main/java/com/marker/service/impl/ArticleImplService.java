package com.marker.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.marker.entity.Article;
import com.marker.mapper.ArticleMapper;
import com.marker.service.ArticleService;
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
public class ArticleImplService extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

}
