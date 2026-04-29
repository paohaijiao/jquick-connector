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

import com.github.paohaijiao.enums.JQuickConnectorCategory;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * packageName com.github.paohaijiao.meta
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025/10/25
 */
@Getter
public class JQuickConnectorType {

    private final String code;

    private final String name;

    private final JQuickConnectorCategory category;

    private final List<String> aliases;

    private final List<String> dependencies;

    private final JQuickConnectorTypeMetadata metadata;

    public JQuickConnectorType(String code, String name, JQuickConnectorCategory category, List<String> aliases, List<String> dependencies, JQuickConnectorTypeMetadata metadata) {
        this.code = code;
        this.name = name;
        this.category = category;
        this.aliases = Collections.unmodifiableList(new ArrayList<>(aliases));
        this.dependencies = Collections.unmodifiableList(new ArrayList<>(dependencies));
        this.metadata = metadata;
    }


}
