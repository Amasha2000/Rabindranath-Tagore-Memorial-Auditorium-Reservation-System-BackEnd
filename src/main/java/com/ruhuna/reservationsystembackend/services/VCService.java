package com.ruhuna.reservationsystembackend.services;

import com.ruhuna.reservationsystembackend.dto.VCDto;
import com.ruhuna.reservationsystembackend.entity.VC;

public interface VCService {
    void vcSignUp(VCDto vcDto);
    VC findByVCUsername(String username);
}
