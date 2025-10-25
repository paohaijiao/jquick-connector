/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright (c) [2025-2099] Martin (goudingcheng@gmail.com)
 */
package com.github.paohaijiao.meta;

import com.github.paohaijiao.handler.ConnectorHandler;
import lombok.Getter;

import java.util.Objects;

/**
 * packageName com.github.paohaijiao.meta
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025/10/25
 */
@Getter
public class ConnectorMetadata {

    private final String type;

    private final ConnectorHandler handler;

    private final long registrationTime;

    private volatile int usageCount;

    private volatile boolean active;

    public ConnectorMetadata(String type, ConnectorHandler handler) {
        this.type = Objects.requireNonNull(type, "connector type cannot be null");
        this.handler = Objects.requireNonNull(handler, "connector handler cannot be null");
        this.registrationTime = System.currentTimeMillis();
        this.usageCount = 0;
        this.active = true;
    }

    public void incrementUsage() {
        usageCount++;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
