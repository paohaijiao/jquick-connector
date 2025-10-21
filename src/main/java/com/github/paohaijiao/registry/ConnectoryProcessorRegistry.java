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
package com.github.paohaijiao.registry;

import com.github.paohaijiao.field.Processor;
import com.github.paohaijiao.field.FieldProcessor;
import com.github.paohaijiao.field.JsonPathProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * packageName com.github.paohaijiao.registry
 *
 * @author Martin
 * @version 1.0.0
 * @since 2025/10/21
 */
public class ConnectoryProcessorRegistry {
    private static final Map<String, Processor> processors = new HashMap<>();

    static {
        registerProcessor("field", new FieldProcessor());
        registerProcessor("jsonPath", new JsonPathProcessor());
    }

    public static void registerProcessor(String name, Processor processor) {
        processors.put(name.toLowerCase(), processor);
    }

    public static Processor getProcessor(String name) {
        return processors.get(name.toLowerCase());
    }

}
