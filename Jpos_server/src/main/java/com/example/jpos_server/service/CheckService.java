package com.example.jpos_server.service;

import com.example.jpos_server.config.error.ErrorCode;
import com.example.jpos_server.config.error.exceptions.NotMatchingUserAndRequestException;
import com.example.jpos_server.config.error.exceptions.SeatNotExistInStoreException;
import com.example.jpos_server.config.security.jwt.JwtUtils;
import com.example.jpos_server.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@RequiredArgsConstructor
@Transactional
@Service
public class CheckService {
    private final SeatRepository seatRepository;
    private final PosOrderRepository posOrderRepository;
    private final EndPosOrderRepository endPosOrderRepository;
    private final UserAccountRepository userAccountRepository;
    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;
    private final JwtUtils jwtUtils;

    public void checkValidUserForRequest(String token, Long anyId, int whatId) {
        Long storeId = switch (whatId) {
            case 0 -> anyId;
            case 1 -> seatRepository.findById(anyId).get().getStore().getId();
            case 2 -> posOrderRepository.findById(anyId).get().getStore().getId();
            case 3 -> endPosOrderRepository.findById(anyId).get().getStore().getId();
            case 4 -> menuRepository.findById(anyId).get().getStore().getId();
            case 5 -> categoryRepository.findById(anyId).get().getStore().getId();
            default -> 0L;
        };

        String jwt = token.substring(7);
        String loginId = jwtUtils.getUserNameFromJwtToken(jwt);
        if (!Objects.equals(userAccountRepository.findByLoginId(loginId).get().getStore().getId(), storeId)) {
            throw new NotMatchingUserAndRequestException("아이디와 요청 매칭 안됨.", ErrorCode.NOT_MATCH_USER_AND_REQUEST);
        }
    }

    @Transactional(readOnly = true)
    public void checkCorrectSeat(Long storeId, Long seatId) {
        if (seatRepository.existsById(seatId)) {
            if (!seatRepository.findById(seatId).get().getStore().getId().equals(storeId))
                throw new SeatNotExistInStoreException("잘못된 주소", ErrorCode.SEAT_NOT_EXIST_IN_STORE);
        } else {
            throw new SeatNotExistInStoreException("잘못된 주소", ErrorCode.SEAT_NOT_EXIST_IN_STORE);
        }
    }

}
