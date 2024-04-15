package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.dto.SizeRequestDTO;
import com.bezkoder.springjwt.entities.ESize;
import com.bezkoder.springjwt.entities.Size;
import com.fasterxml.jackson.datatype.jdk8.OptionalSerializer;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public interface SizeService {
    Optional<Size> findBySizeName(ESize sizename);

    boolean saveSize (SizeRequestDTO sizeRequestDTO);

    List<Size> findByIdProduct (long product);

    Optional<Size> findByIdSize (long size);
}
