package com.musinsa.coordination.brand.service;

import com.musinsa.coordination.brand.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BrandService {

    private final BrandRepository brandRepository;

}
