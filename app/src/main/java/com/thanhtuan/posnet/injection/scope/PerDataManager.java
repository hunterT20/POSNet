package com.thanhtuan.posnet.injection.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Đây là 1 custom scope, chức năng chính là khởi tạo cho đối tượng được gắn
 * trong thời gian chạy (RUNTIME)
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerDataManager {
}