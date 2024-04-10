package com.bezkoder.springjwt.Service.Impl;

import com.bezkoder.springjwt.Service.SizeService;
import com.bezkoder.springjwt.entities.ESize;
import com.bezkoder.springjwt.entities.Size;
import com.bezkoder.springjwt.repository.SizeRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SizeserviceImpl implements SizeService {
    @Autowired
    SizeRepository sizeRepository;

    @Override
    public Optional<Size> findBySizeName(ESize sizename) {
        return sizeRepository.findBySizeName(sizename);
    }


}
