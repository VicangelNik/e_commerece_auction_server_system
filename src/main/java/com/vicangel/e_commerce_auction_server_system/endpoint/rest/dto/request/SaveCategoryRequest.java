package com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SaveCategoryRequest(@NotBlank String name,
                                  String description) {
}
