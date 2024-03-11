package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.entities.ESize;
import com.bezkoder.springjwt.entities.Size;

import java.util.Optional;

public interface SizeService {
    Optional<Size> findBySizeName(ESize sizename);
}
