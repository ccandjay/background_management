package com.marker.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.marker.entity.Building;
import com.marker.mapper.BuildingMapper;
import com.marker.service.BuildingService;
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
public class BuildingImplService extends ServiceImpl<BuildingMapper, Building> implements BuildingService {

}
