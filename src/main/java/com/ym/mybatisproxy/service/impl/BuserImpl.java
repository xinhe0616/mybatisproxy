package com.ym.mybatisproxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ym.mybatisproxy.domain.Buser;
import com.ym.mybatisproxy.mapper.BuserMapper;
import com.ym.mybatisproxy.service.BuserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BuserImpl extends ServiceImpl<BuserMapper, Buser> implements BuserService {
}
