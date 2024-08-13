package com.semicolon.EaziRent.utils;

import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import com.semicolon.EaziRent.exceptions.UploadMediaFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
public class ServicesUtils {

    private ServicesUtils() {}

    public static void copyNonNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullAttributes(source));
    }

    private static String[] getNullAttributes(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        Set<String> nullAttributes = new HashSet<>();
        for(java.beans.PropertyDescriptor descriptor : src.getPropertyDescriptors()) {
            Object value = src.getPropertyValue(descriptor.getName());
            if(value == null) nullAttributes.add(descriptor.getName());
        }
        String[] result = new String[nullAttributes.size()];
        return nullAttributes.toArray(result);
    }

    public static String getMediaUrl(MultipartFile mediaFile, Uploader uploader) {
        log.info("Trying to upload image to Cloudinary");
        try {
            Map<?, ?> map = ObjectUtils.asMap(
             "resource_type", "image",
                    "use_filename", true
            );
            Map<?, ?> uploadResponse = uploader.upload(mediaFile.getBytes(), map);
            log.info("Image uploaded successfully");
            return uploadResponse.get("url").toString();
        } catch (Exception exception) {
            log.error("Error while uploading image to Cloudinary", exception);
            throw new UploadMediaFailedException("a: "+exception.getMessage());
        }
    }

}
