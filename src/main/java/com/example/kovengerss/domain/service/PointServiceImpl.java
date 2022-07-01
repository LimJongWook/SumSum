package com.example.kovengerss.domain.service;

import com.example.kovengerss.domain.dao.PointDAO;
import com.example.kovengerss.domain.vo.PointVO;
import com.example.kovengerss.domain.vo.UserVO;
import com.example.kovengerss.mapper.PointMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Qualifier("point") @Primary
public class PointServiceImpl implements PointService {
    private final PointDAO pointDAO;

    @Override
    public void pointInsert(Integer pointPrice) {

    }

    @Override
    public void pointSelect(PointVO pointVO) {

    }

    @Override
    public void pointGetPrice(PointVO pointVO) {

    }

    @Override
    public void pointGetUsePoint(PointVO pointVO) {

    }

    @Override
    public void pointGetRemainPoint(PointVO pointVO) {

    }

    @Override
    public void pointGetDate(PointVO pointVO) {

    }

    @Override
    public void pointGetWay(PointVO pointVO) {

    }

    @Override
    public void pointSuccess(Map<String, Object> map) {
        Integer userNum = (Integer) map.get("userNum");
        int point = (int) map.get("point");

        boolean result = pointDAO.updateUserPoint(userNum, point);

        map.put("res", result);

    }
}
