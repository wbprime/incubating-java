package im.wangbo.bj58.wconfig.core;

import com.google.common.collect.Maps;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Map;

/**
 * TODO add brief description here
 *
 * Copyright © 2016 58ganji Beijing spat team. All rights reserved.
 *
 * @author Elvis Wang [wangbo12 -AT- 58ganji -DOT- com]
 */
public class Util_merge_Test {
    @Test
    public void testMerge_emptyPatch() throws Exception {
        final Map<String, Object> patchMap = Maps.newHashMap();

        // empty base
        {
            final Map<String, Object> baseMap = Maps.newHashMap();

            Util.merge(baseMap, patchMap);
            Assertions.assertThat(baseMap).isEmpty();
        }
        // level 1 map
        {
            final Map<String, Object> baseMap = Maps.newHashMap();
            baseMap.put("key1", "value1");
            baseMap.put("key2", "value2");

            Util.merge(baseMap, patchMap);
            Assertions.assertThat(baseMap)
                    .containsEntry("key1", "value1")
                    .containsEntry("key2", "value2")
                    .hasSize(2);
        }
        // level 2 map
        {
            final Map<String, Object> baseMap = Maps.newHashMap();
            baseMap.put("key1", "value1");
            baseMap.put("key2", "value2");
            {
                final Map<String, Object> map = Maps.newHashMap();
                map.put("key31", "value31");
                map.put("key32", "value32");
                baseMap.put("key3", map);
            }

            Util.merge(baseMap, patchMap);
            Assertions.assertThat(baseMap)
                    .containsEntry("key1", "value1")
                    .containsEntry("key2", "value2")
                    .hasSize(3);
            {
                final Map<String, Object> map = (Map)baseMap.get("key3");
                Assertions.assertThat(map).isNotNull()
                        .containsEntry("key31", "value31")
                        .containsEntry("key32", "value32")
                        .hasSize(2);
            }
        }
    }

    @Test
    public void testMerge_level1SamePatch() throws Exception {
        final Map<String, Object> patchMap = Maps.newHashMap();
        patchMap.put("key1", "patch1");
        patchMap.put("patch_key1", "patch2");

        // empty base
        {
            final Map<String, Object> baseMap = Maps.newHashMap();

            Util.merge(baseMap, patchMap);
            Assertions.assertThat(baseMap)
                    .containsEntry("key1", "patch1")
                    .containsEntry("patch_key1", "patch2")
                    .hasSize(2);
        }
        // level 1 map
        {
            final Map<String, Object> baseMap = Maps.newHashMap();
            baseMap.put("key1", "value1");
            baseMap.put("key2", "value2");

            Util.merge(baseMap, patchMap);
            Assertions.assertThat(baseMap)
                    .containsEntry("key1", "patch1")
                    .containsEntry("key2", "value2")
                    .containsEntry("patch_key1", "patch2")
                    .hasSize(3);
        }
        // level 2 map
        {
            final Map<String, Object> baseMap = Maps.newHashMap();
            baseMap.put("key1", "value1");
            baseMap.put("key2", "value2");
            {
                final Map<String, Object> map = Maps.newHashMap();
                map.put("key31", "value31");
                map.put("key32", "value32");
                baseMap.put("key3", map);
            }

            Util.merge(baseMap, patchMap);
            Assertions.assertThat(baseMap)
                    .containsEntry("key1", "patch1")
                    .containsEntry("key2", "value2")
                    .containsEntry("patch_key1", "patch2")
                    .hasSize(4);
            {
                final Map<String, Object> map = (Map)baseMap.get("key3");
                Assertions.assertThat(map).isNotNull()
                        .containsEntry("key31", "value31")
                        .containsEntry("key32", "value32")
                        .hasSize(2);
            }
        }
    }

