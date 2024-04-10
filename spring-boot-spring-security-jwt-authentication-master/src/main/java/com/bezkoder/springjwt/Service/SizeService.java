package com.bezkoder.springjwt.Service;

import com.bezkoder.springjwt.entities.ESize;
import com.bezkoder.springjwt.entities.Size;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public interface SizeService {
    Optional<Size> findBySizeName(ESize sizename);

}
