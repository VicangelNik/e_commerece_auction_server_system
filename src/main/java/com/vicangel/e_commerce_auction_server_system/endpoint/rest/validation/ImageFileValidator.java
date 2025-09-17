package com.vicangel.e_commerce_auction_server_system.endpoint.rest.validation;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Nonnull;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

final class ImageFileValidator implements ConstraintValidator<ValidImageFile, MultipartFile> {

  @Override
  public void initialize(ValidImageFile constraintAnnotation) {
    ConstraintValidator.super.initialize(constraintAnnotation);
  }

  @Override
  public boolean isValid(@Nonnull MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {

    final String contentType = file.getContentType();

    if (contentType == null) return false;

    return contentType.equals(MediaType.IMAGE_JPEG_VALUE)
           || contentType.equals(MediaType.IMAGE_PNG_VALUE)
           || contentType.equals("image/jpg");
  }
}