    @Test
    public void testMerge_level2SamePatch() throws Exception {
        final Map<String, Object> patchMap = Maps.newHashMap();
        patchMap.put("key1", "patch1");
        patchMap.put("patch_key1", "patch2");
        {
            final Map<String, Object> map = Maps.newHashMap();
            map.put("key21", "patch21");
            map.put("key31", "patch31");
            patchMap.put("key3", map);
        }
        {
            final Map<String, Object> map = Maps.newHashMap();
            map.put("key41", "patch41");
            map.put("key42", "patch42");
            patchMap.put("patch_key2", map);
        }

        // empty base
        {
            final Map<String, Object> baseMap = Maps.newHashMap();

            Util.merge(baseMap, patchMap);
            Assertions.assertThat(baseMap)
                    .containsEntry("key1", "patch1")
                    .containsEntry("patch_key1", "patch2")
                    .hasSize(4);
            {
                final Map<String, Object> map = (Map)baseMap.get("patch_key2");
                Assertions.assertThat(map).isNotNull()
                        .containsEntry("key41", "patch41")
                        .containsEntry("key42", "patch42")
                        .hasSize(2);
            }
            {
                final Map<String, Object> map = (Map)baseMap.get("key3");
                Assertions.assertThat(map).isNotNull()
                        .containsEntry("key31", "patch31")
                        .containsEntry("key21", "patch21")
                        .hasSize(2);
            }
        }
        // level 1 map
        {
            final Map<String, Object> baseMap = Maps.newHashMap();
            baseMap.put("key1", "value1");
            baseMap.put("key2", "value2");

            Util.merge(baseMap, patchMap);
            Assertions.assertThat(baseMap)
                    .containsEntry("key1", "patch1")
                    .containsEntry("key2", "value2")
                    .containsEntry("patch_key1", "patch2")
                    .hasSize(5);
            {
                final Map<String, Object> map = (Map)baseMap.get("patch_key2");
                Assertions.assertThat(map).isNotNull()
                        .containsEntry("key41", "patch41")
                        .containsEntry("key42", "patch42")
                        .hasSize(2);
            }
            {
                final Map<String, Object> map = (Map)baseMap.get("key3");
                Assertions.assertThat(map).isNotNull()
                        .containsEntry("key31", "patch31")
                        .containsEntry("key21", "patch21")
                        .hasSize(2);
            }
        }
        // level 2 map
        {
            final Map<String, Object> baseMap = Maps.newHashMap();
            baseMap.put("key1", "value1");
            baseMap.put("key2", "value2");
            {
                final Map<String, Object> map = Maps.newHashMap();
                map.put("key31", "value31");
                map.put("key32", "value32");
                baseMap.put("key3", map);
            }

            Util.merge(baseMap, patchMap);
            Assertions.assertThat(baseMap)
                    .containsEntry("key1", "patch1")
                    .containsEntry("key2", "value2")
                    .containsEntry("patch_key1", "patch2")
                    .hasSize(5);
            {
                final Map<String, Object> map = (Map)baseMap.get("patch_key2");
                Assertions.assertThat(map).isNotNull()
                        .containsEntry("key41", "patch41")
                        .containsEntry("key42", "patch42")
                        .hasSize(2);
            }
            {
                final Map<String, Object> map = (Map)baseMap.get("key3");
                Assertions.assertThat(map).isNotNull()
                        .containsEntry("key31", "patch31")
                        .containsEntry("key32", "value32")
                        .containsEntry("key21", "patch21")
                        .hasSize(3);
            }
        }
    }

    @Test
    public void testMerge_patchMergedIntoBase() throws Exception {
        final Map<String, Object> patchMap = Maps.newHashMap();
        patchMap.put("key1", "patch1");
        patchMap.put("patch_key1", "patch2");

        final Map<String, Object> baseMap = Maps.newHashMap();
        {
            final Map<String, Object> map = Maps.newHashMap();
            map.put("key11", "value11");
            map.put("key12", "value12");
            baseMap.put("key1", map);
        }
        baseMap.put("key2", "value2");

        Util.merge(baseMap, patchMap);
        Assertions.assertThat(baseMap)
                .containsEntry("key2", "value2")
                .containsEntry("patch_key1", "patch2")
                .hasSize(3);
        {
            final Map<String, Object> map = (Map)baseMap.get("key1");
            Assertions.assertThat(map).isNotNull()
                    .containsEntry("key11", "value11")
                    .containsEntry("key12", "value12")
                    .containsEntry("", "patch1")
                    .hasSize(3);
        }
    }

    @Test
    public void testMerge_baseIncludePatch() throws Exception {
        final Map<String, Object> baseMap = Maps.newHashMap();
        baseMap.put("key1", "patch1");
        baseMap.put("patch_key1", "patch2");

        final Map<String, Object> patchMap = Maps.newHashMap();
        {
            final Map<String, Object> map = Maps.newHashMap();
            map.put("key11", "value11");
            map.put("key12", "value12");
            patchMap.put("key1", map);
        }
        patchMap.put("key2", "value2");

        Util.merge(baseMap, patchMap);
        Assertions.assertThat(baseMap)
                .containsEntry("key2", "value2")
                .containsEntry("patch_key1", "patch2")
                .hasSize(3);
        {
            final Map<String, Object> map = (Map)patchMap.get("key1");
            Assertions.assertThat(map).isNotNull()
                    .containsEntry("key11", "value11")
                    .containsEntry("key12", "value12")
                    .hasSize(2);
        }
    }
}